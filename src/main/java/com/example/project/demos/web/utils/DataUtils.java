package com.example.project.demos.web.utils;

import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import lombok.extern.slf4j.Slf4j;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 处理数据的工具类
 */
@Slf4j
public class DataUtils {

    /**
     * 格式化单号前缀  日期-厂区-进
     * 单据号格式为 日期-厂区-当天本厂区号码排序（区分进货和出货）
     * @param user
     * @return
     */
    public static String formatBillNoPrefix(UserLoginOutDTO user,String type){
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.YYYYMMDD);
        sb.append(sdf.format(new Date())).append(Constants.SHORT_TERM_LINE).append(user.getDeptName()).append(Constants.SHORT_TERM_LINE).append(type);
        return sb.toString();
    }

    public static String formatBillNo(List<String> list){
        StringBuffer sb = new StringBuffer();
        try{
            String billNo = list.get(0);
            String prefix = billNo.substring(0,billNo.length()-4);
            log.info("现有最新的billNo:"+billNo);
            String num = billNo.substring(billNo.length()-4,billNo.length());
            int no = Integer.parseInt(num) +1;
            if(no > 999){
                sb.append(prefix).append(no);
            }else if(no > 99){
                sb.append(prefix).append("0").append(no);
            }else if(no > 9){
                sb.append(prefix).append("00").append(no);
            }else{
                sb.append(prefix).append("000").append(no);
            }
            log.info("最新的billNo:"+sb.toString());
        }catch (Exception e){
            log.info("格式化单据号失败:"+e.getMessage());
        }
        return sb.toString();
    }

}
