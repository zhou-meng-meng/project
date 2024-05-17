package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.SysDictTypeInfo;
import com.example.project.demos.web.dto.sysDictType.QueryByPageDTO;
import com.example.project.demos.web.entity.SysDictTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 字典类型表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface SysDictTypeDao extends BaseMapper<SysDictTypeEntity> {
    List<SysDictTypeInfo> queryAllByLimit(@Param("type") SysDictTypeEntity sysDictTypeEntity, @Param("pageable") Pageable pageable);

    /**
     * 不分页
     * @param sysDictTypeEntity
     * @return
     */
    List<SysDictTypeInfo> queryAll(@Param("type") SysDictTypeEntity sysDictTypeEntity);


    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    int checkByType(@Param("type") String type);
}
