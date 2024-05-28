package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.approveOperationQueue.*;
import com.example.project.demos.web.service.ApproveOperationQueueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.net.UnknownHostException;

/**
 * 审核队列表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-05-08 17:08:07
 */
@RestController
@RequestMapping("approveOperationQueue")
@Api(tags="待审核事项查询-审核队列")
public class ApproveOperationQueueController {
    /**
     * 服务对象
     */
    @Resource
    private ApproveOperationQueueService approveOperationQueueService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryApproveOperationQueueList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.approveOperationQueueService.queryByPage(queryByPageDTO);
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
        QueryByIdOutDTO outDTO = this.approveOperationQueueService.queryById(dto.getId());
        return outDTO;
    }


    @PostMapping("/dealApproveQueue")
    @ApiOperation("通过主键查询单条数据")
    public DealApproveQueueOutDTO dealApproveQueue(@RequestBody DealApproveQueueDTO dto) throws UnknownHostException {
        DealApproveQueueOutDTO outDTO = this.approveOperationQueueService.dealApproveQueue(dto);
        return outDTO;
    }


}
