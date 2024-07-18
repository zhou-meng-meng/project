package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.uploadFileInfo.UploadFileInfoDTO;
import com.example.project.demos.web.dto.uploadFileInfo.UploadFileInfoOutDTO;
import com.example.project.demos.web.service.UploadFileInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传附件信息表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2024-07-18 13:45:31
 */
@RestController
@RequestMapping("uploadFileInfo")
@Api(tags="附件校验和上传")
public class UploadFileInfoController {
    @Autowired
    private UploadFileInfoService uploadFileInfoService;

    @ApiOperation(value = "附件校验和上传")
    @PostMapping("/uploadFile")
    public UploadFileInfoOutDTO uploadFile( @RequestBody UploadFileInfoDTO dto, @RequestParam(value = "files",required = false) MultipartFile[] files) {
        UploadFileInfoOutDTO outDTO = this.uploadFileInfoService.uploadFile(dto,files);
        return outDTO;
    }
}
