package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.list.MaterialPackageDetailInfo;
import com.example.project.demos.web.dto.materialPackageDetail.*;

import java.util.List;

/**
 * 物料装袋表-明细
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-09 09:26:13
 */
public interface MaterialPackageDetailService  {

    /**
     * 不分页查询
     * @param packageId
     * @return
     */
    List<MaterialPackageDetailInfo> queryByPackageId(Long packageId);

    int deleteByPackageId(Long packageId);

    boolean insertBatch(Long packageId, List<MaterialPackageDetailInfo> list);
}

