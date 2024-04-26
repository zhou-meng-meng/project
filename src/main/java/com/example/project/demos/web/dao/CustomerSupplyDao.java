package com.example.project.demos.web.dao;

import com.example.project.demos.web.entity.CustomerSupplyEntity;
import com.example.project.demos.web.service.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 * 供货商客户维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface CustomerSupplyDao extends BaseMapperPlus<CustomerSupplyDao,CustomerSupplyEntity> {
	
}
