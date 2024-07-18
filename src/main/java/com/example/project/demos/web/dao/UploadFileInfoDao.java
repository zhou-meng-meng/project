package com.example.project.demos.web.dao;

import com.example.project.demos.web.entity.UploadFileInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 上传附件信息表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-18 13:45:31
 */
@Mapper
public interface UploadFileInfoDao extends BaseMapperPlus<UploadFileInfoDao, UploadFileInfoEntity> {
	
}
