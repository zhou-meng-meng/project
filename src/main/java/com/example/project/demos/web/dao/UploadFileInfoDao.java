package com.example.project.demos.web.dao;

import com.example.project.demos.web.dto.list.UploadFileInfo;
import com.example.project.demos.web.entity.UploadFileInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 上传附件信息表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-18 13:45:31
 */
@Mapper
public interface UploadFileInfoDao extends BaseMapperPlus<UploadFileInfoDao, UploadFileInfoEntity> {
	List<UploadFileInfoEntity> queryByParam(@Param(value = "businessId") Long businessId );

    int deleteByBusinessId(@Param(value = "businessId") Long businessId);

    int updateByBusinessId(@Param(value = "businessId") Long businessId,@Param(value = "idList") List<Long> idList);

    List<UploadFileInfo> selectUploadFileInfoListByPage(@Param(value = "businessId") Long businessId, @Param("pageable") Pageable pageable);

}
