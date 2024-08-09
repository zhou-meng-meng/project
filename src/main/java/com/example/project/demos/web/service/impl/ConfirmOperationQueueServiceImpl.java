package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.ConfirmOperationFlowDao;
import com.example.project.demos.web.dao.ConfirmOperationQueueDao;
import com.example.project.demos.web.dto.confirmOperationQueue.*;
import com.example.project.demos.web.dto.list.ConfirmOperationQueueInfo;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.ConfirmOperationFlowEntity;
import com.example.project.demos.web.entity.ConfirmOperationQueueEntity;
import com.example.project.demos.web.enums.ApproveConfirmResultEnums;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("confirmOperationQueueService")
public class ConfirmOperationQueueServiceImpl  implements ConfirmOperationQueueService {

    @Resource
    private ConfirmOperationQueueDao confirmOperationQueueDao;
    @Resource
    private ConfirmOperationFlowDao confirmOperationFlowDao;
    @Autowired
    private TransferOutboundService transferOutboundService;

    @Autowired
    private SalersOrderService salersOrderService;
    @Autowired
    private SalersOrderReturnService salersOrderReturnService;
    @Autowired
    private SalesOutboundService salesOutboundService;
    @Autowired
    private SysLogService sysLogService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("确认流水queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            ConfirmOperationQueueInfo ConfirmOperationQueueInfo = confirmOperationQueueDao.selectConfirmOperationQueueInfoById(id);
            outDTO = BeanUtil.copyProperties(ConfirmOperationQueueInfo, QueryByIdOutDTO.class);
            //outDTO.setFunctionName(FunctionTypeEnums.getDescByCode(info.getFunctionId()));
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("确认流水queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("确认流水queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            dto.setConfirmUser(RequestHolder.getUserInfo().getUserLogin());
            //先用查询条件查询总条数
            long total = this.confirmOperationQueueDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<ConfirmOperationQueueInfo> page = new PageImpl<>(this.confirmOperationQueueDao.selectConfirmOperationQueueInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<ConfirmOperationQueueInfo> list = page.toList();
                //转换业务类型
                for(ConfirmOperationQueueInfo info : list){
                    info.setFunctionName(FunctionTypeEnums.getDescByCode(info.getFunctionId()));
                }
                //出参赋值
                outDTO.setConfirmOperationQueueInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("确认流水queryByPage结束");
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) {
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            int i = confirmOperationQueueDao.deleteById(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public DealConfirmQueueOutDTO dealConfirmQueue(DealConfirmQueueDTO dto)  {
        log.info("确认提交开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        DealConfirmQueueOutDTO outDTO = new DealConfirmQueueOutDTO();
        log.info("获取流水详情");
        ConfirmOperationQueueEntity entity = confirmOperationQueueDao.selectById(dto.getId());
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        try{
            String functionId = entity.getFunctionId();
            Long businessId = entity.getBusinessId();
            if(functionId.equals(FunctionTypeEnums.TRANSFER_OUTBOUND.getCode())){
                log.info("调拨出库操作");
                transferOutboundService.updateApprove(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),date);
            } else if(functionId.equals(FunctionTypeEnums.SALERS_ORDER.getCode())){
                log.info("厂区销售员下单操作");
                salersOrderService.updateConfirm(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),date);
            } else if (functionId.equals(FunctionTypeEnums.SALES_OUTBOUND_CHARGE_OFF.getCode())) {
                log.info("销售出库冲销操作");
                salesOutboundService.chargeOffConfirm(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),date);
            }else if (functionId.equals(FunctionTypeEnums.SALERS_ORDER_CHARGE_OFF.getCode())) {
                log.info("业务员下单冲销操作");
                salersOrderService.chargeOffConfirm(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),date);
            }else if (functionId.equals(FunctionTypeEnums.SALERS_ORDER_RETURN.getCode())) {
                log.info("业务员下单退回操作");
                salersOrderReturnService.updateConfirm(businessId,dto.getResult(),dto.getOpinion(),user.getUserLogin(),date);
            }
            log.info("更新确认流水表");
            ConfirmOperationFlowEntity flowEntity = confirmOperationFlowDao.selectById(entity.getConfirmFlowId());
            flowEntity.setConfirmState(dto.getResult());
            flowEntity.setConfirmTime(date);
            flowEntity.setConfirmOpinion(dto.getOpinion());
            flowEntity.setConfirmUser(user.getUserLogin());
            confirmOperationFlowDao.updateById(flowEntity);
            log.info("删除确认队列");
            int i =confirmOperationQueueDao.deleteByFlowId(entity.getConfirmFlowId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录审核日志
        String info = "业务类型:"+FunctionTypeEnums.getDescByCode(entity.getFunctionId())+",确认结果:"+ ApproveConfirmResultEnums.getDescByCode(dto.getResult()) +",确认意见:"+dto.getOpinion();
        int i =sysLogService.insertSysLog(FunctionTypeEnums.CONFIRM_OPERATION_QUEUE.getCode(), OperationTypeEnums.OPERATION_TYPE_CONFIRM.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(), user.getToken(),Constants.SYSTEM_CODE);
        log.info("确认提交结束");
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public int deleteByBusinessId(Long businessId) {
        return confirmOperationQueueDao.deleteByBusinessId(businessId);
    }
}
