package com.example.project.demos.web.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dao.UploadFileInfoDao;
import com.example.project.demos.web.dto.uploadFileInfo.UploadFileInfoDTO;
import com.example.project.demos.web.dto.uploadFileInfo.UploadFileInfoOutDTO;
import com.example.project.demos.web.entity.UploadFileInfoEntity;
import com.example.project.demos.web.enums.ErrorCodeEnums;
import com.example.project.demos.web.service.UploadFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service("uploadFileInfoService")
public class UploadFileInfoServiceImpl   implements UploadFileInfoService {

    @Autowired
    private UploadFileInfoDao uploadFileInfoDao;

    @Override
    public UploadFileInfoOutDTO uploadFile( UploadFileInfoDTO dto, MultipartFile[] files) {
        log.info("上传文件，判断businessId");
        Long businessId = dto.getBusinessId();
        String errorCode= ErrorCodeEnums.SYS_SUCCESS_FLAG.getCode();
        String errortMsg= ErrorCodeEnums.SYS_SUCCESS_FLAG.getDesc();
        UploadFileInfoOutDTO outDTO = new UploadFileInfoOutDTO();
        try{
            if(ObjectUtil.isNotNull(businessId)){
                log.info("businessId is not null,先获取原配置");
                List<UploadFileInfoEntity> list = uploadFileInfoDao.queryByParam(businessId);
                if(CollectionUtil.isNotEmpty(list) && list.size() > 0){
                    log.info("删除原配置");
                    uploadFileInfoDao.deleteByBusinessId(businessId);
                    log.info("删除附件信息");
                    for(UploadFileInfoEntity entity : list){
                        FileUtil.del(entity.getFilePath());
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

            List<Long> idList = new ArrayList<>();
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
                //全路径
                entity.setFilePath(path+"/"+fileFullName);
                entity.setCreateBy(dto.getLoginUser());
                entity.setCreateTime(date);
                entity.setFunctionId(dto.getFunctionId());
                log.info("插入文件信息");
                uploadFileInfoDao.insert(entity);
                idList.add(entity.getId());
            }
            outDTO.setFileIdList(idList);
        }catch (Exception e){
            log.info("e:"+e.getMessage());
            errorCode = ErrorCodeEnums.UPLOAD_FILE_ERROR.getCode();
            errortMsg = ErrorCodeEnums.UPLOAD_FILE_ERROR.getDesc();
        }
        outDTO.setErrorCode(errorCode);
        outDTO.setErrorMsg(errortMsg);
        return outDTO;
    }
}
