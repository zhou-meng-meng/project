package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.customerPayDetail.AddPayBySystemDTO;
import com.example.project.demos.web.dto.list.SalesReturnInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.salesReturn.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.*;
import com.example.project.demos.web.enums.*;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.DataUtils;
import com.example.project.demos.web.utils.DateUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("salesReturnService")
public class SalesReturnServiceImpl  implements SalesReturnService {

    @Resource
    private SalesReturnDao salesReturnDao;
    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;
    @Autowired
    private SysLogService sysLogService;
    @Resource
    private ApproveOperationFlowDao approveOperationFlowDao;
    @Resource
    private ApproveOperationQueueDao approveOperationQueueDao;
    @Autowired
    private MaterialInventoryService materialInventoryService;
    @Autowired
    private CustomerPayDetailService customerPayDetailService;
    @Autowired
    private SysUserService sysUserService;

    @Resource
    private SalesCustomerPayDao salesCustomerPayDao;

    @Autowired
    private UploadFileInfoService uploadFileInfoService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("销售退回queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            SalesReturnInfo info = salesReturnDao.selectSalesReturnInfoById(id);
            //处理入库方
            List<SalesReturnInfo> list = new ArrayList<>();
            list.add(info);
            list = setSalesReturnObject(list,user);
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售退回queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("销售退回queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        outDTO.setSumCount(new BigDecimal("0"));
        outDTO.setSumAmt(new BigDecimal("0"));
        try {
            //添加权限  总公司审核权限的  查看所有  只有总公司单价权限的 查看自己提交的数据  厂区/仓库人员查看所属厂区/仓库数据
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，查询所有数据");
            }else{
                log.info("当前登录人不属于总公司，只能查看自己所在厂区/仓库的数据");
                dto.setInCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.salesReturnDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<SalesReturnInfo> page = new PageImpl<>(this.salesReturnDao.selectSalesReturnInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<SalesReturnInfo> list = page.toList();
                list = setSalesReturnObject(list,user);
                //出参赋值  集合和合计字段
                outDTO = formatSumObject(list,outDTO);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售退回queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            log.info("查询总公司具有审核权限的人员");
            List<SysUserEntity> userList = sysUserService.queryUserListByRoleType(UserTypeEnums.USER_TYPE_COMPANY.getCode(), RoleAuthorityTypeEnums.ROLE_AUTHORITY_TYPE_AUTH.getCode(),"");
            if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0){
                SalesReturnEntity entity = BeanCopyUtils.copy(dto,SalesReturnEntity.class);
                entity.setApproveState(ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode());
                entity.setCreateBy(user.getUserLogin());
                entity.setCreateTime(date);
                //生成单据号
                entity.setBillNo(getBillNoList(user));
                int i = salesReturnDao.insert(entity);
                log.info("生成审核流水记录");
                ApproveOperationFlowEntity flowEntity = new ApproveOperationFlowEntity(null,entity.getId(),FunctionTypeEnums.SALES_RETURN.getCode(),user.getUserLogin(),date,ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode(),Constants.SYSTEM_CODE);
                approveOperationFlowDao.insert(flowEntity);
                log.info("生成审核队列记录");
                List<ApproveOperationQueueEntity> queueEntityList = new ArrayList<>();
                for(SysUserEntity userEntity : userList){
                    ApproveOperationQueueEntity queueEntity = new ApproveOperationQueueEntity(null,flowEntity.getId(), entity.getId(),FunctionTypeEnums.SALES_RETURN.getCode(),userEntity.getUserLogin(),dto.getCustomerCode(),dto.getMaterialCode(),dto.getReturnCount(),user.getUserLogin(),date,Constants.SYSTEM_CODE);
                    queueEntityList.add(queueEntity);
                }
                log.info("处理增加库存操作");
                i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getReturnCount(),"add",date);
                approveOperationQueueDao.insertBatch(queueEntityList);
                //开始处理附件信息
                uploadFileInfoService.updateByBusinessId(entity.getId(),dto.getFileIdList());
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "退回日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getReturnTime()) +",物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",数量:"+dto.getReturnCount()+",退回人:"+dto.getReturnUserName()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_RETURN.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public EditOutDTO update(EditDTO dto) {
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            //原数据
            SalesReturnEntity entity = salesReturnDao.selectById(dto.getId());
            SalesReturnEntity newEntity = BeanCopyUtils.copy(dto,SalesReturnEntity.class);
            newEntity.setUpdateBy(user.getUserLogin());
            newEntity.setUpdateTime(date);
            int i = salesReturnDao.updateById(newEntity);
            log.info("处理库存操作");
            //修改了数量  要更新库存
            BigDecimal count = entity.getReturnCount();
            log.info("原数量:"+count.toString());
            BigDecimal updateCount = dto.getReturnCount();
            log.info("修改后数量:"+updateCount.toString());
            BigDecimal num = updateCount.subtract(count);
            log.info("修改后数量-原数量:"+num);
            if(num.compareTo(new BigDecimal(0)) > 0){
                log.info("修改数量大于原数量，需要增加:"+num+"的库存");
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), num,"add",date);
            }else if(num.compareTo(new BigDecimal(0)) < 0){
                num  = num.multiply(new BigDecimal(-1));
                log.info("修改数量小于原数量，需要减少:"+num+"的库存");
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), num,"reduce",date);
            }else{
                log.info("修改数量等于原数量，不需要更新库存");
            }
            //开始处理附件信息
            uploadFileInfoService.updateByBusinessId(newEntity.getId(),dto.getFileIdList());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "退回日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getReturnTime()) +",物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",数量:"+dto.getReturnCount()+",退回人:"+dto.getReturnUserName()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_RETURN.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) {
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            //原数据
            SalesReturnEntity entity = salesReturnDao.selectById(dto.getId());
            int i = salesReturnDao.deleteById(dto.getId());
            log.info("删除提交的待审核记录");
            approveOperationFlowDao.deleteByBusinessId(dto.getId());
            approveOperationQueueDao.deleteByBusinessId(dto.getId());
            log.info("删除销售退回的待审核数据，需要减少库存:"+entity.getReturnCount());
            materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getReturnCount(),"reduce",date);
            log.info("开始删除附件信息");
            uploadFileInfoService.deleteFileByBusinessId(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "退回日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getReturnTime()) +",物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",数量:"+dto.getReturnCount()+",退回人:"+dto.getReturnUserName()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_RETURN.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    /**
     * 审核更新
     * @param id
     * @param result
     * @param opinion
     * @param userLogin
     * @param unitPrice
     * @param tollAmount
     * @param date
     * @return
     */
    @Override
    public int updateApprove(Long id, String result, String opinion, String userLogin, BigDecimal unitPrice, BigDecimal tollAmount, Date date,String inCode)  {
        log.info("销售退回审核更新开始");
        SalesReturnEntity entity = salesReturnDao.selectById(id);
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        entity.setApproveUser(userLogin);
        entity.setApproveState(result);
        entity.setApproveOpinion(opinion);
        entity.setApproveTime(date);
        entity.setUnitPrice(unitPrice);
        entity.setTollAmount(tollAmount);
        int i = 0;
        //厂区退回  判断审核结果
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            /*log.info("审核同意，开始更新库存");  销售退回提交和修改时更新库存
            i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getReturnCount(),"add",date);*/
            log.info("隐藏该客户原记录");
            salesCustomerPayDao.updateShowFlagByCustomerCode("1",userLogin,entity.getCustomerCode());
            log.info("生成该客户新记录");
            SalesCustomerPayEntity payEntity = new SalesCustomerPayEntity(null,entity.getId(),entity.getCustomerCode(), entity.getMaterialCode(), entity.getUnitPrice(),entity.getReturnCount(),entity.getTollAmount(),date,FunctionTypeEnums.SALES_RETURN.getCode());
            payEntity.setShowFlag("0");
            payEntity.setCreateTime(date);
            payEntity.setCreateBy(userLogin);
            salesCustomerPayDao.insert(payEntity);
            log.info("生成往来账信息");
            AddPayBySystemDTO dto = new AddPayBySystemDTO(null, entity.getId(), entity.getCustomerCode(), entity.getMaterialCode(), entity.getUnitPrice(),entity.getReturnCount(),entity.getReturnTime(),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),tollAmount,new BigDecimal(0),"1",null,SysEnums.SYS_NO_FLAG.getCode(),entity.getCreateBy(),date,FunctionTypeEnums.SALES_RETURN.getDesc());
            i = customerPayDetailService.addPayBySystem(dto);
        }else{
            log.info("审核拒绝，恢复增加库存");
            i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getReturnCount(),"add",date);
        }
        salesReturnDao.updateById(entity);
        log.info("销售退回审核更新结束");
        return i;
    }

    @Override
    public List<SalesReturnInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("销售退回queryListForExport开始");
        List<SalesReturnInfo> list = new ArrayList<>();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        try {
            //添加权限  总公司审核权限的  查看所有  只有总公司单价权限的 查看自己提交的数据  厂区/仓库人员查看所属厂区/仓库数据
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，查询所有数据");
            }else{
                log.info("当前登录人不属于总公司，只能查看自己所在厂区/仓库的数据");
                dto.setInCode(user.getDeptId());
            }
            list = salesReturnDao.queryListForExport(dto);
            list = setSalesReturnObject(list,user);
            list = formatSumObjectForExport(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_RETURN.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("销售退回queryListForExport结束");
        return list;
    }

    /**
     * 赋值销售退回  入库方名称
     * @param list
     * @return
     */
    private List<SalesReturnInfo> setSalesReturnObject(List<SalesReturnInfo> list,UserLoginOutDTO user){
        boolean isPrice = DataUtils.getIsPrice(user);
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            //获取厂区和仓库集合
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            for(SalesReturnInfo info : list){
                //退货方
                String inCode = info.getInCode();
                if(null != inCode && !"".equals(inCode) && ""!= inCode){
                    if(Constants.FACTORY_CODE_PREFIX.equals(inCode.substring(0,1))){
                        //工厂
                        for(SysFactoryInfo fInfo : factoryInfoList){
                            if(inCode.equals(fInfo.getCode())){
                                info.setInName(fInfo.getName());
                                //销售客户退回打印单据订货地址为退回入库方地址
                                info.setOrderAddress(fInfo.getAddress());
                            }
                        }
                    }else{
                        //仓库
                        for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                            if(inCode.equals(sInfo.getCode())){
                                info.setInName(sInfo.getName());
                                //销售客户退回打印单据订货地址为退回入库方地址
                                info.setOrderAddress(sInfo.getAddress());
                            }
                        }
                    }
                }else {
                    log.info("inCode is null");
                }
                //处理单价和总金额字段
                if(!isPrice){
                    //不具有单价权限   将单价和总金额置为0
                    info.setUnitPrice(new BigDecimal(0));
                    info.setTollAmount(new BigDecimal(0));
                }
            }
        }else{
            log.info("list is null");
        }
        return list;
    }

    /**
     * 格式化单据号
     * @param user
     * @return
     */
    private String getBillNoList(UserLoginOutDTO user){
        String billNo="";
        String prefix = DataUtils.formatBillNoPrefix(user,"进");
        List<String> list = salesReturnDao.queryBillNoListByParam(prefix);
        StringBuffer sb = new StringBuffer();
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            billNo = DataUtils.formatBillNo(list);
        }else{
            sb.append(prefix).append("0001");
            billNo = sb.toString();
        }
        return billNo;
    }

    /**
     * 菜单列表获取数量合计和金额合计
     * @param list
     * @param outDTO
     * @return
     */
    private QueryByPageOutDTO formatSumObject(List<SalesReturnInfo> list, QueryByPageOutDTO outDTO){
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(SalesReturnInfo info: list){
            BigDecimal count = info.getReturnCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
        }
        outDTO.setSalesReturnInfoList(list);
        outDTO.setSumAmt(sumAmt);
        outDTO.setSumCount(sumCount);
        return outDTO;
    }

    /**
     * 导出最后一行增加合计列
     * @param list
     * @return
     */
    private List<SalesReturnInfo> formatSumObjectForExport(List<SalesReturnInfo> list){
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(SalesReturnInfo info: list){
            BigDecimal count = info.getReturnCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
        }
        SalesReturnInfo info = new SalesReturnInfo();
        info.setUnitName("合计:");
        info.setReturnCount(sumCount);
        info.setTollAmount(sumAmt);
        list.add(info);
        return list;
    }
}
