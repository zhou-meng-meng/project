package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.SysUserDao;
import com.example.project.demos.web.dto.list.SysUserInfo;
import com.example.project.demos.web.dto.sysUser.*;
import com.example.project.demos.web.entity.SysUserEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.SysUserService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Resource
    private SysUserDao SysUserDao;
    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("用户queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SysUserEntity SysUserEntity = this.SysUserDao.selectById(id);
            outDTO = BeanUtil.copyProperties(SysUserEntity, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("用户queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("用户queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.SysUserDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                SysUserEntity SysUser = BeanCopyUtils.copy(queryByPageDTO,SysUserEntity.class);
                //开始分页查询
                Page<SysUserInfo> page = new PageImpl<>(this.SysUserDao.selectSysUserInfoListByPage(SysUser, pageRequest), pageRequest, total);
                //获取分页数据
                List<SysUserInfo> list = page.toList();
                //出参赋值
                outDTO.setSysUserInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("用户queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            SysUserEntity SysUserEntity = BeanCopyUtils.copy(dto,SysUserEntity.class);
            SysUserEntity.setCreateBy("zhangyunning");
            SysUserEntity.setCreateTime(new Date());
            int i = SysUserDao.insert(SysUserEntity);
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
            SysUserEntity SysUserEntity = BeanCopyUtils.copy(dto,SysUserEntity.class);
            SysUserEntity.setCreateBy("zhangyunning");
            SysUserEntity.setUpdateTime(new Date());
            int i = SysUserDao.updateById(SysUserEntity);
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
            int i = SysUserDao.deleteById(dto.getId());
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
