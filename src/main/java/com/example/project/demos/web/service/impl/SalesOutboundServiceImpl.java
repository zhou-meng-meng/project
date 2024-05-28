package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.SalesOutboundDao;
import com.example.project.demos.web.dao.SysFactoryDao;
import com.example.project.demos.web.dao.SysStorehouseDao;
import com.example.project.demos.web.dto.list.SalesOutboundInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.salesOutbound.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.SalesOutboundEntity;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.UserTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.SalesOutboundService;
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
import java.util.Map;


@Slf4j
@Service("salesOutboundService")
public class SalesOutboundServiceImpl  implements SalesOutboundService {
    @Resource
    private SalesOutboundDao salesOutboundDao;
    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;


    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("销售出库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SalesOutboundInfo SalesOutboundInfo = salesOutboundDao.selectSalesOutboundInfoById(id);
            List<SalesOutboundInfo> list = new ArrayList<>();
            list.add(SalesOutboundInfo);
            list = setSalesOutboundObject(list);
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售出库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("销售出库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //添加权限  总公司审核权限的  查看所有  厂区和仓库的 查看所属厂区或者仓库数据
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，判断是否有审核权限");
                List<String> list = user.getAuthorityType();
                if(list.contains("0")){
                    log.info("具有审核权限，查询所有数据");
                }else{
                    log.info("不具有审核权限，查询自己提交的数据");
                    queryByPageDTO.setSaler(user.getUserLogin());
                }
            }else{
                log.info("当前登录人不属于总公司，只能查看自己提交的数据");
                queryByPageDTO.setSaler(user.getUserLogin());
            }
            //先用查询条件查询总条数
            long total = this.salesOutboundDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<SalesOutboundInfo> page = new PageImpl<>(this.salesOutboundDao.selectSalesOutboundInfoListByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取分页数据
                List<SalesOutboundInfo> list = page.toList();
                //赋值出库方名称
                list = setSalesOutboundObject(list);
                //出参赋值
                outDTO.setSalesOutboundInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售出库queryByPage结束");
        return outDTO;
    }



    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            SalesOutboundEntity SalesOutboundEntity = BeanCopyUtils.copy(dto,SalesOutboundEntity.class);
            SalesOutboundEntity.setCreateBy("zhangyunning");
            SalesOutboundEntity.setCreateTime(new Date());
            int i = salesOutboundDao.insert(SalesOutboundEntity);
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
            SalesOutboundEntity SalesOutboundEntity = BeanCopyUtils.copy(dto,SalesOutboundEntity.class);
            SalesOutboundEntity.setCreateBy("zhangyunning");
            SalesOutboundEntity.setUpdateTime(new Date());
            int i = salesOutboundDao.updateById(SalesOutboundEntity);
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
            int i = salesOutboundDao.deleteById(dto.getId());
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
     * 赋值销售出库方名称
     * @param list
     * @return
     */
    private List<SalesOutboundInfo> setSalesOutboundObject(List<SalesOutboundInfo> list){
        //获取厂区和仓库集合
        List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
        List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
        for(SalesOutboundInfo info : list){
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
        }
        return list;
    }
}
