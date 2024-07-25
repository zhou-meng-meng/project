package com.example.project.demos.web.service.impl;

import cn.hutool.core.util.IdUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.service.CommomTestService;
import com.example.project.demos.web.utils.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service("commomTestService")
public class CommomTestServiceImpl implements CommomTestService {
    @Override
    public String getArcfondNO(String jkid, String companyname) throws IOException {

        log.info("调用档案系统的获取全宗接口");
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("jkid",jkid);
        param.add("companyname",companyname);
        Object object = restTemplate.postForObject("http://10.72.10.167:8090/fteas/arcfiling/GetArcfondNO",param,Object.class);
        log.info(object.toString());
        Map map = (Map)object;
        String success = map.get("success").toString();
        String arcfondno = "";
        if("true".equals(success)){
            log.info("获取全宗接口成功");
            Map map1 = (Map)map.get("obj");
            arcfondno = map1.get("arcfondno").toString();
        }
        String uuid = IdUtil.simpleUUID();
        log.info("开始调用上传附件接口");
        this.commonfilingFile(arcfondno,jkid,uuid+".docx","",uuid);
        //解析返回值
        return null;
    }

    @Override
    public String commonfilingFile(String arcfondno, String jkid, String fileurl, String origfile,String uuid) throws IOException {
        log.info("调用档案系统的上传附件接口");
        String eepFile="D:\\用户操作手册.docx";
        RestTemplate restTemplate = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(new File(eepFile));
        long fileSize = resource.getFile().length();
        BigDecimal size = new BigDecimal(fileSize);
        String fileMD5 = DataUtils.getFileMD5(eepFile);
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("arcfondno",arcfondno);
        param.add("jkid",jkid);
        param.add("fileUrl",fileurl);
        param.add("origfile",resource);
        Object object = restTemplate.postForObject("http://10.72.10.167:8090/fteas/arcfiling/CommonfilingFile",param,Object.class);
        log.info(object.toString());
        this.commonfilingData(arcfondno,jkid,"",uuid,size, fileMD5);
        //解析返回值
        return null;
    }

    @Override
    public String commonfilingData(String arcfondno, String jkid, String eepinfo,String uuid,BigDecimal size,String fileMD5) throws IOException {
        log.info("调用档案系统的上传目录接口");
        String eepFile="";
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.YYYYMMDD);
        SimpleDateFormat sdf1 = new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS);
        String date = sdf.format(new Date());
        //生成文件的内容
        StringBuffer bodySb = new StringBuffer();
        bodySb.append("<ARCHIVEDATA>")
                /*****ARCHIVEDATA部分 *******/
                //OA业务编号
                .append("<EXSYSTEMPK>").append(uuid).append("</EXSYSTEMPK>")
                //全宗代码
                .append("<ARCFONDNO>").append(arcfondno).append("</ARCFONDNO>")
                //题名
                .append("<HEADING>").append("合同").append("</HEADING>")
                //成文日期
                .append("<COMPLETEDATE>").append(date).append("</COMPLETEDATE>")
                //年度
                .append("<YEAR>").append(date.substring(0,4)).append("</YEAR>")
                //责任者
                .append("<AUTHOR>").append("责任者").append("</AUTHOR>")
                //保管期限  1-永久，2-长期，3-短期，如没有默认填写1
                .append("<TIMELIMIT>1</TIMELIMIT>")
                //密级  3-内部级，4-秘密级，如没有默认填写3
                .append("<SECURITYCLASS>3</SECURITYCLASS>")
                //推动类型
                .append("<SENDTYPE>收文</SENDTYPE>")
                //推动人
                .append("<SENDPERSON>财通</SENDPERSON>")
                //推送人部门
                .append("<SENDDEP>财通集团</SENDDEP>")
                //推送时间
                .append("<UPDATETIME>").append(sdf1.format(new Date())).append("</UPDATETIME>")
                //数据来源  默认“OA系统”
                .append("<DATASRC>OA系统</DATASRC>")
                //载体类型
                .append("<CARRIERTYPE>10</CARRIERTYPE>")
                /*******  ARCHIVEFILE部分 ******/
                .append("<ARCHIVEFILE>")
                //编号
                .append("<REGNO>").append(uuid).append("</REGNO>")
                //全宗代码
                .append("<ARCFONDNO>").append(arcfondno).append("</ARCFONDNO>")
                //原文件名
                .append("<ORIGDOCNAME>用户操作手册.docx").append("</ORIGDOCNAME>")
                //实际文件名 uuid.后缀
                .append("<FILEURL>").append(uuid).append(".docx</FILEURL>")
                //文件目录  CTOA01\全宗号\推送年度
                .append("<FILEADDR>CTOA01").append("\\").append(arcfondno).append("\\").append(date.substring(0,4)).append("</FILEADDR>")
                //关联编号   与ARCHIVEDATA表的REGNO一致
                .append("<POINTERNO>").append(uuid).append("</POINTERNO>")
                //排序序号  多个附件情况下的排序号，从1递增，单个附件时默认1
                .append("<SORTID>1").append("</SORTID>")
                // 文件大小  单位：字节（Byte）
                .append("<FILESIZE>").append(size).append("</FILESIZE>")
                .append("<FILEMD5>").append(fileMD5).append("</FILEMD5>")
                .append("</ARCHIVEFILE>")
                /*******  ARCHIVEFILE部分结束 ******/
                .append("</ARCHIVEDATA>");

        //String bodyStr = "<ARCHIVEDATA><REGNO>"+uuid+"</REGNO><ARCFONDNO>"+arcfondno+"</ARCFONDNO><HEADING>合同</HEADING><EXSYSTEMPK>"+uuid+"</EXSYSTEMPK><ARCHIVEFILE><Origdocname>用户操作手册.docx</Origdocname><FileUrl>"+uuid+".doc</FileUrl></ARCHIVEFILE></ARCHIVEDATA>";
        log.info("将body的值进行base64加密");
        String bbodyStr = bodySb.toString();
        String body64 = Base64.getEncoder().encodeToString(bbodyStr.getBytes());
        log.info("将body的值进行MD5加密");
        String bodyMD5 = DigestUtils.md5DigestAsHex(body64.getBytes());
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                .append("<DATA>").append("<HEAD>")
                .append("<ARCFONDNO>").append(arcfondno).append("</ARCFONDNO>")
                .append("<APPID>CTOA01</APPID>")
                .append("<EEPID>").append(uuid).append("</EEPID>")
                .append("<FILINGWAY>I</FILINGWAY></HEAD><BODY>" );
        log.info("拼接加密文档");
        sb.append(body64).append("</BODY><SIGNATURE>").append(bodyMD5).append("</SIGNATURE></DATA>");
        log.info("写入文件");
        FileWriter writer = new FileWriter("send.eep");
        String content = sb.toString();
        writer.write(content);
        writer.close();
        RestTemplate restTemplate = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(new File("send.eep"));
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("arcfondno",arcfondno);
        param.add("jkid",jkid);
        param.add("eepinfo",resource);
        Object object = restTemplate.postForObject("http://10.72.10.167:8090/fteas/arcfiling/CommonfilingData",param,Object.class);
        log.info(object.toString());
        //解析返回值
        return null;
    }


}
