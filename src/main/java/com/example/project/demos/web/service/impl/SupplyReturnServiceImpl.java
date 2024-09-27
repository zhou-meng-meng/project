package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.customerPayDetail.AddPayBySystemDTO;
import com.example.project.demos.web.dto.list.SupplyReturnInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.supplyReturn.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.*;
import com.example.project.demos.web.enums.*;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.DataUtils;
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
@Service("supplyReturnService")
public class SupplyReturnServiceImpl  implements SupplyReturnService {

    @Resource
    private SupplyReturnDao supplyReturnDao;
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
    private SysUserService sysUserService;

    @Autowired
    private CustomerPayDetailService customerPayDetailService;
    @Resource
    private SupplyCustomerPayDao supplyCustomerPayDao;

    @Autowired
    private UploadFileInfoService uploadFileInfoService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("供应商退回queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            SupplyReturnInfo info = supplyReturnDao.selectSupplyReturnInfoById(id);
            //处理入库方
            List<SupplyReturnInfo> list = new ArrayList<>();
            list.add(info);
            list = setSupplyReturnObject(list, user);
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("供应商退回queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("供应商退回queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        outDTO.setSumCount(new BigDecimal("0"));
        outDTO.setSumAmt(new BigDecimal("0"));
        try {
            //添加权限  总公司人员查看所有数据   厂区或者仓库人员查看所属厂区或者仓库提交数据
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可以查看所有数据");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区的数据");
                dto.setInCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.supplyReturnDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<SupplyReturnInfo> page = new PageImpl<>(this.supplyReturnDao.selectSupplyReturnInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<SupplyReturnInfo> list = page.toList();
                list = setSupplyReturnObject(list,user);
                //出参赋值 集合和合计字段
                outDTO = formatSumObject(list,outDTO);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("供应商退回queryByPage结束");
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
                SupplyReturnEntity entity = BeanCopyUtils.copy(dto,SupplyReturnEntity.class);
                //设置 审核状态 创建人和创建时间
                entity.setApproveState(ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode());
                entity.setCreateBy(user.getUserLogin());
                entity.setCreateTime(date);
                //生成单据号
                entity.setBillNo(getBillNoList(user));
                log.info("插入供应商退回表");
                int i = supplyReturnDao.insert(entity);
                log.info("生成审核流水记录");
                ApproveOperationFlowEntity flowEntity = new ApproveOperationFlowEntity(null,entity.getId(), FunctionTypeEnums.SUPPLY_RETURN.getCode(),user.getUserLogin(),date,ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode(),Constants.SYSTEM_CODE);
                approveOperationFlowDao.insert(flowEntity);
                log.info("生成审核队列记录");
                List<ApproveOperationQueueEntity> queueEntityList = new ArrayList<>();
                for(SysUserEntity userEntity : userList){
                    ApproveOperationQueueEntity queueEntity = new ApproveOperationQueueEntity(null,flowEntity.getId(), entity.getId(),FunctionTypeEnums.SUPPLY_RETURN.getCode(),userEntity.getUserLogin(),dto.getCustomerCode(),dto.getMaterialCode(),dto.getReturnCount(),user.getUserLogin(),date,Constants.SYSTEM_CODE);
                    queueEntityList.add(queueEntity);
                }
                approveOperationQueueDao.insertBatch(queueEntityList);
                log.info("供应商退回提交时，减少库存");
                i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), entity.getReturnCount(),"reduce",date);
                //开始处理附件信息
                uploadFileInfoService.updateByBusinessId(entity.getId(),dto.getFileIdList());
            }else{
                errorCode = ErrorCodeEnums.AUTH_USER_NOT_EXIST.getCode();
                errortMsg = ErrorCodeEnums.AUTH_USER_NOT_EXIST.getDesc();
            }
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getReturnCount()+",供货商:"+dto.getCustomerName()+",退回方:"+dto.getOutName()+",退回人:"+dto.getReturnUserName();
        sysLogService.insertSysLog(FunctionTypeEnums.SUPPLY_RETURN.getCode(),OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            SupplyReturnEntity entity = supplyReturnDao.selectById(dto.getId());
            SupplyReturnEntity newEntity = BeanCopyUtils.copy(dto,SupplyReturnEntity.class);
            newEntity.setUpdateBy(user.getUserLogin());
            newEntity.setUpdateTime(date);
            int i = supplyReturnDao.updateById(newEntity);
            //修改了数量  要更新库存
            BigDecimal count = entity.getReturnCount();
            log.info("原数量:"+count.toString());
            BigDecimal updateCount = dto.getReturnCount();
            log.info("修改后数量:"+updateCount.toString());
            BigDecimal num = updateCount.subtract(count);
            log.info("修改后数量-原数量:"+num);
            if(num.compareTo(new BigDecimal(0)) > 0){
                log.info("修改数量大于原数量，需要减少:"+num+"的库存");
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), num,"reduce",date);
            }else if(num.compareTo(new BigDecimal(0)) < 0){
                num  = num.multiply(new BigDecimal(-1));
                log.info("修改数量小于原数量，需要增加:"+num+"的库存");
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), num,"add",date);
            }else{
                log.info("修改数量等于原数量，不需要更新库存");
            }
            //开始处理附件信息
            uploadFileInfoService.updateByBusinessId(newEntity.getId(),dto.getFileIdList());
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getReturnCount()+",供货商:"+dto.getCustomerName()+",退回方:"+dto.getOutName()+",退回人:"+dto.getReturnUserName();
        sysLogService.insertSysLog(FunctionTypeEnums.SUPPLY_RETURN.getCode(),OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
        try{
            //原数据
            SupplyReturnEntity entity = supplyReturnDao.selectById(dto.getId());
            int i = supplyReturnDao.deleteById(dto.getId());
            log.info("删除提交的待审核记录");
            approveOperationFlowDao.deleteByBusinessId(dto.getId());
            approveOperationQueueDao.deleteByBusinessId(dto.getId());
            log.info("供应商退回删除，需要增加原删除的库存");
            materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), dto.getReturnCount(),"add",date);
            log.info("开始删除附件信息");
            uploadFileInfoService.deleteFileByBusinessId(dto.getId());
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getReturnCount()+",供货商:"+dto.getCustomerName()+",退回方:"+dto.getOutName()+",退回人:"+dto.getReturnUserName();
        sysLogService.insertSysLog(FunctionTypeEnums.SUPPLY_RETURN.getCode(),OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public int updateApprove(Long id, String result, String opinion, String userLogin, BigDecimal unitPrice, BigDecimal tollAmount, Date date,BigDecimal freight)  {
        log.info("供应商退回审核更新开始");
        SupplyReturnEntity  entity = supplyReturnDao.selectById(id);
        entity.setUnitPrice(unitPrice);
        entity.setTollAmount(tollAmount);
        entity.setApproveUser(userLogin);
        entity.setApproveState(result);
        entity.setApproveOpinion(opinion);
        entity.setApproveTime(date);
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        entity.setFreight(freight);
        int i =supplyReturnDao.updateById(entity);
        //判断审核结果
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            /*log.info("审核同意，开始更新库存");  新增、修改、删除时更新库存
            i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), entity.getReturnCount(),"reduce",date);*/
            log.info("隐藏该客户原记录");
            supplyCustomerPayDao.updateShowFlagByCustomerCode("1",userLogin,entity.getCustomerCode());
            log.info("生成该客户新记录");
            SupplyCustomerPayEntity payEntity = new SupplyCustomerPayEntity(entity.getId(),entity.getCustomerCode(), entity.getMaterialCode(), unitPrice,entity.getReturnCount(),tollAmount,date,FunctionTypeEnums.SUPPLY_RETURN.getCode());
            payEntity.setShowFlag("0");
            payEntity.setCreateTime(date);
            payEntity.setCreateBy(userLogin);
            i = supplyCustomerPayDao.insert(payEntity);
            log.info("生成往来账信息");
            AddPayBySystemDTO dto = new AddPayBySystemDTO(null,entity.getId(),entity.getOutCode(),entity.getBillNo(),entity.getCustomerCode(), entity.getMaterialCode(),entity.getReturnTime(),SysEnums.SYS_NO_FLAG.getCode(), entity.getCreateBy(),date, entity.getRemark(),entity.getUnitPrice(),entity.getReturnCount(),tollAmount);
            dto.setFreight(freight);
            i = customerPayDetailService.addPayBySystem(dto);
        }else{
            log.info("审核拒绝,恢复增加库存");
            i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), entity.getReturnCount(),"add",date);
        }
        log.info("供应商退回审核更新结束");
        return i;
    }

    @Override
    public List<SupplyReturnInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("供应商退回queryListForExport开始");
        List<SupplyReturnInfo> list = new ArrayList<>();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        try {
            //添加权限  总公司人员查看所有数据   厂区或者仓库人员查看所属厂区或者仓库提交数据
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可以查看所有数据");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区的数据");
                dto.setInCode(user.getDeptId());
            }
            list = supplyReturnDao.queryListForExport(dto);
            list = setSupplyReturnObject(list,user);
            list = formatSumObjectForExport(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.SUPPLY_RETURN.getCode(),OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("供应商退回queryListForExport结束");
        return list;
    }

    /**
     * 赋值供应商退回  入库方名称  单价信息
     * @param list
     * @return
     */
    private List<SupplyReturnInfo> setSupplyReturnObject(List<SupplyReturnInfo> list,UserLoginOutDTO user){
        boolean isPrice = DataUtils.getIsPrice(user);
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            //获取厂区和仓库集合
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            for(SupplyReturnInfo info : list){
                //退货入库方
                String outCode = info.getOutCode();
                if(Constants.FACTORY_CODE_PREFIX.equals(outCode.substring(0,1))){
                    //工厂
                    for(SysFactoryInfo fInfo : factoryInfoList){
                        if(outCode.equals(fInfo.getCode())){
                            info.setOutName(fInfo.getName());
                            //供应商退回打印单据的订货地址为 退回方地址
                            info.setOrderAddress(fInfo.getAddress());
                        }
                    }
                }else{
                    //仓库
                    for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                        if(outCode.equals(sInfo.getCode())){
                            info.setOutName(sInfo.getName());
                            //供应商退回打印单据的订货地址为 退回方地址
                            info.setOrderAddress(sInfo.getAddress());
                        }
                    }
                }
                //处理单价字段
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
        String prefix = DataUtils.formatBillNoPrefix(user,"出");
        List<String> list = supplyReturnDao.queryBillNoListByParam(prefix);
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
    private QueryByPageOutDTO formatSumObject(List<SupplyReturnInfo> list, QueryByPageOutDTO outDTO){
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(SupplyReturnInfo info: list){
            BigDecimal count = info.getReturnCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
        }
        outDTO.setSupplyReturnInfoList(list);
        outDTO.setSumAmt(sumAmt);
        outDTO.setSumCount(sumCount);
        return outDTO;
    }

    /**
     * 导出最后一行增加合计列
     * @param list
     * @return
     */
    private List<SupplyReturnInfo> formatSumObjectForExport(List<SupplyReturnInfo> list){
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(SupplyReturnInfo info: list){
            BigDecimal count = info.getReturnCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
        }
        SupplyReturnInfo info = new SupplyReturnInfo();
        info.setUnitName("合计:");
        info.setReturnCount(sumCount);
        info.setTollAmount(sumAmt);
        list.add(info);
        return list;
    }

}
