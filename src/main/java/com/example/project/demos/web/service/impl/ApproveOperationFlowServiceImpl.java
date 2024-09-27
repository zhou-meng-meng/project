package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.ApproveOperationFlowDao;
import com.example.project.demos.web.dto.list.ApproveOperationFlowInfo;
import com.example.project.demos.web.dto.approveOperationFlow.*;
import com.example.project.demos.web.entity.ApproveOperationFlowEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.ApproveOperationFlowService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("approveOperationFlowService")
public class ApproveOperationFlowServiceImpl  implements ApproveOperationFlowService {
    @Resource
    private ApproveOperationFlowDao approveOperationFlowDao;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("审核流水queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            ApproveOperationFlowInfo ApproveOperationFlowInfo = approveOperationFlowDao.selectApproveOperationFlowInfoById(id);
            outDTO = BeanUtil.copyProperties(ApproveOperationFlowInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("审核流水queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("审核流水queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //只能查询自己审核过的记录
            dto.setApproveUser(RequestHolder.getUserInfo().getUserLogin());
            //先用查询条件查询总条数
            long total = this.approveOperationFlowDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<ApproveOperationFlowInfo> page = new PageImpl<>(this.approveOperationFlowDao.selectApproveOperationFlowInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<ApproveOperationFlowInfo> list = page.toList();
                //赋值业务类型
                for(ApproveOperationFlowInfo info : list){
                    info.setFunctionName(FunctionTypeEnums.getDescByCode(info.getFunctionId()));
                }
                //出参赋值
                outDTO.setApproveOperationFlowInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("审核流水queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            ApproveOperationFlowEntity approveOperationFlowEntity = BeanCopyUtils.copy(dto,ApproveOperationFlowEntity.class);
            int i = approveOperationFlowDao.insert(approveOperationFlowEntity);
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public EditOutDTO update(EditDTO dto) {
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            ApproveOperationFlowEntity ApproveOperationFlowEntity = BeanCopyUtils.copy(dto,ApproveOperationFlowEntity.class);
            ApproveOperationFlowEntity.setCreateBy("zhangyunning");
            ApproveOperationFlowEntity.setUpdateTime(new Date());
            int i = approveOperationFlowDao.updateById(ApproveOperationFlowEntity);
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
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
            int i = approveOperationFlowDao.deleteById(dto.getId());
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public int deleteByBusinessId(Long businessId) {
        return approveOperationFlowDao.deleteByBusinessId(businessId);
    }

}
