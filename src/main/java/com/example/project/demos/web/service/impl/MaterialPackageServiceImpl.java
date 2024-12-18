package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.MaterialPackageDao;
import com.example.project.demos.web.dao.SysFactoryDao;
import com.example.project.demos.web.dao.SysStorehouseDao;
import com.example.project.demos.web.dto.list.*;
import com.example.project.demos.web.dto.materialPackage.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.MaterialPackageEntity;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.UserTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.MaterialPackageDetailService;
import com.example.project.demos.web.service.MaterialPackageService;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.DateUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("materialPackageService")
public class MaterialPackageServiceImpl  implements MaterialPackageService {

    @Resource
    private MaterialPackageDao materialPackageDao;
    @Autowired
    private MaterialPackageDetailService materialPackageDetailService;
    @Autowired
    private SysLogService sysLogService;
    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("装袋表queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            MaterialPackageInfo info = materialPackageDao.selectMaterialPackageInfoById(id);
            List<MaterialPackageInfo> list = new ArrayList<>();
            list.add(info);
            list = setRebuildInboundObject(list);
            outDTO = BeanUtil.copyProperties(list.get(0),QueryByIdOutDTO.class);
            //通过主表ID获取装袋表明细列表
            List<MaterialPackageDetailInfo> detailInfoList = materialPackageDetailService.queryByPackageId(info.getId());
            outDTO.setDetailInfoList(detailInfoList);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("装袋表queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("装袋表queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //权限判断  总公司人员可查看所有厂区   厂区人员只能查看所属厂区
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可查看所有");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区");
                dto.setFactoryCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.materialPackageDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<MaterialPackageInfo> page = new PageImpl<>(this.materialPackageDao.selectMaterialPackageInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<MaterialPackageInfo> list = page.toList();
                //获取每个装袋表的物料明细集合
                for(MaterialPackageInfo info : list){
                    List<MaterialPackageDetailInfo> detailInfoList = materialPackageDetailService.queryByPackageId(info.getId());
                    info.setDetailInfoList(detailInfoList);
                }
                //出参赋值
                outDTO.setMaterialPackageInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("装袋表queryByPage结束");
        return outDTO;
    }

    @Override
    public List<MaterialPackageExportDTO> queryByParam(QueryByPageDTO dto) {
        List<MaterialPackageExportDTO> result = new ArrayList<>(16);
        log.info("装袋表queryByParam开始");
        try {
            //权限判断  总公司人员可查看所有厂区   厂区人员只能查看所属厂区
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可查看所有");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区");
                dto.setFactoryCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.materialPackageDao.count(dto);
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                //获取分页数据
                List<MaterialPackageInfo> list = this.materialPackageDao.selectMaterialPackageInfoList(dto);
                //获取每个装袋表的物料明细集合
                for(MaterialPackageInfo info : list){
                    List<MaterialPackageDetailInfo> detailInfoList = materialPackageDetailService.queryByPackageId(info.getId());
                    if(CollectionUtils.isEmpty(detailInfoList)){
                        MaterialPackageExportDTO pojo = new MaterialPackageExportDTO();
                        BeanUtils.copyProperties(info,pojo);
                        result.add(pojo);
                    }else{
                        for (MaterialPackageDetailInfo detail : detailInfoList) {
                            MaterialPackageExportDTO pojo = new MaterialPackageExportDTO();
                            BeanUtils.copyProperties(info,pojo);
                            pojo.setMaterialName(detail.getMaterialName());
                            pojo.setPotWeight(detail.getPotWeight());
                            result.add(pojo);
                        }
                    }
                }
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.error("异常:"+e);
        }
        log.info("装袋表queryByParam结束");
        return result;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            MaterialPackageEntity entity = BeanCopyUtils.copy(dto,MaterialPackageEntity.class);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            int i = materialPackageDao.insert(entity);
            log.info("添加装袋明细表");
            materialPackageDetailService.insertBatch(entity.getId(),dto.getDetailInfoList());
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getPackageDate())+",厂区:"+dto.getFactoryName()+"日期:"+dto.getPackageDate()+",班组:"+dto.getDutyName()+",机器号:"+dto.getMachineName()+",锅数:"+dto.getPotNum()+",合计重量:"+dto.getTollWeight()+",应出袋数:"+dto.getShouldNum()+",实际袋数:"+dto.getActualNum()+",差额:"+dto.getBalanceNum();
        sysLogService.insertSysLog(FunctionTypeEnums.MATERIAL_PACKAGE.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
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
            MaterialPackageEntity entity = BeanCopyUtils.copy(dto,MaterialPackageEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = materialPackageDao.updateById(entity);
            log.info("先删除原装袋表明细");
            materialPackageDetailService.deleteByPackageId(dto.getId());
            log.info("重新插入装袋表明细");
            materialPackageDetailService.insertBatch(entity.getId(),dto.getDetailInfoList());
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getPackageDate())+",厂区:"+dto.getFactoryName()+",班组:"+dto.getDutyName()+",机器号:"+dto.getMachineName()+",锅数:"+dto.getPotNum()+",合计重量:"+dto.getTollWeight()+",应出袋数:"+dto.getShouldNum()+",实际袋数:"+dto.getActualNum()+",差额:"+dto.getBalanceNum();
        sysLogService.insertSysLog(FunctionTypeEnums.MATERIAL_PACKAGE.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
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
            int i = materialPackageDao.deleteById(dto.getId());
            log.info("删除装袋明细");
            materialPackageDetailService.deleteByPackageId(dto.getId());
        }catch (Exception e){
            log.error("异常:"+e);
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getPackageDate())+",厂区:"+dto.getFactoryName()+",班组:"+dto.getDutyName()+",机器号:"+dto.getMachineName()+",锅数:"+dto.getPotNum()+",合计重量:"+dto.getTollWeight()+",应出袋数:"+dto.getShouldNum()+",实际袋数:"+dto.getActualNum()+",差额:"+dto.getBalanceNum();
        sysLogService.insertSysLog(FunctionTypeEnums.MATERIAL_PACKAGE.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    /**
     * 赋值重造出库  入库方名称
     * @param list
     * @return
     */
    private List<MaterialPackageInfo> setRebuildInboundObject(List<MaterialPackageInfo> list){
        //获取厂区和仓库集合
        List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
        List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
        for(MaterialPackageInfo info : list){
            //入库方
            String inCode = info.getFactoryCode();
            if(Constants.FACTORY_CODE_PREFIX.equals(inCode.substring(0,1))){
                //工厂
                for(SysFactoryInfo fInfo : factoryInfoList){
                    if(inCode.equals(fInfo.getCode())){
                        info.setFactoryName(fInfo.getName());
                    }
                }
            }else{
                //仓库
                for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                    if(inCode.equals(sInfo.getCode())){
                        info.setFactoryName(sInfo.getName());
                    }
                }
            }
        }
        return list;
    }

}
