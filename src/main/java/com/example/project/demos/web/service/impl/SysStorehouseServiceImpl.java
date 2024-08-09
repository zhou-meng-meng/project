package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.SysStorehouseDao;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.sysStorehouse.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.service.SysStorehouseService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;


@Slf4j
@Service("sysStorehouseService")
public class SysStorehouseServiceImpl  implements SysStorehouseService {

    @Resource
    private SysStorehouseDao sysStorehouseDao;
    @Autowired
    private SysLogService sysLogService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("仓库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SysStorehouseInfo sysStorehouseInfo = sysStorehouseDao.selectSysStorehouseInfoById(id);
            outDTO = BeanUtil.copyProperties(sysStorehouseInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("仓库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("仓库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.sysStorehouseDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //转换实体入参
                SysStorehouseEntity sysStorehouse = BeanCopyUtils.copy(dto,SysStorehouseEntity.class);
                //开始分页查询
                Page<SysStorehouseInfo> page = new PageImpl<>(this.sysStorehouseDao.selectSysStorehouseInfoListByPage(sysStorehouse, pageRequest), pageRequest, total);
                //获取分页数据
                List<SysStorehouseInfo> list = page.toList();
                //出参赋值
                outDTO.setSysStorehouseInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("仓库queryByPage结束");
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
            //判断仓库编码是否存在
            int k = sysStorehouseDao.checkCode(dto.getCode());
            if(k > 0){
                errorCode= ErrorCodeEnums.STORE_CODE_IS_EXIST.getCode();
                errortMsg= ErrorCodeEnums.STORE_CODE_IS_EXIST.getDesc();
            }else {
                SysStorehouseEntity entity = BeanCopyUtils.copy(dto,SysStorehouseEntity.class);
                entity.setCreateBy(user.getUserLogin());
                entity.setCreateTime(date);
                int i = sysStorehouseDao.insert(entity);
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "仓库编号:"+ dto.getCode() +",仓库名称:"+dto.getName()+",地址:"+dto.getAddress()+",负责人:"+dto.getManageName()+",电话:"+dto.getManageTel();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_STOREHOUSE.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
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
            SysStorehouseEntity entity = BeanCopyUtils.copy(dto,SysStorehouseEntity.class);
            entity.setCreateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = sysStorehouseDao.updateById(entity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "仓库编号:"+ dto.getCode() +",仓库名称:"+dto.getName()+",地址:"+dto.getAddress()+",负责人:"+dto.getManageName()+",电话:"+dto.getManageTel();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_STOREHOUSE.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
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
            int i = sysStorehouseDao.deleteById(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "仓库编号:"+ dto.getCode() +",仓库名称:"+dto.getName()+",地址:"+dto.getAddress()+",负责人:"+dto.getManageName()+",电话:"+dto.getManageTel();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_STOREHOUSE.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

}
