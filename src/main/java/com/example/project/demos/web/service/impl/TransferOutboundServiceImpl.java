package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.customerPayDetail.AddPayBySystemDTO;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.list.TransferOutboundInfo;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.dto.transferOutbound.*;
import com.example.project.demos.web.entity.*;
import com.example.project.demos.web.enums.*;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.DataUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
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

    @Autowired
    private SysLogService sysLogService;

    @Autowired
    private SysUserService sysUserService;
    @Resource
    private ConfirmOperationQueueDao confirmOperationQueueDao;
    @Resource
    private ConfirmOperationFlowDao confirmOperationFlowDao;
    @Autowired
    private MaterialInventoryService materialInventoryService;

    @Autowired
    private UploadFileInfoService uploadFileInfoService;
    @Resource
    private UploadFileInfoDao uploadFileInfoDao;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("调拨出库queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            TransferOutboundInfo info = transferOutboundDao.selectTransferOutboundInfoById(id);
            //处理调入方和调出方
            List<TransferOutboundInfo> list = new ArrayList<>();
            list.add(info);
            list = setTransferObject(list);
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("调拨出库queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("调拨出库queryByPage开始");
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
                log.info("当前登录人不属于总公司，只能查看所属厂区或仓库");
                dto.setOutCode(user.getDeptId());
            }
            //先用查询条件查询总条数
            long total = this.transferOutboundDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<TransferOutboundInfo> page = new PageImpl<>(this.transferOutboundDao.selectTransferOutboundInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<TransferOutboundInfo> list = page.toList();
                //赋值调出方和调入方
                list = setTransferObject(list);
                //出参赋值
                outDTO.setTransferOutboundInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("调拨出库queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        log.info("调拨出库新增开始");
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            log.info("获取调入方具有确认权限的人");
            String userType = "";
            String inCode = dto.getInCode();
            if("F".equals(inCode.substring(0,1))){
                log.info("调入方为厂区");
                userType = UserTypeEnums.USER_TYPE_FACTORY.getCode();
            }else{
                log.info("调入方为仓库");
                userType = UserTypeEnums.USER_TYPE_STORE.getCode();
            }
            List<SysUserEntity> userList = sysUserService.queryUserListByRoleType(userType, RoleAuthorityTypeEnums.ROLE_AUTHORITY_TYPE_CONFIRM.getCode(),dto.getInCode());
            if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0){
                TransferOutboundEntity entity = BeanCopyUtils.copy(dto,TransferOutboundEntity.class);
                entity.setCreateBy(user.getUserLogin());
                entity.setCreateTime(date);
                entity.setConfirmState(ConfirmStateEnums.CONFIRM_STATE_UNDO.getCode());
                //生成单据号
                entity.setBillNo(getBillNoList(user));
                log.info("插入调拨出库表");
                int i = transferOutboundDao.insert(entity);
                //生成待确认流水
                ConfirmOperationFlowEntity flowEntity = new ConfirmOperationFlowEntity(null,entity.getId(), FunctionTypeEnums.TRANSFER_OUTBOUND.getCode(),null,user.getUserLogin(),date,null,null,null,null, ConfirmStateEnums.CONFIRM_STATE_UNDO.getCode(),Constants.SYSTEM_CODE);
                confirmOperationFlowDao.insert(flowEntity);
                //生成待确认队列
                List<ConfirmOperationQueueEntity> queueEntityList = new ArrayList<>();
                for(SysUserEntity userEntity : userList){
                    ConfirmOperationQueueEntity queueEntity = new ConfirmOperationQueueEntity(null,flowEntity.getId(),entity.getId(),userEntity.getUserLogin(),FunctionTypeEnums.TRANSFER_OUTBOUND.getCode(),user.getUserLogin(),date,null,null);
                    queueEntityList.add(queueEntity);
                }
                confirmOperationQueueDao.insertBatch(queueEntityList);
                //开始处理附件信息
                uploadFileInfoService.updateByBusinessId(entity.getId(),dto.getFileIdList());
            }else{
                errorCode = ErrorCodeEnums.CONFIRM_USER_NOT_EXIST.getCode();
                errortMsg = ErrorCodeEnums.CONFIRM_USER_NOT_EXIST.getDesc();
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getTransferCount().toString()+",调出方:"+dto.getOutName()+",调入方:"+dto.getInName()+",单据号:"+dto.getBillNo();
        sysLogService.insertSysLog(FunctionTypeEnums.TRANSFER_OUTBOUND.getCode(),OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("调拨出库新增结束");
        return outDTO;
    }

    @Override
    public EditOutDTO update(EditDTO dto) {
        log.info("调拨出库新增开始");
        EditOutDTO outDTO = new EditOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            TransferOutboundEntity entity = BeanCopyUtils.copy(dto,TransferOutboundEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            log.info("插入调拨出库表");
            int i = transferOutboundDao.updateById(entity);
            //开始处理附件信息
            uploadFileInfoService.updateByBusinessId(entity.getId(),dto.getFileIdList());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getTransferCount().toString()+",调出方:"+dto.getOutName()+",调入方:"+dto.getInName()+",单据号:"+dto.getBillNo();
        sysLogService.insertSysLog(FunctionTypeEnums.TRANSFER_OUTBOUND.getCode(),OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("调拨出库新增结束");
        return outDTO;
    }

    @Override
    public DeleteByIdOutDTO deleteById(DeleteByIdDTO dto) {
        log.info("调拨出库删除开始");
        DeleteByIdOutDTO outDTO = new DeleteByIdOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try{
            int i = transferOutboundDao.deleteById(dto.getId());
            log.info("删除提交的待确认记录");
            confirmOperationQueueDao.deleteByBusinessId(dto.getId());
            confirmOperationFlowDao.deleteByBusinessId(dto.getId());
            log.info("开始删除附件信息");
            uploadFileInfoService.deleteFileByBusinessId(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        //记录操作日志
        String info = "物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",数量:"+dto.getTransferCount().toString()+",调出方:"+dto.getOutName()+",调入方:"+dto.getInName()+",单据号:"+dto.getBillNo();
        sysLogService.insertSysLog(FunctionTypeEnums.TRANSFER_OUTBOUND.getCode(),OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("调拨出库删除结束");
        return outDTO;
    }

    @Override
    public int updateApprove(Long id, String result, String opinion, String userLogin,  Date date)  {
        log.info("调拨出库确认更新开始");
        TransferOutboundEntity entity = transferOutboundDao.selectById(id);
        entity.setConfirmState(result);
        entity.setConfirmUser(userLogin);
        entity.setConfirmOpinion(opinion);
        entity.setConfirmTime(date);
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        int i =transferOutboundDao.updateById(entity);
        //判断确认结果
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            log.info("确认同意，开始更新调入方库存");
            i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getTransferCount(),"add",date);
            log.info("开始更新调出方库存");
            i = materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getOutCode(), entity.getTransferCount(),"reduce",date);
        }else{
            log.info("确认拒绝");
        }
        log.info("调拨出库确认更新结束");
        return i;
    }

    @Override
    public List<TransferOutboundInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("调拨出库queryListForExport开始");
        List<TransferOutboundInfo> list = new ArrayList<>();
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
                log.info("当前登录人不属于总公司，只能查看所属厂区或仓库");
                dto.setOutCode(user.getDeptId());
            }
            list = transferOutboundDao.queryListForExport(dto);
            //赋值调出方和调入方
            list = setTransferObject(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.TRANSFER_OUTBOUND.getCode(),OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("调拨出库queryListForExport结束");
        return list;
    }

    /**
     * 赋值调入方和调出方的名称
     * @param list
     * @return
     */
    private List<TransferOutboundInfo> setTransferObject(List<TransferOutboundInfo> list){
        log.info("赋值调入方和调出方的名称");
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            //获取厂区和仓库集合
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
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
        }else{
            log.info("list is null");
        }
        return list;
    }

    /**
     * 格式化单据号
     * @param user
     * @return
     */
    private String getBillNoList(UserLoginOutDTO user){
        String billNo="";
        String prefix = DataUtils.formatBillNoPrefix(user,"出");
        List<String> list = transferOutboundDao.queryBillNoListByParam(prefix);
        StringBuffer sb = new StringBuffer();
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            billNo = DataUtils.formatBillNo(list);
        }else{
            sb.append(prefix).append("0001");
            billNo = sb.toString();
        }
        return billNo;
    }

}
