package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.RebuildOutboundDao;
import com.example.project.demos.web.dto.list.RebuildOutboundInfo;
import com.example.project.demos.web.dto.rebuildOutbound.*;
import com.example.project.demos.web.entity.RebuildOutboundEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.RebuildOutboundService;
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
@Service("rebuildOutboundService")
public class RebuildOutboundServiceImpl  implements RebuildOutboundService {

    @Resource
    private RebuildOutboundDao rebuildOutboundDao;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("重造出库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        /*try{
            RebuildOutboundInfo RebuildOutboundInfo = RebuildOutboundDao.selectRebuildOutboundInfoById(id);
            outDTO = BeanUtil.copyProperties(RebuildOutboundInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }*/
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("重造出库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("重造出库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.rebuildOutboundDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                //RebuildOutboundEntity RebuildOutbound = BeanCopyUtils.copy(queryByPageDTO,RebuildOutboundEntity.class);
                //开始分页查询
                Page<RebuildOutboundInfo> page = new PageImpl<>(this.rebuildOutboundDao.selectRebuildOutboundInfoListByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取分页数据
                List<RebuildOutboundInfo> list = page.toList();
                //出参赋值
                outDTO.setRebuildOutboundInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("重造出库queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            RebuildOutboundEntity RebuildOutboundEntity = BeanCopyUtils.copy(dto,RebuildOutboundEntity.class);
            RebuildOutboundEntity.setCreateBy("zhangyunning");
            RebuildOutboundEntity.setCreateTime(new Date());
            int i = rebuildOutboundDao.insert(RebuildOutboundEntity);
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
            RebuildOutboundEntity RebuildOutboundEntity = BeanCopyUtils.copy(dto,RebuildOutboundEntity.class);
            RebuildOutboundEntity.setCreateBy("zhangyunning");
            RebuildOutboundEntity.setUpdateTime(new Date());
            int i = rebuildOutboundDao.updateById(RebuildOutboundEntity);
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
            int i = rebuildOutboundDao.deleteById(dto.getId());
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
