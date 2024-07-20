package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.sysUser.QueryByPageDTO;
import com.example.project.demos.web.dto.sysUser.QueryByPageOutDTO;
import com.example.project.demos.web.dto.uploadFileInfo.QueryUploadFileInfoListDTO;
import com.example.project.demos.web.dto.uploadFileInfo.QueryUploadFileInfoListOutDTO;
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
    public UploadFileInfoOutDTO uploadFile( @RequestParam("bodyDto") String bodyDto, @RequestPart(value = "files",required = false) MultipartFile[] files) {
        UploadFileInfoOutDTO outDTO = this.uploadFileInfoService.uploadFile(bodyDto,files);
        return outDTO;
    }

    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryFiileInfoList")
    @ApiOperation("查询列表(分页)")
    public QueryUploadFileInfoListOutDTO queryFiileInfoList(@RequestBody QueryUploadFileInfoListDTO dto) {
        QueryUploadFileInfoListOutDTO outDTO = this.uploadFileInfoService.queryFiileInfoList(dto);
        return outDTO;
    }


}
