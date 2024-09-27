package com.example.project.demos.web.controller;

import com.example.project.demos.web.dto.uploadFileInfo.*;
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
    //RequestPart
    public UploadFileInfoOutDTO uploadFile( @RequestParam("bodyDto") String bodyDto, @RequestParam(value = "files",required = false) MultipartFile[] files) {
        UploadFileInfoOutDTO outDTO = this.uploadFileInfoService.uploadFile(bodyDto,files);
        return outDTO;
    }

    /**
     * 分页查询
     *
     * @param dto 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryFileInfoList")
    @ApiOperation("查询列表(分页)")
    public QueryFileInfoListOutDTO queryFileInfoList(@RequestBody QueryFileInfoListDTO dto) {
        QueryFileInfoListOutDTO outDTO = this.uploadFileInfoService.queryFileInfoList(dto);
        return outDTO;
    }

    /**
     * 编辑页面查询
     *
     * @param dto 筛选条件
     * @return 查询结果
     */
    @PostMapping("/queryFileInfoEditList")
    @ApiOperation("编辑页面查询列表(不分页)")
    public QueryFileInfoEditListOutDTO queryFileInfoEditList(@RequestBody QueryFileInfoEditListDTO dto) {
        QueryFileInfoEditListOutDTO outDTO = this.uploadFileInfoService.queryFileInfoEditList(dto);
        return outDTO;
    }

    /**
     * 文件下载
     *
     * @param dto 筛选条件
     * @return 查询结果
     */
    @PostMapping("/downloadFileInfo")
    @ApiOperation("文件下载")
    public DownloadFileInfoOutDTO downloadFileInfo(@RequestBody DownloadFileInfoDTO dto) {
        DownloadFileInfoOutDTO outDTO = this.uploadFileInfoService.getFileInfoBase64Str(dto);
        return outDTO;
    }


}
