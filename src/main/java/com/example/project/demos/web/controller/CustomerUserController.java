package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.customerUser.QueryByPageDTO;
import com.example.project.demos.web.dto.customerUser.QueryByPageOutDTO;
import com.example.project.demos.web.entity.CustomerUser;
import com.example.project.demos.web.service.CustomerUserService;
import com.example.project.demos.web.utils.R;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
public class CustomerUserController extends BaseController{
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
    public QueryByPageOutDTO queryByPage(@RequestBody QueryByPageDTO queryByPageDTO) {
        QueryByPageOutDTO outDTO = this.customerUserService.queryByPage(queryByPageDTO);
        return outDTO;
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<CustomerUser> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.customerUserService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param customerUser 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public R<Void> add(@RequestBody CustomerUser customerUser) {
        return toAjax(customerUserService.insert(customerUser));
    }

    /**
     * 编辑数据
     *
     * @param customerUser 实体
     * @return 编辑结果
     */
    @PostMapping("/edit")
    public R<Void> edit(@RequestBody CustomerUser customerUser) {
        return toAjax(this.customerUserService.update(customerUser));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/{id}")
    public R<Void> deleteById(@PathVariable("id") Long id) {
        return toAjax(this.customerUserService.deleteById(id));
    }

}

