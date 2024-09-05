package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.ProductionMaterialIncomeDao;
import com.example.project.demos.web.dao.ProductionMaterialIncomeDetailDao;
import com.example.project.demos.web.dao.SysFactoryDao;
import com.example.project.demos.web.dao.SysStorehouseDao;
import com.example.project.demos.web.dto.list.*;
import com.example.project.demos.web.dto.productionMaterialIncome.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.ProductionMaterialIncomeDetailEntity;
import com.example.project.demos.web.entity.ProductionMaterialIncomeEntity;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.UserTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.MaterialInventoryService;
import com.example.project.demos.web.service.ProductionMaterialIncomeService;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.utils.BeanCopyUtils;
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
@Service("productionMaterialIncomeService")
public class ProductionMaterialIncomeServiceImpl  implements ProductionMaterialIncomeService {

    @Resource
    private ProductionMaterialIncomeDao productionMaterialIncomeDao;

    @Resource
    private ProductionMaterialIncomeDetailDao productionMaterialIncomeDetailDao;

    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;

    @Autowired
    private MaterialInventoryService materialInventoryService;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("产量入库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            ProductionMaterialIncomeInfo productionMaterialIncomeInfo = productionMaterialIncomeDao.selectProductionMaterialIncomeInfoById(id);
            List<ProductionMaterialIncomeInfo> list = new ArrayList<>();
            list.add(productionMaterialIncomeInfo);
            list = setProductionMaterialIncomeObject(list);
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
            //获取员工产量信息
            List<ProductProducerInfo> producerInfoList = productionMaterialIncomeDetailDao.selectProductProducerInfoList(outDTO.getId(),null);
            outDTO.setProducerInfoList(producerInfoList);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("产量入库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("产量入库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        outDTO.setSumCount(new BigDecimal("0"));
        try {
            //权限判断  总公司人员可查看所有厂区   厂区人员只能查看所属厂区
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可查看所有");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区或仓库");
                dto.setInCode(user.getDeptId());
            }
            //先用查询条件查询出符合条件的主键集合
            List<Long> idList = productionMaterialIncomeDao.queryIdList(dto);
            if(CollectionUtil.isNotEmpty(idList) && idList.size() > 0){
                outDTO.setTurnPageTotalNum(idList.size());
                long total = Long.valueOf(idList.size());
                //使用idList查询列表
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<ProductionMaterialIncomeInfo> page = new PageImpl<>(this.productionMaterialIncomeDao.selectProductionMaterialIncomeInfoListByPage(idList, pageRequest), pageRequest, total);
                //获取分页数据
                List<ProductionMaterialIncomeInfo> list = page.toList();
                //处理入库方名称
                list = setProductionMaterialIncomeObject(list);
                //计算入库产量合计
                outDTO.setSumCount(formatSumIncomeCount(list));
                //员工产量合计
                BigDecimal sumProducerCount = new BigDecimal("0");
                //循环获取生产者集合信息
                for(ProductionMaterialIncomeInfo info : list){
                    List<ProductProducerInfo> producerInfoList = productionMaterialIncomeDetailDao.selectProductProducerInfoList(info.getId(),dto.getProducerName());
                    info.setProducerInfoList(producerInfoList);
                    for(ProductProducerInfo userInfo : producerInfoList){
                        BigDecimal producerNum = userInfo.getProducerNum();
                        sumProducerCount = sumProducerCount.add(producerNum);
                    }
                }
                outDTO.setSumProducerCount(sumProducerCount);
                outDTO.setProductionMaterialIncomeInfoList(list);
            }else{
                outDTO.setTurnPageTotalNum(0);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("产量入库queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto)  {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            String billNo = getBillNoList(user);
            ProductionMaterialIncomeEntity entity = BeanCopyUtils.copy(dto,ProductionMaterialIncomeEntity.class);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            //生成单据号
            entity.setBillNo(billNo);
            int i = productionMaterialIncomeDao.insert(entity);
            List<ProductProducerInfo> producerInfoList = dto.getProducerInfoList();
            List<ProductionMaterialIncomeDetailEntity> detailEntities = new ArrayList<>();
            for(ProductProducerInfo info : producerInfoList){
                ProductionMaterialIncomeDetailEntity detailEntity = new ProductionMaterialIncomeDetailEntity();
                //主表主键
                detailEntity.setIncomeId(entity.getId());
                //员工
                detailEntity.setProducer(info.getProducer());
                //产量
                detailEntity.setProducerNum(info.getProducerNum());
                detailEntities.add(detailEntity);
            }
            productionMaterialIncomeDetailDao.insertBatch(detailEntities);
            //修改库存
            materialInventoryService.updateStockInventory(dto.getMaterialCode(), dto.getInCode(), dto.getIncomeNum(),"add",date);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",入库产量:"+dto.getIncomeNum().toString()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.PRODUCTION_MATERIAL_INCOME.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            //修改了数量 要实时更新库存
            ProductionMaterialIncomeEntity entity = productionMaterialIncomeDao.selectById(dto.getId());
            ProductionMaterialIncomeEntity newEntity = BeanCopyUtils.copy(dto,ProductionMaterialIncomeEntity.class);
            newEntity.setUpdateBy(user.getUserLogin());
            newEntity.setUpdateTime(date);
            int i = productionMaterialIncomeDao.updateById(newEntity);
            log.info("删除原员工产量信息");
            productionMaterialIncomeDetailDao.deleteByIncomeId(dto.getId());
            log.info("生成新的员工产量信息");
            List<ProductProducerInfo> producerInfoList = dto.getProducerInfoList();
            List<ProductionMaterialIncomeDetailEntity> detailEntities = new ArrayList<>();
            for(ProductProducerInfo info : producerInfoList){
                ProductionMaterialIncomeDetailEntity detailEntity = new ProductionMaterialIncomeDetailEntity();
                //主表主键
                detailEntity.setIncomeId(dto.getId());
                //员工
                detailEntity.setProducer(info.getProducer());
                //产量
                detailEntity.setProducerNum(info.getProducerNum());
                detailEntities.add(detailEntity);
            }
            productionMaterialIncomeDetailDao.insertBatch(detailEntities);
            BigDecimal count = entity.getIncomeNum();
            log.info("原数量:"+count.toString());
            BigDecimal updateCount = dto.getIncomeNum();
            log.info("修改后数量:"+updateCount.toString());
            BigDecimal num = updateCount.subtract(count);
            log.info("修改后数量-原数量:"+num.toString());
            if(num.compareTo(new BigDecimal(0)) > 0){
                log.info("修改数量大于原数量，需要增加:"+num.toString()+"的库存");
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), num,"add",date);
            }else if(num.compareTo(new BigDecimal(0)) < 0){
                log.info("修改数量小于原数量，需要减少:"+num.multiply(new BigDecimal(-1))+"的库存");
                num  = num.multiply(new BigDecimal(-1));
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), num,"reduce",date);
            }else{
                log.info("修改数量等于原数量，不需要增加库存");
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",入库产量:"+dto.getIncomeNum().toString()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.PRODUCTION_MATERIAL_INCOME.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto)  {
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            ProductionMaterialIncomeEntity entity = productionMaterialIncomeDao.selectById(dto.getId());
            //删除数据要更新库存
            materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getIncomeNum(),"reduce",date);
            int i = productionMaterialIncomeDao.deleteById(dto.getId());
            log.info("删除员工产量信息");
            productionMaterialIncomeDetailDao.deleteByIncomeId(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",入库产量:"+dto.getIncomeNum().toString()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.PRODUCTION_MATERIAL_INCOME.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public List<ProductionMaterialIncomeInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("产量入库queryListForExport开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        //最后导出的数据集合
        List<ProductionMaterialIncomeInfo> exportList = new ArrayList<>();
        try {
            //权限判断  总公司人员可查看所有厂区   厂区人员只能查看所属厂区
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可查看所有");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区或仓库");
                dto.setInCode(user.getDeptId());
            }
            //先用查询条件查询出符合条件的主键集合
            List<Long> idList = productionMaterialIncomeDao.queryIdList(dto);
            if(CollectionUtil.isNotEmpty(idList) && idList.size() > 0){
                //开始查询
                List<ProductionMaterialIncomeInfo> list = productionMaterialIncomeDao.queryListForExport(idList);
                //处理入库方名称
                list = setProductionMaterialIncomeObject(list);
                //计算入库产量合计
                BigDecimal sumIncomeCount = formatSumIncomeCount(list);
                //员工产量合计
                BigDecimal sumProducerCount = new BigDecimal("0");
                //循环获取生产者集合信息
                for(ProductionMaterialIncomeInfo info : list){
                    List<ProductProducerInfo> producerInfoList = productionMaterialIncomeDetailDao.selectProductProducerInfoList(info.getId(),dto.getProducerName());
                    for(ProductProducerInfo producerInfo : producerInfoList){
                        ProductionMaterialIncomeInfo newInfo = BeanCopyUtils.copy(info,ProductionMaterialIncomeInfo.class);
                        newInfo.setProducerName(producerInfo.getProducerName());
                        newInfo.setProducerNum(producerInfo.getProducerNum());
                        exportList.add(newInfo);
                        sumProducerCount = sumProducerCount.add(newInfo.getProducerNum());
                    }
                }
                //增加最后一行合计列
                ProductionMaterialIncomeInfo sumInfo = new ProductionMaterialIncomeInfo();
                sumInfo.setUnitName("合计:");
                sumInfo.setIncomeNum(sumIncomeCount);
                sumInfo.setProducerNum(sumProducerCount);
                exportList.add(sumInfo);
            }else{
                log.info("idList  is null");
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.PRODUCTION_MATERIAL_INCOME.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("产量入库queryListForExport结束");
        return exportList;
    }


    /**
     * 赋值产量入库方名称
     * @param list
     * @return
     */
    private List<ProductionMaterialIncomeInfo> setProductionMaterialIncomeObject(List<ProductionMaterialIncomeInfo> list){
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            log.info("获取厂区和仓库集合");
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            for(ProductionMaterialIncomeInfo info : list){
                //入库方
                String inCode = info.getInCode();
                if(Constants.FACTORY_CODE_PREFIX.equals(inCode.substring(0,1))){
                    //工厂
                    for(SysFactoryInfo fInfo : factoryInfoList){
                        if(inCode.equals(fInfo.getCode())){
                            info.setInName(fInfo.getName());
                        }
                    }
                }else{
                    //仓库
                    for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                        if(inCode.equals(sInfo.getCode())){
                            info.setInName(sInfo.getName());
                        }
                    }
                }
            }
        }else{
            log.info("list is null");
        }
        return list;
    }

    /**
     * 格式化单据号
     * @param user
     * @return
     */
    private String getBillNoList(UserLoginOutDTO user){
        String billNo="";
        String prefix = DataUtils.formatBillNoPrefix(user,"进");
        List<String> list = productionMaterialIncomeDao.queryBillNoListByParam(prefix);
        StringBuffer sb = new StringBuffer();
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            billNo = DataUtils.formatBillNo(list);
        }else{
            sb.append(prefix).append("0001");
            billNo = sb.toString();
        }
        return billNo;
    }

    /**
     * 计算入库产量合计
     * @param list
     * @return
     */
    private BigDecimal formatSumIncomeCount(List<ProductionMaterialIncomeInfo> list){
        BigDecimal sumCount = new BigDecimal("0");
        for(ProductionMaterialIncomeInfo info: list){
            BigDecimal count = info.getIncomeNum();
            sumCount = sumCount.add(count);
        }
        return sumCount;
    }


}
