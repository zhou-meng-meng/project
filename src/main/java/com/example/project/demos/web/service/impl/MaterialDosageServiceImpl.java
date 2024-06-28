package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.MaterialDosageDao;
import com.example.project.demos.web.dto.list.MaterialDosageInfo;
import com.example.project.demos.web.dto.list.MaterialDosageTollAmountInfo;
import com.example.project.demos.web.dto.materialDosage.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.MaterialDosageEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.FunctionTypeEnums;
import com.example.project.demos.web.enums.OperationTypeEnums;
import com.example.project.demos.web.enums.UserTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.MaterialDosageService;
import com.example.project.demos.web.service.SysLogService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.DateUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("materialDosageService")
public class MaterialDosageServiceImpl  implements MaterialDosageService {

    @Resource
    private MaterialDosageDao materialDosageDao;
    @Autowired
    private SysLogService sysLogService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("物料用量queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            MaterialDosageInfo materialDosageInfo = materialDosageDao.selectMaterialDosageInfoById(id);
            outDTO = BeanUtil.copyProperties(materialDosageInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("物料用量queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("物料用量queryByPage开始");
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
            long total = this.materialDosageDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<MaterialDosageInfo> page = new PageImpl<>(this.materialDosageDao.selectMaterialDosageInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<MaterialDosageInfo> list = page.toList();
                //出参赋值
                outDTO.setMaterialDosageInfoList(list);
                //获取三个合计数量
                MaterialDosageTollAmountInfo tollInfo = materialDosageDao.queryTollAmount(dto);
                outDTO.setGrindingWeightToll(tollInfo.getGrindingWeightToll());
                outDTO.setMachineWeightToll(tollInfo.getMachineWeightToll());
                outDTO.setDifferentWeightToll(tollInfo.getDifferentWeightToll());
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("物料用量queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        log.info("物料用量表新增开始");
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            MaterialDosageEntity entity = BeanCopyUtils.copy(dto,MaterialDosageEntity.class);
            entity.setCreateBy(user.getUserLogin());
            entity.setCreateTime(date);
            int i = materialDosageDao.insert(entity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getDutyDate())+",厂区:"+dto.getFactoryName()+",班组:"+dto.getDutyName()+",机器号:"+dto.getMachineName()+",磨粉棒重量:"+dto.getGrindingWeight()+",机器磅重量:"+dto.getMachineWeight()+",差额:"+dto.getDifferentWeight();
        sysLogService.insertSysLog(FunctionTypeEnums.MATERIAL_DOSAGE.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("物料用量表新增结束");
        return outDTO;
    }

    @Override
    public EditOutDTO update(EditDTO dto) {
        log.info("物料用量表修改开始");
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            MaterialDosageEntity entity = BeanCopyUtils.copy(dto,MaterialDosageEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = materialDosageDao.updateById(entity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        String info = "日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getDutyDate())+",厂区:"+dto.getFactoryName()+",班组:"+dto.getDutyName()+",机器号:"+dto.getMachineName()+",磨粉棒重量:"+dto.getGrindingWeight()+",机器磅重量:"+dto.getMachineWeight()+",差额:"+dto.getDifferentWeight();
        sysLogService.insertSysLog(FunctionTypeEnums.MATERIAL_DOSAGE.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("物料用量表修改结束");
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) {
        log.info("物料用量表删除开始");
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            int i = materialDosageDao.deleteById(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        String info = "日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getDutyDate())+",厂区:"+dto.getFactoryName()+",班组:"+dto.getDutyName()+",机器号:"+dto.getMachineName()+",磨粉棒重量:"+dto.getGrindingWeight()+",机器磅重量:"+dto.getMachineWeight()+",差额:"+dto.getDifferentWeight();
        sysLogService.insertSysLog(FunctionTypeEnums.MATERIAL_DOSAGE.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("物料用量表删除结束");
        return outDTO;
    }

    @Override
    public List<MaterialDosageInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("物料用量表queryListForExport开始");
        List<MaterialDosageInfo> list = new ArrayList<>();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        try {
            //权限判断  总公司人员可查看所有厂区   厂区人员只能查看所属厂区
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可查看所有");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区");
                dto.setFactoryCode(user.getDeptId());
            }
            list = materialDosageDao.queryListForExport(dto);
            if(CollectionUtil.isNotEmpty(list) && list.size()> 0){
                MaterialDosageTollAmountInfo tollInfo = materialDosageDao.queryTollAmount(dto);
                //赋值最后一排的合计
                MaterialDosageInfo info = new MaterialDosageInfo();
                info.setBillNo("合计");
                info.setGrindingWeight(tollInfo.getGrindingWeightToll());
                info.setMachineWeight(tollInfo.getMachineWeightToll());
                info.setDifferentWeight(tollInfo.getDifferentWeightToll());
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.MATERIAL_DOSAGE.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(), Constants.SYSTEM_CODE);
        log.info("物料用量表queryListForExport结束");
        return list;
    }

}
