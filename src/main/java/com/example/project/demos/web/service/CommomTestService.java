package com.example.project.demos.web.service;

public interface  CommomTestService {
    /**
     * jkid:接口编号，companyname:公司名称
     * @return
     * 档案系统的fteas/arcfiling/GetArcfondNO 获取全宗接口
     */
    String getArcfondNO(String jkid,String companyname);

    /**
     *
     arcfondno:全宗号
     Jkid:接口编码
     Fileurl:文件存储名
     origfile:需要上传的附件
     * @return
     * 档案系统的fteas/arcfiling/CommonfilingFile 上传附件接口
     */
    String commonfilingFile(String arcfondno,String jkid,String fileurl,String origfile);

    /**
     * arcfondno:全宗号
     * Jkid:接口编码
     * 	Eepinfo:目录组成的eep报文
     * 	档案系统的 fteas/arcfiling/CommonfilingData  上传目录接口
     */
    String commonfilingData (String arcfondno,String jkid,String eepinfo);

    /**
     * arcfondno:全宗号
     * jkid:接口编码
     * fileurl:文件存储名称
     * @return
     * 档案系统的fteas/arcfiling/CheckOrigFile   附件检测
     */
    String CheckOrigFile(String arcfondno,String jkid,String fileUrl);

}
