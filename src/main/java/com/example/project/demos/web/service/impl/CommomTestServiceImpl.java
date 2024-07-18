package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.service.CommomTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Base64;

@Slf4j
@Service("commomTestService")
public class CommomTestServiceImpl implements CommomTestService {
    @Override
    public String getArcfondNO(String jkid, String companyname) throws IOException {
        /*BufferedReader reader = new BufferedReader(new FileReader("/templates/test.eep"));
        StringBuilder template = new StringBuilder();*/

        log.info("调用档案系统的获取全宗接口");
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("jkid",jkid);
        param.add("companyname",companyname);
        Object object = restTemplate.postForObject("http://10.72.10.167:8090/fteas/arcfiling/GetArcfondNO",param,Object.class);
        log.info(object.toString());
        log.info("开始调用上传附件接口");
        this.commonfilingFile("551-1",jkid,"用户操作手册.docx","");
        //解析返回值
        return null;
    }

    @Override
    public String commonfilingFile(String arcfondno, String jkid, String fileurl, String origfile) throws IOException {
        log.info("调用档案系统的上传附件接口");
        String eepFile="D:\\用户操作手册.docx";
        RestTemplate restTemplate = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(new File(eepFile));
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("arcfondno",arcfondno);
        param.add("jkid",jkid);
        param.add("fileUrl",fileurl);
        param.add("origfile",resource);
        Object object = restTemplate.postForObject("http://10.72.10.167:8090/fteas/arcfiling/CommonfilingFile",param,Object.class);
        log.info(object.toString());
        this.commonfilingData(arcfondno,jkid,"");
        //解析返回值
        return null;
    }

    @Override
    public String commonfilingData(String arcfondno, String jkid, String eepinfo) throws IOException {
        log.info("调用档案系统的上传目录接口");
        String eepFile="";
        //生成文件的内容
        String bodyStr = "<ARCHIVEDATA><REGNO>件01</REGNO><ARCFONDNO>00666</ARCFONDNO><HEADING>件01</HEADING><EXSYSTEMPK>件001</EXSYSTEMPK><ARCHIVEFILE><Origdocname>附件1-1.txt</Origdocname><FileUrl>附件1-11.txt</FileUrl></ARCHIVEFILE>" +
                "<ARCHIVEFILE> " +
                "<Origdocname>附件1-2.txt</Origdocname>" +
                "<FileUrl>附件1-22.txt</FileUrl> " +
                "</ARCHIVEFILE> " +
                "<ARCHIVEA>" +
                "<NOTE>附件信息A01</NOTE> " +
                "</ARCHIVEA>" +
                "<ARCHIVEB>" +
                "<NOTE>附件信息B01</NOTE>" +
                "</ARCHIVEB>" +
                "</ARCHIVEDATA>";
        log.info("将body的值进行base64加密");
        String body64 = Base64.getEncoder().encodeToString(bodyStr.getBytes());
        log.info("将body的值进行MD5加密");
        String bodyMD5 = DigestUtils.md5DigestAsHex(bodyStr.getBytes());
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<DATA>" +
                "<HEAD>" +
                "<ARCFONDNO>00666</ARCFONDNO>" +
                "<APPID>T03</APPID>" +
                "<EEPID>EEP01</EEPID>" +
                "<FILINGWAY>I</FILINGWAY>  " +
                "</HEAD>" +
                "<BODY>");
        log.info("拼接加密文档");
        sb.append(body64).append("</BODY><SIGNATURE>").append(bodyMD5).append("</SIGNATURE></DATA>");
        log.info("写入文件");
        FileWriter writer = null;
        writer = new FileWriter("send.eep");
        writer.write(sb.toString());
        RestTemplate restTemplate = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(new File("send.eep"));
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("arcfondno",arcfondno);
        param.add("jkid",jkid);
        param.add("eppinfo",resource);
        Object object = restTemplate.postForObject("http://10.72.10.167:8090/fteas/arcfiling/CommonfilingData",param,Object.class);
        log.info(object.toString());
        //解析返回值
        return null;
    }


}
