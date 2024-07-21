package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.uploadFileInfo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * 上传附件信息表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-18 13:45:31
 */
public interface UploadFileInfoService  {

    UploadFileInfoOutDTO uploadFile(String bodyDto,  MultipartFile[] files);

    QueryUploadFileInfoListOutDTO queryFileInfoList(QueryUploadFileInfoListDTO dto);

    DownloadFileInfoOutDTO getFileInfoBase64Str(DownloadFileInfoDTO dto) ;

    int deleteFileByBusinessId(Long businessId);


}

