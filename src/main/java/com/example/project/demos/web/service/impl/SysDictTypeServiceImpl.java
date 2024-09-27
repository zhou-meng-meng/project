package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.SysDictDataDao;
import com.example.project.demos.web.dao.SysDictTypeDao;
import com.example.project.demos.web.dto.list.SysDictDataInfo;
import com.example.project.demos.web.dto.list.SysDictDataKeyValueInfo;
import com.example.project.demos.web.dto.list.SysDictTypeInfo;
import com.example.project.demos.web.dto.sysDictType.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.SysDictTypeEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.SysDictTypeService;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service("sysDictTypeService")
public class SysDictTypeServiceImpl  implements SysDictTypeService {

    @Resource
    private SysDictTypeDao sysDictTypeDao;
    @Resource
    private SysDictDataDao sysDictDataDao;

    @Autowired
    private SysLogService sysLogService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("数据字段类型queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SysDictTypeEntity sysDictTypeEntity = this.sysDictTypeDao.selectById(id);
            outDTO = BeanUtil.copyProperties(sysDictTypeEntity, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("数据字段类型queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("数据字段类型queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.sysDictTypeDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                SysDictTypeEntity sysDictType = BeanCopyUtils.copy(queryByPageDTO,SysDictTypeEntity.class);
                //开始分页查询
                Page<SysDictTypeInfo> page = new PageImpl<>(this.sysDictTypeDao.queryAllByLimit(sysDictType, pageRequest), pageRequest, total);
                //获取分页数据
                List<SysDictTypeInfo> list = page.toList();
                //出参赋值
                outDTO.setSysDictTypeInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("数据字段类型queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            //判断录入的type值是否已经存在
            int k = sysDictTypeDao.checkByType(dto.getDictType());
            if(k > 0){
                errorCode = ErrorCodeEnums.DICT_TYPE_IS_EXIST.getCode();
                errortMsg = ErrorCodeEnums.DICT_TYPE_IS_EXIST.getDesc();
            }else{
                SysDictTypeEntity entity = BeanCopyUtils.copy(dto,SysDictTypeEntity.class);
                entity.setCreateBy(user.getUserLogin());
                entity.setCreateTime(date);
                int i = sysDictTypeDao.insert(entity);
            }
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "字典名称:"+ dto.getDictName() +",字典类型:"+dto.getDictType();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_DICT_TYPE.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
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
            SysDictTypeEntity entity = BeanCopyUtils.copy(dto,SysDictTypeEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = sysDictTypeDao.updateById(entity);
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "字典名称:"+ dto.getDictName() +",字典类型:"+dto.getDictType();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_DICT_TYPE.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) {
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            //获取数据
            SysDictTypeEntity entity = sysDictTypeDao.selectById(dto.getId());
            int i = sysDictTypeDao.deleteById(dto.getId());
            //删除下属字典数据
            int j = sysDictDataDao.deleteByType(entity.getDictType());
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "字典名称:"+ dto.getDictName() +",字典类型:"+dto.getDictType();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_DICT_TYPE.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public QueryKeyValueListOutDTO QueryKeyValueList(QueryKeyValueListDTO dto) {
        Map<String, List<SysDictDataKeyValueInfo>> map = new HashMap<>();
        QueryKeyValueListOutDTO outDTO = new QueryKeyValueListOutDTO();
        //获取dict_type集合
        List<SysDictTypeInfo> typeList = sysDictTypeDao.queryAll(new SysDictTypeEntity());
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            for(SysDictTypeInfo info : typeList){
                String type = info.getDictType();
                //根据type获取dict_data数据集合
                List<SysDictDataInfo> dataList = sysDictDataDao.queryList(type);
                List<SysDictDataKeyValueInfo> keyValueInfoList = new ArrayList<>();
                //遍历dataList  组装SysDictDataKeyValueInfo  键值对
                for(SysDictDataInfo dataInfo : dataList){
                    SysDictDataKeyValueInfo keyValueInfo = new SysDictDataKeyValueInfo();
                    keyValueInfo.setKey(dataInfo.getDictCode());
                    keyValueInfo.setValue(dataInfo.getDictValue());
                    keyValueInfoList.add(keyValueInfo);
                }
                map.put(type,keyValueInfoList);
            }
        }catch (Exception e){
            log.error("异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        outDTO.setMap(map);
        return outDTO;
    }
}
