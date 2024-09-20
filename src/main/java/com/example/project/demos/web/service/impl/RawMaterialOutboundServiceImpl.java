package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.list.RawMaterialOutboundInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.rawMaterialOutbound.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.RawMaterialOutboundEntity;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import com.example.project.demos.web.enums.*;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.MaterialInventoryService;
import com.example.project.demos.web.service.RawMaterialOutboundService;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.service.UploadFileInfoService;
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
@Service("rawMaterialOutboundService")
public class RawMaterialOutboundServiceImpl  implements RawMaterialOutboundService {

    @Resource
    private RawMaterialOutboundDao rawMaterialOutboundDao;

    @Resource
    private RawMaterialIncomeDao rawMaterialIncomeDao;
    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;

    @Autowired
    private MaterialInventoryService materialInventoryService;
    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private UploadFileInfoService uploadFileInfoService;
    

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("原材料出库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            RawMaterialOutboundInfo rawMaterialOutboundInfo = rawMaterialOutboundDao.selectRawMaterialOutboundInfoById(id);
            List<RawMaterialOutboundInfo> list = new ArrayList<>();
            list.add(rawMaterialOutboundInfo);
            list = setRawMaterialOutboundObject(list,RequestHolder.getUserInfo());
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("原材料出库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("原材料出库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        outDTO.setSumAmt(new BigDecimal("0"));
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
                dto.setOutCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.rawMaterialOutboundDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<RawMaterialOutboundInfo> page = new PageImpl<>(this.rawMaterialOutboundDao.selectRawMaterialOutboundInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<RawMaterialOutboundInfo> list = page.toList();
                //赋值出库方名称
                list = setRawMaterialOutboundObject(list,RequestHolder.getUserInfo());
                //出参赋值
                outDTO = formatSumObject(list,outDTO);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("原材料出库queryByPage结束");
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
            RawMaterialOutboundEntity entity = BeanCopyUtils.copy(dto,RawMaterialOutboundEntity.class);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            log.info("系统计算平均价格");
            //由系统计算该物料进料时的平均单价，带入此字段，录入员不可见，总公司可见
            BigDecimal unitPrice = rawMaterialIncomeDao.getAvgUnitPriceOfMaterial(dto.getMaterialCode());
            //计算总价格
            BigDecimal tollAmount = unitPrice.multiply(dto.getCount());
            entity.setUnitPrice(unitPrice);
            entity.setTollAmount(tollAmount);
            //生成单据号
            entity.setBillNo(getBillNoList(user));
            int i = rawMaterialOutboundDao.insert(entity);
            //修改库存 减少
            materialInventoryService.updateStockInventory(dto.getMaterialCode(), dto.getOutCode(), dto.getCount(),"reduce",date);
            //开始处理附件信息
            uploadFileInfoService.updateByBusinessId(entity.getId(),dto.getFileIdList());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getCount().toString()+",出库方:"+dto.getOutName()+",领用人:"+dto.getReceiverName();
        sysLogService.insertSysLog(FunctionTypeEnums.RAW_MATERIAL_OUTBOUND.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public EditOutDTO update(EditDTO dto)  {
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            RawMaterialOutboundEntity entity = rawMaterialOutboundDao.selectById(dto.getId());
            RawMaterialOutboundEntity newEntity = BeanCopyUtils.copy(dto,RawMaterialOutboundEntity.class);
            //原单价不变   重新计算总金额
            BigDecimal tollAmount = entity.getUnitPrice().multiply(dto.getCount());
            newEntity.setTollAmount(tollAmount);
            newEntity.setUpdateBy(user.getUserLogin());
            newEntity.setUpdateTime(date);
            int i = rawMaterialOutboundDao.updateById(newEntity);

            //修改了数量  要更新库存
            BigDecimal count = entity.getCount();
            log.info("原数量:"+count.toString());
            BigDecimal updateCount = dto.getCount();
            log.info("修改后数量:"+updateCount.toString());
            BigDecimal num = updateCount.subtract(count);
            log.info("修改后数量-原数量:"+num);
            if(num.compareTo(new BigDecimal(0)) > 0){
                log.info("修改数量大于原数量，需要减少:"+num+"的库存");
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), num,"reduce",date);
            }else if(num.compareTo(new BigDecimal(0)) < 0){
                num  = num.multiply(new BigDecimal(-1));
                log.info("修改数量小于原数量，需要增加:"+num+"的库存");
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), num,"add",date);
            }else{
                log.info("修改数量等于原数量，不需要增加库存");
            }
            //开始处理附件信息
            uploadFileInfoService.updateByBusinessId(newEntity.getId(),dto.getFileIdList());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getCount().toString()+",出库方:"+dto.getOutName()+",领用人:"+dto.getReceiverName();
        sysLogService.insertSysLog(FunctionTypeEnums.RAW_MATERIAL_OUTBOUND.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            RawMaterialOutboundEntity entity = rawMaterialOutboundDao.selectById(dto.getId());
            int i = rawMaterialOutboundDao.deleteById(dto.getId());
            log.info("开始恢复库存:"+entity.getCount());
            materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), entity.getCount(),"add",date);
            log.info("开始删除附件信息");
            uploadFileInfoService.deleteFileByBusinessId(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getCount().toString()+",出库方:"+dto.getOutName()+",领用人:"+dto.getReceiverName();
        sysLogService.insertSysLog(FunctionTypeEnums.RAW_MATERIAL_OUTBOUND.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public List<RawMaterialOutboundInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("原材料出库queryListForExport开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        List<RawMaterialOutboundInfo> list = new ArrayList<>();
        try {
            //权限判断  总公司人员可查看所有厂区   厂区人员只能查看所属厂区
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可查看所有");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区或仓库");
                dto.setOutCode(user.getDeptId());
            }
            list = rawMaterialOutboundDao.queryListForExport(dto);
            //赋值出库方名称
            list = setRawMaterialOutboundObject(list,RequestHolder.getUserInfo());
            //添加合计列
            list = formatSumObjectForExport(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.RAW_MATERIAL_OUTBOUND.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("原材料出库queryListForExport结束");
        return list;
    }

    /**
     * 赋值原材料出库方名称
     * @param list
     * @return
     */
    private List<RawMaterialOutboundInfo> setRawMaterialOutboundObject(List<RawMaterialOutboundInfo> list,UserLoginOutDTO userLoginOutDTO){
        boolean isPrice = DataUtils.getIsPrice(userLoginOutDTO);
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            //获取厂区和仓库集合
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            for(RawMaterialOutboundInfo info : list){
                //出库方
                String outCode = info.getOutCode();
                if(Constants.FACTORY_CODE_PREFIX.equals(outCode.substring(0,1))){
                    //工厂
                    for(SysFactoryInfo fInfo : factoryInfoList){
                        if(outCode.equals(fInfo.getCode())){
                            info.setOutName(fInfo.getName());
                        }
                    }
                }else{
                    //仓库
                    for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                        if(outCode.equals(sInfo.getCode())){
                            info.setOutName(sInfo.getName());
                        }
                    }
                }
                //权限判断  单价和金额
                if(isPrice){
                    //log.info("具有单价权限,不处理");
                }else{
                    info.setUnitPrice(new BigDecimal(0));
                    info.setTollAmount(new BigDecimal(0));
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
        String prefix = DataUtils.formatBillNoPrefix(user,"出");
        List<String> list = rawMaterialOutboundDao.queryBillNoListByParam(prefix);
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
     * 菜单列表获取数量合计和金额合计
     * @param list
     * @param outDTO
     * @return
     */
    private QueryByPageOutDTO formatSumObject(List<RawMaterialOutboundInfo> list, QueryByPageOutDTO outDTO){
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(RawMaterialOutboundInfo info: list){
            BigDecimal count = info.getCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
        }
        outDTO.setRawMaterialOutboundInfoList(list);
        outDTO.setSumAmt(sumAmt);
        outDTO.setSumCount(sumCount);
        return outDTO;
    }

    /**
     * 新增合计列
     * @param list
     * @return
     */
    private List<RawMaterialOutboundInfo> formatSumObjectForExport (List<RawMaterialOutboundInfo> list){
        BigDecimal sumAmt = new BigDecimal("0");
        BigDecimal sumCount = new BigDecimal("0");
        for(RawMaterialOutboundInfo info: list){
            BigDecimal count = info.getCount();
            BigDecimal tollAmount = info.getTollAmount();
            sumAmt = sumAmt.add(tollAmount);
            sumCount = sumCount.add(count);
        }
        RawMaterialOutboundInfo info = new RawMaterialOutboundInfo();
        info.setUnitName("合计:");
        info.setCount(sumCount);
        info.setTollAmount(sumAmt);
        list.add(info);
        return list;
    }


}
