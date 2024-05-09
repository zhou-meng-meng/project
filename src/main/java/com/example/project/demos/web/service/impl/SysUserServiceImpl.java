package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.project.demos.web.auth.OauthSupport;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.SysUserDao;
import com.example.project.demos.web.dto.list.SysUserInfo;
import com.example.project.demos.web.dto.list.SysUserRoleInfo;
import com.example.project.demos.web.dto.sysUser.*;
import com.example.project.demos.web.entity.SysUserEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.SysEnums;
import com.example.project.demos.web.service.SysRoleMenuService;
import com.example.project.demos.web.service.SysUserRoleService;
import com.example.project.demos.web.service.SysUserService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.DateUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.net.InetAddress;
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
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private OauthSupport oauthSupport;

    @Autowired
    private Executor commonTaskExecutor;
    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("用户queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SysUserInfo sysUserInfo = this.sysUserDao.selectSysUserInfoById(id);
            outDTO = BeanUtil.copyProperties(sysUserInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
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
            long total = this.sysUserDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                SysUserEntity SysUser = BeanCopyUtils.copy(queryByPageDTO,SysUserEntity.class);
                //开始分页查询
                Page<SysUserInfo> page = new PageImpl<>(this.sysUserDao.selectSysUserInfoListByPage(SysUser, pageRequest), pageRequest, total);
                //获取分页数据
                List<SysUserInfo> list = page.toList();
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
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        try{
            SysUserEntity sysUserEntity = BeanCopyUtils.copy(dto,SysUserEntity.class);
            //密码加密处理
            String pwd = DigestUtils.md5DigestAsHex(dto.getPassword().getBytes());
            sysUserEntity.setPassword(pwd);
            sysUserEntity.setLastPasswordDate(date);
            sysUserEntity.setCreateBy("zhangyunning");
            sysUserEntity.setCreateTime(date);
            int i = sysUserDao.insert(sysUserEntity);
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
            SysUserEntity sysUserEntity = BeanCopyUtils.copy(dto,SysUserEntity.class);
            sysUserEntity.setUpdateBy("zhangyunning");
            sysUserEntity.setUpdateTime(new Date());
            int i = sysUserDao.updateById(sysUserEntity);
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
            int i = sysUserDao.deleteById(dto.getId());
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
    public UserLoginOutDTO userLogin(UserLoginDTO dto) {
        UserLoginOutDTO outDTO = new UserLoginOutDTO();
        Date date = new Date();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        //是否初始密码     密码是否过期
        String isInitePwd = SysEnums.SYS_YES_FLAG.getCode();
        String isOverDuePwd = SysEnums.SYS_YES_FLAG.getCode();
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
                outDTO = BeanUtil.copyProperties(info,UserLoginOutDTO.class);
                log.info("判断该用户密码是不是初始密码");
                String pwd = info.getPassword();
                log.info("用户密码:"+pwd);
                if(pwd.equals(Constants.INITE_PWD_ENCODE)){
                    log.info("是初始化密码，需要重置");
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
                    }else{
                        log.info("密码未过期，查询用户其他信息");
                        isOverDuePwd = SysEnums.SYS_NO_FLAG.getCode();
                        //查询其角色信息
                        SysUserRoleInfo userRoleInfo = sysUserRoleService.selectRoleInfoByUserLogin(userLogin);
                        if(ObjectUtil.isNotNull(userRoleInfo)){
                            //根据角色查询其权限菜单
                            String roleId = userRoleInfo.getRoleId();
                            log.info("roleId:"+roleId);
                            List<String> menuList = sysRoleMenuService.queryMenuListByRoleId(roleId);
                            outDTO.setMenuList(menuList);
                        }else{
                            log.info("userRoleInfo is null");
                        }
                        //修改当前登录IP和登录时间
                        InetAddress inetAddress = InetAddress.getLocalHost();
                        String ipAddress = inetAddress.getHostAddress();
                        SysUserEntity entity = new SysUserEntity();
                        entity.setId(info.getId());
                        entity.setLoginIp(ipAddress);
                        entity.setLoginDate(date);
                        sysUserDao.updateById(entity);
                        //20240509 add by gc 生成token
                        CompletableFuture.runAsync(() -> oauthSupport.persistenceToken(info.getId()), commonTaskExecutor);
                    }
                }
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }

        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        outDTO.setIsInitePwd(isInitePwd);
        outDTO.setIsOverDuePwd(isOverDuePwd);
        return outDTO;
    }

    @Override
    public ResetPwdOutDTO restPwd(ResetPwdDTO dto) {
        log.info("重置密码开始");
        ResetPwdOutDTO outDTO = new ResetPwdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
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
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("重置密码结束");
        return outDTO;
    }
}
