package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.ProductionMaterialIncomeDao;
import com.example.project.demos.web.dao.SysFactoryDao;
import com.example.project.demos.web.dao.SysStorehouseDao;
import com.example.project.demos.web.dto.list.ProductionMaterialIncomeInfo;
import com.example.project.demos.web.dto.list.RawMaterialIncomeInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.productionMaterialIncome.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
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
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("productionMaterialIncomeService")
public class ProductionMaterialIncomeServiceImpl  implements ProductionMaterialIncomeService {

    @Resource
    private ProductionMaterialIncomeDao productionMaterialIncomeDao;

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
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("产量入库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //权限判断  总公司人员可查看所有厂区   厂区人员只能查看所属厂区
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可查看所有");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区或仓库");
                queryByPageDTO.setInCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.productionMaterialIncomeDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<ProductionMaterialIncomeInfo> page = new PageImpl<>(this.productionMaterialIncomeDao.selectProductionMaterialIncomeInfoListByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取分页数据
                List<ProductionMaterialIncomeInfo> list = page.toList();
                //处理入库方名称
                list = setProductionMaterialIncomeObject(list);
                //出参赋值
                outDTO.setProductionMaterialIncomeInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
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
            ProductionMaterialIncomeEntity entity = BeanCopyUtils.copy(dto,ProductionMaterialIncomeEntity.class);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            int i = productionMaterialIncomeDao.insert(entity);
            //修改库存
            i = materialInventoryService.updateStockInventory(dto.getMaterialCode(), dto.getFactoryCode(), dto.getIncomeNum(),"add",date);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getIncomeNum().toString()+",生产员工:"+dto.getProducerName()+",入库方:"+dto.getFactoryName();
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
                log.info("修改数量小于原数量，需要减少:"+num.toString()+"的库存");
                num  = num.multiply(new BigDecimal(-1));
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), num,"reduce",date);
            }else{
                log.info("修改数量等于原数量，不需要增加库存");
            }
            ProductionMaterialIncomeEntity newEntity = BeanCopyUtils.copy(dto,ProductionMaterialIncomeEntity.class);
            newEntity.setUpdateBy(user.getUserLogin());
            newEntity.setUpdateTime(date);
            int i = productionMaterialIncomeDao.updateById(newEntity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getIncomeNum().toString()+",生产员工:"+dto.getProducerName()+",入库方:"+dto.getFactoryName();
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
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getIncomeNum().toString()+",生产员工:"+dto.getProducerName()+",入库方:"+dto.getFactoryName();
        sysLogService.insertSysLog(FunctionTypeEnums.PRODUCTION_MATERIAL_INCOME.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }


    /**
     * 赋值产量入库方名称
     * @param list
     * @return
     */
    private List<ProductionMaterialIncomeInfo> setProductionMaterialIncomeObject(List<ProductionMaterialIncomeInfo> list){
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
        return list;
    }

}
