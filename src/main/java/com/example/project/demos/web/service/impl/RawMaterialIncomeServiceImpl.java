package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.dao.RawMaterialIncomeDao;
import com.example.project.demos.web.dto.list.RawMaterialIncomeInfo;
import com.example.project.demos.web.dto.rawMaterialIncome.*;
import com.example.project.demos.web.entity.RawMaterialIncomeEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.RawMaterialIncomeService;
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
@Service("rawMaterialIncomeService")
public class RawMaterialIncomeServiceImpl  implements RawMaterialIncomeService {

    @Resource
    private RawMaterialIncomeDao rawMaterialIncomeDao;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("来料入库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            RawMaterialIncomeInfo rawMaterialIncomeInfo = rawMaterialIncomeDao.selectRawMaterialIncomeInfoById(id);
            outDTO = BeanUtil.copyProperties(rawMaterialIncomeInfo, QueryByIdOutDTO.class);

        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("来料入库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("来料入库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.rawMaterialIncomeDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //转换实体入参
                //RawMaterialIncomeEntity RawMaterialIncome = BeanCopyUtils.copy(queryByPageDTO,RawMaterialIncomeEntity.class);
                //开始分页查询
                Page<RawMaterialIncomeInfo> page = new PageImpl<>(this.rawMaterialIncomeDao.selectRawMaterialIncomeInfoListByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取分页数据
                List<RawMaterialIncomeInfo> list = page.toList();
                //出参赋值
                outDTO.setRawMaterialIncomeInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("来料入库queryByPage结束");
        return outDTO;
    }



    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            RawMaterialIncomeEntity RawMaterialIncomeEntity = BeanCopyUtils.copy(dto,RawMaterialIncomeEntity.class);
            RawMaterialIncomeEntity.setCreateBy("zhangyunning");
            RawMaterialIncomeEntity.setCreateTime(new Date());
            int i = rawMaterialIncomeDao.insert(RawMaterialIncomeEntity);
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
            RawMaterialIncomeEntity RawMaterialIncomeEntity = BeanCopyUtils.copy(dto,RawMaterialIncomeEntity.class);
            RawMaterialIncomeEntity.setCreateBy("zhangyunning");
            RawMaterialIncomeEntity.setUpdateTime(new Date());
            int i = rawMaterialIncomeDao.updateById(RawMaterialIncomeEntity);
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
            int i = rawMaterialIncomeDao.deleteById(dto.getId());
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
