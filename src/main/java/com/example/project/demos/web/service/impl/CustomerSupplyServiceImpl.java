package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.CustomerSupplyDao;
import com.example.project.demos.web.dto.customerPayDetail.AddPayBySystemDTO;
import com.example.project.demos.web.dto.list.CustomerAccountRelInfo;
import com.example.project.demos.web.dto.list.CustomerSupplyInfo;
import com.example.project.demos.web.dto.customerSupply.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.CustomerSupplyEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.SysEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("customerSupplyService")
public class CustomerSupplyServiceImpl  implements CustomerSupplyService {

    @Resource
    private CustomerSupplyDao customerSupplyDao;
    @Autowired
    private CustomerAccountRelService customerAccountRelService;

    @Autowired
    private CustomerPayDetailService customerPayDetailService;
    @Autowired
    private SysLogService sysLogService;

    @Override
    public QueryByIdOutDTO queryById(QueryByIdDTO dto) {
        log.info("供应商客户queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            CustomerSupplyInfo info = customerSupplyDao.selectCustomerSupplyInfoById(dto.getId());
            outDTO = BeanUtil.copyProperties(info, QueryByIdOutDTO.class);
            //查询对应账户
            List<CustomerAccountRelInfo> list  = customerAccountRelService.queryRelListByCustomerCode(dto.getId());
            outDTO.setAccountRelList(list);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("供应商客户queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("供应商客户queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //数据权限  总公司审核人和财务可使用此菜单 因为不需要添加数据权限
            //先用查询条件查询总条数
            long total = this.customerSupplyDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //转换实体入参
                CustomerSupplyEntity entity = BeanCopyUtils.copy(dto,CustomerSupplyEntity.class);
                //开始分页查询
                Page<CustomerSupplyInfo> page = new PageImpl<>(this.customerSupplyDao.selectCustomerSupplyInfoListByPage(entity, pageRequest), pageRequest, total);
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
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            CustomerSupplyEntity entity = BeanCopyUtils.copy(dto,CustomerSupplyEntity.class);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            int i = customerSupplyDao.insert(entity);
            //添加账号对应关系
            customerAccountRelService.savaBatch(entity.getId(), dto.getList());
            log.info("添加一条默认往来账信息，金额都为0");
            AddPayBySystemDTO addDTO = new AddPayBySystemDTO(null,dto.getCode(),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),"1",SysEnums.SYS_YES_FLAG.getCode(),Constants.SYSTEM_CODE,date,"默认往来账");
            customerPayDetailService.addPayBySystem(addDTO);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "客户编号:"+ dto.getCode() +",客户名称:"+dto.getName()+",联系人:"+dto.getLinkUser()+",联系电话:"+dto.getLinkPhoneNo()+",地址:"+dto.getAddress()+",传真:"+dto.getFaxNo();
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_SUPPLY.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public EditOutDTO update(EditDTO dto) {
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            CustomerSupplyEntity entity = BeanCopyUtils.copy(dto,CustomerSupplyEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = customerSupplyDao.updateById(entity);
            //先删除原账号对应关系
            customerAccountRelService.deleteRelByCustomerCode(entity.getId());
            //重新插入账号对应关系
            customerAccountRelService.savaBatch(entity.getId(), dto.getList());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "客户编号:"+ dto.getCode() +",客户名称:"+dto.getName()+",联系人:"+dto.getLinkUser()+",联系电话:"+dto.getLinkPhoneNo()+",地址:"+dto.getAddress()+",传真:"+dto.getFaxNo();
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_SUPPLY.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) {
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            int i = customerSupplyDao.deleteById(dto.getId());
            //删除该客户账户
            customerAccountRelService.deleteRelByCustomerCode(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "客户编号:"+ dto.getCode() +",客户名称:"+dto.getName()+",联系人:"+dto.getLinkUser()+",联系电话:"+dto.getLinkPhoneNo()+",地址:"+dto.getAddress()+",传真:"+dto.getFaxNo();
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_SUPPLY.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public List<CustomerSupplyInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("供应商客户queryListForExport开始");
        List<CustomerSupplyInfo> list = new ArrayList<>();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        try {
            //转换实体入参
            CustomerSupplyEntity entity = BeanCopyUtils.copy(dto,CustomerSupplyEntity.class);
            list = customerSupplyDao.queryListForExport(entity);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_SUPPLY.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("供应商客户queryListForExport结束");
        return list;
    }
}
