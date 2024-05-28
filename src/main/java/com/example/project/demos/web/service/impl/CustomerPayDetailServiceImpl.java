package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.CustomerPayDetailDao;
import com.example.project.demos.web.dto.customerPayDetail.*;
import com.example.project.demos.web.dto.list.CustomerPayDetailInfo;
import com.example.project.demos.web.entity.CustomerPayDetailEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.SysEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.CustomerAccountRelService;
import com.example.project.demos.web.service.CustomerPayDetailService;
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
    private CustomerAccountRelService customerAccountRelService;

    /*@Override
    public QueryByIdOutDTO queryById(QueryByIdDTO dto) {
        log.info("销售客户queryById开始");
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
        log.info("销售客户queryById结束");
        return outDTO;
    }*/

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("销售客户queryByPage开始");
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
                //转换实体入参
                //CustomerPayDetailEntity customerPayDetail = BeanCopyUtils.copy(queryByPageDTO,CustomerPayDetailEntity.class);
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
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售客户queryByPage结束");
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
        try{
            CustomerPayDetailEntity newEntity = BeanCopyUtils.copy(dto,CustomerPayDetailEntity.class);
            //需要获取该客户最新一笔来往账信息
            CustomerPayDetailEntity entity = customerPayDetailDao.selectLatestPayDetail(dto.getCustomerCode());
            //账面金额=上次账面余额-总金额+退回金额+打款金额
            BigDecimal bookBalance = entity.getBookBalance().subtract(dto.getMaterialBalance()).add(dto.getReturnBalance()).add(dto.getPayBalance());
            log.info("新的账面余额:"+entity.getBookBalance() + "-" + dto.getMaterialBalance() + "+" + dto.getReturnBalance() +"+" +  dto.getPayBalance() +"=" +bookBalance);
            newEntity.setBookBalance(bookBalance );
            newEntity.setCreateBy(RequestHolder.getUserInfo().getUserLogin());
            newEntity.setCreateTime(new Date());
            int i = customerPayDetailDao.insert(newEntity);
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
    public int addPayBySystem(AddPayBySystemDTO dto) {
        int i = 0;
        if(dto.getIsDefault().equals(SysEnums.SYS_YES_FLAG.getCode())){
            //默认往来账  直接添加
            CustomerPayDetailEntity entity = BeanCopyUtils.copy(dto,CustomerPayDetailEntity.class);
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
            newEntity.setCreateTime(new Date());
            i = customerPayDetailDao.insert(newEntity);
        }
        return i;
    }


    @Override
    public EditOutDTO update(EditDTO dto) {
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            CustomerPayDetailEntity customerPayDetailEntity = BeanCopyUtils.copy(dto,CustomerPayDetailEntity.class);
            //修改
            customerPayDetailEntity.setUpdateBy("zhangyunning");
            customerPayDetailEntity.setUpdateTime(new Date());
            int i = customerPayDetailDao.updateById(customerPayDetailEntity);
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
            int i = customerPayDetailDao.deleteById(dto.getId());
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
