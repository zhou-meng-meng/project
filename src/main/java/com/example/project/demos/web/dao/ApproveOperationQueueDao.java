package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.approveOperationQueue.QueryByPageDTO;
import com.example.project.demos.web.dto.list.ApproveOperationQueueInfo;
import com.example.project.demos.web.entity.ApproveOperationQueueEntity;
import com.example.project.demos.web.dao.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 审核队列表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-26 14:30:10
 */
@Mapper
public interface ApproveOperationQueueDao extends BaseMapperPlus<ApproveOperationQueueDao,ApproveOperationQueueEntity> {
    List<ApproveOperationQueueInfo> selectApproveOperationQueueInfoListByPage(@Param("queue") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    ApproveOperationQueueInfo selectApproveOperationQueueInfoById(Long id);

    int deleteByFlowId(Long flowId);

    int deleteByBusinessId(Long businessId);
}
