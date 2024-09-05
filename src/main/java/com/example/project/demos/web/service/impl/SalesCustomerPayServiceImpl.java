package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.SalesCustomerPayDao;
import com.example.project.demos.web.dto.customerPayDetail.UpdateUnitPriceDTO;
import com.example.project.demos.web.dto.list.SalesCustomerPayInfo;
import com.example.project.demos.web.dto.list.SupplyCustomerPayInfo;
import com.example.project.demos.web.dto.salesCustomerPay.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.SalesCustomerPayEntity;
import com.example.project.demos.web.entity.SupplyCustomerPayEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.RoleAuthorityTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.SalesCustomerPayService;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.utils.DataUtils;
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
@Service("salesCoustomerPayService")
public class SalesCustomerPayServiceImpl implements SalesCustomerPayService {

    @Resource
    private SalesCustomerPayDao salesCustomerPayDao;
    @Autowired
    private SysLogService sysLogService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("销售记录queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SalesCustomerPayInfo info = salesCustomerPayDao.selectSalesCustomerPayInfoById(id);
            outDTO = BeanUtil.copyProperties(info, QueryByIdOutDTO.class);
            //赋值业务类型
            outDTO.setFunctionTypeName(FunctionTypeEnums.getDescByCode(outDTO.getFunctionType()));
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            boolean isPrice = DataUtils.getIsPrice(user);
            if(!isPrice){
                log.info("没有单价权限");
                outDTO.setUnitPrice(new BigDecimal("0"));
                outDTO.setTollAmount(new BigDecimal("0"));
            }
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售记录queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("销售记录queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.salesCustomerPayDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<SalesCustomerPayInfo> page = new PageImpl<>(this.salesCustomerPayDao.selectSalesCustomerPayInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<SalesCustomerPayInfo> list = page.toList();
                UserLoginOutDTO user = RequestHolder.getUserInfo();
                //出参赋值  集合和合计字段
                outDTO = formatObject(outDTO,user,list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售记录queryByPage结束");
        return outDTO;
    }

    @Override
    public List<SalesCustomerPayInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("销售客户往来账queryListForExport开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        List<SalesCustomerPayInfo> list = new ArrayList<>();
        try {
            list = salesCustomerPayDao.queryListForExport(dto);
            //赋值集合和合计字段
            list = formatSumObjectForExport(user,list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_COUSTOMER_PAY.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        log.info("销售客户往来账queryListForExport结束");
        return list;
    }

    @Override
    public int updateUnitPrice(UpdateUnitPriceDTO dto, Date date, UserLoginOutDTO user) {
        SalesCustomerPayEntity entity = salesCustomerPayDao.selectBySaleId(dto.getBusinessId());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTollAmount(dto.getTollAmount());
        entity.setUpdateBy(user.getUserLogin());
        entity.setUpdateTime(date);
        entity.setRemark(dto.getRemark());
        return salesCustomerPayDao.updateById(entity);
    }

    private QueryByPageOutDTO formatObject(QueryByPageOutDTO outDTO, UserLoginOutDTO user, List<SalesCustomerPayInfo> list){
        boolean isPrice = DataUtils.getIsPrice(user);
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(SalesCustomerPayInfo info: list){
            info.setFunctionTypeName(FunctionTypeEnums.getDescByCode(info.getFunctionType()));
            if(!isPrice){
                info.setUnitPrice(new BigDecimal("0"));
                info.setTollAmount(new BigDecimal("0"));
            }
            BigDecimal count = info.getSaleCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
        }
        outDTO.setSumAmt(sumAmt);
        outDTO.setSumCount(sumCount);
        outDTO.setSalesCustomerPayInfoList(list);
        return outDTO;
    }

    /**
     * 导出最后一行增加合计列
     * @param list
     * @return
     */
    private List<SalesCustomerPayInfo> formatSumObjectForExport(UserLoginOutDTO user,List<SalesCustomerPayInfo> list){
        boolean isPrice = DataUtils.getIsPrice(user);
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(SalesCustomerPayInfo info: list){
            if(!isPrice){
                info.setUnitPrice(new BigDecimal("0"));
                info.setTollAmount(new BigDecimal("0"));
            }
            BigDecimal count = info.getSaleCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
            info.setFunctionTypeName(FunctionTypeEnums.getDescByCode(info.getFunctionType()));
        }
        SalesCustomerPayInfo info = new SalesCustomerPayInfo();
        info.setUnitName("合计:");
        info.setSaleCount(sumCount);
        info.setTollAmount(sumAmt);
        list.add(info);
        return list;
    }

}
