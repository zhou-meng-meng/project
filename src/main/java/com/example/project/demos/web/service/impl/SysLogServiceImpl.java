package com.example.project.demos.web.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.example.project.demos.web.dao.SysLogDao;
import com.example.project.demos.web.dto.list.SysLogInfo;
import com.example.project.demos.web.dto.sysLog.QueryByPageDTO;
import com.example.project.demos.web.dto.sysLog.QueryByPageOutDTO;
import com.example.project.demos.web.entity.SysLogEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.service.SysLogService;
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
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {

    @Resource
    private SysLogDao sysLogDao;
    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("日志查询queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.sysLogDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //转换实体入参
                SysLogEntity sysLog = BeanCopyUtils.copy(dto,SysLogEntity.class);
                //开始分页查询
                Page<SysLogInfo> page = new PageImpl<>(this.sysLogDao.selectSysLogInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<SysLogInfo> list = page.toList();
                //处理枚举值
                for(SysLogInfo info : list){
                    info.setFunctionName(FunctionTypeEnums.getDescByCode(info.getFunctionId()));
                    info.setOperationTypeName(OperationTypeEnums.getDescByCode(info.getOperationType()));
                    if(ObjectUtil.isNull(info.getOperationResult())){
                        info.setOperationResultName("未知");
                    }else{
                        info.setOperationResultName(ErrorCodeEnums.getDescByCode(info.getOperationResult()));
                    }
                }
                //出参赋值
                outDTO.setSysLogInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("日志查询queryByPage结束");
        return outDTO;
    }

    @Override
    public int insertSysLog(String functionId, String operationType, String userCode, Date operationTime, String operationInfo, String operationResult, String operationMsg, String ip,String token,String remark) {
        log.info("生成操作日志开始");
        int i = 0;
        try {
            SysLogEntity entity = new SysLogEntity();
            entity.setFunctionId(functionId);
            entity.setOperationType(operationType);
            entity.setUserCode(userCode);
            entity.setOperationTime(operationTime);
            entity.setOperationInfo(operationInfo);
            entity.setOperationResult(operationResult);
            entity.setOperationMsg(operationMsg);
            entity.setLoginIp(ip);
            entity.setToken(token);
            i = sysLogDao.insert(entity);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        log.info("生成操作日志结束");
        return i;
    }


}
