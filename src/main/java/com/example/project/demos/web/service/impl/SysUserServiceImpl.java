package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.project.demos.web.auth.OauthSupport;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.list.*;
import com.example.project.demos.web.dto.sysMenu.QueryMenuTreeDTO;
import com.example.project.demos.web.dto.sysMenu.QueryMenuTreeOutDTO;
import com.example.project.demos.web.dto.sysUser.*;
import com.example.project.demos.web.entity.SysDeptEntity;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import com.example.project.demos.web.entity.SysUserEntity;
import com.example.project.demos.web.enums.*;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.DateUtils;
import com.example.project.demos.web.utils.IpUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
    @Resource
    private SysUserDao sysUserDao;

    @Autowired
    private SysRoleAuthorityTypeService sysRoleAuthorityTypeService;

    @Autowired
    private OauthSupport oauthSupport;

    @Autowired
    private Executor commonTaskExecutor;

    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;

    @Resource
    private SysDeptDao sysDeptDao;
    @Autowired
    private SysLogService sysLogService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("用户queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SysUserInfo sysUserInfo = this.sysUserDao.selectSysUserInfoById(id);
            //赋值单位名称
            List<SysUserInfo> list = new ArrayList<>();
            list.add(sysUserInfo);
            list = setDeptName(list);
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("用户queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("用户queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.sysUserDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //转换实体入参
                SysUserEntity SysUser = BeanCopyUtils.copy(dto,SysUserEntity.class);
                //开始分页查询
                Page<SysUserInfo> page = new PageImpl<>(this.sysUserDao.selectSysUserInfoListByPage(SysUser, pageRequest), pageRequest, total);
                //获取分页数据
                List<SysUserInfo> list = page.toList();
                //赋值所属单位名称
                list = setDeptName(list);
                //出参赋值
                outDTO.setSysUserInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("用户queryByPage结束");
        return outDTO;
    }


    @Override
    public QueryPopByPageOutDTO queryPopByPage(QueryPopByPageDTO queryPopByPageDTO) {
        log.info("用户queryByPage开始");
        QueryPopByPageOutDTO outDTO = new QueryPopByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            QueryByPageDTO dto = BeanUtil.copyProperties(queryPopByPageDTO,QueryByPageDTO.class);
            long total = this.sysUserDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //转换实体入参
                SysUserEntity SysUser = BeanCopyUtils.copy(dto,SysUserEntity.class);
                //开始分页查询
                Page<SysUserInfo> page = new PageImpl<>(this.sysUserDao.selectSysUserInfoPopListByPage(SysUser, pageRequest), pageRequest, total);
                //获取分页数据
                List<SysUserInfo> list = page.toList();
                //赋值所属单位名称
                list = setDeptName(list);
                //出参赋值
                outDTO.setSysUserInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
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
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            SysUserEntity entity = BeanCopyUtils.copy(dto,SysUserEntity.class);
            //密码加密处理
            String pwd = DigestUtils.md5DigestAsHex(Constants.INITE_PWD.getBytes());
            entity.setPassword(pwd);
            entity.setLastPasswordDate(date);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            int i = sysUserDao.insert(entity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "工号:"+ dto.getUserId() +",登录名:"+dto.getUserLogin()+",姓名:"+dto.getUserName()+",所属类型:"+dto.getUserTypeName()+",角色:"+dto.getRoleName()+",部门:"+dto.getDeptName();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_USER.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            SysUserEntity entity = BeanCopyUtils.copy(dto,SysUserEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = sysUserDao.updateById(entity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "工号:"+ dto.getUserId() +",登录名:"+dto.getUserLogin()+",姓名:"+dto.getUserName()+",所属类型:"+dto.getUserTypeName()+",角色:"+dto.getRoleName()+",部门:"+dto.getDeptName();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_USER.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            int i = sysUserDao.deleteById(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "工号:"+ dto.getUserId() +",登录名:"+dto.getUserLogin()+",姓名:"+dto.getUserName()+",所属类型:"+dto.getUserTypeName()+",角色:"+dto.getRoleName()+",部门:"+dto.getDeptName();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_USER.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public UserLoginOutDTO userLogin(UserLoginDTO dto) {
        UserLoginOutDTO outDTO = new UserLoginOutDTO();
        Date date = new Date();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        //是否初始密码     密码是否过期
        String isInitePwd = SysEnums.SYS_YES_FLAG.getCode();
        String isOverDuePwd = SysEnums.SYS_YES_FLAG.getCode();
        //token
        String token = IdUtil.simpleUUID();
        String ipAddress ="";
        try{
            log.info("用户登录-userLogin开始");
            String userLogin = dto.getUserLogin();
            String password = dto.getPassword();
            log.info("userLogin:"+userLogin);
            log.info("password:"+password);
            log.info("对密码进行md5处理");
            String encodePWD = DigestUtils.md5DigestAsHex(password.getBytes());
            log.info("encodePWD:"+encodePWD);
            log.info("开始查找用户信息");
            SysUserInfo info = sysUserDao.selectUserForLogin(userLogin,encodePWD);
            if(ObjectUtil.isNull(info)){
                log.info("未查到用户信息");
                errorCode= ErrorCodeEnums.LOGIN_ERROR.getCode();
                errortMsg= ErrorCodeEnums.LOGIN_ERROR.getDesc();
            }else{
                //赋值部门名称
                List<SysUserInfo> list = new ArrayList<>();
                list.add(info);
                list = setDeptName(list);
                outDTO = BeanUtil.copyProperties(list.get(0),UserLoginOutDTO.class);
                log.info("判断该用户密码是不是初始密码");
                String pwd = info.getPassword();
                log.info("用户密码:"+pwd);
                if(pwd.equals(Constants.INITE_PWD_ENCODE)){
                    log.info("是初始化密码，需要重置");
                    //errorCode= ErrorCodeEnums.PWD_INITE.getCode();
                    errortMsg= ErrorCodeEnums.PWD_INITE.getDesc();
                    isInitePwd = SysEnums.SYS_YES_FLAG.getCode();
                }else{
                    log.info("不是初始化密码，判断有效期");
                    isInitePwd = SysEnums.SYS_NO_FLAG.getCode();
                    Date lastPasswordDate = info.getLastPasswordDate();
                    int days = DateUtils.differentDaysByMillisecond(lastPasswordDate,date);
                    log.info("密码过期天数:"+days);
                    if(days > Constants.OVERDUE_PWD_DAYS){
                        log.info("密码已过期");
                        isOverDuePwd = SysEnums.SYS_YES_FLAG.getCode();
                        //errorCode= ErrorCodeEnums.PWD_OVERDUE.getCode();
                        errortMsg= ErrorCodeEnums.PWD_OVERDUE.getDesc();
                    }else{
                        log.info("密码未过期，查询用户其他信息");
                        isOverDuePwd = SysEnums.SYS_NO_FLAG.getCode();
                        log.info("根据角色编码获取该角色权限类型");
                        List<String> authorityType = sysRoleAuthorityTypeService.queryRoleAuthorityTypeList(info.getRoleId());
                        outDTO.setAuthorityType(authorityType);
                        //修改当前登录IP和登录时间
                        ipAddress = IpUtils.getLocalIp4Address().get().toString().replaceAll("/","");
                        outDTO.setLoginIp(ipAddress);
                        SysUserEntity entity = new SysUserEntity();
                        entity.setId(info.getId());
                        entity.setLoginIp(ipAddress);
                        entity.setLoginDate(date);
                        sysUserDao.updateById(entity);
                    }
                }
                //20240509 add by gc 生成token
                UserLoginOutDTO finalOutDTO = outDTO;
                CompletableFuture.runAsync(() -> oauthSupport.persistenceToken(finalOutDTO,token), commonTaskExecutor);
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        outDTO.setIsInitePwd(isInitePwd);
        outDTO.setIsOverDuePwd(isOverDuePwd);
        outDTO.setToken(token);
        //记录操作日志
        String info = "登录名:"+dto.getUserLogin();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_USER.getCode(), OperationTypeEnums.OPERATION_TYPE_LOGIN.getCode(),dto.getUserLogin(),date,info,errorCode,errortMsg,ipAddress,token,Constants.SYSTEM_CODE);
        return outDTO;
    }

    @Override
    public ResetPwdOutDTO restPwd(ResetPwdDTO dto) {
        log.info("重置密码开始");
        ResetPwdOutDTO outDTO = new ResetPwdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            //将用户密码重置为123456的MD5
            SysUserEntity entity = sysUserDao.selectById(dto.getId());
            //密码加密处理
            String pwd = DigestUtils.md5DigestAsHex(Constants.INITE_PWD.getBytes());
            entity.setPassword(pwd);
            entity.setLastPasswordDate(date);
            sysUserDao.updateById(entity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        //记录操作日志
        String info = "工号:"+dto.getUserId()+",登录名:"+dto.getUserLogin()+"姓名:"+dto.getUserName();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_USER.getCode(), OperationTypeEnums.OPERATION_TYPE_RESET_PWD.getCode(),dto.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("重置密码结束");
        return outDTO;
    }

    @Override
    public UpdatePwdOutDTO updatePwd(UpdatePwdDTO dto) {
        log.info("修改密码开始");
        UpdatePwdOutDTO outDTO = new UpdatePwdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        SysUserEntity sysUser = sysUserDao.selectById(dto.getId());
        String ipAddress = "";
        try{
            String oldPwd = dto.getOldPwd();
            log.info("输入的原密码:"+oldPwd);
            //转换MD5
            String encodePWD = DigestUtils.md5DigestAsHex(oldPwd.getBytes());
            log.info("encodePWD:"+encodePWD);
            //判断用户密码是否正确
            int i = sysUserDao.selectUserByPwd(dto.getId(),encodePWD);
            if(i == 0 ){
                log.info("输入原密码错误");
                errorCode= ErrorCodeEnums.PWD_INITE_ERROR.getCode();
                errortMsg= ErrorCodeEnums.PWD_INITE_ERROR.getDesc();
            }else{
                //新密码加密
                String newPwd = dto.getNewPwd();
                log.info("输入原密码正确，开始修改密码:"+newPwd);
                encodePWD = DigestUtils.md5DigestAsHex(newPwd.getBytes());
                SysUserEntity entity = new SysUserEntity();
                entity.setId(dto.getId());
                entity.setPassword(encodePWD);
                entity.setLastPasswordDate(date);
                sysUserDao.updateById(entity);
            }
            ipAddress = IpUtils.getLocalIp4Address().get().toString().replaceAll("/","");
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        //记录操作日志
        String info = "工号:"+sysUser.getUpdateBy()+",登录名:"+sysUser.getUserLogin()+"姓名:"+sysUser.getUserName();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_USER.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE_PWD.getCode(),sysUser.getUserLogin(),date,info,errorCode,errortMsg,ipAddress,null,Constants.SYSTEM_CODE);
        log.info("修改密码结束");
        return outDTO;
    }

    @Override
    public List<SysUserEntity> queryUserListByRoleType(String userType, String roleType,String deptId) {
        return sysUserDao.queryUserListByRoleType(userType,roleType,deptId);
    }

    @Override
    public List<SysUserInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("用户queryListForExport开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        List<SysUserInfo> list = new ArrayList<>();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        try {
            //转换实体入参
            SysUserEntity sysUser = BeanCopyUtils.copy(dto,SysUserEntity.class);
            list = sysUserDao.queryListForExport(sysUser);
            //赋值所属单位名称
            list = setDeptName(list);
            //赋值角色是否有单价权限
            list = setIsPriceDesc(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_USER.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),null,Constants.SYSTEM_CODE);
        log.info("用户queryListForExport结束");
        return list;
    }


    private List<SysUserInfo> setDeptName(List<SysUserInfo> list){
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> storehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            List<SysDeptInfo> deptInfoList = sysDeptDao.selectSysDeptInfoList(new SysDeptEntity());
            for(SysUserInfo info : list){
                String type = info.getUserType();
                if(type.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                    //属于总公司
                    for(SysDeptInfo deptInfo : deptInfoList){
                        if(info.getDeptId().equals(deptInfo.getDeptId())){
                            info.setDeptName(deptInfo.getDeptName());
                        }
                    }
                }else if(type.equals(UserTypeEnums.USER_TYPE_FACTORY.getCode())){
                    //属于厂区
                    for(SysFactoryInfo sysFactoryInfo : factoryInfoList){
                        if(info.getDeptId().equals(sysFactoryInfo.getCode())){
                            info.setDeptName(sysFactoryInfo.getName());
                        }
                    }
                }else{
                    //属于仓库
                    for(SysStorehouseInfo sysStorehouseInfo : storehouseInfoList ){
                        if(info.getDeptId().equals(sysStorehouseInfo.getCode())){
                            info.setDeptName(sysStorehouseInfo.getName());
                        }
                    }
                }
            }
        }else{
            log.info("list is null");
        }
        return list;
    }

    private List<SysUserInfo> setIsPriceDesc(List<SysUserInfo> list){
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            for(SysUserInfo info : list){
                String roleId = info.getRoleId();
                if(ObjectUtil.isNotNull(roleId)){
                    //获取角色权集合
                    List<String> authorityType = sysRoleAuthorityTypeService.queryRoleAuthorityTypeList(info.getRoleId());
                    if(CollectionUtil.isNotEmpty(authorityType) && authorityType.size() > 0){
                        if(authorityType.contains(RoleAuthorityTypeEnums.ROLE_AUTHORITY_TYPE_PRICE.getCode())){
                            info.setIsPriceEdit(SysEnums.SYS_YES_FLAG.getDesc());
                        }else{
                            info.setIsPriceEdit(SysEnums.SYS_NO_FLAG.getDesc());
                        }
                    }
                }else{
                    info.setIsPriceEdit(SysEnums.SYS_NO_FLAG.getDesc());
                }
            }
        }else{
            log.info("list is null");
        }
        return list;
    }

}
