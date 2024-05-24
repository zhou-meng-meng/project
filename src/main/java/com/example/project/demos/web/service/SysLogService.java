package com.example.project.demos.web.service;


import com.example.project.demos.web.dto.sysLog.*;

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
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO);
}

