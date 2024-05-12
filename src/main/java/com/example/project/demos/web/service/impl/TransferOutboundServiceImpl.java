package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.SysFactoryDao;
import com.example.project.demos.web.dao.SysStorehouseDao;
import com.example.project.demos.web.dao.TransferOutboundDao;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.list.TransferOutboundInfo;
import com.example.project.demos.web.dto.transferOutbound.*;
import com.example.project.demos.web.entity.SysFactoryEntity;
import com.example.project.demos.web.entity.SysStorehouseEntity;
import com.example.project.demos.web.entity.TransferOutboundEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.TransferOutboundService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

@Slf4j
@Service("transferOutboundService")
public class TransferOutboundServiceImpl  implements TransferOutboundService {

    @Resource
    private TransferOutboundDao transferOutboundDao;

    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("调拨出库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        /*try{
            TransferOutboundInfo TransferOutboundInfo = transferOutboundDao.selectTransferOutboundInfoById(id);
            outDTO = BeanUtil.copyProperties(TransferOutboundInfo, QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }*/
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("调拨出库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("调拨出库queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.transferOutboundDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<TransferOutboundInfo> page = new PageImpl<>(this.transferOutboundDao.selectTransferOutboundInfoListByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取分页数据
                List<TransferOutboundInfo> list = page.toList();
                //获取厂区和仓库集合
                List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
                List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
                //赋值调出方和调入方
                list = setTransferObject(list,factoryInfoList,sysStorehouseInfoList);
                //出参赋值
                outDTO.setTransferOutboundInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("调拨出库queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            TransferOutboundEntity TransferOutboundEntity = BeanCopyUtils.copy(dto,TransferOutboundEntity.class);
            TransferOutboundEntity.setCreateBy("zhangyunning");
            TransferOutboundEntity.setCreateTime(new Date());
            int i = transferOutboundDao.insert(TransferOutboundEntity);
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
            TransferOutboundEntity TransferOutboundEntity = BeanCopyUtils.copy(dto,TransferOutboundEntity.class);
            TransferOutboundEntity.setCreateBy("zhangyunning");
            TransferOutboundEntity.setUpdateTime(new Date());
            int i = transferOutboundDao.updateById(TransferOutboundEntity);
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
            int i = transferOutboundDao.deleteById(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    /**
     * 赋值调入方和调出方的名称
     * @param list
     * @param factoryInfoList
     * @param sysStorehouseInfoList
     * @return
     */
    private List<TransferOutboundInfo> setTransferObject(List<TransferOutboundInfo> list,List<SysFactoryInfo> factoryInfoList,List<SysStorehouseInfo> sysStorehouseInfoList){
        for(TransferOutboundInfo info : list){
            //调入方
            String inCode = info.getInCode();
            if(Constants.FACTORY_CODE_PREFIX.equals(inCode.substring(0,1))){
                //工厂
                for(SysFactoryInfo fInfo : factoryInfoList){
                    if(inCode.equals(fInfo.getCode())){
                        info.setInName(fInfo.getName());
                    }
                }
            }else{
                //仓库
                for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                    if(inCode.equals(sInfo.getCode())){
                        info.setInName(sInfo.getName());
                    }
                }
            }
            //调出方
            String outCode = info.getOutCode();
            if(Constants.FACTORY_CODE_PREFIX.equals(outCode.substring(0,1))){
                //工厂
                for(SysFactoryInfo fInfo : factoryInfoList){
                    if(outCode.equals(fInfo.getCode())){
                        info.setOutName(fInfo.getName());
                    }
                }
            }else{
                //仓库
                for(SysStorehouseInfo sInfo : sysStorehouseInfoList){
                    if(outCode.equals(sInfo.getCode())){
                        info.setOutName(sInfo.getName());
                    }
                }
            }
        }
        return list;
    }

}
