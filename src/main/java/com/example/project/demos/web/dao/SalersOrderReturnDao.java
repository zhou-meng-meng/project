package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.SalersOrderReturnInfo;
import com.example.project.demos.web.dto.salersOrderReturn.QueryByPageDTO;
import com.example.project.demos.web.entity.SalersOrderReturnEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-23 13:55:47
 */
@Mapper
public interface SalersOrderReturnDao extends BaseMapper<SalersOrderReturnEntity> {

    List<SalersOrderReturnInfo> selectSalersOrderReturnInfoListByPage(@Param("return") QueryByPageDTO dto, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    SalersOrderReturnInfo selectSalersOrderReturnInfoById(Long id);
}
