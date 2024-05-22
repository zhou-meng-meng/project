package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.SysLogInfo;
import com.example.project.demos.web.dto.sysLog.QueryByPageDTO;
import com.example.project.demos.web.entity.SysLogEntity;
import com.example.project.demos.web.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-22 15:26:24
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
    List<SysLogInfo> selectSysLogInfoListByPage(@Param("log") QueryByPageDTO queryByPageDTO, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);
}
