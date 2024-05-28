package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.*;
import com.example.project.demos.web.dto.sysStorehouse.QueryByPageDTO;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 仓库维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface SysStorehouseDao extends BaseMapper<SysStorehouseEntity> {
    List<SysStorehouseInfo> selectSysStorehouseInfoListByPage(@Param("storehouse") SysStorehouseEntity sysStorehouseEntity, @Param("pageable") Pageable pageable);

    //不分页
    List<SysStorehouseInfo> selectStorehouseInfoList(@Param("storehouse") SysStorehouseEntity sysStorehouseEntity);
    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    SysStorehouseInfo selectSysStorehouseInfoById(Long id);
    SysStorehouseInfo selectSysStorehouseInfoByCode(String code);

    int checkCode(@Param("code") String code);
	
}
