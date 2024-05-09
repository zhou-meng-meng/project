package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.CustomerSupplyDao;
import com.example.project.demos.web.dto.list.CustomerAccountRelInfo;
import com.example.project.demos.web.dto.list.CustomerSupplyInfo;
import com.example.project.demos.web.dto.customerSupply.*;
import com.example.project.demos.web.entity.CustomerSupplyEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.CustomerAccountRelService;
import com.example.project.demos.web.service.CustomerSupplyService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("customerSupplyService")
public class CustomerSupplyServiceImpl  implements CustomerSupplyService {

    @Resource
    private CustomerSupplyDao customerSupplyDao;
    @Autowired
    private CustomerAccountRelService customerAccountRelService;

    @Override
    public QueryByIdOutDTO queryById(QueryByIdDTO dto) {
        log.info("供应商客户queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            CustomerSupplyInfo CustomerSupplyInfo = customerSupplyDao.selectCustomerSupplyInfoById(dto.getId());
            outDTO = BeanUtil.copyProperties(CustomerSupplyInfo, QueryByIdOutDTO.class);
            //查询对应账户
            List<CustomerAccountRelInfo> list  = customerAccountRelService.queryRelListByCustomerCode(dto.getCode());
            outDTO.setAccountRelList(list);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("供应商客户queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("供应商客户queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.customerSupplyDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                CustomerSupplyEntity CustomerSupply = BeanCopyUtils.copy(queryByPageDTO,CustomerSupplyEntity.class);
                //开始分页查询
                Page<CustomerSupplyInfo> page = new PageImpl<>(this.customerSupplyDao.selectCustomerSupplyInfoListByPage(CustomerSupply, pageRequest), pageRequest, total);
                //获取分页数据
                List<CustomerSupplyInfo> list = page.toList();
                //出参赋值
                outDTO.setCustomerSupplyInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("供应商客户queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            CustomerSupplyEntity CustomerSupplyEntity = BeanCopyUtils.copy(dto,CustomerSupplyEntity.class);
            CustomerSupplyEntity.setCreateBy("zhangyunning");
            CustomerSupplyEntity.setCreateTime(new Date());
            int i = customerSupplyDao.insert(CustomerSupplyEntity);
            //添加账号对应关系
            customerAccountRelService.savaBatch(dto.getCode(),dto.getList());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public EditOutDTO update(EditDTO dto) {
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            CustomerSupplyEntity CustomerSupplyEntity = BeanCopyUtils.copy(dto,CustomerSupplyEntity.class);
            CustomerSupplyEntity.setCreateBy("zhangyunning");
            CustomerSupplyEntity.setUpdateTime(new Date());
            int i = customerSupplyDao.updateById(CustomerSupplyEntity);
            //先删除原账号对应关系
            customerAccountRelService.deleteRelByCustomerCode(dto.getCode());
            //重新插入账号对应关系
            customerAccountRelService.savaBatch(dto.getCode(),dto.getList());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) {
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            int i = customerSupplyDao.deleteById(dto.getId());
            //删除该客户账户
            customerAccountRelService.deleteRelByCustomerCode(dto.getCode());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }
}
