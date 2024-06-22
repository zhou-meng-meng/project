package com.example.project.demos.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.ApproveOperationFlowDao;
import com.example.project.demos.web.dao.ApproveOperationQueueDao;
import com.example.project.demos.web.dao.CustomerSaleDao;
import com.example.project.demos.web.dto.customerPayDetail.AddPayBySystemDTO;
import com.example.project.demos.web.dto.customerSale.*;
import com.example.project.demos.web.dto.list.CustomerAccountRelInfo;
import com.example.project.demos.web.dto.list.CustomerSaleInfo;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.entity.*;
import com.example.project.demos.web.enums.*;
import com.example.project.demos.web.handler.RequestHolder;
import com.example.project.demos.web.service.*;
import com.example.project.demos.web.utils.BeanCopyUtils;
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
@Service("customerSaleService")
public class CustomerSaleServiceImpl implements CustomerSaleService {

    @Resource
    private CustomerSaleDao customerSaleDao;

    @Autowired
    private CustomerAccountRelService customerAccountRelService;

    @Autowired
    private CustomerPayDetailService customerPayDetailService;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private SysUserService sysUserService;

    @Resource
    private ApproveOperationFlowDao approveOperationFlowDao;
    @Resource
    private ApproveOperationQueueDao approveOperationQueueDao;

    @Override
    public QueryByIdOutDTO queryById(QueryByIdDTO dto) {
        log.info("销售客户queryById开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryByIdOutDTO outDTO = new QueryByIdOutDTO();
        try{
            CustomerSaleInfo info = customerSaleDao.selectCustomerSaleInfoById(dto.getId());
            outDTO = BeanUtil.copyProperties(info, QueryByIdOutDTO.class);
            //查询对应账户
            List<CustomerAccountRelInfo> list  = customerAccountRelService.queryRelListByCustomerCode(info.getCode());
            outDTO.setAccountRelList(list);
        }catch(Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售客户queryById结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryByPage(QueryByPageDTO queryByPageDTO) {
        log.info("销售客户queryByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //权限判断  总公司审核人员可查看所有数据 其他人员只能看属于自己的客户数据
            UserLoginOutDTO user = RequestHolder.getUserInfo();
            String userType = user.getUserType();
            log.info("userType:"+userType);
            List<String> authList = user.getAuthorityType();
            if(authList.contains(RoleAuthorityTypeEnums.ROLE_AUTHORIT_YTYPE_AUTH.getCode())){
                log.info("具有审核权限，可以查看所有数据");
            }else{
                log.info("不具有审核权限，只能查看自己的客户信息");
                queryByPageDTO.setSaler(user.getUserLogin());
            }
            //先用查询条件查询总条数
            long total = this.customerSaleDao.count(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<CustomerSaleInfo> page = new PageImpl<>(this.customerSaleDao.selectCustomerSaleInfoListByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取分页数据
                List<CustomerSaleInfo> list = page.toList();
                //出参赋值
                outDTO.setCustomerSaleInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售客户queryByPage结束");
        return outDTO;
    }

    @Override
    public QueryByPageOutDTO queryPopByPage(QueryByPageDTO queryByPageDTO) {
        log.info("销售客户queryPopByPage开始");
        QueryByPageOutDTO outDTO = new QueryByPageOutDTO();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        try {
            //先用查询条件查询总条数
            long total = this.customerSaleDao.countPop(queryByPageDTO);
            outDTO.setTurnPageTotalNum(Integer.parseInt(String.valueOf(total)));
            //存在数据的   继续查询
            if(total != 0L){
                //分页信息
                PageRequest pageRequest = new PageRequest(queryByPageDTO.getTurnPageBeginPos()-1,queryByPageDTO.getTurnPageShowNum());
                //开始分页查询
                Page<CustomerSaleInfo> page = new PageImpl<>(this.customerSaleDao.selectPopListByPage(queryByPageDTO, pageRequest), pageRequest, total);
                //获取分页数据
                List<CustomerSaleInfo> list = page.toList();
                //出参赋值
                outDTO.setCustomerSaleInfoList(list);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("销售客户queryPopByPage结束");
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
            if(CollectionUtil.isNotEmpty(userList) && userList.size() > 0) {
                CustomerSaleEntity entity = BeanCopyUtils.copy(dto, CustomerSaleEntity.class);
                entity.setCreateBy(user.getUserLogin());
                entity.setCreateTime(date);
                //添加待审状态
                entity.setApproveState(ApproveStateEnums.APPROVE_STATE_UNAUTH.getCode());
                int i = customerSaleDao.insert(entity);
                //添加账号对应关系
                customerAccountRelService.savaBatch(dto.getCode(), dto.getList());
                log.info("生成审核流水记录");
                ApproveOperationFlowEntity flowEntity = new ApproveOperationFlowEntity(null, entity.getId(), FunctionTypeEnums.CUSTOMER_SALE.getCode(), user.getUserLogin(), date, Constants.SYSTEM_CODE);
                approveOperationFlowDao.insert(flowEntity);
                log.info("生成审核队列记录");
                List<ApproveOperationQueueEntity> queueEntityList = new ArrayList<>();
                for (SysUserEntity userEntity : userList) {
                    ApproveOperationQueueEntity queueEntity = new ApproveOperationQueueEntity(null, flowEntity.getId(), entity.getId(), FunctionTypeEnums.CUSTOMER_SALE.getCode(), userEntity.getUserLogin(), user.getUserLogin(), date, Constants.SYSTEM_CODE);
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
        String info = "客户编号:"+ dto.getCode() +",客户名称:"+dto.getName()+",联系人:"+dto.getLinkUser()+",联系电话:"+dto.getLinkPhoneNo()+",地址:"+dto.getAddress()+",销售员:"+dto.getSalerName();
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_SALE.getCode(), OperationTypeEnums.OPERATION_TYPE_ADD.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            CustomerSaleEntity entity = BeanCopyUtils.copy(dto,CustomerSaleEntity.class);
            entity.setUpdateBy(user.getUserLogin());
            entity.setUpdateTime(new Date());
            int i = customerSaleDao.updateById(entity);
            //先删除原账号对应关系
            customerAccountRelService.deleteRelByCustomerCode(dto.getCode());
            //重新插入账号对应关系
            customerAccountRelService.savaBatch(dto.getCode(),dto.getList());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "客户编号:"+ dto.getCode() +",客户名称:"+dto.getName()+",联系人:"+dto.getLinkUser()+",联系电话:"+dto.getLinkPhoneNo()+",地址:"+dto.getAddress()+",销售员:"+dto.getSalerName();
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_SALE.getCode(), OperationTypeEnums.OPERATION_TYPE_UPDATE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
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
            int i = customerSaleDao.deleteById(dto.getId());
            //删除客户对应账号
            customerAccountRelService.deleteRelByCustomerCode(dto.getCode());
            log.info("删除提交的待审核记录");
            approveOperationFlowDao.deleteByBusinessId(dto.getId());
            approveOperationQueueDao.deleteByBusinessId(dto.getId());
        }catch (Exception e){
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        //记录操作日志
        String info = "客户编号:"+ dto.getCode() +",客户名称:"+dto.getName()+",联系人:"+dto.getLinkUser()+",联系电话:"+dto.getLinkPhoneNo()+",地址:"+dto.getAddress()+",销售员:"+dto.getSalerName();
        sysLogService.insertSysLog(FunctionTypeEnums.CUSTOMER_SALE.getCode(), OperationTypeEnums.OPERATION_TYPE_DELETE.getCode(),user.getUserLogin(),date,info,errorCode,errortMsg,user.getLoginIp(),user.getToken(),Constants.SYSTEM_CODE);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public int updateApprove(Long id, String result, String opinion, String userLogin,  Date date)  {
        log.info("销售客户审核更新开始");
        CustomerSaleEntity entity = customerSaleDao.selectById(id);
        entity.setApproveUser(userLogin);
        entity.setApproveState(result);
        entity.setApproveOpinion(opinion);
        entity.setApproveTime(date);
        entity.setUpdateBy(userLogin);
        entity.setUpdateTime(date);
        int i =customerSaleDao.updateById(entity);
        //判断审核结果
        if(result.equals(ApproveConfirmResultEnums.APPROVE_CONFIRM_RESULT_AGREE.getCode())){
            log.info("审核同意，生成往来账信息");
            AddPayBySystemDTO dto = new AddPayBySystemDTO(null,entity.getCode(),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),new BigDecimal(0),"1",SysEnums.SYS_YES_FLAG.getCode(),Constants.SYSTEM_CODE,date,FunctionTypeEnums.CUSTOMER_SALE.getDesc());
            i = customerPayDetailService.addPayBySystem(dto);
        }else{
            log.info("审核拒绝，删除账户信息");
            customerAccountRelService.deleteRelByCustomerCode(entity.getCode());
        }
        log.info("销售维护审核更新结束");
        return i;
    }






}
