package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.MaterialInfoDao;
import com.example.project.demos.web.dao.MaterialInventoryDao;
import com.example.project.demos.web.dao.SysFactoryDao;
import com.example.project.demos.web.dao.SysStorehouseDao;
import com.example.project.demos.web.dto.list.*;
import com.example.project.demos.web.dto.materialInventory.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.MaterialInventoryEntity;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.UserTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.MaterialInventoryService;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("materialInventoryService")
public class MaterialInventoryServiceImpl  implements MaterialInventoryService {

    @Resource
    private MaterialInventoryDao materialInventoryDao;
    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;

    @Resource
    private MaterialInfoDao materialInfoDao;
    @Autowired
    private SysLogService sysLogService;
    
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("实时库存queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //添加权限  总公司的  查看所有  其他的角色 查看自己所在厂区/仓库的数据
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可以查看所有数据");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区的数据");
                queryByPageDTO.setStockCode(user.getDeptId());
            }
            //先用查询条件查询物料总条数
            long total = this.materialInventoryDao.countMaterialCode(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<String> page = new PageImpl<>(this.materialInventoryDao.selectMaterialCodeByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取物料编号数据
                List<String> codeList  = page.toList();
                //获取物料库存信息
                List<MaterialInventoryInfo> list = materialInventoryDao.selectMaterialInventoryList(codeList,queryByPageDTO.getStockCode());
                list = setMaterialInventoryObject(list);
                for(MaterialInventoryInfo info : list){
                    info.setGroupBy(info.getMaterialCode()+info.getMaterialName()+info.getModelName()+info.getUnitName());
                }
                //按照物料编号  物料名称  型号名称  单位名称分组
                Map<String, List<MaterialInventoryInfo>> collectMap = list.stream().
                        collect(Collectors.groupingBy(MaterialInventoryInfo::getGroupBy ));
                Set<String> set = collectMap.keySet();
                List<MaterialInventoryInfo> returnList = new ArrayList<>();
                for(String s : set){
                    //各物料合计数量
                    BigDecimal tollNum = new BigDecimal(0);
                    List<MaterialInventoryInfo> infoList = collectMap.get(s);
                    //厂区物料库存集合  存厂区名称和库存数量
                    List<MaterialInventoryStockInfo> stockInfoList = new ArrayList<>();
                    for(MaterialInventoryInfo info : infoList){
                        tollNum = tollNum.add(info.getInventoryNum());
                        MaterialInventoryStockInfo stockInfo = new MaterialInventoryStockInfo();
                        stockInfo.setStockName(info.getStockName());
                        stockInfo.setInventoryNum(info.getInventoryNum());
                        stockInfoList.add(stockInfo);
                    }
                    MaterialInventoryInfo info = new MaterialInventoryInfo();
                    info.setMaterialCode(infoList.get(0).getMaterialCode());
                    info.setMaterialName(infoList.get(0).getMaterialName());
                    info.setModelName(infoList.get(0).getModelName());
                    info.setUnitName(infoList.get(0).getUnitName());
                    info.setTollNum(tollNum);
                    info.setStockInfo(stockInfoList);
                    returnList.add(info);
                }
                //出参赋值
                outDTO.setMaterialInventoryInfoList(returnList);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("实时库存queryByPage结束");
        return outDTO;
    }

    @Override
    public QueryByPagePopOutDTO queryPagePopList(QueryByPagePopDTO queryByPageDTO) {
        log.info("实时库存queryByPage开始");
        QueryByPagePopOutDTO outDTO = new QueryByPagePopOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询物料总条数
            long total = this.materialInventoryDao.countPop(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<MaterialInventoryInfo> page = new PageImpl<>(this.materialInventoryDao.selectMaterialByPagePop(queryByPageDTO, pageRequest), pageRequest, total);
                //获取物料编号数据
                List<MaterialInventoryInfo> list  = page.toList();
                //赋值厂区/仓库名称
                list = setMaterialInventoryObject(list);
                //出参赋值
                outDTO.setMaterialInventoryInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("实时库存queryByPage结束");
        return outDTO;
    }


    @Override
    public int checkIfMaterialCodeExist(String materialCode, String code) {
        return materialInventoryDao.checkIfMaterialCodeExist(materialCode,code);
    }

    /**
     *
     * @param materialCode 物料编号
     * @param code 厂区/仓库编码
     * @param num 数量
     * @param type 增加/减少
     * @param date 日期
     * @return
     * @throws UnknownHostException
     */
    @Override
    public int updateStockInventory(String materialCode, String code, BigDecimal num, String type, Date date)  {
        log.info("更新库存开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        String storeName="";
        MaterialInfo mInfo = null;
        String operationType="";
        try{
            log.info("判断该物料是否在仓库/厂区中");
            int k = materialInventoryDao.checkIfMaterialCodeExist(materialCode,code);
            if(k > 0){
                operationType = OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode();
                log.info("当前厂区/仓库:"+code +" 已经存储了物料:"+materialCode);
                if("add".equals(type)){
                    log.info("增加库存操作");
                    materialInventoryDao.addStockInventory(materialCode,code,num);
                }else{
                    log.info("减少库存操作");
                    materialInventoryDao.reduceStockInventory(materialCode,code,num);
                }
            }else{
                operationType = OperationTypeEnums.OPERATION_TYPE_ADD.getCode();
                log.info("当前厂区/仓库:"+code +" 没有存储物料:"+materialCode+",新增库存");
                MaterialInventoryEntity entity = new MaterialInventoryEntity();
                entity.setMaterialCode(materialCode);
                entity.setStockCode(code);
                entity.setInventoryNum(num);
                entity.setLastUpdateTime(date);
                materialInventoryDao.insert(entity);
            }
            log.info("获取物料名称和厂区/仓库名称");
            mInfo = materialInfoDao.selectMaterialInfoByCode(materialCode);
            storeName = "";
            if("F".equals(code.substring(0,1))){
                SysFactoryInfo fInfo = sysFactoryDao.selectSysFactoryInfoByCode(code);
                storeName = fInfo.getName();
            }else{
                SysStorehouseInfo sInfo = sysStorehouseDao.selectSysStorehouseInfoByCode(code);
                storeName = sInfo.getName();
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录日志
        String info = "物料编号:"+materialCode+",物料名称:"+mInfo.getName()+",数量:"+num.toString()+",仓库/厂区:"+storeName;
        int i = sysLogService.insertSysLog(FunctionTypeEnums.MATERIAL_INVENTORY.getCode(), operationType,user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("更新库存结束");
        return i;
    }

    /**
     * 赋值实时库存  库存方名称
     * @param list
     * @return
     */
    private List<MaterialInventoryInfo> setMaterialInventoryObject(List<MaterialInventoryInfo> list){
        //获取厂区和仓库集合
        List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
        List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
        for(MaterialInventoryInfo info : list){
            //库存方
            String code = info.getStockCode();
            if(Constants.FACTORY_CODE_PREFIX.equals(code.substring(0,1))){
                //工厂
                for(SysFactoryInfo fInfo : factoryInfoList){
                    if(code.equals(fInfo.getCode())){
                        info.setStockName(fInfo.getName());
                    }
                }
            }else{
                //仓库
                for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                    if(code.equals(sInfo.getCode())){
                        info.setStockName(sInfo.getName());
                    }
                }
            }
        }
        return list;
    }
}
