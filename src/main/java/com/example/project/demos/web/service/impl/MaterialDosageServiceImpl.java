package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.MaterialDosageDao;
import com.example.project.demos.web.dto.list.MaterialDosageInfo;
import com.example.project.demos.web.dto.materialDosage.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.MaterialDosageEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.UserTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.MaterialDosageService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("materialDosageService")
public class MaterialDosageServiceImpl  implements MaterialDosageService {

    @Resource
    private MaterialDosageDao materialDosageDao;

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
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("物料用量queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        BigDecimal grindingWeightToll = new BigDecimal(0);
        BigDecimal machineWeightToll = new BigDecimal(0);
        BigDecimal differentWeightToll =new BigDecimal(0);
        try {
            //权限判断  总公司人员可查看所有厂区   厂区人员只能查看所属厂区
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            if(userType.equals(UserTypeEnums.USER_TYPE_COMPANY.getCode())){
                log.info("当前登录人属于总公司，可查看所有");
            }else{
                log.info("当前登录人不属于总公司，只能查看所属厂区");
                queryByPageDTO.setFactoryCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.materialDosageDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<MaterialDosageInfo> page = new PageImpl<>(this.materialDosageDao.selectMaterialDosageInfoListByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取分页数据
                List<MaterialDosageInfo> list = page.toList();
                //出参赋值
                outDTO.setMaterialDosageInfoList(list);
                //计算三个合计
                for(MaterialDosageInfo info : list){
                    grindingWeightToll = grindingWeightToll.add(info.getGrindingWeight());
                    machineWeightToll = machineWeightToll.add(info.getMachineWeight());
                    differentWeightToll = differentWeightToll.add(info.getDifferentWeight());
                }
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setGrindingWeightToll(grindingWeightToll);
        outDTO.setMachineWeightToll(machineWeightToll);
        outDTO.setDifferentWeightToll(differentWeightToll);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("物料用量queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            MaterialDosageEntity materialDosageEntity = BeanCopyUtils.copy(dto,MaterialDosageEntity.class);
            materialDosageEntity.setCreateBy("zhangyunning");
            materialDosageEntity.setCreateTime(new Date());
            int i = materialDosageDao.insert(materialDosageEntity);
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
            MaterialDosageEntity materialDosageEntity = BeanCopyUtils.copy(dto,MaterialDosageEntity.class);
            materialDosageEntity.setCreateBy("zhangyunning");
            materialDosageEntity.setUpdateTime(new Date());
            int i = materialDosageDao.updateById(materialDosageEntity);
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
            int i = materialDosageDao.deleteById(dto.getId());
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
