package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.*;
import com.example.project.demos.web.dto.customerPayDetail.AddPayBySystemDTO;
import com.example.project.demos.web.dto.list.SalersOrderReturnInfo;
import com.example.project.demos.web.dto.list.SysFactoryInfo;
import com.example.project.demos.web.dto.list.SysStorehouseInfo;
import com.example.project.demos.web.dto.salersOrderReturn.*;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.*;
import com.example.project.demos.web.enums.*;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
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
@Service("salersOrderReturnService")
public class SalersOrderReturnServiceImpl implements SalersOrderReturnService {

    @Resource
    private SalersOrderReturnDao salersOrderReturnDao;
    @Resource
    private SysFactoryDao sysFactoryDao;

    @Resource
    private SysStorehouseDao sysStorehouseDao;
    @Autowired
    private SysLogService sysLogService;
    @Resource
    private ApproveOperationFlowDao approveOperationFlowDao;
    @Resource
    private ApproveOperationQueueDao approveOperationQueueDao;
    @Autowired
    private MaterialInventoryService materialInventoryService;
    @Autowired
    private CustomerPayDetailService customerPayDetailService;
    @Autowired
    private SysUserService sysUserService;
    @Resource
    private SalesCustomerPayDao salesCustomerPayDao;

    @Override
    public QueryByIdOutDTO queryById(Long id) {
        log.info("业务员下单退回queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            SalersOrderReturnInfo info = salersOrderReturnDao.selectSalersOrderReturnInfoById(id);
            //处理入库方
            List<SalersOrderReturnInfo> list = new ArrayList<>();
            list.add(info);
            list = setSalersOrderReturnObject(list);
            outDTO = BeanUtil.copyProperties(list.get(0), QueryByIdOutDTO.class);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("业务员下单queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO dto) {
        log.info("业务员下单退回queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //添加权限  总公司审核权限的  查看所有  只有总公司单价权限的 查看自己提交的数据  厂区/仓库人员查看所属厂区/仓库数据
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            List<String> listType = user.getAuthorityType();
            if(listType.contains(RoleAuthorityTypeEnums.ROLE_AUTHORIT_YTYPE_AUTH.getCode())){
                log.info("具有审核权限，查询所有数据");
            }else{
                log.info("不具有审核权限，查询自己提交的数据");
                dto.setReturnUser(user.getUserLogin());
            }
            //先用查询条件查询总条数
            long total = this.salersOrderReturnDao.count(dto);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<SalersOrderReturnInfo> page = new PageImpl<>(this.salersOrderReturnDao.selectSalersOrderReturnInfoListByPage(dto, pageRequest), pageRequest, total);
                //获取分页数据
                List<SalersOrderReturnInfo> list = page.toList();
                list = setSalersOrderReturnObject(list);
                //出参赋值
                outDTO.setSalersOrderReturnInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("业务员下单queryByPage结束");
        return outDTO;
    }

    @Override
    public AddOutDTO insert(AddDTO dto) {
        AddOutDTO outDTO = new AddOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        Date date = new Date();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        try{
            log.info("查询总公司具有审核权限的人员");
            List<SysUserEntity> userList = sysUserService.queryUserListByRoleType(UserTypeEnums.USER_TYPE_COMPANY.getCode(), RoleAuthorityTypeEnums.ROLE_AUTHORIT_YTYPE_AUTH.getCode(),"");
            if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0){
                SalersOrderReturnEntity entity = BeanCopyUtils.copy(dto,SalersOrderReturnEntity.class);
                entity.setApproveState(ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode());
                entity.setCreateBy(user.getUserLogin());
                entity.setCreateTime(date);
                int i = salersOrderReturnDao.insert(entity);
                log.info("生成审核流水记录");
                ApproveOperationFlowEntity flowEntity = new ApproveOperationFlowEntity(null,entity.getId(),FunctionTypeEnums.SALERS_ORDER_RETURN.getCode(),user.getUserLogin(),date,ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode(),Constants.SYSTEM_CODE);
                approveOperationFlowDao.insert(flowEntity);
                log.info("生成审核队列记录");
                List<ApproveOperationQueueEntity> queueEntityList = new ArrayList<>();
                for(SysUserEntity userEntity : userList){
                    ApproveOperationQueueEntity queueEntity = new ApproveOperationQueueEntity(null,flowEntity.getId(), entity.getId(),FunctionTypeEnums.SALERS_ORDER_RETURN.getCode(),userEntity.getUserLogin(),user.getUserLogin(),date,Constants.SYSTEM_CODE);
                    queueEntityList.add(queueEntity);
                }
                approveOperationQueueDao.insertBatch(queueEntityList);
            }
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "退回日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getReturnTime()) +",物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",数量:"+dto.getReturnCount()+",退回人:"+dto.getReturnUserName()+",单价:"+dto.getUnitPrice()+",总金额:"+dto.getTollAmount();
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_RETURN.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            SalersOrderReturnEntity entity = BeanCopyUtils.copy(dto,SalersOrderReturnEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(date);
            int i = salersOrderReturnDao.updateById(entity);
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "退回日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getReturnTime()) +",物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",数量:"+dto.getReturnCount()+",退回人:"+dto.getReturnUserName()+",单价:"+dto.getUnitPrice()+",总金额:"+dto.getTollAmount();
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_RETURN.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            int i = salersOrderReturnDao.deleteById(dto.getId());
            log.info("删除提交的待审核记录");
            approveOperationFlowDao.deleteByBusinessId(dto.getId());
            approveOperationQueueDao.deleteByBusinessId(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = e.getMessage();
        }
        //记录操作日志
        String info = "退回日期:"+ DateUtils.parseDateToStr(Constants.YYYY_MM_DD,dto.getReturnTime()) +",物料编号:"+dto.getMaterialCode()+",物料名称:"+dto.getMaterialName()+",客户:"+dto.getCustomerName()+",数量:"+dto.getReturnCount()+",退回人:"+dto.getReturnUserName()+",单价:"+dto.getUnitPrice()+",总金额:"+dto.getTollAmount();
        sysLogService.insertSysLog(FunctionTypeEnums.SALES_RETURN.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    /**
     * 审核更新
     * @param id
     * @param result
     * @param opinion
     * @param userLogin
     * @param unitPrice
     * @param tollAmount
     * @param date
     * @return
     */
    @Override
    public int updateApprove(Long id, String result, String opinion, String userLogin, BigDecimal unitPrice, BigDecimal tollAmount, Date date,String inCode)  {
        log.info("业务员下单退回审核更新开始");
        SalersOrderReturnEntity entity = salersOrderReturnDao.selectById(id);
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        entity.setApproveUser(userLogin);
        entity.setApproveState(result);
        entity.setApproveOpinion(opinion);
        entity.setApproveTime(date);
        int i = 0;
        //判断审核结果
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            log.info("审核同意，更新业务员下单退回表，不更新库存，不生成往来账，等待确认");
            // 需要确认  更新待确认状态
            entity.setConfirmState(ConfirmStateEnums.CONFIRM_STATE_UNDO.getCode());
            entity.setInCode(inCode);
        }else{
            log.info("审核拒绝，停止操作");
        }
        i = salersOrderReturnDao.updateById(entity);
        log.info("业务员下单退回审核更新结束");
        return i;
    }

    /**
     * 确认更新
     * @param id
     * @param result
     * @param opinion
     * @param userLogin
     * @param date
     * @return
     */
    @Override
    public int updateConfirm(Long id, String result, String opinion, String userLogin, Date date)  {
        log.info("业务员下单退回确认更新开始");
        SalersOrderReturnEntity entity = salersOrderReturnDao.selectById(id);
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        entity.setConfirmUser(userLogin);
        entity.setConfirmState(result);
        entity.setConfirmOpinion(opinion);
        entity.setConfirmTime(date);
        //判断确认结果
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            log.info("确认同意，开始更新库存");
            materialInventoryService.updateStockInventory(entity.getMaterialCode(), entity.getInCode(), entity.getReturnCount(),"add",date);
            log.info("生成该客户业务员下单记录");
            SalesCustomerPayEntity payEntity = new SalesCustomerPayEntity(null,entity.getId(),entity.getCustomerCode(), entity.getMaterialCode(), entity.getUnitPrice(),entity.getReturnCount(),entity.getTollAmount(),date,FunctionTypeEnums.SALERS_ORDER_RETURN.getCode());
            salesCustomerPayDao.insert(payEntity);
            log.info("生成往来账信息");
            AddPayBySystemDTO dto = new AddPayBySystemDTO(null,entity.getCustomerCode(),entity.getUnitPrice(),entity.getReturnCount(),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),entity.getTollAmount(),new BigDecimal(0),"1",SysEnums.SYS_NO_FLAG.getCode(),Constants.SYSTEM_CODE,date,FunctionTypeEnums.SALERS_ORDER_RETURN.getDesc());
            customerPayDetailService.addPayBySystem(dto);
        }else{
            log.info("确认拒绝");
        }
        int i =salersOrderReturnDao.updateById(entity);
        log.info("业务员下单退回确认更新结束");
        return i;
    }

    @Override
    public List<SalersOrderReturnInfo> queryListForExport(QueryByPageDTO dto) {
        log.info("业务员下单退回queryListForExport开始");
        List<SalersOrderReturnInfo> list = new ArrayList<>();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UserLoginOutDTO user = RequestHolder.getUserInfo();
        Date date = new Date();
        try {
            //添加权限  总公司审核权限的  查看所有  只有总公司单价权限的 查看自己提交的数据  厂区/仓库人员查看所属厂区/仓库数据
            List<String> listType = user.getAuthorityType();
            if(listType.contains(RoleAuthorityTypeEnums.ROLE_AUTHORIT_YTYPE_AUTH.getCode())){
                log.info("具有审核权限，查询所有数据");
            }else{
                log.info("不具有审核权限，查询自己提交的数据");
                dto.setReturnUser(user.getUserLogin());
            }
            list = salersOrderReturnDao.queryListForExport(dto);
            list = setSalersOrderReturnObject(list);
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "导出Excel操作";
        sysLogService.insertSysLog(FunctionTypeEnums.SALERS_ORDER_RETURN.getCode(), OperationTypeEnums.OPERATION_TYPE_EXPORT.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        log.info("业务员下单queryListForExport结束");
        return list;
    }

    /**
     * 赋值销售退回  入库方名称
     * @param list
     * @return
     */
    private List<SalersOrderReturnInfo> setSalersOrderReturnObject(List<SalersOrderReturnInfo> list){
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            //获取厂区和仓库集合
            List<SysFactoryInfo> factoryInfoList = sysFactoryDao.selectSysFactoryInfoList(new SysFactoryEntity());
            List<SysStorehouseInfo> sysStorehouseInfoList = sysStorehouseDao.selectStorehouseInfoList(new SysStorehouseEntity());
            for(SalersOrderReturnInfo info : list){
                //退货方
                String inCode = info.getInCode();
                if(null != inCode && !"".equals(inCode) && ""!= inCode){
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
                }else {
                    log.info("inCode is null");
                }
            }
        }else{
            log.info("list is null");
        }
        return list;
    }
}
