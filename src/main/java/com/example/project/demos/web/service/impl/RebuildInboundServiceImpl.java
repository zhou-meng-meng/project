package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.RebuildInboundDao;
import com.example.project.demos.web.dao.SysFactoryDao;
import com.example.project.demos.web.dao.SysStorehouseDao;
import com.example.project.demos.web.dto.list.*;
import com.example.project.demos.web.dto.rebuildInbound.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.RebuildInboundEntity;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.UserTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.MaterialInventoryService;
import com.example.project.demos.web.service.RebuildInboundService;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.DateUtils;
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
@Service("rebuildInboundService")
public class RebuildInboundServiceImpl implements RebuildInboundService {

    @Resource
    private RebuildInboundDao rebuildInboundDao;
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
        log.info("重造入库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            RebuildInboundInfo info = rebuildInboundDao.selectRebuildInboundInfoById(id);
            //处理入库方
            List<RebuildInboundInfo> list = new ArrayList<>();
            list.add(info);
            list = setRebuildInboundObject(list);
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("重造入库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("重造入库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //添加权限  总公司审核权限的  查看所有  其他的角色 查看自己所在厂区/仓库的数据
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可以查看所有数据");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区的数据");
                dto.setInCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.rebuildInboundDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<RebuildInboundInfo> page = new PageImpl<>(this.rebuildInboundDao.selectRebuildInboundInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<RebuildInboundInfo> list = page.toList();
                list = setRebuildInboundObject(list);
                //出参赋值  集合和数量合计
                outDTO.setRebuildInboundInfoList(list);
                outDTO.setSumCount(formatSumCount(list));
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("重造出库queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        log.info("重造入库insert开始");
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            RebuildInboundEntity entity = BeanCopyUtils.copy(dto,RebuildInboundEntity.class);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            int i = rebuildInboundDao.insert(entity);
            //修改库存
            materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getRebuildCount(),"add",date);
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getRebuildDate()) +",物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",机器号:"+dto.getMachineName()+",班组:"+dto.getDutyName()+",数量:"+dto.getRebuildCount()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.REBUILD_INBOUND.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("重造入库insert结束");
        return outDTO;
    }

    @Override
    public EditOutDTO update(EditDTO dto) {
        log.info("重造入库update开始");
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            RebuildInboundEntity entity1 = rebuildInboundDao.selectById(dto.getId());
            RebuildInboundEntity entity = BeanCopyUtils.copy(dto,RebuildInboundEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = rebuildInboundDao.updateById(entity);
            //修改了数量 要实时更新库存
            BigDecimal count = entity1.getRebuildCount();
            log.info("原数量:"+count.toString());
            BigDecimal updateCount = dto.getRebuildCount();
            log.info("修改后数量:"+updateCount.toString());
            BigDecimal num = updateCount.subtract(count);
            log.info("修改后数量-原数量:"+num.toString());
            if(num.compareTo(new BigDecimal(0)) > 0){
                log.info("修改数量大于原数量，需要增加:"+num+"的库存");
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), num,"add",date);
            }else if(num.compareTo(new BigDecimal(0)) < 0){
                num  = num.multiply(new BigDecimal(-1));
                log.info("修改数量小于原数量，需要减少:"+num+"的库存");
                materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), num,"reduce",date);
            }else{
                log.info("修改数量等于原数量，不需要增加库存");
            }
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getRebuildDate()) +",物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",机器号:"+dto.getMachineName()+",班组:"+dto.getDutyName()+",数量:"+dto.getRebuildCount()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.REBUILD_INBOUND.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("重造入库update结束");
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) {
        log.info("重造入库deleteById开始");
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            RebuildInboundEntity entity = rebuildInboundDao.selectById(dto.getId());
            int i = rebuildInboundDao.deleteById(dto.getId());
            materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getRebuildCount(),"reduce",date);
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getRebuildDate()) +",物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",机器号:"+dto.getMachineName()+",班组:"+dto.getDutyName()+",数量:"+dto.getRebuildCount()+",入库方:"+dto.getInName();
        sysLogService.insertSysLog(FunctionTypeEnums.REBUILD_INBOUND.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("重造入库deleteById结束");
        return outDTO;
    }

    @Override
    public List<RebuildInboundInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("重造入库queryListForExport开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        List<RebuildInboundInfo> list = new ArrayList<>();
        try {
            //添加权限  总公司审核权限的  查看所有  其他的角色 查看自己所在厂区/仓库的数据
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可以查看所有数据");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区的数据");
                dto.setInCode(user.getDeptId());
            }
            list = rebuildInboundDao.queryListForExport(dto);
            list = setRebuildInboundObject(list);
            log.info("循环结束，增加合计列");
            RebuildInboundInfo sumInfo = new RebuildInboundInfo();
            sumInfo.setUnitName("合计:");
            sumInfo.setRebuildCount(formatSumCount(list));
            list.add(sumInfo);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.REBUILD_INBOUND.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("重造入库queryListForExport结束");
        return list;
    }

    /**
     * 赋值重造入库  入库方名称
     * @param list
     * @return
     */
    private List<RebuildInboundInfo> setRebuildInboundObject(List<RebuildInboundInfo> list){
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            log.info("获取厂区和仓库集合");
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            for(RebuildInboundInfo info : list){
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
     * 获取总数量
     * @param list
     * @return
     */
    private BigDecimal formatSumCount(List<RebuildInboundInfo> list){
        BigDecimal sumCount = new BigDecimal("0");
        for(RebuildInboundInfo info: list){
            BigDecimal count = info.getRebuildCount();
            sumCount = sumCount.add(count);
        }
        return sumCount;
    }

}
