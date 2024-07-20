package com.example.project.demos.web.service;

import com.example.project.demos.web.dto.uploadFileInfo.QueryUploadFileInfoListDTO;
import com.example.project.demos.web.dto.uploadFileInfo.QueryUploadFileInfoListOutDTO;
import com.example.project.demos.web.dto.uploadFileInfo.UploadFileInfoDTO;
import com.example.project.demos.web.dto.uploadFileInfo.UploadFileInfoOutDTO;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;


/**
 * 上传附件信息表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-18 13:45:31
 */
public interface UploadFileInfoService  {

    UploadFileInfoOutDTO uploadFile(String bodyDto,  MultipartFile[] files);

    QueryUploadFileInfoListOutDTO queryFiileInfoList(QueryUploadFileInfoListDTO dto);

}

