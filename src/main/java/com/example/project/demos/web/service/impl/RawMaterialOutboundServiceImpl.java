package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.RawMaterialOutboundDao;
import com.example.project.demos.web.dto.list.RawMaterialOutboundInfo;
import com.example.project.demos.web.dto.rawMaterialOutbound.*;
import com.example.project.demos.web.entity.RawMaterialOutboundEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.RawMaterialOutboundService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Slf4j
@Service("rawMaterialOutboundService")
public class RawMaterialOutboundServiceImpl  implements RawMaterialOutboundService {

    @Resource
    private RawMaterialOutboundDao rawMaterialOutboundDao;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("原材料出库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            RawMaterialOutboundInfo rawMaterialOutboundInfo = rawMaterialOutboundDao.selectRawMaterialOutboundInfoById(id);
            outDTO = BeanUtil.copyProperties(rawMaterialOutboundInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("原材料出库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("原材料出库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.rawMaterialOutboundDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                //RawMaterialOutboundEntity RawMaterialOutbound = BeanCopyUtils.copy(queryByPageDTO,RawMaterialOutboundEntity.class);
                //开始分页查询
                Page<RawMaterialOutboundInfo> page = new PageImpl<>(this.rawMaterialOutboundDao.selectRawMaterialOutboundInfoListByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取分页数据
                List<RawMaterialOutboundInfo> list = page.toList();
                //出参赋值
                outDTO.setRawMaterialOutboundInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("原材料出库queryByPage结束");
        return outDTO;
    }



    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            RawMaterialOutboundEntity RawMaterialOutboundEntity = BeanCopyUtils.copy(dto,RawMaterialOutboundEntity.class);
            RawMaterialOutboundEntity.setCreateBy("zhangyunning");
            RawMaterialOutboundEntity.setCreateTime(new Date());
            int i = rawMaterialOutboundDao.insert(RawMaterialOutboundEntity);
            //修改库存
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
            RawMaterialOutboundEntity RawMaterialOutboundEntity = BeanCopyUtils.copy(dto,RawMaterialOutboundEntity.class);
            RawMaterialOutboundEntity.setCreateBy("zhangyunning");
            RawMaterialOutboundEntity.setUpdateTime(new Date());
            int i = rawMaterialOutboundDao.updateById(RawMaterialOutboundEntity);
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
            int i = rawMaterialOutboundDao.deleteById(dto.getId());
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
