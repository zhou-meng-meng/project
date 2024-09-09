package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.SupplyCustomerPayDao;
import com.example.project.demos.web.dto.customerPayDetail.UpdateUnitPriceDTO;
import com.example.project.demos.web.dto.list.RawMaterialIncomeInfo;
import com.example.project.demos.web.dto.list.SupplyCustomerPayInfo;
import com.example.project.demos.web.dto.supplyCustomerPay.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.SupplyCustomerPayEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.RoleAuthorityTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.SupplyCustomerPayService;
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

/**
 * 此功能的数据 在来料入库审核时系统添加，不涉及增删改
 */
@Slf4j
@Service("supplyCoustomerPayService")
public class SupplyCustomerPayServiceImpl  implements SupplyCustomerPayService {

    @Resource
    private SupplyCustomerPayDao supplyCustomerPayDao;
    @Autowired
    private SysLogService sysLogService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("进货记录queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SupplyCustomerPayInfo info = supplyCustomerPayDao.selectSupplyCustomerPayInfoById(id);
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
        log.info("进货记录queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("进货记录queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            //先用查询条件查询总条数
            long total = this.supplyCustomerPayDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<SupplyCustomerPayInfo> page = new PageImpl<>(this.supplyCustomerPayDao.selectSupplyCustomerPayInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<SupplyCustomerPayInfo> list = page.toList();
                //出参赋值  集合和合计字段
                outDTO = formatObject(outDTO,user,list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("进货记录queryByPage结束");
        return outDTO;
    }

    @Override
    public List<SupplyCustomerPayInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("供货方往来账queryListForExport开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        List<SupplyCustomerPayInfo> list = new ArrayList<>();
        try {
            list = supplyCustomerPayDao.queryListForExport(dto);
            list = formatSumObjectForExport(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.SUPPLY_COUSTOMER_PAY.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        log.info("供货方往来账queryListForExport结束");
        return list;
    }

    @Override
    public int updateUnitPrice(UpdateUnitPriceDTO dto, Date date, UserLoginOutDTO user) {
        SupplyCustomerPayEntity entity = supplyCustomerPayDao.selectByIncomeId(dto.getBusinessId());
        int i = 0;
        if(null != entity){
            entity.setUnitPrice(dto.getUnitPrice());
            entity.setTollAmount(dto.getTollAmount());
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            entity.setRemark(dto.getRemark());
            i = supplyCustomerPayDao.updateById(entity);
        }else{
            log.info("当前供货记录已不存在，不需要修改");
        }
        return i;
    }

    private QueryByPageOutDTO formatObject(QueryByPageOutDTO outDTO,UserLoginOutDTO user, List<SupplyCustomerPayInfo> list){
        boolean isPrice = DataUtils.getIsPrice(user);
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(SupplyCustomerPayInfo info: list){
            info.setFunctionTypeName(FunctionTypeEnums.getDescByCode(info.getFunctionType()));
            if(!isPrice){
                info.setUnitPrice(new BigDecimal("0"));
                info.setTollAmount(new BigDecimal("0"));
            }
            BigDecimal count = info.getIncomeCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
        }
        outDTO.setSumAmt(sumAmt);
        outDTO.setSumCount(sumCount);
        outDTO.setSupplyCustomerPayInfoList(list);
        return outDTO;
    }

    /**
     * 导出最后一行增加合计列
     * @param list
     * @return
     */
    private List<SupplyCustomerPayInfo> formatSumObjectForExport(List<SupplyCustomerPayInfo> list){
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(SupplyCustomerPayInfo info: list){
            BigDecimal count = info.getIncomeCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
            info.setFunctionTypeName(FunctionTypeEnums.getDescByCode(info.getFunctionType()));
        }
        SupplyCustomerPayInfo info = new SupplyCustomerPayInfo();
        info.setUnitName("合计:");
        info.setIncomeCount(sumCount);
        info.setTollAmount(sumAmt);
        list.add(info);
        return list;
    }

}
