package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.SalersOrderInfo;
import com.example.project.demos.web.dto.salersOrder.QueryByPageDTO;
import com.example.project.demos.web.entity.SalersOrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 业务员下单表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-06-11 10:33:39
 */
@Mapper
public interface SalersOrderDao extends BaseMapper<SalersOrderEntity> {
    List<SalersOrderInfo> selectSalersOrderInfoListByPage(@Param("order") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    SalersOrderInfo selectSalersOrderInfoById(Long id);

    List<SalersOrderInfo> queryListForExport(@Param("order") QueryByPageDTO dto);
}
