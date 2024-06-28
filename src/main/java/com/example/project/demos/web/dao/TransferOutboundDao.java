package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.TransferOutboundInfo;
import com.example.project.demos.web.dto.transferOutbound.QueryByPageDTO;
import com.example.project.demos.web.entity.TransferOutboundEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 调拨出库表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-11 16:09:49
 */
@Mapper
public interface TransferOutboundDao extends BaseMapper<TransferOutboundEntity> {
    List<TransferOutboundInfo> selectTransferOutboundInfoListByPage(@Param("transfer") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    TransferOutboundInfo selectTransferOutboundInfoById(Long id);

    List<TransferOutboundInfo> queryListForExport(@Param("transfer") QueryByPageDTO dto);
}
