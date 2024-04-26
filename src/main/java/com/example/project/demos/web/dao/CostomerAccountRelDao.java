package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.entity.CostomerAccountRelEntity;
import com.example.project.demos.web.service.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 客户开户账号对应关系表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface CostomerAccountRelDao extends BaseMapperPlus<CostomerAccountRelDao,CostomerAccountRelEntity> {
	
}
