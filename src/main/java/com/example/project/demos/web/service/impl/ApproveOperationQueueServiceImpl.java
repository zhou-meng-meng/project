package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.ApproveOperationFlowDao;
import com.example.project.demos.web.dao.ApproveOperationQueueDao;
import com.example.project.demos.web.dto.approveOperationQueue.*;
import com.example.project.demos.web.dto.list.ApproveOperationQueueInfo;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.ApproveOperationFlowEntity;
import com.example.project.demos.web.entity.ApproveOperationQueueEntity;
import com.example.project.demos.web.enums.ApproveConfirmResultEnums;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.ApproveOperationQueueService;
import com.example.project.demos.web.service.RawMaterialIncomeService;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.UnknownHostException;
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
    public DealApproveQueueOutDTO dealApproveQueue(DealApproveQueueDTO dto) throws UnknownHostException {
        log.info("审核提交开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        DealApproveQueueOutDTO outDTO = new DealApproveQueueOutDTO();
        log.info("获取流水详情");
        ApproveOperationQueueEntity entity = approveOperationQueueDao.selectById(dto.getId());
        String userLogin = RequestHolder.getUserInfo().getUserLogin();
        Date date = new Date();
        try{
            String functionId = entity.getFunctionId();
            Long businessId = entity.getBusinessId();
            if(functionId.equals(FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode())){
                log.info("来料入库操作");
                rawMaterialIncomeService.updateApprove(businessId,dto.getResult(),dto.getOpinion(),userLogin,dto.getUnitPrice(),dto.getTollAmount(),date);
            }
            log.info("更新审核流水表");
            ApproveOperationFlowEntity flowEntity = approveOperationFlowDao.selectById(entity.getOperationFlowId());
            flowEntity.setApproveUser(userLogin);
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
        int i =sysLogService.insertSysLog(FunctionTypeEnums.APPROVE_OPERATION_FLOW.getCode(), OperationTypeEnums.OPERATION_TYPE_APPROVE.getCode(),userLogin,date,info,errorCode,errortMsg,"system");
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


}
