package com.example.project.demos.web.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.project.demos.web.dto.list.SysFactoryAndStorePopInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.sysFactory.*;
import com.example.project.demos.web.entity.SysFactoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 工厂维护表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
@Mapper
public interface SysFactoryDao extends BaseMapper<SysFactoryEntity> {
    List<SysFactoryInfo> selectSysFactoryInfoListByPage(@Param("factory") SysFactoryEntity sysFactoryEntity, @Param("pageable") Pageable pageable);

    //不分页
    List<SysFactoryInfo> selectSysFactoryInfoList(@Param("factory") SysFactoryEntity sysFactoryEntity);

    /**
     * 统计总行数
     *
     * @param queryByPageDTO 查询条件
     * @return 总行数
     */
    int count(QueryByPageDTO queryByPageDTO);

    SysFactoryInfo selectSysFactoryInfoById(Long id);

    SysFactoryInfo selectSysFactoryInfoByCode(String code);

    int checkCode(@Param("code") String code);

    /**
     * 厂区和仓库弹窗数量查询
     * @param code
     * @param name
     * @param type
     * @return
     */
    int countPop(@Param("code") String code,@Param("name") String name,@Param("type") String type);

    /**
     * 厂区和仓库弹窗l列表查询
     * @param code
     * @param name
     * @param type
     * @return
     */
    List<SysFactoryAndStorePopInfo> queryPopListByPage(@Param("code") String code,@Param("name") String name,@Param("type") String type,@Param("pageable") Pageable pageable);
}
