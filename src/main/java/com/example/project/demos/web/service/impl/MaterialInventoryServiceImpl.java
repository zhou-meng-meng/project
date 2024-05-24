package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.MaterialInventoryDao;
import com.example.project.demos.web.dao.SysFactoryDao;
import com.example.project.demos.web.dao.SysStorehouseDao;
import com.example.project.demos.web.dto.list.MaterialInventoryInfo;
import com.example.project.demos.web.dto.list.MaterialInventoryStockInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.materialInventory.*;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.MaterialInventoryService;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("实时库存queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
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
                List<MaterialInventoryInfo> list = materialInventoryDao.selectMaterialInventoryList(codeList);
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
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("实时库存queryByPage结束");
        return outDTO;
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
