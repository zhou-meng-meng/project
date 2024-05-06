package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.project.demos.web.dto.customerUser.QueryByIdOutDTO;
import com.example.project.demos.web.dto.customerUser.QueryByPageDTO;
import com.example.project.demos.web.dto.customerUser.QueryByPageOutDTO;
import com.example.project.demos.web.dto.list.CustomerUserInfo;
import com.example.project.demos.web.entity.CustomerUser;
import com.example.project.demos.web.dao.CustomerUserDao;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.CustomerUserService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageQuery;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageImpl;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户用户表(CustomerUser)表服务实现类
 *
 * @author makejava
 * @since 2024-02-26 20:37:53
 */
@Service("customerUserService")
@Slf4j
public class CustomerUserServiceImpl implements CustomerUserService {
    @Resource
    private CustomerUserDao customerUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public QueryByIdOutDTO queryById(Long id) {
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            CustomerUser customerUser = this.customerUserDao.selectById(id);
            outDTO = BeanUtil.copyProperties(customerUser,QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info(queryByPageDTO.toString());
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.customerUserDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                CustomerUser customerUser = BeanCopyUtils.copy(queryByPageDTO,CustomerUser.class);
                //开始分页查询
                Page<CustomerUser> page = new PageImpl<>(this.customerUserDao.queryAllByLimit(customerUser, pageRequest), pageRequest, total);
                //获取分页数据
                List<CustomerUser> list = page.toList();
                //出参赋值
                outDTO.setCustomerUserList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    /**
     * 新增数据
     *
     * @param customerUser 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(CustomerUser customerUser) {
        int i = customerUserDao.insert(customerUser);
        return i >0;
    }

    /**
     * 修改数据
     *
     * @param customerUser 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(CustomerUser customerUser) {
        int i =this.customerUserDao.updateById(customerUser);
        return i >0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.customerUserDao.deleteById(id) > 0;
    }
}
