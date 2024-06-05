package com.example.project.demos.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dto.customerUser.*;
import com.example.project.demos.web.entity.CustomerUser;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.CustomerUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 客户用户表(CustomerUser)表控制层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@RestController
@RequestMapping("customerUser")
@Api(tags="客户用户测试表")
public class CustomerUserController {
    /**
     * 服务对象
     */
    @Resource
    private CustomerUserService customerUserService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryCustomerList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.customerUserService.queryByPage(queryByPageDTO);
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
        QueryByIdOutDTO outDTO = this.customerUserService.queryById(dto.getId());
        return outDTO;
    }

    /**
     * 新增数据
     *
     * @param dto 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    @ApiOperation("新增数据")
    public AddOutDTO add(@RequestBody AddDTO dto) {
        CustomerUser customerUser = BeanUtil.copyProperties(dto,CustomerUser.class);
        boolean f = customerUserService.insert(customerUser);
        AddOutDTO outDTO = new AddOutDTO();
        if(f){
            outDTO.setErrorCode(ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode());
            outDTO.setErrorMsg(ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc());
        }else{
            outDTO.setErrorCode(ErrorCodeEnums.SYS_FAIL_FLAG.getCode());
            outDTO.setErrorMsg(ErrorCodeEnums.SYS_FAIL_FLAG.getDesc());
        }
        return outDTO;
    }

    /**
     * 编辑数据
     *
     * @param dto 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    @ApiOperation("编辑数据")
    public EditOutDTO edit(@RequestBody EditDTO dto) {
        CustomerUser customerUser = BeanUtil.copyProperties(dto,CustomerUser.class);
        boolean f = customerUserService.update(customerUser);
        EditOutDTO outDTO = new EditOutDTO();
        if(f){
            outDTO.setErrorCode(ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode());
            outDTO.setErrorMsg(ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc());
        }else{
            outDTO.setErrorCode(ErrorCodeEnums.SYS_FAIL_FLAG.getCode());
            outDTO.setErrorMsg(ErrorCodeEnums.SYS_FAIL_FLAG.getDesc());
        }
        return outDTO;
    }

    /**
     * 删除数据
     *
     * @param dto 主键
     * @return 删除是否成功
     */
    @PostMapping("/deleteById")
    @ApiOperation("根据ID删除数据")
    public DeleteByIdOutDTO deleteById(@RequestBody DeleteByIdDTO dto) {
        boolean f = customerUserService.deleteById(dto.getId());
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        if(f){
            outDTO.setErrorCode(ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode());
            outDTO.setErrorMsg(ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc());
        }else{
            outDTO.setErrorCode(ErrorCodeEnums.SYS_FAIL_FLAG.getCode());
            outDTO.setErrorMsg(ErrorCodeEnums.SYS_FAIL_FLAG.getDesc());
        }
        return outDTO;
    }

}

