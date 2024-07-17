package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.RawMaterialOutboundInfo;
import com.example.project.demos.web.dto.rawMaterialOutbound.QueryByPageDTO;
import com.example.project.demos.web.entity.RawMaterialOutboundEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 原材料出库表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-10 16:46:15
 */
@Mapper
public interface RawMaterialOutboundDao extends BaseMapper<RawMaterialOutboundEntity> {
    List<RawMaterialOutboundInfo> selectRawMaterialOutboundInfoListByPage(@Param("raw") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    RawMaterialOutboundInfo selectRawMaterialOutboundInfoById(Long id);

    List<RawMaterialOutboundInfo> queryListForExport(@Param("raw") QueryByPageDTO dto);

    List<String> queryBillNoListByParam(@Param("billNoPrefix") String billNoPrefix);
}
