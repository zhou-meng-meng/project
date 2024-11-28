package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.MaterialPackageDetailDao;
import com.example.project.demos.web.dto.list.MaterialPackageDetailInfo;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.MaterialPackageDetailEntity;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.MaterialPackageDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("materialPackageDetailService")
public class MaterialPackageDetailServiceImpl  implements MaterialPackageDetailService {

    @Resource
    private MaterialPackageDetailDao materialPackageDetailDao;


    @Override
    public List<MaterialPackageDetailInfo> queryByPackageId(Long packageId) {
        List<MaterialPackageDetailInfo> list = materialPackageDetailDao.selectMaterialPackageDetailInfoList(packageId);
        return list;
    }

    @Override
    public int deleteByPackageId(Long packageId) {
        int i = materialPackageDetailDao.deleteByPackageId(packageId);
        return i;
    }

    @Override
    public boolean insertBatch(Long packageId, List<MaterialPackageDetailInfo> list) {
        List<MaterialPackageDetailEntity> entityList = new ArrayList<>();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        for(MaterialPackageDetailInfo info : list){
            MaterialPackageDetailEntity entity = new MaterialPackageDetailEntity();
            entity.setPackageId(packageId);
            entity.setMaterialCode(info.getMaterialCode());
            entity.setPotWeight(info.getPotWeight());
            entityList.add(entity);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(new Date());
        }
        boolean f = materialPackageDetailDao.insertBatch(entityList);
        return f;
    }

}
