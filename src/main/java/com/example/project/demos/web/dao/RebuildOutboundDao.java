package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.RebuildOutboundInfo;
import com.example.project.demos.web.dto.rebuildOutbound.QueryByPageDTO;
import com.example.project.demos.web.entity.RebuildOutboundEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 11:13:27
 */
@Mapper
public interface RebuildOutboundDao extends BaseMapper<RebuildOutboundEntity> {
    List<RebuildOutboundInfo> selectRebuildOutboundInfoListByPage(@Param("rebuild") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    RebuildOutboundInfo selectRebuildOutboundInfoById(Long id);
}
