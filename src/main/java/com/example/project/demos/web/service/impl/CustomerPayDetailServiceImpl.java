package com.example.project.demos.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.customerPayDetail.*;
import com.example.project.demos.web.dto.list.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.*;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.SysEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
import com.example.project.demos.web.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service("customerPayDetailService")
public class CustomerPayDetailServiceImpl  implements CustomerPayDetailService {

    @Resource
    private CustomerPayDetailDao customerPayDetailDao;
    @Resource
    private RawMaterialIncomeDao rawMaterialIncomeDao;
    @Resource
    private SalesOutboundDao salesOutboundDao;
    @Resource
    private SupplyReturnDao supplyReturnDao;
    @Resource
    private SalesReturnDao salesReturnDao;
    @Resource
    private SalersOrderDao salersOrderDao;
    @Resource
    private SalersOrderReturnDao salersOrderReturnDao;

    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private UploadFileInfoService uploadFileInfoService;
    @Autowired
    private SupplyCustomerPayService supplyCustomerPayService;
    @Autowired
    private SalesCustomerPayService salesCustomerPayService;

    @Resource
    private SysFactoryDao sysFactoryDao;
    @Resource
    private SysStorehouseDao sysStorehouseDao;

    @Resource
    private SupplyCustomerPayDao supplyCustomerPayDao;
    @Resource
    private SalesCustomerPayDao salesCustomerPayDao;

    //zip压缩包的路径
    @Value("${downloadZip.zipPath}")
    public String zipPath;

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("客户往来账明细queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.customerPayDetailDao.count(dto.getCustomerCode(),dto.getCustomerName(), dto.getMaterialName(),dto.getBeginDate(),dto.getEndDate(),dto.getPayStartDate(),dto.getPayEndDate());
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<CustomerPayDetailInfo> page = new PageImpl<>(this.customerPayDetailDao.selectCustomerPayDetailInfoListByPage(dto.getCustomerCode(),dto.getCustomerName(), dto.getMaterialName(),dto.getBeginDate(),dto.getEndDate(),dto.getPayStartDate(),dto.getPayEndDate(),pageRequest), pageRequest, total);
                //获取分页数据
                List<CustomerPayDetailInfo> list = page.toList();
                //赋值厂区名称
                list = formatFactoryName(list);
                //出参赋值 集合和合计字段
                outDTO = formatSumObject(list,outDTO);

            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("客户往来账明细queryByPage结束");
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
        BigDecimal materialBalance = dto.getMaterialBalance();
        BigDecimal returnBalance = dto.getReturnBalance();
        BigDecimal payBalance = dto.getPayBalance();
        BigDecimal taxBalance = dto.getTaxBalance();
        BigDecimal otherBalance = dto.getOtherBalance();
        try{
            CustomerPayDetailEntity newEntity = BeanCopyUtils.copy(dto,CustomerPayDetailEntity.class);
            //需要获取该客户最新一笔来往账信息
            CustomerPayDetailEntity entity = customerPayDetailDao.selectLatestPayDetail(dto.getCustomerCode());
            //账面金额=上次账面余额-总金额+退回金额+打款金额-税金-其他金额
            if(ObjectUtil.isNull(materialBalance)){
                materialBalance = new BigDecimal(0);
            }
            if(ObjectUtil.isNull(returnBalance)){
                returnBalance = new BigDecimal(0);
            }
            if(ObjectUtil.isNull(payBalance)){
                payBalance = new BigDecimal(0);
            }
            if(ObjectUtil.isNull(taxBalance)){
                taxBalance = new BigDecimal(0);
            }
            if(ObjectUtil.isNull(otherBalance)){
                otherBalance = new BigDecimal(0);
            }
            BigDecimal bookBalance = entity.getBookBalance().subtract(materialBalance).add(returnBalance).add(payBalance).subtract(taxBalance).subtract(otherBalance);
            log.info("新的账面余额:"+entity.getBookBalance() + "-" + materialBalance + "+" + returnBalance +"+" +  payBalance +"-"+taxBalance +"-"+otherBalance+" = " +bookBalance);
            newEntity.setMaterialDate(dto.getPayDate());
            newEntity.setPayDate(dto.getPayDate());
            newEntity.setBookBalance(bookBalance );
            newEntity.setCreateBy(user.getUserLogin());
            newEntity.setCreateTime(date);
            newEntity.setOperatorBy(user.getUserLogin());
            newEntity.setIsDefault(SysEnums.SYS_NO_FLAG.getCode());
            newEntity.setUpdateBy(user.getUserLogin());
            newEntity.setUpdateTime(date);
            int i = customerPayDetailDao.insert(newEntity);
            //开始处理附件信息
            uploadFileInfoService.updateByBusinessId(newEntity.getId(),dto.getFileIdList());
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        String payDate = DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getPayDate());
        //记录操作日志
        String info = "客户名称:"+dto.getCustomerName()+",打款金额:"+payBalance+",退回金额:"+returnBalance+",税金:"+taxBalance+",其他金额:"+otherBalance+",打款日期:"+payDate;
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_PAY_DETAIL.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            entity.setCreateTime(date);
            entity.setOperatorBy(Constants.SYSTEM_CODE);
            entity.setUpdateBy(Constants.SYSTEM_CODE);
            entity.setUpdateTime(date);
            entity.setRemark("默认往来账");
            i = customerPayDetailDao.insert(entity);
        }else{
            BigDecimal materialBalance = dto.getMaterialBalance();
            BigDecimal returnBalance = dto.getReturnBalance();
            BigDecimal payBalance = dto.getPayBalance();

            CustomerPayDetailEntity newEntity = BeanCopyUtils.copy(dto,CustomerPayDetailEntity.class);
            //需要获取该客户最新一笔来往账信息
            CustomerPayDetailEntity entity = customerPayDetailDao.selectLatestPayDetail(dto.getCustomerCode());
            //账面金额=上次账面余额-总金额+退回金额+打款金额
            if(ObjectUtil.isNull(materialBalance)){
                materialBalance = new BigDecimal(0);
            }
            if(ObjectUtil.isNull(returnBalance)){
                returnBalance = new BigDecimal(0);
            }
            if(ObjectUtil.isNull(payBalance)){
                payBalance = new BigDecimal(0);
            }

            BigDecimal bookBalance = entity.getBookBalance().subtract(materialBalance).add(returnBalance).add(payBalance);
            log.info("新的账面余额:"+entity.getBookBalance() + "-" + materialBalance + "+" + returnBalance +"+" +  payBalance +"=" +bookBalance);
            newEntity.setUnitPrice(dto.getUnitPrice());
            newEntity.setMaterialCount(dto.getMaterialCount());
            newEntity.setMaterialBalance(materialBalance);
            newEntity.setBookBalance(bookBalance );
            newEntity.setCreateBy(dto.getOperatorBy());
            newEntity.setOperatorBy(dto.getOperatorBy());
            newEntity.setCreateTime(date);
            newEntity.setUpdateBy(dto.getOperatorBy());
            newEntity.setUpdateTime(date);
            newEntity.setRemark(dto.getRemark());
            i = customerPayDetailDao.insert(newEntity);
        }
        return i;
    }


    /**
     * 去掉往来账修改按钮  改为只修改附件
     * @param dto 实例对象
     * @return
     */
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
            if(ObjectUtil.isNull(returnBalance)){
                returnBalance = new BigDecimal(0);
            }
            if(ObjectUtil.isNull(payBalance)){
                payBalance = new BigDecimal(0);
            }
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
                customerPayDetailDao.addBookBalance(dto.getId(),amount,dto.getCustomerCode());
            }else{
                log.info("原金额比修改后的金额大，需要减去差值");
                bookBalance = bookBalance.subtract(amount);
                customerPayDetailDao.reduceBookBalance(dto.getId(),amount,dto.getCustomerCode());
            }
            CustomerPayDetailEntity entity1 = BeanCopyUtils.copy(dto,CustomerPayDetailEntity.class);
            //修改后的账面余额
            entity1.setBookBalance(bookBalance);
            entity1.setOperatorBy(user.getUserLogin());
            entity1.setUpdateBy(user.getUserLogin());
            entity1.setUpdateTime(date);
            int i = customerPayDetailDao.updateById(entity1);
            //开始处理附件信息
            uploadFileInfoService.updateByBusinessId(entity1.getId(),dto.getFileIdList());
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "客户名称:"+dto.getCustomerName()+",打款金额:"+payBalance+",退回金额:"+returnBalance;
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_PAY_DETAIL.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    /**
     * 去掉往来账删除按钮
     * @param dto 主键
     * @return
     */
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
        CustomerPayDetailEntity entity = customerPayDetailDao.selectById(dto.getId());
        BigDecimal discountBalance = entity.getDiscountBalance();
        try{
            if(ObjectUtil.isNull(returnBalance)){
                returnBalance = new BigDecimal(0);
            }
            if(ObjectUtil.isNull(payBalance)){
                payBalance = new BigDecimal(0);
            }
            if(ObjectUtil.isNull(discountBalance)){
                discountBalance = new BigDecimal(0);
            }
            //判断panBalance不为0还是returnBalance 不为0
            if(payBalance.compareTo(new BigDecimal("0")) == 0){
                log.info("有退回金额不为0的删除");
                amount = returnBalance;
            }else{
                log.info("有打款金额不为0的删除");
                amount = payBalance;
            }
            amount = amount.add(discountBalance);
            //删除往来账  需要将当前记录以后的账面余额全部减去被删除的金额+折扣金额
            customerPayDetailDao.reduceBookBalance(dto.getId(),amount,entity.getCustomerCode());
            int i = customerPayDetailDao.deleteById(dto.getId());
            log.info("开始删除附件信息");
            uploadFileInfoService.deleteFileByBusinessId(dto.getId());
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "客户名称:"+dto.getCustomerName()+",打款金额:"+payBalance+",退回金额:"+returnBalance;
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_PAY_DETAIL.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public List<CustomerPayDetailInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("客户往来账明细queryListForExport开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        List<CustomerPayDetailInfo> list = new ArrayList<>();
        try {
            list = customerPayDetailDao.queryListForExport(dto.getCustomerCode(),dto.getCustomerName(),dto.getMaterialName(),dto.getBeginDate(),dto.getEndDate(),dto.getPayStartDate(),dto.getPayEndDate());
            list = formatFactoryName(list);
            list = DataUtils.formatSumObjectForExport(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_PAY_DETAIL.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        log.info("客户往来账明细queryListForExport结束");
        return list;
    }

    /**
     * 对于审核通过的数据，可以修改单价，总金额也会变化，要更新往来账信息
     * 1、对于来料入库、销售出库、业务员下单业务，要更新往来账明细表里的物料金额，
     *    修改后的金额大于原金额的，当前往来账数据及后面的数据要减去多出来的金额
     *    修改后的金额小于原金额的，当前往来账数据及后面的数据要加上减少的金额
     * 2、对于供货方退回、销售客户退回、业务员下单退回，要更新往来账明细表里的退回金额
     *    修改后的金额大于原金额的，当前往来账数据及后面的数据要加上多出来的金额
     *    修改后的金额小于原金额的，当前往来账数据及后面的数据要减去减少的金额
     * @param dto
     * @return
     */
    @Override
    public UpdateUnitPriceOutDTO updateUnitPrice(UpdateUnitPriceDTO dto) {
        UpdateUnitPriceOutDTO outDTO  = new UpdateUnitPriceOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        boolean edit = true;
        String fuctionId = dto.getFunctionId();
        try{
            log.info("通过businessId获取该笔数据的往来账id");
            Long id = customerPayDetailDao.selectIdByBusinessId(dto.getBusinessId());

            log.info("判断修改后的金额和原金额");
            BigDecimal preTollAmount = dto.getPreTollAmount();
            BigDecimal tollAmount = dto.getTollAmount();
            log.info("修改前总金额:"+preTollAmount);
            log.info("修改后总金额:"+tollAmount);
            BigDecimal substract = tollAmount.subtract(preTollAmount);
            log.info("修改后金额-修改前金额:"+tollAmount +" - " + preTollAmount +" = " + substract);

            CustomerPayDetailEntity entity = customerPayDetailDao.selectById(id);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            entity.setRemark(dto.getRemark());
            if(fuctionId.equals(FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode()) || fuctionId.equals(FunctionTypeEnums.SALES_OUTBOUND.getCode()) || fuctionId.equals(FunctionTypeEnums.SALERS_ORDER.getCode()) ){
                log.info("来料入库、销售出库、业务员下单，更新单价和物料金额");
                entity.setUnitPrice(dto.getUnitPrice());
                entity.setMaterialBalance(dto.getTollAmount());
                if(substract.compareTo(new BigDecimal("0")) > 0){
                    log.info("更新当前往来账记录的单价和物料金额");
                    customerPayDetailDao.updateById(entity);
                    log.info("修改后金额大于原金额，更新当前记录及以后得往来账，账面余额减去大于的金额");
                    customerPayDetailDao.reduceBookBalanceByUnitPrice(id,substract,entity.getCustomerCode());
                }else if (substract.compareTo(new BigDecimal("0")) < 0){
                    log.info("更新当前往来账记录的单价和物料金额");
                    customerPayDetailDao.updateById(entity);
                    log.info("修改后金额小于原金额，更新当前记录的物料金额，更新当前记录及以后得往来账，账面余额加上大于的金额");
                    substract  = substract.multiply(new BigDecimal(-1));
                    customerPayDetailDao.addBookBalanceByUnitPrice(id,substract,entity.getCustomerCode());
                }else{
                    log.info("修改后金额等于原金额，不操作");
                    edit = false;
                }
            }else if (fuctionId.equals(FunctionTypeEnums.SUPPLY_RETURN.getCode()) || fuctionId.equals(FunctionTypeEnums.SALES_RETURN.getCode()) || fuctionId.equals(FunctionTypeEnums.SALERS_ORDER_RETURN.getCode())){
                log.info("供货方退回、销售客户退回、业务员下单退回，更新退回单价和退回金额");
                entity.setReturnUnitPrice(dto.getUnitPrice());
                entity.setReturnBalance(dto.getTollAmount());
                if(substract.compareTo(new BigDecimal("0")) > 0){
                    log.info("更新当前往来账记录的单价和退回金额");
                    customerPayDetailDao.updateById(entity);
                    log.info("修改后金额大于原金额，更新当前记录及以后得往来账，账面余额加上多余的金额");
                    customerPayDetailDao.addBookBalanceByUnitPrice(id,substract,entity.getCustomerCode());
                }else if (substract.compareTo(new BigDecimal("0")) < 0){
                    log.info("更新当前往来账记录的单价和退回金额");
                    customerPayDetailDao.updateById(entity);
                    log.info("修改后金额小于原金额，更新当前记录及以后得往来账，账面余额减多余的金额");
                    substract  = substract.multiply(new BigDecimal(-1));
                    customerPayDetailDao.reduceBookBalanceByUnitPrice(id,substract,entity.getCustomerCode());
                }else{
                    log.info("修改后金额等于原金额，不操作");
                    edit = false;
                }
            }

            if(edit){
                log.info("修改各业务数据单价开始");
                if(fuctionId.equals(FunctionTypeEnums.RAW_MATERIAL_INCOME.getCode())){
                    log.info("来料入库修改单价和总金额");
                    RawMaterialIncomeEntity updateEntity = new RawMaterialIncomeEntity();
                    updateEntity.setId(dto.getBusinessId());
                    updateEntity.setUnitPrice(dto.getUnitPrice());
                    updateEntity.setTollAmount(dto.getTollAmount());
                    updateEntity.setUpdateBy(user.getUserLogin());
                    updateEntity.setUpdateTime(date);
                    updateEntity.setRemark(dto.getRemark());
                    rawMaterialIncomeDao.updateById(updateEntity);
                    log.info("往来账来料记录修改单价和总金额");
                    supplyCustomerPayService.updateUnitPrice(dto,date,user);
                }else if(fuctionId.equals(FunctionTypeEnums.SALES_OUTBOUND.getCode())){
                    log.info("销售出库修改单价和总金额");
                    SalesOutboundEntity updateEntity = new SalesOutboundEntity();
                    updateEntity.setId(dto.getBusinessId());
                    updateEntity.setUnitPrice(dto.getUnitPrice());
                    updateEntity.setTollAmount(dto.getTollAmount());
                    updateEntity.setUpdateBy(user.getUserLogin());
                    updateEntity.setUpdateTime(date);
                    updateEntity.setRemark(dto.getRemark());
                    salesOutboundDao.updateById(updateEntity);
                    log.info("往来账销售记录修改单价和总金额");
                    salesCustomerPayService.updateUnitPrice(dto,date,user);
                }else if(fuctionId.equals(FunctionTypeEnums.SUPPLY_RETURN.getCode())){
                    log.info("供货方退回修改单价和总金额");
                    SupplyReturnEntity updateEntity = new SupplyReturnEntity();
                    updateEntity.setId(dto.getBusinessId());
                    updateEntity.setUnitPrice(dto.getUnitPrice());
                    updateEntity.setTollAmount(dto.getTollAmount());
                    updateEntity.setUpdateBy(user.getUserLogin());
                    updateEntity.setUpdateTime(date);
                    updateEntity.setRemark(dto.getRemark());
                    supplyReturnDao.updateById(updateEntity);
                    log.info("往来账来料记录修改单价和总金额");
                    supplyCustomerPayService.updateUnitPrice(dto,date,user);
                }else if(fuctionId.equals(FunctionTypeEnums.SALES_RETURN.getCode())){
                    log.info("销售客户退回修改单价和总金额");
                    SalesReturnEntity updateEntity = new SalesReturnEntity();
                    updateEntity.setId(dto.getBusinessId());
                    updateEntity.setUnitPrice(dto.getUnitPrice());
                    updateEntity.setTollAmount(dto.getTollAmount());
                    updateEntity.setUpdateBy(user.getUserLogin());
                    updateEntity.setUpdateTime(date);
                    updateEntity.setRemark(dto.getRemark());
                    salesReturnDao.updateById(updateEntity);
                    log.info("往来账销售记录修改单价和总金额");
                    salesCustomerPayService.updateUnitPrice(dto,date,user);
                }else if(fuctionId.equals(FunctionTypeEnums.SALERS_ORDER.getCode())){
                    log.info("业务员下单修改单价和总金额");
                    SalersOrderEntity updateEntity = new SalersOrderEntity();
                    updateEntity.setId(dto.getBusinessId());
                    updateEntity.setUnitPrice(dto.getUnitPrice());
                    updateEntity.setTollAmount(dto.getTollAmount());
                    updateEntity.setUpdateBy(user.getUserLogin());
                    updateEntity.setUpdateTime(date);
                    updateEntity.setRemark(dto.getRemark());
                    salersOrderDao.updateById(updateEntity);
                    log.info("往来账销售记录修改单价和总金额");
                    salesCustomerPayService.updateUnitPrice(dto,date,user);
                }else if(fuctionId.equals(FunctionTypeEnums.SALERS_ORDER_RETURN.getCode())){
                    log.info("业务员下单退回修改单价和总金额");
                    SalersOrderReturnEntity updateEntity = new SalersOrderReturnEntity();
                    updateEntity.setId(dto.getBusinessId());
                    updateEntity.setUnitPrice(dto.getUnitPrice());
                    updateEntity.setTollAmount(dto.getTollAmount());
                    updateEntity.setUpdateBy(user.getUserLogin());
                    updateEntity.setUpdateTime(date);
                    updateEntity.setRemark(dto.getRemark());
                    salersOrderReturnDao.updateById(updateEntity);
                    log.info("往来账销售记录修改单价和总金额");
                    salesCustomerPayService.updateUnitPrice(dto,date,user);
                }
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public EditBookBalanceOutDTO editBookBalance(EditBookBalanceDTO dto) {
        log.info("维护客户初始化账面余额开始");
        EditBookBalanceOutDTO outDTO  = new EditBookBalanceOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try {
            //往来账明细实体赋值
            CustomerPayDetailEntity entity = new CustomerPayDetailEntity();
            //客户编号
            entity.setCustomerCode(dto.getCustomerCode());
            //账面余额
            entity.setBookBalance(dto.getBookBalance());
            entity.setOperatorBy(user.getUserLogin());
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            entity.setMaterialDate(date);
            entity.setRemark("初始化-"+dto.getRemark());
            customerPayDetailDao.insert(entity);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("维护客户初始化账面余额异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        String info = "客户编号:"+dto.getCustomerCode()+",客户名称:"+dto.getCustomerName()+",账面余额:"+dto.getBookBalance()+",备注:"+dto.getRemark();
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_PAY_DETAIL.getCode(), OperationTypeEnums.OPERATION_TYPE_INITE_BALANCE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("维护客户初始化账面余额结束");
        return outDTO;
    }

    /**
     * 菜单列表获取数量合计和金额合计
     * @param list
     * @param outDTO
     * @return
     */
    private QueryByPageOutDTO formatSumObject(List<CustomerPayDetailInfo> list, QueryByPageOutDTO outDTO){

        //合计物料数量
        BigDecimal sumCount = new BigDecimal("0");
        //合计单价
        BigDecimal sumUnitPrice = new BigDecimal("0");
        //合计物料金额
        BigDecimal sumMaterialAmt = new BigDecimal("0");

        //合计退回数量
        BigDecimal sumReturnCount = new BigDecimal("0");
        //合计退回单价
        BigDecimal sumReturnUnitPrice = new BigDecimal("0");
        //合计退回金额
        BigDecimal sumReturnAmt = new BigDecimal("0");

        //合计税金
        BigDecimal sumTaxAmt = new BigDecimal("0");
        //合计其他金额
        BigDecimal sumOtherAmt = new BigDecimal("0");
        //合计打款金额
        BigDecimal sumPayAmt = new BigDecimal("0");
        //合计运费
        BigDecimal sumFreightAmt = new BigDecimal("0");

        for(CustomerPayDetailInfo info: list){
            BigDecimal count = info.getMaterialCount();
            if(count == null){
                count = new BigDecimal("0");
            }
            BigDecimal unitPrice = info.getUnitPrice();
            if(unitPrice == null){
                unitPrice = new BigDecimal("0");
            }
            BigDecimal materialAmt = info.getMaterialBalance();
            if(materialAmt == null){
                materialAmt = new BigDecimal("0");
            }

            BigDecimal returnCount = info.getReturnCount();
            if(returnCount == null){
                returnCount = new BigDecimal("0");
            }
            BigDecimal returnUnitPrice = info.getReturnUnitPrice();
            if(returnUnitPrice == null){
                returnUnitPrice = new BigDecimal("0");
            }
            BigDecimal returnAmt = info.getReturnBalance();
            if(returnAmt == null){
                returnAmt = new BigDecimal("0");
            }

            BigDecimal taxAmt = info.getTaxBalance();
            if(taxAmt == null){
                taxAmt = new BigDecimal("0");
            }
            BigDecimal otherAmt = info.getOtherBalance();
            if(otherAmt == null){
                otherAmt = new BigDecimal("0");
            }
            BigDecimal payAmt = info.getPayBalance();
            if(payAmt == null){
                payAmt = new BigDecimal("0");
            }
            BigDecimal freightAmt = info.getFreight();
            if(freightAmt == null){
                freightAmt = new BigDecimal("0");
            }

            //销售/来料金额
            sumCount = sumCount.add(count);
            sumUnitPrice = sumUnitPrice.add(unitPrice);
            sumMaterialAmt = sumMaterialAmt.add(materialAmt);
            //退回金额
            sumReturnCount = sumReturnCount.add(returnCount);
            sumReturnUnitPrice = sumReturnUnitPrice.add(returnUnitPrice);
            sumReturnAmt = sumReturnAmt.add(returnAmt);

            //税金、其他金额 打款金额  运费
            sumTaxAmt = sumTaxAmt.add(taxAmt);
            sumOtherAmt = sumOtherAmt.add(otherAmt);
            sumPayAmt = sumPayAmt.add(payAmt);
            sumFreightAmt = sumFreightAmt.add(freightAmt);
        }
        outDTO.setCustomerPayDetailInfoList(list);
        outDTO.setSumCount(sumCount);
        outDTO.setSumUnitPrice(sumUnitPrice);
        outDTO.setSumMaterialAmt(sumMaterialAmt);
        outDTO.setSumReturnCount(sumReturnCount);
        outDTO.setSumReturnUnitPrice(sumReturnUnitPrice);
        outDTO.setSumReturnAmt(sumReturnAmt);
        outDTO.setSumTaxAmt(sumTaxAmt);
        outDTO.setSumOtherAmt(sumOtherAmt);
        outDTO.setSumPayAmt(sumPayAmt);
        outDTO.setSumFreightAmt(sumFreightAmt);
        return outDTO;
    }


    @Override
    public void downPoliceZip(ExportPayDetailBakDTO dto, HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("开始导出往来账明细备份zip,先删除服务器tempDir文件夹和其中的excel和zip文件");
        boolean b = FileDownloadUtils.deleteDir(new File(zipPath ));
        if (!b) {
            log.info("tempDir文件夹及其中的临时Excel和zip文件删除失败");
        }
        log.info("删除tempDir文件夹和其中的excel和zip文件完成");
        //zip压缩包不加汉字  防止乱码
        String zipName = "";
        Map<String,List<CustomerPayDetailInfoForExport>> payMaps = new LinkedHashMap<>();

        //生成Excel的文件夹路径
        String tempDir = zipPath + LocalDate.now() + "/";
        List<CustomerPayInfo> customerPayInfos = new ArrayList<>();
        String functionId = dto.getFunctionId();
        if(functionId.equals(FunctionTypeEnums.SUPPLY_COUSTOMER_PAY.getCode())){
            log.info("开始获取供应商客户");
            zipName = DateUtils.dateTime(new Date())+"Supply";
            customerPayInfos = supplyCustomerPayDao.querySupplyCustomer();
        }else{
            zipName = DateUtils.dateTime(new Date())+"Sales";
            log.info("开始获取销售客户");
            customerPayInfos = salesCustomerPayDao.querySalesCustomer();
        }
        //将导出的数据转成多个excel
        List<String> fileNameList = new ArrayList<>();
        for(CustomerPayInfo info : customerPayInfos){
            String name = info.getCustomerCode()+"-"+info.getCustomerName();
            String customerName = info.getCustomerName();
            fileNameList.add(name);
            log.info("开始获取客户:{} 的往来账明细列表", info.getCustomerName());
            List<CustomerPayDetailInfo> payDetailInfos =  customerPayDetailDao.queryListForExport(info.getCustomerCode(),null,null,null,null,null,null);
            payDetailInfos = formatFactoryName(payDetailInfos);
            List<CustomerPayDetailInfoForExport> exportList = DataUtils.formatForBakExport(payDetailInfos,customerName);
            payMaps.put(name,exportList);
        }
        log.info("payMaps加工结束");
        List<File> files = this.getStoreOrderExcels(tempDir, payMaps, fileNameList);
        log.info("开始压缩zip");
        String generateFileName = FileDownloadUtils.generateFile(tempDir, "zip", zipPath, zipName);
        log.info("开始下载压缩包");
        FileInputStream inputStream = null;
        BufferedInputStream bis = null;
        File file = new File(generateFileName);
        try {
            String filename = file.getName();
            /*String userAgent = request.getHeader("User-Agent");
            log.info("针对IE或者以IE为内核的浏览器处理");
            if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
                filename = java.net.URLEncoder.encode(filename, "UTF-8");
            } else {
                log.info("非IE浏览器的处理：");
                filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
            }*/
            log.info("filename:{}",filename);
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", filename));
            response.setContentType("application/download");
            response.setCharacterEncoding("UTF-8");
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            bis = new BufferedInputStream(inputStream);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            log.error("异常:"+e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    /**
     * 将导出的数据转成多个excel
     *
     * @param tempDir      路径
     * @param fileNameList 文件名
     * @return List<File>
     * @throws IOException 异常
     */
    private List<File> getStoreOrderExcels(String tempDir,  Map<String,List<CustomerPayDetailInfoForExport>> payMaps, List<String> fileNameList) throws IOException {
        FileDownloadUtils.createFile(tempDir);
        //存在多个文件
        List<File> files = new ArrayList<>();
        String path;
        for (int i = 0; i < fileNameList.size(); i++) {
            path = this.getStoreOrderExcel(fileNameList.get(i), payMaps.get(fileNameList.get(i)), tempDir);
            //excel添加到files中
            files.add(new File(path));
        }
        return files;
    }

    /**
     * @param fileName    文件名
     * @param list    map数组
     * @param tempDir 路径
     * @return String
     * @throws IOException 异常
     */
    public String getStoreOrderExcel(String fileName, List<CustomerPayDetailInfoForExport> list, String tempDir) throws IOException {
        // 通过工具类创建writer，默认创建xls格式
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.addHeaderAlias("materialDate", "日期");
        writer.addHeaderAlias("factoryName", "厂区");
        writer.addHeaderAlias("billNo", "单据号");
        writer.addHeaderAlias("customerCode", "客户编号");
        writer.addHeaderAlias("customerName", "客户名称");
        writer.addHeaderAlias("materialCode", "物料编号");
        writer.addHeaderAlias("materialName", "物料名称");
        writer.addHeaderAlias("modelName", "型号");
        writer.addHeaderAlias("unitName", "单位");
        writer.addHeaderAlias("unitPrice", "单价");
        writer.addHeaderAlias("materialCount", "数量");
        writer.addHeaderAlias("materialBalance", "物料金额");
        writer.addHeaderAlias("returnCount", "退回数量");
        writer.addHeaderAlias("returnUnitPrice", "退回单价");
        writer.addHeaderAlias("returnBalance", "退回金额");
        writer.addHeaderAlias("taxBalance", "税金");
        writer.addHeaderAlias("otherBalance", "其他金额");
        writer.addHeaderAlias("payBalance", "打款金额");
        writer.addHeaderAlias("bookBalance", "账面余额");
        writer.addHeaderAlias("freight", "运费");
        writer.addHeaderAlias("operatorByName", "经办人名字");
        writer.addHeaderAlias("remark", "备注");
        writer.addHeaderAlias("createByName", "创建者名字");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("updateByName", "更新者名字");
        writer.addHeaderAlias("updateTime", "更新时间");

        writer.write(list, true);
        String path = tempDir + LocalDate.now() + "_" + fileName + ".xls";
        log.info("生成一个excel:{}",path);
        FileOutputStream outputStream = new FileOutputStream(path);
        writer.flush(outputStream, true);
        writer.close();
        return path;
    }


    private List<CustomerPayDetailInfo> formatFactoryName(List<CustomerPayDetailInfo> list){
        log.info("开始赋值厂区/仓库名称");
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            //获取厂区和仓库集合
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            for(CustomerPayDetailInfo info : list){
                String factoryCode = info.getFactoryCode();
                if(null != factoryCode && !"".equals(factoryCode)){
                    if(Constants.FACTORY_CODE_PREFIX.equals(factoryCode.substring(0,1))){
                        //工厂
                        for(SysFactoryInfo fInfo : factoryInfoList){
                            if(factoryCode.equals(fInfo.getCode())){
                                info.setFactoryName(fInfo.getName());
                            }
                        }
                    }else{
                        //仓库
                        for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                            if(factoryCode.equals(sInfo.getCode())){
                                info.setFactoryName(sInfo.getName());
                            }
                        }
                    }
                }else{
                    // factoryCode is null
                }
            }

        }
        return list;
    }


}
