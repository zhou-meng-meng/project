package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.materialInventory.*;
import com.example.project.demos.web.service.MaterialInventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 实时库存(material_inventory)表控制层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@RestController
@RequestMapping("materialInventory")
@Api(tags="实时库存")
public class MaterialInventoryController extends BaseController{
    /**
     * 服务对象
     */
    @Resource
    private MaterialInventoryService MaterialInventoryService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryPageList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.MaterialInventoryService.queryByPage(queryByPageDTO);
        return outDTO;
    }

}

