package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.SysFactoryDao;
import com.example.project.demos.web.dto.list.SysFactoryAndStorePopInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.sysFactory.*;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.SysFactoryService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("sysFactoryService")
public class SysFactoryServiceImpl  implements SysFactoryService {

    @Resource
    private SysFactoryDao sysFactoryDao;
    
    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("厂区queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SysFactoryInfo sysFactoryInfo = sysFactoryDao.selectSysFactoryInfoById(id);
            outDTO = BeanUtil.copyProperties(sysFactoryInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("厂区queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("厂区queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.sysFactoryDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                SysFactoryEntity sysFactory = BeanCopyUtils.copy(queryByPageDTO,SysFactoryEntity.class);
                //开始分页查询
                Page<SysFactoryInfo> page = new PageImpl<>(this.sysFactoryDao.selectSysFactoryInfoListByPage(sysFactory, pageRequest), pageRequest, total);
                //获取分页数据
                List<SysFactoryInfo> list = page.toList();
                //出参赋值
                outDTO.setSysFactoryInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("厂区queryByPage结束");
        return outDTO;
    }


    @Override
    public QueryPopPageListOutDTO queryPopListByPage(QueryPopPageListDTO dto) {
        log.info("厂区/仓储queryPopListByPage开始");
        QueryPopPageListOutDTO outDTO = new QueryPopPageListOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.sysFactoryDao.countPop(dto.getCode(),dto.getName(),dto.getType());
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<SysFactoryAndStorePopInfo> page = new PageImpl<>(this.sysFactoryDao.queryPopListByPage(dto.getCode(),dto.getName(),dto.getType(), pageRequest), pageRequest, total);
                //获取分页数据
                List<SysFactoryAndStorePopInfo> list = page.toList();
                //出参赋值
                outDTO.setPopList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("厂区/仓储queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            //判断厂区编号是否存在
            int k = sysFactoryDao.checkCode(dto.getCode());
            if( k > 0){
                errorCode= ErrorCodeEnums.FACTORY_CODE_IS_EXIST.getCode();
                errortMsg= ErrorCodeEnums.FACTORY_CODE_IS_EXIST.getDesc();
            }else{
                SysFactoryEntity sysFactoryEntity = BeanCopyUtils.copy(dto,SysFactoryEntity.class);
                sysFactoryEntity.setCreateBy("zhangyunning");
                sysFactoryEntity.setCreateTime(new Date());
                int i = sysFactoryDao.insert(sysFactoryEntity);
            }
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
            SysFactoryEntity sysFactoryEntity = BeanCopyUtils.copy(dto,SysFactoryEntity.class);
            sysFactoryEntity.setCreateBy("zhangyunning");
            sysFactoryEntity.setUpdateTime(new Date());
            int i = sysFactoryDao.updateById(sysFactoryEntity);
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
            int i = sysFactoryDao.deleteById(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }
}
