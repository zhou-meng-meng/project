package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.approveOperationQueue.*;
import com.example.project.demos.web.dto.list.ApproveOperationQueueInfo;
import com.example.project.demos.web.dto.list.SysUserInfo;
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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("approveOperationQueueService")
public class ApproveOperationQueueServiceImpl  implements ApproveOperationQueueService {
    @Resource
    private ApproveOperationQueueDao approveOperationQueueDao;

    @Resource
    private ApproveOperationFlowDao approveOperationFlowDao;
    @Autowired
    private RawMaterialIncomeService rawMaterialIncomeService;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private SalesOutboundService salesOutboundService;
    @Autowired
    private SupplyReturnService supplyReturnService;
    @Autowired
    private SalesReturnService salesReturnService;
    @Autowired
    private SalersOrderService salersOrderService;
    @Resource
    private SalesReturnDao salesReturnDao;
    @Autowired
    private SysUserService sysUserService;
    @Resource
    private ConfirmOperationQueueDao confirmOperationQueueDao;
    @Resource
    private ConfirmOperationFlowDao confirmOperationFlowDao;
    @Resource
    private SysUserDao sysUserDao;
    @Autowired
    private CustomerSaleService customerSaleService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("审核队列queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            ApproveOperationQueueInfo ApproveOperationQueueInfo = approveOperationQueueDao.selectApproveOperationQueueInfoById(id);
            outDTO = BeanUtil.copyProperties(ApproveOperationQueueInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("审核队列queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("审核队列queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            queryByPageDTO.setApproveUser(RequestHolder.getUserInfo().getUserLogin());
            //先用查询条件查询总条数
            long total = this.approveOperationQueueDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<ApproveOperationQueueInfo> page = new PageImpl<>(this.approveOperationQueueDao.selectApproveOperationQueueInfoListByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取分页数据
                List<ApproveOperationQueueInfo> list = page.toList();
                //转换业务类型
                for(ApproveOperationQueueInfo info :list){
                    info.setFunctionName(FunctionTypeEnums.getDescByCode(info.getFunctionId()));
                }
                //出参赋值
                outDTO.setApproveOperationQueueInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("审核队列queryByPage结束");
        return outDTO;
    }

    @Override
    public DealApproveQueueOutDTO dealApproveQueue(DealApproveQueueDTO dto)  {
        log.info("审核提交开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        DealApproveQueueOutDTO outDTO = new DealApproveQueueOutDTO();
        log.info("获取流水详情");
        ApproveOperationQueueEntity entity = approveOperationQueueDao.selectById(dto.getId());
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        try{
            String functionId = entity.getFunctionId();
            Long businessId = entity.getBusinessId();
            if(functionId.equals(FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode())){
                log.info("来料入库操作");
                rawMaterialIncomeService.updateApprove(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),dto.getUnitPrice(),dto.getTollAmount(),date);
            }else if(functionId.equals(FunctionTypeEnums.SALES_OUTBOUND.getCode())){
                log.info("销售出库操作");
                salesOutboundService.updateApprove(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),dto.getUnitPrice(),dto.getTollAmount(),date);
            }else if(functionId.equals(FunctionTypeEnums.SUPPLY_RETURN.getCode())){
                log.info("供应商退回操作");
                supplyReturnService.updateApprove(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),dto.getUnitPrice(),dto.getTollAmount(),date);
            } else if (functionId.equals(FunctionTypeEnums.CUSTOMER_SALE.getCode())) {
                log.info("销售客户维护操作");
                customerSaleService.updateApprove(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),date);
            } else if(functionId.equals(FunctionTypeEnums.SALES_RETURN.getCode())) {
                log.info("销售退回,判断是销售员退回还是厂区退回");
                SalesReturnEntity entity1 = salesReturnDao.selectById(businessId);
                String returnType = entity1.getReturnType();
                if("0".equals(returnType)){
                    log.info("销售员退回，审核通过后需要退回入库方的录入员确认，生成确认队列");
                    log.info("获取退回入库方具有确认权限的人");
                    String userType = "";
                    String inCode = dto.getInCode();
                    if("F".equals(inCode.substring(0,1))){
                        log.info("调入方为厂区");
                        userType = UserTypeEnums.USER_TYPE_FACTORY.getCode();
                    }else{
                        log.info("调入方为仓库");
                        userType = UserTypeEnums.USER_TYPE_STORE.getCode();
                    }
                    List<SysUserEntity> userList = sysUserService.queryUserListByRoleType(userType, RoleAuthorityTypeEnums.ROLE_AUTHORIT_YTYPE_CONFIRM.getCode(),dto.getInCode());
                    if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0){
                        //生成待确认流水
                        ConfirmOperationFlowEntity flowEntity = new ConfirmOperationFlowEntity(null,businessId, FunctionTypeEnums.SALES_RETURN.getCode(),entity.getOperationFlowId(),entity.getSubmitUser(),entity.getSubmitTime(),user.getUserLogin(),date,ApproveStateEnums.APPROVE_STATE_PASSED.getCode(),dto.getOpinion(), ConfirmStateEnums.CONFIRM_STATE_UNDO.getCode(),Constants.SYSTEM_CODE);
                        confirmOperationFlowDao.insert(flowEntity);
                        //生成待确认队列
                        List<ConfirmOperationQueueEntity> queueEntityList = new ArrayList<>();
                        for(SysUserEntity userEntity : userList){
                            ConfirmOperationQueueEntity queueEntity = new ConfirmOperationQueueEntity(null,flowEntity.getId(),businessId,userEntity.getUserLogin(),FunctionTypeEnums.SALES_RETURN.getCode(),entity.getSubmitUser(),entity.getSubmitTime(),user.getUserLogin(),date);
                            queueEntityList.add(queueEntity);
                        }
                        confirmOperationQueueDao.insertBatch(queueEntityList);
                        //更新销售退回表
                        salesReturnService.updateApprove(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),dto.getUnitPrice(),dto.getTollAmount(),date);
                    }else{
                        errorCode = ErrorCodeEnums.CONFIRM_USER_NOT_EXIST.getCode();
                        errortMsg = ErrorCodeEnums.CONFIRM_USER_NOT_EXIST.getDesc();
                    }
                }else{
                    log.info("仓库/厂区退回的，审核通过后结束");
                    salesReturnService.updateApprove(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),dto.getUnitPrice(),dto.getTollAmount(),date);
                }
            } else if (functionId.equals(FunctionTypeEnums.SALERS_ORDER)) {
                log.info("销售员下单，审核通过后需要退回出库方的录入员确认，生成确认队列");
                log.info("获取出库方具有确认权限的人");
                String userType = "";
                String outCode = dto.getOutCode();
                if("F".equals(outCode.substring(0,1))){
                    log.info("出库方为厂区");
                    userType = UserTypeEnums.USER_TYPE_FACTORY.getCode();
                }else{
                    log.info("出库方为仓库");
                    userType = UserTypeEnums.USER_TYPE_STORE.getCode();
                }
                List<SysUserEntity> userList = sysUserService.queryUserListByRoleType(userType, RoleAuthorityTypeEnums.ROLE_AUTHORIT_YTYPE_CONFIRM.getCode(),dto.getInCode());
                if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0){
                    //生成待确认流水
                    ConfirmOperationFlowEntity flowEntity = new ConfirmOperationFlowEntity(null,businessId, FunctionTypeEnums.SALERS_ORDER.getCode(),entity.getOperationFlowId(),entity.getSubmitUser(),entity.getSubmitTime(),user.getUserLogin(),date,ApproveStateEnums.APPROVE_STATE_PASSED.getCode(),dto.getOpinion(), ConfirmStateEnums.CONFIRM_STATE_UNDO.getCode(),Constants.SYSTEM_CODE);
                    confirmOperationFlowDao.insert(flowEntity);
                    //生成待确认队列
                    List<ConfirmOperationQueueEntity> queueEntityList = new ArrayList<>();
                    for(SysUserEntity userEntity : userList){
                        ConfirmOperationQueueEntity queueEntity = new ConfirmOperationQueueEntity(null,flowEntity.getId(),businessId,userEntity.getUserLogin(),FunctionTypeEnums.SALERS_ORDER.getCode(),entity.getSubmitUser(),entity.getSubmitTime(),user.getUserLogin(),date);
                        queueEntityList.add(queueEntity);
                    }
                    confirmOperationQueueDao.insertBatch(queueEntityList);
                    //更新销售员下单表
                    salersOrderService.updateApprove(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),date);
                }else{
                    errorCode = ErrorCodeEnums.CONFIRM_USER_NOT_EXIST.getCode();
                    errortMsg = ErrorCodeEnums.CONFIRM_USER_NOT_EXIST.getDesc();
                }
            }
            log.info("更新审核流水表");
            ApproveOperationFlowEntity flowEntity = approveOperationFlowDao.selectById(entity.getOperationFlowId());
            flowEntity.setApproveUser(user.getUserLogin());
            flowEntity.setApproveState(dto.getResult());
            flowEntity.setApproveTime(date);
            flowEntity.setApproveOpinion(dto.getOpinion());
            approveOperationFlowDao.updateById(flowEntity);
            log.info("删除审核队列");
            int i =approveOperationQueueDao.deleteByFlowId(entity.getOperationFlowId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录审核日志
        String info = "业务类型:"+FunctionTypeEnums.getDescByCode(entity.getFunctionId())+",审核结果:"+ ApproveConfirmResultEnums.getDescByCode(dto.getResult()) +",审核意见:"+dto.getOpinion();
        int i =sysLogService.insertSysLog(FunctionTypeEnums.APPROVE_OPERATION_FLOW.getCode(), OperationTypeEnums.OPERATION_TYPE_APPROVE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(), user.getToken(),Constants.SYSTEM_CODE);
        log.info("审核提交结束");
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public int deleteByFlowId(Long flowId) {
        log.info("删除审核队列开始");
        int i = approveOperationQueueDao.deleteByFlowId(flowId);
        log.info("删除审核队列结束");
        return i;
    }

    @Override
    public int deleteByBusinessId(Long businessId) {
        return approveOperationQueueDao.deleteByBusinessId(businessId);
    }


}
