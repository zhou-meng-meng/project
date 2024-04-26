package com.example.project.demos.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.project.demos.web.entity.SysDeptEntity;

import java.util.Map;

/**
 * 部门表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-04-24 13:42:58
 */
public interface SysDeptService extends IService<SysDeptEntity> {
    int insert(SysDeptEntity entity);
}

