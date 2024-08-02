package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.RebuildInboundInfo;
import com.example.project.demos.web.dto.rebuildInbound.QueryByPageDTO;
import com.example.project.demos.web.entity.RebuildInboundEntity;
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
public interface RebuildInboundDao extends BaseMapper<RebuildInboundEntity> {
    List<RebuildInboundInfo> selectRebuildInboundInfoListByPage(@Param("rebuild") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    RebuildInboundInfo selectRebuildInboundInfoById(Long id);

    List<RebuildInboundInfo> queryListForExport(@Param("rebuild") QueryByPageDTO dto);
}
