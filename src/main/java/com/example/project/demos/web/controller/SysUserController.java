package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.list.SysUserInfo;
import com.example.project.demos.web.dto.sysUser.*;
import com.example.project.demos.web.service.SysUserService;
import com.example.project.demos.web.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * 用户维护表(sys_user)表控制层
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@RestController
@RequestMapping("sysUser")
@Api(tags="用户维护表")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService sysUserService;

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryPopByPageList")
    @ApiOperation("查询列表(弹窗)")
    public QueryPopByPageOutDTO queryPopByPageList(@RequestBody QueryPopByPageDTO queryByPageDTO) {
        QueryPopByPageOutDTO outDTO = this.sysUserService.queryPopByPage(queryByPageDTO);
        return outDTO;
    }

    /**
     * 分页查询
     *
     * @param queryByPageDTO 筛选条件
     * @return 查询结果
     */
    @PostMapping("/querySysUserList")
    @ApiOperation("查询列表(分页)")
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.sysUserService.queryByPage(queryByPageDTO);
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
        QueryByIdOutDTO outDTO = this.sysUserService.queryById(dto.getId());
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
        AddOutDTO outDTO = sysUserService.insert(dto);
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
        EditOutDTO outDTO = sysUserService.update(dto);
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
        DeleteByIdOutDTO outDTO = sysUserService.deleteById(dto);
        return outDTO;
    }

    /**
     * 用户登录
     */
    @PostMapping("/userLogin")
    @ApiOperation("用户登录")
    public UserLoginOutDTO userLogin(@RequestBody UserLoginDTO dto) {
        UserLoginOutDTO outDTO = sysUserService.userLogin(dto);
        return outDTO;
    }

    /**
     * 重置密码操作
     */
    @PostMapping("/resetPwd")
    @ApiOperation("重置密码操作")
    public ResetPwdOutDTO resetPwd(@RequestBody ResetPwdDTO dto) {
        ResetPwdOutDTO outDTO = sysUserService.restPwd(dto);
        return outDTO;
    }

    /**
     * 重置密码操作
     */
    @PostMapping("/updatePwd")
    @ApiOperation("修改密码操作")
    public UpdatePwdOutDTO updatePwd(@RequestBody UpdatePwdDTO dto) {
        UpdatePwdOutDTO outDTO = sysUserService.updatePwd(dto);
        return outDTO;
    }

    /**
     * 导出用户列表
     */
    @PostMapping("/export")
    @ApiOperation("导出用户列表")
    public void export(@RequestBody QueryByPageDTO dto ) {
        List<SysUserInfo> list = sysUserService.queryListForExport(dto);
        ExcelUtil.exportExcel(list, "用户列表", SysUserInfo.class);
    }

}

