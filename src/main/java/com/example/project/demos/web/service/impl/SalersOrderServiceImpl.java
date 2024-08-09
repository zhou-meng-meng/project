package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.customerPayDetail.AddPayBySystemDTO;
import com.example.project.demos.web.dto.list.SalersOrderInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.salersOrder.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.*;
import com.example.project.demos.web.enums.*;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
import com.example.project.demos.web.utils.BeanCopyUtils;
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
@Service("salersOrderService")
public class SalersOrderServiceImpl  implements SalersOrderService {

    @Resource
    private SalersOrderDao salersOrderDao;
    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;
    @Autowired
    private MaterialInventoryService materialInventoryService;
    @Autowired
    private SysLogService sysLogService;

    @Resource
    private ApproveOperationFlowDao approveOperationFlowDao;
    @Resource
    private ApproveOperationQueueDao approveOperationQueueDao;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private CustomerPayDetailService customerPayDetailService;
    @Resource
    private ConfirmOperationQueueDao confirmOperationQueueDao;
    @Resource
    private ConfirmOperationFlowDao confirmOperationFlowDao;
    @Resource
    private SalesCustomerPayDao salesCustomerPayDao;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("销售员下单queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SalersOrderInfo info = salersOrderDao.selectSalersOrderInfoById(id);
            //处理出库方
            List<SalersOrderInfo> list = new ArrayList<>();
            list.add(info);
            list = setSalersOrderObject(list);
            list = formatPriceByRoleType(list,RequestHolder.getUserInfo());
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售员下单queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("销售员下单queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //添加权限  总公司审核权限的  查看所有  销售员只能查看自己的数据
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            List<String> listType = user.getAuthorityType();
            if(listType.contains(RoleAuthorityTypeEnums.ROLE_AUTHORITY_TYPE_AUTH.getCode())){
                log.info("具有审核权限，查询所有数据");
            }else{
                log.info("不具有审核权限，查询自己提交的数据");
                dto.setSaler(user.getUserLogin());
            }
            //先用查询条件查询总条数
            long total = this.salersOrderDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<SalersOrderInfo> page = new PageImpl<>(this.salersOrderDao.selectSalersOrderInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<SalersOrderInfo> list = page.toList();
                list = setSalersOrderObject(list);
                //出参赋值
                outDTO.setSalersOrderInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售员下单queryByPage结束");
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
                SalersOrderEntity entity = BeanCopyUtils.copy(dto,SalersOrderEntity.class);
                entity.setApproveState(ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode());
                entity.setBillState(BillStateEnums.BILL_STATE_NORMAL.getCode());
                entity.setCreateBy(user.getUserLogin());
                entity.setCreateTime(date);
                log.info("插入销售员下单表");
                int i = salersOrderDao.insert(entity);
                log.info("生成审核流水记录");
                ApproveOperationFlowEntity flowEntity = new ApproveOperationFlowEntity(null,entity.getId(),FunctionTypeEnums.SALERS_ORDER.getCode(),user.getUserLogin(),date,ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode(),Constants.SYSTEM_CODE);
                approveOperationFlowDao.insert(flowEntity);
                log.info("生成审核队列记录");
                List<ApproveOperationQueueEntity> queueEntityList = new ArrayList<>();
                for(SysUserEntity userEntity : userList){
                    ApproveOperationQueueEntity queueEntity = new ApproveOperationQueueEntity(null,flowEntity.getId(), entity.getId(),FunctionTypeEnums.SALERS_ORDER.getCode(),userEntity.getUserLogin(),user.getUserLogin(),date,Constants.SYSTEM_CODE);
                    queueEntityList.add(queueEntity);
                }
                approveOperationQueueDao.insertBatch(queueEntityList);
            }else{
                errorCode = ErrorCodeEnums.AUTH_USER_NOT_EXIST.getCode();
                errortMsg = ErrorCodeEnums.AUTH_USER_NOT_EXIST.getDesc();
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info =  "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",单据号:"+dto.getBillCode()+",装车日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getLoadDate())+",装车数量:"+dto.getLoadNum()+",单价"+dto.getUnitPrice()+",总金额:"+dto.getTollAmount()+",装车方式:"+dto.getLoadTypeName()+",销售员:"+dto.getSalerName()+",汇款:"+dto.getRemit();
        sysLogService.insertSysLog(FunctionTypeEnums.SALERS_ORDER.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            SalersOrderEntity entity = BeanCopyUtils.copy(dto,SalersOrderEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = salersOrderDao.updateById(entity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info =  "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",单据号:"+dto.getBillCode()+",装车日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getLoadDate())+",装车数量:"+dto.getLoadNum()+",单价"+dto.getUnitPrice()+",总金额:"+dto.getTollAmount()+",装车方式:"+dto.getLoadTypeName()+",销售员:"+dto.getSalerName()+",汇款:"+dto.getRemit();
        sysLogService.insertSysLog(FunctionTypeEnums.SALERS_ORDER.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            int i = salersOrderDao.deleteById(dto.getId());
            log.info("删除提交的待审核记录");
            approveOperationFlowDao.deleteByBusinessId(dto.getId());
            approveOperationQueueDao.deleteByBusinessId(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info =  "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",单据号:"+dto.getBillCode()+",装车日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getLoadDate())+",装车数量:"+dto.getLoadNum()+",单价"+dto.getUnitPrice()+",总金额:"+dto.getTollAmount()+",装车方式:"+dto.getLoadTypeName()+",销售员:"+dto.getSalerName()+",汇款:"+dto.getRemit();
        sysLogService.insertSysLog(FunctionTypeEnums.SALERS_ORDER.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public int updateApprove(Long id, String result, String opinion, String userLogin,  Date date,String outCode)  {
        log.info("业务员下单审核更新开始");
        SalersOrderEntity  entity = salersOrderDao.selectById(id);
        entity.setOutCode(outCode);
        entity.setApproveUser(userLogin);
        entity.setApproveState(result);
        entity.setApproveOpinion(opinion);
        entity.setApproveTime(date);
        //对于审核同意的  确认状态改为待确认
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            entity.setConfirmState(ConfirmStateEnums.CONFIRM_STATE_UNDO.getCode());
        }
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        int i =salersOrderDao.updateById(entity);
        log.info("业务员下单审核更新结束");
        return i;
    }

    @Override
    public int updateConfirm(Long id, String result, String opinion, String userLogin, Date date)  {
        log.info("业务员下单确认更新开始");
        SalersOrderEntity  entity = salersOrderDao.selectById(id);
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        entity.setConfirmUser(userLogin);
        entity.setConfirmState(result);
        entity.setConfirmOpinion(opinion);
        entity.setConfirmTime(date);
        int i = salersOrderDao.updateById(entity);
        //判断确认结果
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            log.info("确认同意，开始更新库存");
            materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), entity.getLoadNum(),"reduce",date);
            log.info("生成该客户业务员下单记录");
            SalesCustomerPayEntity payEntity = new SalesCustomerPayEntity(null,entity.getId(),entity.getCustomerCode(), entity.getMaterialCode(), entity.getUnitPrice(),entity.getLoadNum(),entity.getTollAmount(),date,FunctionTypeEnums.SALERS_ORDER.getCode());
            i = salesCustomerPayDao.insert(payEntity);
            log.info("生成往来账信息");
            AddPayBySystemDTO dto = new AddPayBySystemDTO(null,entity.getCustomerCode(),entity.getUnitPrice(),entity.getLoadNum(),entity.getTollAmount(),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),"1",SysEnums.SYS_NO_FLAG.getCode(),Constants.SYSTEM_CODE,date,FunctionTypeEnums.SALERS_ORDER.getDesc());
            customerPayDetailService.addPayBySystem(dto);
        }else{
            log.info("确认拒绝");
        }
        log.info("业务员下单确认更新结束");
        return i;
    }

    /**
     * 冲销操作    需要确认
     * @param dto
     * @return
     */
    @Override
    public ChargeOffOutDTO chargeOffSubmit(ChargeOffDTO dto) {
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        ChargeOffOutDTO outDTO = new ChargeOffOutDTO();
        try{
            SalersOrderEntity entity = salersOrderDao.selectById(dto.getId());
            //退回到原出库方
            String userType = "";
            String outCode = entity.getOutCode();
            if("F".equals(outCode.substring(0,1))){
                log.info("退回方为厂区");
                userType = UserTypeEnums.USER_TYPE_FACTORY.getCode();
            }else{
                log.info("退回方为仓库");
                userType = UserTypeEnums.USER_TYPE_STORE.getCode();
            }
            log.info("生成待确认数据");
            List<SysUserEntity> userList = sysUserService.queryUserListByRoleType(userType, RoleAuthorityTypeEnums.ROLE_AUTHORITY_TYPE_CONFIRM.getCode(),outCode);
            if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0){
                //生成待确认流水
                ConfirmOperationFlowEntity flowEntity = new ConfirmOperationFlowEntity(null,entity.getId(), FunctionTypeEnums.SALERS_ORDER_CHARGE_OFF.getCode(),null,user.getUserLogin(),date,null,null,null,null, ConfirmStateEnums.CONFIRM_STATE_UNDO.getCode(),Constants.SYSTEM_CODE);
                confirmOperationFlowDao.insert(flowEntity);
                //生成待确认队列
                List<ConfirmOperationQueueEntity> queueEntityList = new ArrayList<>();
                for(SysUserEntity userEntity : userList){
                    ConfirmOperationQueueEntity queueEntity = new ConfirmOperationQueueEntity(null,flowEntity.getId(),entity.getId(),userEntity.getUserLogin(),FunctionTypeEnums.SALERS_ORDER_CHARGE_OFF.getCode(),user.getUserLogin(),date,null,null);
                    queueEntityList.add(queueEntity);
                }
                confirmOperationQueueDao.insertBatch(queueEntityList);
                log.info("修改业务员下单表单据冲销信息");
                entity.setConfirmState(ConfirmStateEnums.CONFIRM_STATE_UNDO.getCode());
                //将确认人   确认时间   确认意见 清空
                entity.setConfirmUser("");
                entity.setConfirmOpinion("");
                entity.setConfirmTime(null);
                entity.setBillState(BillStateEnums.BILL_STATE_UNCONFIRM.getCode());
                entity.setChargeoffUser(user.getUserLogin());
                entity.setChargeoffTime(date);
                entity.setChargeoffOpinion(dto.getChargeoffOpinion());
                entity.setUpdateBy(user.getUserLogin());
                entity.setUpdateTime(date);
                salersOrderDao.updateById(entity);
            }else{
                errorCode = ErrorCodeEnums.CONFIRM_USER_NOT_EXIST.getCode();
                errortMsg = ErrorCodeEnums.CONFIRM_USER_NOT_EXIST.getDesc();
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info =  "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",单据号:"+dto.getBillCode()+",装车数量:"+dto.getLoadNum()+",销售员:"+dto.getSalerName();
        sysLogService.insertSysLog(FunctionTypeEnums.SALERS_ORDER.getCode(), OperationTypeEnums.OPERATION_TYPE_CHARGE_OFF.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    /**
     * 冲销操作  从哪里出库的  就退回到哪里
     * 增加往来账   增加库存
     * @param id
     * @param result
     * @param opinion
     * @param userLogin
     * @param date
     * @return
     */
    @Override
    public int chargeOffConfirm(Long id, String result, String opinion, String userLogin, Date date) {
        log.info("业务员下单冲销确认更新开始");
        SalersOrderEntity  entity = salersOrderDao.selectById(id);
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        entity.setConfirmUser(userLogin);
        entity.setConfirmState(result);
        entity.setConfirmOpinion(opinion);
        entity.setConfirmTime(date);
        //判断确认结果
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            entity.setBillState(BillStateEnums.BILL_STATE_CHARGE_OFF.getCode());
            log.info("确认同意，开始更新库存");
            materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), entity.getLoadNum(),"add",date);
            log.info("生成往来账信息");
            AddPayBySystemDTO dto = new AddPayBySystemDTO(null,entity.getCustomerCode(),entity.getUnitPrice(),entity.getLoadNum(),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),entity.getTollAmount(),new BigDecimal(0),"1",SysEnums.SYS_NO_FLAG.getCode(),Constants.SYSTEM_CODE,date,FunctionTypeEnums.SALERS_ORDER_CHARGE_OFF.getDesc());
            customerPayDetailService.addPayBySystem(dto);
        }else{
            log.info("确认拒绝");
            entity.setBillState(BillStateEnums.BILL_STATE_CONFIRM_REJECT.getCode());
        }
        int i = salersOrderDao.updateById(entity);
        log.info("业务员下单确认更新结束");
        return i;
    }

    @Override
    public List<SalersOrderInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("销售员下单queryListForExport开始");
        List<SalersOrderInfo> list = new ArrayList<>();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try {
            //添加权限  总公司审核权限的  查看所有  销售员只能查看自己的数据
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可以查看所有数据");
            }else{
                log.info("当前登录人不属于总公司，只能查看自己提交的数据");
                dto.setSaler(user.getUserLogin());
            }
            list = salersOrderDao.queryListForExport(dto);
            list = setSalersOrderObject(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info =  "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.SALERS_ORDER.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("销售员下单queryListForExport结束");
        return list;
    }

    /**
     * 赋值销售员下单  出库方名称
     * @param list
     * @return
     */
    private List<SalersOrderInfo> setSalersOrderObject(List<SalersOrderInfo> list){
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0 ){
            //获取厂区和仓库集合
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            for(SalersOrderInfo info : list){
                //出库方
                String outCode = info.getOutCode();
                if(null != outCode && !"".equals(outCode) && ""!= outCode){
                    if(Constants.FACTORY_CODE_PREFIX.equals(outCode.substring(0,1))){
                        //工厂
                        for(SysFactoryInfo fInfo : factoryInfoList){
                            if(outCode.equals(fInfo.getCode())){
                                info.setOutName(fInfo.getName());
                            }
                        }
                    }else{
                        //仓库
                        for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                            if(outCode.equals(sInfo.getCode())){
                                info.setOutName(sInfo.getName());
                            }
                        }
                    }
                }
            }
        }else{
            log.info("list is null");
        }
        return list;
    }

    /**
     * 处理单价和总金额字段  有单价权限的可以查看，没有单价权限的不能查看
     * @param list
     * @param userInfo
     * @return
     */
    private List<SalersOrderInfo> formatPriceByRoleType(List<SalersOrderInfo> list, UserLoginOutDTO userInfo){
        log.info("处理单价和总金额字段  有单价权限的可以查看，没有单价权限的不能查看");
        if(CollectionUtil.isNotEmpty(list) && list.size()>0){
            List<String> typeList = userInfo.getAuthorityType();
            if(typeList.contains(RoleAuthorityTypeEnums.ROLE_AUTHORITY_TYPE_PRICE.getCode())){
                log.info("具有单价权限,不处理");
            }else{
                log.info("没有单价权限，将单价和总金额置为0");
                for(SalersOrderInfo info : list){
                    info.setUnitPrice(new BigDecimal(0));
                    info.setTollAmount(new BigDecimal(0));
                }
            }
        }else{
            log.info("list is null");
        }
        return list;
    }

}
