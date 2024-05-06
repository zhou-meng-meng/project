package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.SysDictDataDao;
import com.example.project.demos.web.dto.list.SysDictDataInfo;
import com.example.project.demos.web.dto.list.SysDictTypeInfo;
import com.example.project.demos.web.dto.sysDictData.*;
import com.example.project.demos.web.entity.SysDictDataEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.SysDictDataService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("sysDictDataService")
public class SysDictDataServiceImpl  implements SysDictDataService {

    @Resource
    private SysDictDataDao sysDictDataDao;
    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("数据字典数值queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SysDictDataEntity sysDictDataEntity = this.sysDictDataDao.selectById(id);
            outDTO = BeanUtil.copyProperties(sysDictDataEntity, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("数据字典数值queryById结束");
        return outDTO;
    }

    @Override
    public QueryListOutDTO queryList(QueryListDTO dto) {
        log.info("数据字典数值queryList开始");
        QueryListOutDTO outDTO = new QueryListOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            List<SysDictDataInfo> list = sysDictDataDao.queryList(dto.getDictType());
            outDTO.setSysDictDataInfoList(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("数据字典数值queryList结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            SysDictDataEntity sysDictDataEntity = BeanCopyUtils.copy(dto,SysDictDataEntity.class);
            sysDictDataEntity.setCreateBy("zhangsan");
            sysDictDataEntity.setCreateTime(new Date());
            int i = sysDictDataDao.insert(sysDictDataEntity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
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
            SysDictDataEntity sysDictDataEntity = BeanCopyUtils.copy(dto,SysDictDataEntity.class);
            sysDictDataEntity.setCreateBy("lisi");
            sysDictDataEntity.setUpdateTime(new Date());
            int i = sysDictDataDao.updateById(sysDictDataEntity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
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
            int i = sysDictDataDao.deleteById(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }
}
