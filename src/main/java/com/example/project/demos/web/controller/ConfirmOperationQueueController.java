package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.confirmOperationQueue.*;
import com.example.project.demos.web.service.ConfirmOperationQueueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 确认队列表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 17:08:07
 */
@RestController
@RequestMapping("confirmOperationQueue")
@Api(tags="待确认事项查询-确认队列")
public class ConfirmOperationQueueController {
    /**
     * 服务对象
     */
    @Resource
    private ConfirmOperationQueueService confirmOperationQueueService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryConfirmOperationQueueList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.confirmOperationQueueService.queryByPage(queryByPageDTO);
        return outDTO;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param dto 主键
     * @return 单条数据
     */
    @PostMapping("/queryById")
    @ApiOperation("通过主键查询单条数据")
    public QueryByIdOutDTO queryById(@RequestBody QueryByIdDTO dto) {
        QueryByIdOutDTO outDTO = this.confirmOperationQueueService.queryById(dto.getId());
        return outDTO;
    }


    @PostMapping("/dealConfirmQueue")
    @ApiOperation("确认提交操作")
    public DealConfirmQueueOutDTO dealApproveQueue(@RequestBody DealConfirmQueueDTO dto)  {
        DealConfirmQueueOutDTO outDTO = this.confirmOperationQueueService.dealConfirmQueue(dto);
        return outDTO;
    }


}
