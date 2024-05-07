package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.SysDeptInfo;
import com.example.project.demos.web.dto.sysDept.QueryByPageDTO;
import com.example.project.demos.web.entity.SysDeptEntity;
import com.example.project.demos.web.entity.SysDeptEntity;
import com.example.project.demos.web.service.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 部门表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {
    List<SysDeptInfo> selectSysDeptInfoListByPage(@Param("dept") SysDeptEntity sysDeptEntity, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    SysDeptInfo selectSysDeptInfoById(Long id);
}
