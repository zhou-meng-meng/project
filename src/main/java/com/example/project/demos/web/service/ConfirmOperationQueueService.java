package com.example.project.demos.web.service;


import com.example.project.demos.web.dto.approveOperationQueue.DealApproveQueueDTO;
import com.example.project.demos.web.dto.approveOperationQueue.DealApproveQueueOutDTO;
import com.example.project.demos.web.dto.confirmOperationQueue.*;

import java.util.Map;

/**
 * 确认队列表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-26 14:30:10
 */
public interface ConfirmOperationQueueService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    QueryByIdOutDTO queryById(Long id);

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO);

    /**
     * 通过主键删除数据
     *
     * @param dto 主键
     * @return 是否成功
     */
    DeleteByIdOutDTO deleteById(DeleteByIdDTO dto);

    int deleteByBusinessId(Long businessId);

    DealConfirmQueueOutDTO dealConfirmQueue(DealConfirmQueueDTO dto) ;

}

