package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.MaterialPackageDetailDao;
import com.example.project.demos.web.dto.list.MaterialPackageDetailInfo;
import com.example.project.demos.web.dto.materialPackageDetail.*;
import com.example.project.demos.web.entity.MaterialPackageDetailEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.MaterialPackageDetailService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("materialPackageDetailService")
public class MaterialPackageDetailServiceImpl  implements MaterialPackageDetailService {

    @Resource
    private MaterialPackageDetailDao materialPackageDetailDao;

    /*@Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("装袋表明细queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            MaterialPackageInfo materialPackageInfo = MaterialPackageDao.selectMaterialPackageInfoById(id);
            outDTO = BeanUtil.copyProperties(materialPackageInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("装袋表明细queryById结束");
        return outDTO;
    }*/

    /*@Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("装袋表明细queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.MaterialPackageDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                MaterialPackageEntity MaterialPackage = BeanCopyUtils.copy(queryByPageDTO,MaterialPackageEntity.class);
                //开始分页查询
                Page<MaterialPackageInfo> page = new PageImpl<>(this.MaterialPackageDao.selectMaterialPackageInfoListByPage(MaterialPackage, pageRequest), pageRequest, total);
                //获取分页数据
                List<MaterialPackageInfo> list = page.toList();
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
        log.info("装袋表明细queryByPage结束");
        return outDTO;
    }*/

    @Override
    public List<MaterialPackageDetailInfo> queryByPackageId(Long packageId) {
        List<MaterialPackageDetailInfo> list = materialPackageDetailDao.selectMaterialPackageDetailInfoList(packageId);
        return list;
    }

    @Override
    public int deleteByPackageId(Long packageId) {
        int i = materialPackageDetailDao.deleteByPackageId(packageId);
        return i;
    }

    @Override
    public boolean insertBatch(Long packageId, List<MaterialPackageDetailInfo> list) {
        List<MaterialPackageDetailEntity> entityList = new ArrayList<>();
        for(MaterialPackageDetailInfo info : list){
            MaterialPackageDetailEntity entity = new MaterialPackageDetailEntity();
            entity.setPackageId(packageId);
            entity.setMaterialCode(info.getMaterialCode());
            entity.setPackageWeight(info.getPackageWeight());
            entityList.add(entity);
            //创建人待添加
            entity.setCreateBy("zhangyunning");
            entity.setCreateTime(new Date());
        }
        boolean f = materialPackageDetailDao.insertBatch(entityList);
        return f;
    }

    /*@Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            MaterialPackageEntity MaterialPackageEntity = BeanCopyUtils.copy(dto,MaterialPackageEntity.class);
            MaterialPackageEntity.setCreateBy("zhangyunning");
            MaterialPackageEntity.setCreateTime(new Date());
            int i = MaterialPackageDao.insert(MaterialPackageEntity);
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
            MaterialPackageEntity MaterialPackageEntity = BeanCopyUtils.copy(dto,MaterialPackageEntity.class);
            MaterialPackageEntity.setCreateBy("zhangyunning");
            MaterialPackageEntity.setUpdateTime(new Date());
            int i = MaterialPackageDao.updateById(MaterialPackageEntity);
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
            int i = MaterialPackageDao.deleteById(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }*/

}
