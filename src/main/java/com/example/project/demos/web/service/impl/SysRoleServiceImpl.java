package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.SysRoleDao;
import com.example.project.demos.web.dto.list.SysRoleInfo;
import com.example.project.demos.web.dto.sysRole.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.SysRoleEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.RoleAuthorityTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.service.SysRoleAuthorityTypeService;
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
    @Autowired
    private SysRoleAuthorityTypeService sysRoleAuthorityTypeService;
    @Resource
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysLogService sysLogService;
    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("角色维护queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SysRoleEntity entity = this.sysRoleDao.selectById(id);
            outDTO = BeanUtil.copyProperties(entity, QueryByIdOutDTO.class);
            //获取权限菜单集合
            List<String> list = sysRoleMenuService.queryMenuListByRoleId(entity.getRoleId());
            //获取角色权限类型集合
            List<String> typeList = sysRoleAuthorityTypeService.queryRoleAuthorityTypeList(entity.getRoleId());
            outDTO.setAuthorityType(typeList);
            outDTO.setMenuList(list);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
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
                //循环赋值权限类型字段
                for(SysRoleInfo info : list){
                    String typeStr = "";
                    String roleId = info.getRoleId();
                    List<String> typeList = sysRoleAuthorityTypeService.queryRoleAuthorityTypeList(roleId);
                    for(String type : typeList){
                        String name = RoleAuthorityTypeEnums.getDescByCode(type);
                        typeStr = typeStr +"  "+name;
                    }
                    info.setAuthorityTypeStr(typeStr.trim());
                }
                //出参赋值
                outDTO.setSysRoleInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
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
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            SysRoleEntity entity = BeanCopyUtils.copy(dto,SysRoleEntity.class);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            int i = sysRoleDao.insert(entity);
            //插入角色菜单权限表
            sysRoleMenuService.insertBatch(dto.getRoleId(),dto.getMenuList());
            //插入角色权限类型表
            sysRoleAuthorityTypeService.insertBatch(dto.getRoleId(),dto.getAuthorityType());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //循环获取权限说明
        String roleTypeInfo="";
        for(String type : dto.getAuthorityType()){
            roleTypeInfo = roleTypeInfo+" " + RoleAuthorityTypeEnums.getDescByCode(type);
        }
        //记录操作日志
        String info = "角色编码:"+ dto.getRoleId() +",角色名称:"+dto.getRoleName()+",权限:"+roleTypeInfo;
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_ROLE.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
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
            SysRoleEntity entity = BeanCopyUtils.copy(dto,SysRoleEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = sysRoleDao.updateById(entity);
            //先删除原角色菜单对应集合
            int j = sysRoleMenuService.deleteByRoleId(dto.getRoleId());
            //插入角色菜单权限表
            sysRoleMenuService.insertBatch(dto.getRoleId(),dto.getMenuList());
            //先删除原来的角色权限类型表
            sysRoleAuthorityTypeService.deleteByRoleId(dto.getRoleId());
            //插入角色权限类型表
            sysRoleAuthorityTypeService.insertBatch(dto.getRoleId(),dto.getAuthorityType());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //循环获取权限说明
        String roleTypeInfo="";
        for(String type : dto.getAuthorityType()){
            roleTypeInfo = roleTypeInfo+" " + RoleAuthorityTypeEnums.getDescByCode(type);
        }
        //记录操作日志
        String info = "角色编码:"+ dto.getRoleId() +",角色名称:"+dto.getRoleName()+",权限:"+roleTypeInfo;
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_ROLE.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
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
        SysRoleEntity entity = this.sysRoleDao.selectById(dto.getId());
        String roleTypeInfo = "";
        try{

            String roleId = entity.getRoleId();
            List<String> typeList = sysRoleAuthorityTypeService.queryRoleAuthorityTypeList(roleId);
            for(String type : typeList){
                String name = RoleAuthorityTypeEnums.getDescByCode(type);
                roleTypeInfo = roleTypeInfo +"  "+name;
            }
            int i = sysRoleDao.deleteById(dto.getId());
            //删除角色对应菜单关系表
            int j = sysRoleMenuService.deleteByRoleId(entity.getRoleId());
            //删除角色权限类型表
            sysRoleAuthorityTypeService.deleteByRoleId(entity.getRoleId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "角色编码:"+ entity.getRoleId() +",角色名称:"+entity.getRoleName()+",权限:"+roleTypeInfo;
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_ROLE.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }
}
