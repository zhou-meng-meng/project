package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.customerPayDetail.AddPayBySystemDTO;
import com.example.project.demos.web.dto.list.RawMaterialIncomeInfo;
import com.example.project.demos.web.dto.list.SalesOutboundInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.salesOutbound.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.*;
import com.example.project.demos.web.enums.*;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
import com.example.project.demos.web.utils.BeanCopyUtils;
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
import java.util.Map;


@Slf4j
@Service("salesOutboundService")
public class SalesOutboundServiceImpl  implements SalesOutboundService {
    @Resource
    private SalesOutboundDao salesOutboundDao;
    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;

    @Resource
    private ApproveOperationFlowDao approveOperationFlowDao;
    @Resource
    private ApproveOperationQueueDao approveOperationQueueDao;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private MaterialInventoryService materialInventoryService;

    @Autowired
    private CustomerPayDetailService customerPayDetailService;

    @Resource
    private SalesCustomerPayDao salesCustomerPayDao;
    @Resource
    private ConfirmOperationQueueDao confirmOperationQueueDao;
    @Resource
    private ConfirmOperationFlowDao confirmOperationFlowDao;


    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("销售出库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SalesOutboundInfo SalesOutboundInfo = salesOutboundDao.selectSalesOutboundInfoById(id);
            List<SalesOutboundInfo> list = new ArrayList<>();
            list.add(SalesOutboundInfo);
            list = setSalesOutboundObject(list);
            list = formatPriceByRoleType(list,RequestHolder.getUserInfo());
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售出库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("销售出库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //添加权限  总公司审核权限的  查看所有  厂区和仓库的 查看所属厂区或者仓库数据
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，查询所有数据");
            }else{
                log.info("当前登录人不属于总公司，只能查看自己所在厂区/仓库提交的数据");
                dto.setOutCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.salesOutboundDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<SalesOutboundInfo> page = new PageImpl<>(this.salesOutboundDao.selectSalesOutboundInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<SalesOutboundInfo> list = page.toList();
                //赋值出库方名称
                list = setSalesOutboundObject(list);
                list = formatPriceByRoleType(list,RequestHolder.getUserInfo());
                //出参赋值
                outDTO.setSalesOutboundInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售出库queryByPage结束");
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
            List<SysUserEntity> userList = sysUserService.queryUserListByRoleType(UserTypeEnums.USER_TYPE_COMPANY.getCode(), RoleAuthorityTypeEnums.ROLE_AUTHORIT_YTYPE_AUTH.getCode(),"");
            if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0){
                SalesOutboundEntity entity = BeanCopyUtils.copy(dto,SalesOutboundEntity.class);
                //设置 审核状态 创建人和创建时间
                entity.setApproveState(ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode());
                entity.setBillState(BillStateEnums.BILL_STATE_NORMAL.getCode());
                entity.setCreateBy(user.getUserLogin());
                entity.setCreateTime(date);
                log.info("插入销售出库表");
                int i = salesOutboundDao.insert(entity);
                log.info("生成审核流水记录");
                ApproveOperationFlowEntity flowEntity = new ApproveOperationFlowEntity(null,entity.getId(), FunctionTypeEnums.SALES_OUTBOUND.getCode(),user.getUserLogin(),date,ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode(),"system");
                approveOperationFlowDao.insert(flowEntity);
                log.info("生成审核队列记录");
                List<ApproveOperationQueueEntity> queueEntityList = new ArrayList<>();
                for(SysUserEntity userEntity : userList){
                    ApproveOperationQueueEntity queueEntity = new ApproveOperationQueueEntity(null,flowEntity.getId(), entity.getId(),FunctionTypeEnums.SALES_OUTBOUND.getCode(),userEntity.getUserLogin(),user.getUserLogin(),date,"system");
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
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getOutCount().toString()+",购货方:"+dto.getCustomerName()+",出库方:"+dto.getOutName()+",运输方式:"+dto.getTransportTypeName()+",运费:"+dto.getFreight().toString()+",单据号:"+dto.getBillNo();
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_OUTBOUND.getCode(),OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            SalesOutboundEntity entity = BeanCopyUtils.copy(dto,SalesOutboundEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = salesOutboundDao.updateById(entity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getOutCount().toString()+",购货方:"+dto.getCustomerName()+",出库方:"+dto.getOutName()+",运输方式:"+dto.getTransportTypeName()+",运费:"+dto.getFreight().toString()+",单据号:"+dto.getBillNo();
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_OUTBOUND.getCode(),OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) {
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            int i = salesOutboundDao.deleteById(dto.getId());
            log.info("删除提交的待审核记录");
            approveOperationFlowDao.deleteByBusinessId(dto.getId());
            approveOperationQueueDao.deleteByBusinessId(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getOutCount().toString()+",购货方:"+dto.getCustomerName()+",出库方:"+dto.getOutName()+",运输方式:"+dto.getTransportTypeName()+",运费:"+dto.getFreight().toString()+",单据号:"+dto.getBillNo();
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_OUTBOUND.getCode(),OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public int updateApprove(Long id, String result, String opinion, String userLogin, BigDecimal unitPrice,BigDecimal tollAmount, Date date)  {
        log.info("销售出库审核更新开始");
        SalesOutboundEntity  entity = salesOutboundDao.selectById(id);
        entity.setUnitPrice(unitPrice);
        entity.setTollAmount(tollAmount);
        entity.setApproveState(result);
        entity.setApproveOpinion(opinion);
        entity.setApproveTime(date);
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        int i =salesOutboundDao.updateById(entity);
        //判断审核结果
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            log.info("审核同意，开始更新库存");
            i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), entity.getOutCount(),"reduce",date);
            log.info("生成该客户销售记录");
            SalesCustomerPayEntity payEntity = new SalesCustomerPayEntity(null,entity.getId(),entity.getCustomerCode(), entity.getMaterialCode(), unitPrice,entity.getOutCount(),tollAmount,date,FunctionTypeEnums.SALES_OUTBOUND.getCode());
            i = salesCustomerPayDao.insert(payEntity);
            log.info("生成往来账信息");
            AddPayBySystemDTO dto = new AddPayBySystemDTO(null,entity.getCustomerCode(),entity.getUnitPrice(),entity.getOutCount(),tollAmount,new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),"0",SysEnums.SYS_NO_FLAG.getCode(),Constants.SYSTEM_CODE,date,FunctionTypeEnums.SALES_OUTBOUND.getDesc());
            i = customerPayDetailService.addPayBySystem(dto);
        }else{
            log.info("审核拒绝");
        }
        log.info("销售出库审核更新结束");
        return i;
    }

    @Override
    public ChargeOffOutDTO chargeOffSubmit(ChargeOffDTO dto) {
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        ChargeOffOutDTO outDTO = new ChargeOffOutDTO();
        try{
            SalesOutboundEntity entity = salesOutboundDao.selectById(dto.getId());
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
            List<SysUserEntity> userList = sysUserService.queryUserListByRoleType(userType, RoleAuthorityTypeEnums.ROLE_AUTHORIT_YTYPE_CONFIRM.getCode(),outCode);
            if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0){
                //生成待确认流水
                ConfirmOperationFlowEntity flowEntity = new ConfirmOperationFlowEntity(null,entity.getId(), FunctionTypeEnums.SALES_OUTBOUND_CHARGE_OFF.getCode(),null,user.getUserLogin(),date,null,null,null,null, ConfirmStateEnums.CONFIRM_STATE_UNDO.getCode(),Constants.SYSTEM_CODE);
                confirmOperationFlowDao.insert(flowEntity);
                //生成待确认队列
                List<ConfirmOperationQueueEntity> queueEntityList = new ArrayList<>();
                for(SysUserEntity userEntity : userList){
                    ConfirmOperationQueueEntity queueEntity = new ConfirmOperationQueueEntity(null,flowEntity.getId(),entity.getId(),userEntity.getUserLogin(),FunctionTypeEnums.SALES_OUTBOUND_CHARGE_OFF.getCode(),user.getUserLogin(),date,null,null);
                    queueEntityList.add(queueEntity);
                }
                confirmOperationQueueDao.insertBatch(queueEntityList);
                log.info("修改销售出库表冲销字段");
                entity.setBillState(BillStateEnums.BILL_STATE_UNCONFIRM.getCode());
                entity.setChargeoffUser(user.getUserLogin());
                entity.setChargeoffTime(date);
                entity.setChargeoffOpinion(dto.getChargeOpinion());
                entity.setUpdateBy(user.getUserLogin());
                entity.setUpdateTime(date);
                salesOutboundDao.updateById(entity);
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
        String info =  "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",单据号:"+dto.getBillCode()+",装车数量:"+dto.getLoadNum()+",运输方式:"+dto.getTransportTypeName()+",运费:"+dto.getFreight();
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
        log.info("厂区下单冲销确认更新开始");
        SalesOutboundEntity  entity = salesOutboundDao.selectById(id);
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
            materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), entity.getOutCount(),"add",date);
            log.info("生成该客户销售出库-冲销记录");
            SalesCustomerPayEntity payEntity = new SalesCustomerPayEntity(null,entity.getId(),entity.getCustomerCode(), entity.getMaterialCode(), entity.getUnitPrice(),entity.getOutCount(),entity.getTollAmount(),date,FunctionTypeEnums.SALES_OUTBOUND_CHARGE_OFF.getCode());
            salesCustomerPayDao.insert(payEntity);
            log.info("生成往来账信息");
            AddPayBySystemDTO dto = new AddPayBySystemDTO(null,entity.getCustomerCode(),entity.getUnitPrice(),entity.getOutCount(),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),entity.getTollAmount(),"1",SysEnums.SYS_NO_FLAG.getCode(),Constants.SYSTEM_CODE,date,FunctionTypeEnums.SALES_OUTBOUND_CHARGE_OFF.getDesc());
            customerPayDetailService.addPayBySystem(dto);
        }else{
            log.info("确认拒绝");
            entity.setBillState(BillStateEnums.BILL_STATE_CONFIRM_REJECT.getCode());
        }
        int i = salesOutboundDao.updateById(entity);
        log.info("厂区下单确认更新结束");
        return i;
    }

    @Override
    public List<SalesOutboundInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("销售出库queryListForExport开始");
        List<SalesOutboundInfo> list = new ArrayList<>();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        try {
            //添加权限  总公司审核权限的  查看所有  厂区和仓库的 查看所属厂区或者仓库数据
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，查询所有数据");
            }else{
                log.info("当前登录人不属于总公司，只能查看自己所在厂区/仓库提交的数据");
                dto.setOutCode(user.getDeptId());
            }
            list = salesOutboundDao.queryListForExport(dto);
            //赋值出库方名称
            list = setSalesOutboundObject(list);
            list = formatPriceByRoleType(list,RequestHolder.getUserInfo());
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info =  "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.SALERS_ORDER.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("销售出库queryListForExport结束");
        return list;
    }


    /**
     * 赋值销售出库方名称
     * @param list
     * @return
     */
    private List<SalesOutboundInfo> setSalesOutboundObject(List<SalesOutboundInfo> list){
        log.info("赋值销售出库方名称");
        if(CollectionUtil.isNotEmpty(list) && list.size()>0){
            //获取厂区和仓库集合
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            for(SalesOutboundInfo info : list){
                //出库方
                String outCode = info.getOutCode();
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
        }else{
            log.info("list is not null");
        }
        return list;
    }

    /**
     * 处理单价和总金额字段  有单价权限的可以查看，没有单价权限的不能查看
     * @param list
     * @param userInfo
     * @return
     */
    private List<SalesOutboundInfo> formatPriceByRoleType(List<SalesOutboundInfo> list, UserLoginOutDTO userInfo){
        log.info("处理单价和总金额字段  有单价权限的可以查看，没有单价权限的不能查看");
        if(CollectionUtil.isNotEmpty(list) && list.size()>0){
            List<String> typeList = userInfo.getAuthorityType();
            if(typeList.contains(RoleAuthorityTypeEnums.ROLE_AUTHORIT_YTYPE_PRICE.getCode())){
                log.info("具有单价权限,不处理");
            }else{
                log.info("没有单价权限，将单价和总金额置为0");
                for(SalesOutboundInfo info : list){
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
