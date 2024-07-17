package com.example.project.demos.web.service.impl;

import com.example.project.demos.web.service.CommomTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Slf4j
@Service("commomTestService")
public class CommomTestServiceImpl implements CommomTestService {
    @Override
    public String getArcfondNO(String jkid, String companyname) {
        log.info("调用档案系统的获取全宗接口");
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("jkid",jkid);
        param.add("companyname",companyname);
        Object object = restTemplate.postForObject("http://10.72.10.167:8090/fteas/arcfiling/GetArcfondNO",param,Object.class);
        log.info(object.toString());
        //解析返回值
        return null;
    }

    @Override
    public String commonfilingFile(String arcfondno, String jkid, String fileurl, String origfile) {
        log.info("调用档案系统的上传附件接口");
        String eepFile="";
        RestTemplate restTemplate = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(new File(eepFile));
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("arcfondno",arcfondno);
        param.add("jkid",jkid);
        param.add("fileurl",fileurl);
        param.add("origfile",resource);
        Object object = restTemplate.postForObject("",param,Object.class);
        log.info(object.toString());
        //解析返回值
        return null;
    }

    @Override
    public String commonfilingData(String arcfondno, String jkid, String eepinfo) {
        log.info("调用档案系统的上传目录接口");
        String eepFile="";
        RestTemplate restTemplate = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(new File(eepFile));
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("arcfondno",arcfondno);
        param.add("jkid",jkid);
        param.add("eppinfo",resource);
        Object object = restTemplate.postForObject("",param,Object.class);
        log.info(object.toString());
        //解析返回值
        return null;
    }

    @Override
    public String CheckOrigFile(String arcfondno, String jkid, String fileUrl) {
        log.info("调用档案系统的附件检测接口");
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String,Object> param = new LinkedMultiValueMap<>();
        param.add("arcfondno",arcfondno);
        param.add("jkid",jkid);
        param.add("fileUrl",fileUrl);
        Object object = restTemplate.postForObject("",param,Object.class);
        log.info(object.toString());
        //解析返回值
        return null;
    }

}
