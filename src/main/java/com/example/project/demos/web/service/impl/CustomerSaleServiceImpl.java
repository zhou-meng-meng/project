package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.CustomerSaleDao;
import com.example.project.demos.web.dto.customerPayDetail.AddPayBySystemDTO;
import com.example.project.demos.web.dto.customerSale.*;
import com.example.project.demos.web.dto.list.CustomerAccountRelInfo;
import com.example.project.demos.web.dto.list.CustomerSaleInfo;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.CustomerSaleEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.SysEnums;
import com.example.project.demos.web.enums.UserTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.CustomerAccountRelService;
import com.example.project.demos.web.service.CustomerPayDetailService;
import com.example.project.demos.web.service.CustomerSaleService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("customerSaleService")
public class CustomerSaleServiceImpl implements CustomerSaleService {

    @Resource
    private CustomerSaleDao customerSaleDao;

    @Autowired
    private CustomerAccountRelService customerAccountRelService;

    @Autowired
    private CustomerPayDetailService customerPayDetailService;

    @Override
    public QueryByIdOutDTO queryById(QueryByIdDTO dto) {
        log.info("销售客户queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            CustomerSaleInfo customerSaleInfo = customerSaleDao.selectCustomerSaleInfoById(dto.getId());
            outDTO = BeanUtil.copyProperties(customerSaleInfo, QueryByIdOutDTO.class);
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
        log.info("销售客户queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("销售客户queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //权限判断  总公司审核人员可查看所有数据 其他人员只能看属于自己的客户数据
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            List<String> authList = user.getAuthorityType();
            if(authList.contains("0")){
                log.info("具有审核权限，可以查看所有数据");
            }else{
                log.info("不具有审核权限，只能查看自己的客户信息");
                queryByPageDTO.setSaler(user.getUserLogin());
            }
            //先用查询条件查询总条数
            long total = this.customerSaleDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                CustomerSaleEntity customerSale = BeanCopyUtils.copy(queryByPageDTO,CustomerSaleEntity.class);
                //开始分页查询
                Page<CustomerSaleInfo> page = new PageImpl<>(this.customerSaleDao.selectCustomerSaleInfoListByPage(customerSale, pageRequest), pageRequest, total);
                //获取分页数据
                List<CustomerSaleInfo> list = page.toList();
                //出参赋值
                outDTO.setCustomerSaleInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售客户queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        try{
            CustomerSaleEntity customerSaleEntity = BeanCopyUtils.copy(dto,CustomerSaleEntity.class);
            customerSaleEntity.setCreateBy(RequestHolder.getUserInfo().getUserLogin());
            customerSaleEntity.setCreateTime(date);
            int i = customerSaleDao.insert(customerSaleEntity);
            //添加账号对应关系
            customerAccountRelService.savaBatch(dto.getCode(),dto.getList());
            log.info("添加一条默认往来账信息，金额都为0");
            AddPayBySystemDTO addDTO = new AddPayBySystemDTO(null,dto.getCode(),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),"0",Constants.SYSTEM_CODE,Constants.SYSTEM_CODE,date,"默认往来账");
            customerPayDetailService.addPayBySystem(addDTO);
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
            CustomerSaleEntity customerSaleEntity = BeanCopyUtils.copy(dto,CustomerSaleEntity.class);
            customerSaleEntity.setCreateBy("zhangyunning");
            customerSaleEntity.setUpdateTime(new Date());
            int i = customerSaleDao.updateById(customerSaleEntity);
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
            int i = customerSaleDao.deleteById(dto.getId());
            //删除客户对应账号
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
