package com.example.project.demos.web.service;


import com.example.project.demos.web.dto.sysLog.*;
import java.util.Date;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-22 15:26:24
 */
public interface SysLogService  {
    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return 查询结果
     */
    QueryByPageOutDTO queryByPage(QueryByPageDTO dto);

    int insertSysLog(String functionId, String operationType, String userCode, Date operationTime,String operationInfo,String operationResult,String operationMsg,String ip,String token,String remark);
}

