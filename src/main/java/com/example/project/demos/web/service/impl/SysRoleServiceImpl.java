package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.SysRoleDao;
import com.example.project.demos.web.dto.list.SysRoleInfo;
import com.example.project.demos.web.dto.sysRole.*;
import com.example.project.demos.web.entity.SysRoleEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.SysRoleMenuService;
import com.example.project.demos.web.service.SysRoleService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("sysRoleService")
public class SysRoleServiceImpl  implements SysRoleService {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Resource
    private SysRoleDao sysRoleDao;
    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("角色维护queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SysRoleEntity sysRoleEntity = this.sysRoleDao.selectById(id);
            outDTO = BeanUtil.copyProperties(sysRoleEntity, QueryByIdOutDTO.class);
            //获取权限菜单集合
            List<String> list = sysRoleMenuService.queryMenuListByRoleId(sysRoleEntity.getRoleId());
            outDTO.setMenuList(list);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("角色维护queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("角色维护queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.sysRoleDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                SysRoleEntity sysRole = BeanCopyUtils.copy(queryByPageDTO,SysRoleEntity.class);
                //开始分页查询
                Page<SysRoleInfo> page = new PageImpl<>(this.sysRoleDao.selectSysRoleInfoListByPage(sysRole, pageRequest), pageRequest, total);
                //获取分页数据
                List<SysRoleInfo> list = page.toList();
                //出参赋值
                outDTO.setSysRoleInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("角色维护queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            SysRoleEntity sysRoleEntity = BeanCopyUtils.copy(dto,SysRoleEntity.class);
            sysRoleEntity.setCreateBy("zhangyunning");
            sysRoleEntity.setCreateTime(new Date());
            int i = sysRoleDao.insert(sysRoleEntity);
            //插入角色菜单权限表
            sysRoleMenuService.insertBatch(dto.getRoleId(),dto.getMenuList());
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
            SysRoleEntity sysRoleEntity = BeanCopyUtils.copy(dto,SysRoleEntity.class);
            sysRoleEntity.setUpdateBy("zhangyunning");
            sysRoleEntity.setUpdateTime(new Date());
            int i = sysRoleDao.updateById(sysRoleEntity);
            //先删除原角色菜单对应集合
            int j = sysRoleMenuService.deleteByRoleId(dto.getRoleId());
            //插入角色菜单权限表
            sysRoleMenuService.insertBatch(dto.getRoleId(),dto.getMenuList());
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
            int i = sysRoleDao.deleteById(dto.getId());
            //删除角色对应菜单关系表
            SysRoleEntity sysRoleEntity = this.sysRoleDao.selectById(dto.getId());
            int j = sysRoleMenuService.deleteByRoleId(sysRoleEntity.getRoleId());
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
