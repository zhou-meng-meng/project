package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.SysMenuDao;
import com.example.project.demos.web.dto.list.SysMenuInfo;
import com.example.project.demos.web.dto.list.SysMenuTreeInfo;
import com.example.project.demos.web.dto.sysMenu.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.SysMenuEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.service.SysMenuService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service("sysMenuService")
public class SysMenuServiceImpl  implements SysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;
    @Autowired
    private SysLogService sysLogService;
    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("菜单管理queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SysMenuEntity entity = this.sysMenuDao.selectById(id);
            outDTO = BeanUtil.copyProperties(entity, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("菜单管理queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("菜单管理queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.sysMenuDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //转换实体入参
                SysMenuEntity sysMenu = BeanCopyUtils.copy(dto,SysMenuEntity.class);
                //开始分页查询
                Page<SysMenuInfo> page = new PageImpl<>(this.sysMenuDao.selectSysMenuInfoListByPage(sysMenu, pageRequest), pageRequest, total);
                //获取分页数据
                List<SysMenuInfo> list = page.toList();
                //出参赋值
                outDTO.setSysMenuInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("菜单管理queryByPage结束");
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
            SysMenuEntity entity = BeanCopyUtils.copy(dto,SysMenuEntity.class);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            int i = sysMenuDao.insert(entity);
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "菜单编码:"+ dto.getMenuId() +",菜单名称:"+dto.getMenuName();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_MENU.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
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
            SysMenuEntity entity = BeanCopyUtils.copy(dto,SysMenuEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = sysMenuDao.updateById(entity);
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "菜单编码:"+ dto.getMenuId() +",菜单名称:"+dto.getMenuName();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_MENU.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
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
            int i = sysMenuDao.deleteById(dto.getId());
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "菜单编码:"+ dto.getMenuId() +",菜单名称:"+dto.getMenuName();
        sysLogService.insertSysLog(FunctionTypeEnums.SYS_MENU.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public QueryMenuTreeOutDTO queryMenuTree(QueryMenuTreeDTO dto) {
        //先查寻出菜单列表
        String roleId = dto.getRoleId();
        log.info("roleId:"+roleId);
        List<SysMenuInfo> menuInfoList = new ArrayList<>();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryMenuTreeOutDTO outDTO = new QueryMenuTreeOutDTO();
        List<SysMenuTreeInfo> list = new ArrayList<>();
        try{
            if(ObjectUtil.isNull(roleId)){
                //获取全量菜单
                menuInfoList = sysMenuDao.selectSysMenuInfoList();
            }else{
                //获取角色对应菜单集合
                menuInfoList = sysMenuDao.selectSysMenuInfoListByRoleId(roleId);
            }
            //开始处理
            List<SysMenuInfo> menuTrees = buildMenuTree(menuInfoList);
            list = menuTrees.stream().map(SysMenuTreeInfo::new).collect(Collectors.toList());
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        outDTO.setList(list);
        return outDTO;
    }

    public List<SysMenuInfo> buildMenuTree(List<SysMenuInfo> menus) {
        List<SysMenuInfo> returnList = new ArrayList<SysMenuInfo>();
        List<String> tempList = new ArrayList<>();
        for (SysMenuInfo menu : menus) {
            tempList.add(menu.getMenuId());
        }
        for (Iterator<SysMenuInfo> iterator = menus.iterator(); iterator.hasNext();) {
            SysMenuInfo menu = (SysMenuInfo) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenuInfo> list, SysMenuInfo t) {
        // 得到子节点列表
        List<SysMenuInfo> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenuInfo tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenuInfo> getChildList(List<SysMenuInfo> list, SysMenuInfo t) {
        List<SysMenuInfo> tlist = new ArrayList<SysMenuInfo>();
        Iterator<SysMenuInfo> it = list.iterator();
        while (it.hasNext()) {
            SysMenuInfo n = (SysMenuInfo) it.next();
            if (n.getParentId() .equals( t.getMenuId())) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    private boolean hasChild(List<SysMenuInfo> list, SysMenuInfo t) {
        return getChildList(list, t).size() > 0;
    }
}
