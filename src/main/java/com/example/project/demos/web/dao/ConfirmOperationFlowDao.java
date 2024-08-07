package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.confirmOperationFlow.QueryByPageDTO;
import com.example.project.demos.web.dto.list.ConfirmOperationFlowInfo;
import com.example.project.demos.web.entity.ConfirmOperationFlowEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 确认流水表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-26 14:30:10
 */
@Mapper
public interface ConfirmOperationFlowDao extends BaseMapper<ConfirmOperationFlowEntity> {
    List<ConfirmOperationFlowInfo> selectConfirmOperationFlowInfoListByPage(@Param("flow") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    ConfirmOperationFlowInfo selectConfirmOperationFlowInfoById(Long id);

    int deleteByBusinessId(Long businessId);
}
