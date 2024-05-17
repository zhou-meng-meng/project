package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.SysDictDataInfo;
import com.example.project.demos.web.entity.SysDictDataEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 字典数据表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface SysDictDataDao extends BaseMapper<SysDictDataEntity> {
	List<SysDictDataInfo> queryList(@Param(value = "type") String type);

	int deleteByType(@Param(value = "type") String type);

	int checkByValue(@Param(value = "type") String type,@Param(value = "code") String code,@Param(value = "isDefault") String isDefault);
}
