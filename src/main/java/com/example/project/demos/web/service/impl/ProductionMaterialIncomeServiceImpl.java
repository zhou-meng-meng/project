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
import com.example.project.demos.web.enums.UserTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.ProductionMaterialIncomeService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            ProductionMaterialIncomeEntity ProductionMaterialIncomeEntity = BeanCopyUtils.copy(dto,ProductionMaterialIncomeEntity.class);
            ProductionMaterialIncomeEntity.setCreateBy("zhangyunning");
            ProductionMaterialIncomeEntity.setCreateTime(new Date());
            int i = productionMaterialIncomeDao.insert(ProductionMaterialIncomeEntity);
            //修改库存
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
    public EditOutDTO update(EditDTO dto) {
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            ProductionMaterialIncomeEntity ProductionMaterialIncomeEntity = BeanCopyUtils.copy(dto,ProductionMaterialIncomeEntity.class);
            ProductionMaterialIncomeEntity.setCreateBy("zhangyunning");
            ProductionMaterialIncomeEntity.setUpdateTime(new Date());
            int i = productionMaterialIncomeDao.updateById(ProductionMaterialIncomeEntity);
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
            int i = productionMaterialIncomeDao.deleteById(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
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
