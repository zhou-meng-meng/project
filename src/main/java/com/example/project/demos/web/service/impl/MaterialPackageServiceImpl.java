package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.dao.MaterialPackageDao;
import com.example.project.demos.web.dto.list.MaterialPackageDetailInfo;
import com.example.project.demos.web.dto.list.MaterialPackageInfo;
import com.example.project.demos.web.dto.materialPackage.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.MaterialPackageEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.enums.UserTypeEnums;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.MaterialPackageDetailService;
import com.example.project.demos.web.service.MaterialPackageService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("materialPackageService")
public class MaterialPackageServiceImpl  implements MaterialPackageService {

    @Resource
    private MaterialPackageDao materialPackageDao;

    @Autowired
    private MaterialPackageDetailService materialPackageDetailService;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("装袋表queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        /*try{
            MaterialPackageInfo materialPackageInfo = materialPackageDao.selectMaterialPackageInfoById(id);
            outDTO = BeanUtil.copyProperties(materialPackageInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }*/
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("装袋表queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
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
                queryByPageDTO.setFactoryCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.materialPackageDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<MaterialPackageInfo> page = new PageImpl<>(this.materialPackageDao.selectMaterialPackageInfoListByPage(queryByPageDTO, pageRequest), pageRequest, total);
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
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("装袋表queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            MaterialPackageEntity materialPackageEntity = BeanCopyUtils.copy(dto,MaterialPackageEntity.class);
            materialPackageEntity.setCreateBy("zhangyunning");
            materialPackageEntity.setCreateTime(new Date());
            int i = materialPackageDao.insert(materialPackageEntity);
            log.info("添加装袋明细表");
            materialPackageDetailService.insertBatch(materialPackageEntity.getId(),dto.getDetailInfoList());
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
            MaterialPackageEntity materialPackageEntity = BeanCopyUtils.copy(dto,MaterialPackageEntity.class);
            materialPackageEntity.setCreateBy("zhangyunning");
            materialPackageEntity.setUpdateTime(new Date());
            int i = materialPackageDao.updateById(materialPackageEntity);
            log.info("先删除原装袋表明细");
            materialPackageDetailService.deleteByPackageId(dto.getId());
            log.info("重新插入装袋表明细");
            materialPackageDetailService.insertBatch(materialPackageEntity.getId(),dto.getDetailInfoList());
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
            int i = materialPackageDao.deleteById(dto.getId());
            log.info("删除装袋明细");
            materialPackageDetailService.deleteByPackageId(dto.getId());
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
