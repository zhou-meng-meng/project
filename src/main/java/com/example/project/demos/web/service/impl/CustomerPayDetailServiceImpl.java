package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.CustomerPayDetailDao;
import com.example.project.demos.web.dto.customerPayDetail.*;
import com.example.project.demos.web.dto.list.CustomerPayDetailInfo;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.CustomerPayDetailEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.SysEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.CustomerAccountRelService;
import com.example.project.demos.web.service.CustomerPayDetailService;
import com.example.project.demos.web.service.SysLogService;
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
@Service("customerPayDetailService")
public class CustomerPayDetailServiceImpl  implements CustomerPayDetailService {

    @Resource
    private CustomerPayDetailDao customerPayDetailDao;

    @Autowired
    private SysLogService sysLogService;

    /*@Override
    public QueryByIdOutDTO queryById(QueryByIdDTO dto) {
        log.info("销售客户往来账queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            CustomerPayDetailInfo customerPayDetailInfo = customerPayDetailDao.selectCustomerPayDetailInfoById(dto.getId());
            outDTO = BeanUtil.copyProperties(customerPayDetailInfo, QueryByIdOutDTO.class);
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
        log.info("销售客户往来账queryById结束");
        return outDTO;
    }*/

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("销售客户往来账queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.customerPayDetailDao.count(queryByPageDTO.getCustomerCode());
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<CustomerPayDetailInfo> page = new PageImpl<>(this.customerPayDetailDao.selectCustomerPayDetailInfoListByPage(queryByPageDTO.getCustomerCode(),queryByPageDTO.getCustomerName(), pageRequest), pageRequest, total);
                //获取分页数据
                List<CustomerPayDetailInfo> list = page.toList();
                //出参赋值
                outDTO.setCustomerPayDetailInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售客户往来账queryByPage结束");
        return outDTO;
    }

    /**
     * 增加往来账   财务从页面使用
     * @param dto 实例对象
     * @return
     */
    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            CustomerPayDetailEntity newEntity = BeanCopyUtils.copy(dto,CustomerPayDetailEntity.class);
            //需要获取该客户最新一笔来往账信息
            CustomerPayDetailEntity entity = customerPayDetailDao.selectLatestPayDetail(dto.getCustomerCode());
            //账面金额=上次账面余额-总金额+退回金额+打款金额
            BigDecimal bookBalance = entity.getBookBalance().subtract(dto.getMaterialBalance()).add(dto.getReturnBalance()).add(dto.getPayBalance());
            log.info("新的账面余额:"+entity.getBookBalance() + "-" + dto.getMaterialBalance() + "+" + dto.getReturnBalance() +"+" +  dto.getPayBalance() +"=" +bookBalance);
            newEntity.setBookBalance(bookBalance );
            newEntity.setCreateBy(user.getUserLogin());
            newEntity.setCreateTime(date);
            newEntity.setOperatorBy(user.getUserLogin());
            newEntity.setIsDefault(SysEnums.SYS_NO_FLAG.getCode());
            newEntity.setUpdateBy(user.getUserLogin());
            newEntity.setUpdateTime(date);
            int i = customerPayDetailDao.insert(newEntity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "客户名称:"+dto.getCustomerName()+",打款金额:"+dto.getPayBalance()+",退回金额:"+dto.getReturnBalance();
        sysLogService.insertSysLog(FunctionTypeEnums.COUSTOMER_PAY_DETAIL.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }


    @Override
    public int addPayBySystem(AddPayBySystemDTO dto) {
        int i = 0;
        Date date = new Date();
        if(dto.getIsDefault().equals(SysEnums.SYS_YES_FLAG.getCode())){
            //默认往来账  直接添加
            CustomerPayDetailEntity entity = BeanCopyUtils.copy(dto,CustomerPayDetailEntity.class);
            entity.setOperatorBy(RequestHolder.getUserInfo().getUserLogin());
            entity.setCreateTime(date);
            entity.setOperatorBy(Constants.SYSTEM_CODE);
            entity.setUpdateBy(Constants.SYSTEM_CODE);
            entity.setUpdateTime(date);
            i = customerPayDetailDao.insert(entity);
        }else{
            CustomerPayDetailEntity newEntity = BeanCopyUtils.copy(dto,CustomerPayDetailEntity.class);
            //需要获取该客户最新一笔来往账信息
            CustomerPayDetailEntity entity = customerPayDetailDao.selectLatestPayDetail(dto.getCustomerCode());
            //账面金额=上次账面余额-总金额+退回金额+打款金额
            BigDecimal bookBalance = entity.getBookBalance().subtract(dto.getMaterialBalance()).add(dto.getReturnBalance()).add(dto.getPayBalance());
            log.info("新的账面余额:"+entity.getBookBalance() + "-" + dto.getMaterialBalance() + "+" + dto.getReturnBalance() +"+" +  dto.getPayBalance() +"=" +bookBalance);
            newEntity.setBookBalance(bookBalance );
            newEntity.setCreateBy(Constants.SYSTEM_CODE);
            newEntity.setOperatorBy(Constants.SYSTEM_CODE);
            newEntity.setCreateTime(new Date());
            newEntity.setUpdateBy(Constants.SYSTEM_CODE);
            newEntity.setUpdateTime(date);
            newEntity.setRemark(dto.getRemark());
            i = customerPayDetailDao.insert(newEntity);
        }
        return i;
    }


    @Override
    public EditOutDTO update(EditDTO dto) {
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        BigDecimal amount = new BigDecimal("0");
        BigDecimal payBalance = dto.getPayBalance();
        BigDecimal returnBalance = dto.getReturnBalance();
        BigDecimal bookBalance = new BigDecimal("0");
        try{
            log.info("获取原数据");
            CustomerPayDetailEntity entity = customerPayDetailDao.selectById(dto.getId());
            bookBalance = entity.getBookBalance();
            //判断修改的字段是打款金额还是退回金额
            //判断panBalance不为0还是returnBalance 不为0
            if(payBalance.compareTo(new BigDecimal("0")) == 0){
                log.info("修改了退回金额");
                amount = entity.getReturnBalance().subtract(returnBalance);
            }else{
                log.info("修改了打款金额");
                amount = entity.getPayBalance().subtract(payBalance);
            }
            if(amount.compareTo(new BigDecimal("0")) < 0){
                amount = amount.multiply(new BigDecimal("-1"));
                log.info("原金额比修改后的金额小，需要加上差值");
                bookBalance = bookBalance.add(amount);
                customerPayDetailDao.addBookBalance(dto.getId(),amount);
            }else{
                log.info("原金额比修改后的金额大，需要减去差值");
                bookBalance = bookBalance.subtract(amount);
                customerPayDetailDao.reduceBookBalance(dto.getId(),amount);
            }
            CustomerPayDetailEntity entity1 = BeanCopyUtils.copy(dto,CustomerPayDetailEntity.class);
            //修改h后的账面余额
            entity1.setBookBalance(bookBalance);
            entity1.setOperatorBy(user.getUserLogin());
            entity1.setUpdateBy(user.getUserLogin());
            entity1.setUpdateTime(date);
            int i = customerPayDetailDao.updateById(entity1);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "客户名称:"+dto.getCustomerName()+",打款金额:"+dto.getPayBalance()+",退回金额:"+dto.getReturnBalance();
        sysLogService.insertSysLog(FunctionTypeEnums.COUSTOMER_PAY_DETAIL.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
        BigDecimal amount = new BigDecimal("0");
        BigDecimal payBalance = dto.getPayBalance();
        BigDecimal returnBalance = dto.getReturnBalance();
        try{
            //判断panBalance不为0还是returnBalance 不为0
            if(payBalance.compareTo(new BigDecimal("0")) == 0){
                log.info("有退回金额不为0的删除");
                amount = returnBalance;
            }else{
                log.info("有打款金额不为0的删除");
                amount = payBalance;
            }
            //删除往来账  需要将当前记录以后的账面余额全部减去被删除的金额
            customerPayDetailDao.reduceBookBalance(dto.getId(),amount);
            int i = customerPayDetailDao.deleteById(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "客户名称:"+dto.getCustomerName()+",打款金额:"+dto.getPayBalance()+",退回金额:"+dto.getReturnBalance();
        sysLogService.insertSysLog(FunctionTypeEnums.COUSTOMER_PAY_DETAIL.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }
}
