package com.example.project.demos.web.service;


import com.example.project.demos.web.dto.approveOperationQueue.*;

import java.net.UnknownHostException;

/**
 * 审核队列表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-26 14:30:10
 */
public interface ApproveOperationQueueService {
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

    DealApproveQueueOutDTO dealApproveQueue(DealApproveQueueDTO dto) ;

    int deleteByFlowId(Long flowId);

    /**
     * 录入员提交的待审核数据   删除时  需要将待审核队列也删除掉
     * @param businessid
     * @return
     */
    int deleteByBusinessId(Long businessid);

    QueryUndoNumOutDTO queryUndoNum(QueryUndoNumDTO dto);
}

