package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.customerPayDetail.AddPayBySystemDTO;
import com.example.project.demos.web.dto.list.RawMaterialIncomeInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.rawMaterialIncome.*;
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
@Service("rawMaterialIncomeService")
public class RawMaterialIncomeServiceImpl  implements RawMaterialIncomeService {

    @Resource
    private RawMaterialIncomeDao rawMaterialIncomeDao;
    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;

    @Autowired
    private SysUserService sysUserService;

    @Resource
    private ApproveOperationFlowDao approveOperationFlowDao;
    @Resource
    private ApproveOperationQueueDao approveOperationQueueDao;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private MaterialInventoryService materialInventoryService;

    @Resource
    private SupplyCustomerPayDao supplyCustomerPayDao;

    @Autowired
    private CustomerPayDetailService customerPayDetailService;

    @Autowired
    private UploadFileInfoService uploadFileInfoService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("来料入库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            RawMaterialIncomeInfo rawMaterialIncomeInfo = rawMaterialIncomeDao.selectRawMaterialIncomeInfoById(id);
            List<RawMaterialIncomeInfo> list = new ArrayList<>();
            list.add(rawMaterialIncomeInfo);
            list = setRawMaterialIncomeObject(list,RequestHolder.getUserInfo());
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("来料入库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("来料入库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        outDTO.setSumCount(new BigDecimal("0"));
        outDTO.setSumAmt(new BigDecimal("0"));
        try {
            //权限判断  总公司人员可查看所有厂区   厂区人员只能查看所属厂区
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可查看所有");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区或仓库");
                dto.setInCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.rawMaterialIncomeDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<RawMaterialIncomeInfo> page = new PageImpl<>(this.rawMaterialIncomeDao.selectRawMaterialIncomeInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<RawMaterialIncomeInfo> list = page.toList();
                //处理入库方名称
                list = setRawMaterialIncomeObject(list,RequestHolder.getUserInfo());
                //出参赋值
                outDTO = formatSumObject(list,outDTO);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("来料入库queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto)  {
        log.info("来料入库新增操作开始");
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            log.info("查询总公司具有审核权限的人员");
            List<SysUserEntity> userList = sysUserService.queryUserListByRoleType(UserTypeEnums.USER_TYPE_COMPANY.getCode(), RoleAuthorityTypeEnums.ROLE_AUTHORITY_TYPE_AUTH.getCode(),"");
            if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0){
                RawMaterialIncomeEntity entity = BeanCopyUtils.copy(dto,RawMaterialIncomeEntity.class);
                //设置 审核状态 创建人和创建时间
                entity.setApproveState(ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode());
                entity.setCreateBy(user.getUserLogin());
                entity.setCreateTime(date);
                //生成单据号
                entity.setBillNo(getBillNoList(user));
                log.info("插入来料入库表");
                int i = rawMaterialIncomeDao.insert(entity);
                log.info("生成审核流水记录");
                ApproveOperationFlowEntity flowEntity = new ApproveOperationFlowEntity(null,entity.getId(),FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode(),user.getUserLogin(),date,ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode(),Constants.SYSTEM_CODE);
                approveOperationFlowDao.insert(flowEntity);
                log.info("生成审核队列记录");
                List<ApproveOperationQueueEntity> queueEntityList = new ArrayList<>();
                for(SysUserEntity userEntity : userList){
                    ApproveOperationQueueEntity queueEntity = new ApproveOperationQueueEntity(null,flowEntity.getId(), entity.getId(),FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode(),userEntity.getUserLogin(),dto.getSupplyerCode(),dto.getMaterialCode(),dto.getCount(),user.getUserLogin(),date,Constants.SYSTEM_CODE,dto.getMaterialBuytime());
                    queueEntityList.add(queueEntity);
                }
                approveOperationQueueDao.insertBatch(queueEntityList);
                //来料入库提交时  增加库存
                log.info("开始增加库存");
                i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getCount(),"add",date);
                //开始处理附件信息
                uploadFileInfoService.updateByBusinessId(entity.getId(),dto.getFileIdList());
            }else{
                errorCode = ErrorCodeEnums.AUTH_USER_NOT_EXIST.getCode();
                errortMsg = ErrorCodeEnums.AUTH_USER_NOT_EXIST.getDesc();
            }
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getCount().toString()+",供货商:"+dto.getSupplyerName()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode(),OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("来料入库新增操作结束");
        return outDTO;
    }

    @Override
    public EditOutDTO update(EditDTO dto)  {
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            //原数据
            RawMaterialIncomeEntity entity = rawMaterialIncomeDao.selectById(dto.getId());
            log.info("查询总公司具有审核权限的人员");
            List<SysUserEntity> userList = sysUserService.queryUserListByRoleType(UserTypeEnums.USER_TYPE_COMPANY.getCode(), RoleAuthorityTypeEnums.ROLE_AUTHORITY_TYPE_AUTH.getCode(),"");
            if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0){
                RawMaterialIncomeEntity newEntity = BeanCopyUtils.copy(dto,RawMaterialIncomeEntity.class);
                newEntity.setUpdateBy(user.getUserLogin());
                newEntity.setUpdateTime(date);
                int i = rawMaterialIncomeDao.updateById(newEntity);
                //修改了数量  要更新库存
                BigDecimal count = entity.getCount();
                log.info("原数量:"+count.toString());
                BigDecimal updateCount = dto.getCount();
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
                log.info("删除提交的原待审核记录");
                approveOperationFlowDao.deleteByBusinessId(dto.getId());
                approveOperationQueueDao.deleteByBusinessId(dto.getId());
                log.info("重新生成审核流水记录");
                ApproveOperationFlowEntity flowEntity = new ApproveOperationFlowEntity(null,entity.getId(),FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode(),user.getUserLogin(),date,ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode(),Constants.SYSTEM_CODE);
                approveOperationFlowDao.insert(flowEntity);
                log.info("重新生成审核队列记录");
                List<ApproveOperationQueueEntity> queueEntityList = new ArrayList<>();
                for(SysUserEntity userEntity : userList){
                    ApproveOperationQueueEntity queueEntity = new ApproveOperationQueueEntity(null,flowEntity.getId(), entity.getId(),FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode(),userEntity.getUserLogin(),dto.getSupplyerCode(),dto.getMaterialCode(),dto.getCount(),user.getUserLogin(),date,Constants.SYSTEM_CODE,dto.getMaterialBuytime());
                    queueEntityList.add(queueEntity);
                }
                approveOperationQueueDao.insertBatch(queueEntityList);
            }else{
                errorCode = ErrorCodeEnums.AUTH_USER_NOT_EXIST.getCode();
                errortMsg = ErrorCodeEnums.AUTH_USER_NOT_EXIST.getDesc();
            }
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getCount().toString()+",供货商:"+dto.getSupplyerName()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode(),OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto)  {
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        try{
            //原数据
            RawMaterialIncomeEntity entity = rawMaterialIncomeDao.selectById(dto.getId());
            int i = rawMaterialIncomeDao.deleteById(dto.getId());
            log.info("删除提交的待审核记录");
            approveOperationFlowDao.deleteByBusinessId(dto.getId());
            approveOperationQueueDao.deleteByBusinessId(dto.getId());
            //删除待审核记录，需要减少对用库存
            i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getCount(),"reduce",date);
            log.info("开始删除附件信息");
            uploadFileInfoService.deleteFileByBusinessId(dto.getId());
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getCount().toString()+",供货商:"+dto.getSupplyerName()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode(),OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public int updateApprove(Long id, String result, String opinion, String userLogin, BigDecimal unitPrice,BigDecimal tollAmount, Date date,BigDecimal freight)  {
        log.info("来料入库审核更新开始");
        RawMaterialIncomeEntity  entity = rawMaterialIncomeDao.selectById(id);
        entity.setUnitPrice(unitPrice);
        entity.setTollAmount(tollAmount);
        entity.setApproveUser(userLogin);
        entity.setApproveState(result);
        entity.setApproveOpinion(opinion);
        entity.setApproveTime(date);
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        entity.setFreight(freight);
        int i = rawMaterialIncomeDao.updateById(entity);
        //判断审核结果
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            /*log.info("审核同意，开始更新库存");  在新增和修改、删除时   更新库存
            i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getCount(),"add",date);*/
            log.info("先隐藏该客户原记录");
            i = supplyCustomerPayDao.updateShowFlagByCustomerCode("1",userLogin,entity.getSupplyerCode());
            log.info("生成该客户新记录");
            SupplyCustomerPayEntity payEntity = new SupplyCustomerPayEntity(entity.getId(),entity.getSupplyerCode(), entity.getMaterialCode(), unitPrice,entity.getCount(),tollAmount,date,FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode());
            payEntity.setShowFlag("0");
            payEntity.setCreateTime(date);
            payEntity.setCreateBy(userLogin);
            i = supplyCustomerPayDao.insert(payEntity);
            log.info("生成往来账信息");
            AddPayBySystemDTO dto = new AddPayBySystemDTO(null,entity.getId(),entity.getInCode(),entity.getBillNo(),entity.getSupplyerCode(), entity.getMaterialCode(), entity.getMaterialBuytime(),  entity.getUnitPrice(),entity.getCount(),tollAmount,SysEnums.SYS_NO_FLAG.getCode(),entity.getCreateBy(),date, entity.getRemark());
            dto.setFreight(freight);
            i = customerPayDetailService.addPayBySystem(dto);
        }else{
            log.info("审核拒绝，恢复减少库存");
            i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getCount(),"reduce",date);
        }
        log.info("来料入库审核更新结束");
        return i;
    }

    @Override
    public List<RawMaterialIncomeInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("来料入库queryListForExport开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        List<RawMaterialIncomeInfo> list  = new ArrayList<>();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try {
            //权限判断  总公司人员可查看所有厂区   厂区人员只能查看所属厂区
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可查看所有");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区或仓库");
                dto.setInCode(user.getDeptId());
            }
            log.info("开始查询数据");
            list = rawMaterialIncomeDao.queryListForExport(dto);
            //处理入库方名称
            list = setRawMaterialIncomeObject(list,RequestHolder.getUserInfo());
            //添加合计列
            list = formatSumObjectForExport(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode(),OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("来料入库queryListForExport结束");
        return list;
    }

    /**
     * 赋值来料入库方名称
     * @param list
     * @return
     */
    private List<RawMaterialIncomeInfo> setRawMaterialIncomeObject(List<RawMaterialIncomeInfo> list,UserLoginOutDTO userInfo){
        log.info("赋值来料入库方名称");
        boolean isPrice = false;
        List<String> typeList = userInfo.getAuthorityType();
        if(typeList.contains(RoleAuthorityTypeEnums.ROLE_AUTHORITY_TYPE_PRICE.getCode())){
            isPrice = true;
            log.info("有单价权限，不处理");
        }else{
            isPrice = false;
            log.info("没有单价权限，将单价和总金额置为0");
        }
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            //获取厂区和仓库集合
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            for(RawMaterialIncomeInfo info : list){
                //入库方
                String inCode = info.getInCode();
                if(Constants.FACTORY_CODE_PREFIX.equals(inCode.substring(0,1))){
                    //工厂
                    for(SysFactoryInfo fInfo : factoryInfoList){
                        if(inCode.equals(fInfo.getCode())){
                            info.setInName(fInfo.getName());
                            //来料入库的单据打印订货地址为入库方地址
                            info.setOrderAddress(fInfo.getAddress());
                        }
                    }
                }else{
                    //仓库
                    for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                        if(inCode.equals(sInfo.getCode())){
                            info.setInName(sInfo.getName());
                            //来料入库的单据打印订货地址为入库方地址
                            info.setOrderAddress(sInfo.getAddress());
                        }
                    }
                }
                //处理单价和总金额权限
                if(isPrice){
                    //log.info("有单价权限，不处理");
                }else{
                    //log.info("没有单价权限，将单价和总金额置为0");
                    info.setUnitPrice("0");
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
        List<String> list = rawMaterialIncomeDao.queryBillNoListByParam(prefix);
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
    private QueryByPageOutDTO formatSumObject(List<RawMaterialIncomeInfo> list,QueryByPageOutDTO outDTO){
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(RawMaterialIncomeInfo info: list){
            BigDecimal count = info.getCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
        }
        outDTO.setRawMaterialIncomeInfoList(list);
        outDTO.setSumAmt(sumAmt);
        outDTO.setSumCount(sumCount);
        return outDTO;
    }

    /**
     * 导出最后一行增加合计列
     * @param list
     * @return
     */
    private List<RawMaterialIncomeInfo> formatSumObjectForExport(List<RawMaterialIncomeInfo> list){
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(RawMaterialIncomeInfo info: list){
            BigDecimal count = info.getCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
        }
        RawMaterialIncomeInfo info = new RawMaterialIncomeInfo();
        info.setUnitName("合计:");
        info.setCount(sumCount);
        info.setTollAmount(sumAmt);
        list.add(info);
        return list;
    }

}
