package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.SysDeptDao;
import com.example.project.demos.web.entity.SysDeptEntity;
import com.example.project.demos.web.service.SysDeptService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    private SysDeptDao sysDeptDao;

    @Override
    public int insert(SysDeptEntity entity) {
        return sysDeptDao.insert(entity);
    }
}
