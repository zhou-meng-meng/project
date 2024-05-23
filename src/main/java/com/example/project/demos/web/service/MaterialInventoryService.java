package com.example.project.demos.web.service;


import com.example.project.demos.web.dto.materialInventory.QueryByPageDTO;
import com.example.project.demos.web.dto.materialInventory.QueryByPageOutDTO;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-23 14:35:27
 */
public interface MaterialInventoryService  {
    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO);

}

