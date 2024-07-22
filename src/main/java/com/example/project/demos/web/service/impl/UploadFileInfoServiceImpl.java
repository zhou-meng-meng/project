package com.example.project.demos.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.UploadFileInfoDao;
import com.example.project.demos.web.dto.list.UploadFileEditInfo;
import com.example.project.demos.web.dto.list.UploadFileId;
import com.example.project.demos.web.dto.list.UploadFileInfo;
import com.example.project.demos.web.dto.uploadFileInfo.*;
import com.example.project.demos.web.entity.UploadFileInfoEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.UploadFileInfoService;
import com.example.project.demos.web.utils.BeanCopyUtils;
import com.example.project.demos.web.utils.PageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("uploadFileInfoService")
public class UploadFileInfoServiceImpl   implements UploadFileInfoService {

    @Autowired
    private UploadFileInfoDao uploadFileInfoDao;

    @Override
    public UploadFileInfoOutDTO uploadFile( String bodyDto, MultipartFile[] files) {
        log.info("上传文件，判断businessId");
        UploadFileInfoDTO dto =JSONObject.parseObject(bodyDto,UploadFileInfoDTO.class);
        Long businessId = dto.getBusinessId();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UploadFileInfoOutDTO outDTO = new UploadFileInfoOutDTO();
        List<UploadFileId> idList = new ArrayList<>();
        try{
            if(ObjectUtil.isNotNull(businessId)){
                log.info("businessId is not null,先获取原配置");
                List<UploadFileInfoEntity> list = uploadFileInfoDao.queryByParam(businessId);
                if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
                    log.info("删除原配置");
                    uploadFileInfoDao.deleteByBusinessId(businessId);
                    log.info("删除附件信息");
                    for(UploadFileInfoEntity entity : list){
                        File file = new File(entity.getFilePath());
                        if(file.exists()){
                            FileUtil.del(entity.getFilePath());
                        }else{
                            log.info(entity.getFilePath() +" 文件不存在");
                        }
                    }
                }else{
                    log.info("没有附件配置信息");
                }
            }else{
                log.info("businessId is null");
            }
            log.info("开始上传附件信息");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.YYYY_MM);
            String  month = sdf.format(date);
            for (int i = 0; i < files.length; i++) {
                MultipartFile file = files[i];
                //生成随机码
                Random random = new Random();
                int randomNumber = random.nextInt(9000) + 1000;
                // 原始文件名称
                String originalFileName = Objects.requireNonNull(file.getOriginalFilename());
                log.info("originalFileName:"+originalFileName);
                String type = originalFileName.substring(originalFileName.lastIndexOf(".")+1);
                log.info("type:"+type);
                String name = originalFileName.substring(0,originalFileName.lastIndexOf("."));
                log.info("name:"+name);
                String fileFullName = name + "_" + String.valueOf(randomNumber)+ "."+type;
                log.info("fileFullName:"+fileFullName);
                String path  = Constants.filePath + month ;
                log.info("path:"+path);
                log.info("文件上传至服务器开始");
                //创建文件夹
                File folder = new File(path);
                if (!folder.exists()) {
                    log.info("创建文件夹");
                    folder.mkdirs();
                }else{
                    log.info("文件夹:"+path +" 已存在");
                }
                // 文件上传至服务器
                try {
                    log.info("正在上传");
                    file.transferTo(new File(path, fileFullName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                log.info("文件上传至服务器完成，开始记录文件信息");
                UploadFileInfoEntity entity = new UploadFileInfoEntity();
                entity.setBusinessId(businessId);
                entity.setFileOriginalName(originalFileName);
                entity.setFileFullName(fileFullName);
                entity.setFileType(type);
                //全路径
                entity.setFilePath(path+"/"+fileFullName);
                entity.setCreateBy(dto.getLoginUser());
                entity.setCreateTime(date);
                entity.setFunctionId(dto.getFunctionId());
                log.info("插入文件信息");
                uploadFileInfoDao.insert(entity);
                UploadFileId fileId = new UploadFileId();
                fileId.setId(entity.getId());
                idList.add(fileId);
            }
            outDTO.setFileIdList(idList);
        }catch (Exception e){
            log.info("e:"+e.getMessage());
            errorCode = ErrorCodeEnums.UPLOAD_FILE_ERROR.getCode();
            errortMsg = ErrorCodeEnums.UPLOAD_FILE_ERROR.getDesc();
            log.info("删除原附件信息");
            uploadFileInfoDao.deleteBatchIds(idList);
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }

    @Override
    public QueryFileInfoListOutDTO queryFileInfoList(QueryFileInfoListDTO dto) {
        log.info("获取附件列表queryFiileInfoList开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryFileInfoListOutDTO outDTO = new QueryFileInfoListOutDTO();
        try {
            //先用查询条件查询总条数
            List<UploadFileInfoEntity> entityList = uploadFileInfoDao.queryByParam(dto.getBusinessId());
            if(CollectionUtil.isNotEmpty(entityList) && entityList.size() >0 ){
                outDTO.setTurnPageTotalNum(entityList.size());
                PageRequest pageRequest = new PageRequest(dto.getTurnPageBeginPos()-1,dto.getTurnPageShowNum());
                //开始分页查询
                Page<UploadFileInfo> page = new PageImpl<>(this.uploadFileInfoDao.selectUploadFileInfoListByPage(dto.getBusinessId(), pageRequest), pageRequest, Long.valueOf(entityList.size()));
                //获取分页数据
                List<UploadFileInfo> list = page.toList();
                //出参赋值
                outDTO.setFileInfoList(list);
            }else{
                log.info("list is null ");
                outDTO.setTurnPageTotalNum(0);
            }
        }catch (Exception e){
            //异常情况   赋值错误码和错误值
            log.info(e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("获取附件列表queryFiileInfoList结束");
        return outDTO;
    }

    @Override
    public DownloadFileInfoOutDTO getFileInfoBase64Str(DownloadFileInfoDTO dto)  {
        log.info("下载文件-获取文件base64流-getFileInfoBase64Str开始");
        DownloadFileInfoOutDTO outDTO = new DownloadFileInfoOutDTO();
        String filePath = dto.getFilePath();
        log.info("filePath:"+filePath);
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        String base64Str = "";
        try{
            File file = new File(filePath);
            if(file.exists()){
                byte[] fileByte = Files.readAllBytes(Paths.get(filePath));
                base64Str = Base64.getEncoder().encodeToString(fileByte);
            }else{
                log.info(filePath +" 文件不存在");
                errorCode = ErrorCodeEnums.UPLOAD_FILE_NOT_EXISTS.getCode();
                errortMsg = ErrorCodeEnums.UPLOAD_FILE_NOT_EXISTS.getDesc();
            }
        }catch (IOException e){
            log.info("下载文件异常:"+e.getMessage());
            errorCode = ErrorCodeEnums.SYS_FAIL_FLAG.getCode();
            errortMsg = ErrorCodeEnums.SYS_FAIL_FLAG.getDesc();
        }
        outDTO.setBase64Str(base64Str);
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        log.info("下载文件-获取文件base64流-getFileInfoBase64Str结束");
        return outDTO;
    }

    @Override
    public int deleteFileByBusinessId(Long businessId) {
        log.info("删除文件信息deleteFileByBusinessId开始:"+businessId);
        log.info("先获取原配置");
        int i = 0;
        List<UploadFileInfoEntity> list = uploadFileInfoDao.queryByParam(businessId);
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            log.info("删除原配置");
            i = uploadFileInfoDao.deleteByBusinessId(businessId);
            log.info("删除附件信息");
            for(UploadFileInfoEntity entity : list){
                //File file = new File(entity.getFilePath());
                FileUtil.del(entity.getFilePath());
                /*if(file.exists()){
                    FileUtil.del(entity.getFilePath());
                }else{
                    log.info(entity.getFilePath() +" 文件不存在");
                }*/
            }
        }else{
            log.info("没有附件配置信息");
        }
        log.info("删除文件信息deleteFileByBusinessId结束");
        return i;
    }

    @Override
    public int updateByBusinessId(Long businessId, List<UploadFileId> fileIdList) {
        log.info("更新附件配置表的业务主键开始:"+businessId);
        int  i= 0;
        List<Long> list = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(fileIdList) && fileIdList.size()> 0){
            for(UploadFileId fileId : fileIdList){
                list.add(fileId.getId());
            }
            i = uploadFileInfoDao.updateByBusinessId(businessId, list);
        }else{
            log.info("没有附件信息，不处理");
        }
        log.info("更新附件配置表的业务主键结束");
        return i;
    }

    /**
     * 各业务的编辑页面 获取文件的id  文件名  base64 文件类型
     * @param businessId
     * @return
     */
    @Override
    public QueryFileInfoEditListOutDTO queryFileInfoEditList(Long businessId) {
        log.info("获取附件列表queryFileInfoEditList开始");
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        QueryFileInfoEditListOutDTO outDTO = new QueryFileInfoEditListOutDTO();
        log.info("获取该业务和附件的配置信息");
        List<UploadFileInfoEntity> list = uploadFileInfoDao.queryByParam(businessId);
        List<UploadFileEditInfo> fileInfoList = new ArrayList<>();
        if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
            for(UploadFileInfoEntity entity : list){
                try{
                    String base64Str = "";
                    File file = new File(entity.getFilePath());
                    if(file.exists()){
                        byte[] fileByte = Files.readAllBytes(Paths.get(entity.getFilePath()));
                        base64Str = Base64.getEncoder().encodeToString(fileByte);
                        UploadFileEditInfo info = BeanCopyUtils.copy(entity,UploadFileEditInfo.class);
                        info.setBase64Str(base64Str);
                        fileInfoList.add(info);
                    }else{
                        log.info(entity.getFilePath() +" 文件不存在");
                    }
                }catch (IOException e){
                    log.info("获取base64异常:"+e.getMessage());
                }
            }
        }else{
            log.info("没有附件配置信息");
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        outDTO.setFileInfoList(fileInfoList);
        log.info("获取附件列表queryFileInfoEditList结束");
        return outDTO;
    }
}
