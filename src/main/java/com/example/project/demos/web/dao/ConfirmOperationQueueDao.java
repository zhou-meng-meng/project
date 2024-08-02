package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.confirmOperationQueue.QueryByPageDTO;
import com.example.project.demos.web.dto.list.ConfirmOperationQueueInfo;
import com.example.project.demos.web.entity.ConfirmOperationQueueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 确认队列表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-26 14:30:10
 */
@Mapper
public interface ConfirmOperationQueueDao extends BaseMapperPlus<ConfirmOperationQueueDao,ConfirmOperationQueueEntity> {
    List<ConfirmOperationQueueInfo> selectConfirmOperationQueueInfoListByPage(@Param("queue") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    ConfirmOperationQueueInfo selectConfirmOperationQueueInfoById(Long id);

    int deleteByFlowId(Long flowId);

    int deleteByBusinessId(Long businessId);

    int queryConfirmUnDoNum(String userLogin);


}
