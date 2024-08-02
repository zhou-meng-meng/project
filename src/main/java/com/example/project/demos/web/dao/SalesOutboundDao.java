package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.SalesOutboundInfo;
import com.example.project.demos.web.dto.salesOutbound.QueryByPageDTO;
import com.example.project.demos.web.entity.SalesOutboundEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 销售出库表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-24 14:38:26
 */
@Mapper
public interface SalesOutboundDao extends BaseMapper<SalesOutboundEntity> {
    List<SalesOutboundInfo> selectSalesOutboundInfoListByPage(@Param("sale") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    SalesOutboundInfo selectSalesOutboundInfoById(Long id);

    List<SalesOutboundInfo> queryListForExport(@Param("sale") QueryByPageDTO dto);

    List<String> queryBillNoListByParam(@Param("billNoPrefix") String billNoPrefix);
}
