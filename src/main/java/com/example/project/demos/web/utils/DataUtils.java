package com.example.project.demos.web.utils;

import cn.hutool.core.util.ObjectUtil;
import com.example.project.demos.web.constant.Constants;
import com.example.project.demos.web.dto.list.CustomerPayDetailInfo;
import com.example.project.demos.web.dto.list.CustomerPayDetailInfoForExport;
import com.example.project.demos.web.dto.sysUser.UserLoginOutDTO;
import com.example.project.demos.web.enums.RoleAuthorityTypeEnums;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    /**
     * 格式化单据号
     * @param list
     * @return
     */
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

    /**
     * 测试使用
     * @param filePath
     * @return
     */
    public static String getFileMD5(String filePath){
        String md5 ="";
        BigInteger bi = null;
        try {
            byte[] buffer = new byte[8192];
            int len = 0;
            MessageDigest md = MessageDigest.getInstance("MD5");
            File f = new File(filePath);
            FileInputStream fis = new FileInputStream(f);
            while ((len = fis.read(buffer)) != -1) {
                md.update(buffer, 0, len);
            }
            fis.close();
            byte[] b = md.digest();
            bi = new BigInteger(1, b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        md5 = bi.toString();
        return md5;
    }

    public static String formatCode(String code){
        String prefix = code.substring(0,1);
        String num = code.substring(1);
        int codeInt = Integer.parseInt(num) +1;
        StringBuffer sb = new StringBuffer();
        sb.append(prefix).append(codeInt);
        return sb.toString();
    }

    /**
     * 判断是否有单价权限
     * @param userLoginOutDTO
     * @return
     */
    public static boolean getIsPrice(UserLoginOutDTO userLoginOutDTO){
        boolean isPrice = false;
        List<String> typeList = userLoginOutDTO.getAuthorityType();
        if(typeList.contains(RoleAuthorityTypeEnums.ROLE_AUTHORITY_TYPE_PRICE.getCode())){
            log.info("具有单价权限,不处理");
            isPrice = true;
        }else{
            log.info("没有单价权限，将单价和总金额置为0");
            isPrice = false;
        }
        return isPrice;
    }

    /**
     * 往来账首页  导出全部的往来账备份  只需要导出的字段 并格式化时间字段
     * @param infoList
     * @return
     */
    public static List<CustomerPayDetailInfoForExport> formatForBakExport(List<CustomerPayDetailInfo> infoList,String customerName) throws Exception {
        List<CustomerPayDetailInfoForExport> list = new ArrayList<>();
        try{
            //合计物料数量
            BigDecimal sumCount = new BigDecimal("0");
            //合计单价
            BigDecimal sumUnitPrice = new BigDecimal("0");
            //合计物料金额
            BigDecimal sumMaterialAmt = new BigDecimal("0");

            //合计退回数量
            BigDecimal sumReturnCount = new BigDecimal("0");
            //合计退回单价
            BigDecimal sumReturnUnitPrice = new BigDecimal("0");
            //合计退回金额
            BigDecimal sumReturnAmt = new BigDecimal("0");

            //合计税金
            BigDecimal sumTaxAmt = new BigDecimal("0");
            //合计其他金额
            BigDecimal sumOtherAmt = new BigDecimal("0");
            //合计打款金额
            BigDecimal sumPayAmt = new BigDecimal("0");
            //合计运费
            BigDecimal sumFreightAmt = new BigDecimal("0");

            for(CustomerPayDetailInfo info : infoList){
                CustomerPayDetailInfoForExport export = new CustomerPayDetailInfoForExport();
                if(ObjectUtil.isNotNull(info.getMaterialDate())){
                    export.setMaterialDate(DateUtils.parseDateToStr(Constants.YYYY_MM_DD,info.getMaterialDate()));
                }
                export.setFactoryName(info.getFactoryName());
                export.setBillNo(info.getBillNo());
                export.setCustomerCode(info.getCustomerCode());
                export.setCustomerName(customerName);
                export.setMaterialCode(info.getMaterialCode());
                export.setMaterialName(info.getMaterialName());
                export.setModelName(info.getModelName());
                export.setUnitName(info.getUnitName());
                export.setUnitPrice(info.getUnitPrice());
                export.setMaterialCount(info.getMaterialCount());
                export.setMaterialBalance(info.getMaterialBalance());
                export.setReturnBalance(info.getReturnBalance());
                export.setReturnUnitPrice(info.getReturnUnitPrice());
                export.setReturnCount(info.getReturnCount());
                export.setTaxBalance(info.getTaxBalance());
                export.setOtherBalance(info.getOtherBalance());
                export.setPayBalance(info.getPayBalance());
                export.setBookBalance(info.getBookBalance());
                export.setFreight(info.getFreight());
                export.setOperatorByName(info.getOperatorByName());
                export.setRemark(info.getRemark());
                export.setCreateByName(info.getCreateByName());
                if(ObjectUtil.isNotNull(info.getCreateTime())){
                    export.setCreateTime(DateUtils.parseDateToStr(Constants.YYYY_MM_DD_HH_MM_SS,info.getCreateTime()));
                }
                export.setUpdateByName(info.getUpdateByName());
                if(ObjectUtil.isNotNull(info.getUpdateTime())){
                    export.setUpdateTime(DateUtils.parseDateToStr(Constants.YYYY_MM_DD_HH_MM_SS,info.getUpdateTime()));
                }
                //导出的list集合
                list.add(export);

                //开始处理合计字段
                BigDecimal count = info.getMaterialCount();
                if(count == null){
                    count = new BigDecimal("0");
                }
                BigDecimal unitPrice = info.getUnitPrice();
                if(unitPrice == null){
                    unitPrice = new BigDecimal("0");
                }
                BigDecimal materialAmt = info.getMaterialBalance();
                if(materialAmt == null){
                    materialAmt = new BigDecimal("0");
                }

                BigDecimal returnCount = info.getReturnCount();
                if(returnCount == null){
                    returnCount = new BigDecimal("0");
                }
                BigDecimal returnUnitPrice = info.getReturnUnitPrice();
                if(returnUnitPrice == null){
                    returnUnitPrice = new BigDecimal("0");
                }
                BigDecimal returnAmt = info.getReturnBalance();
                if(returnAmt == null){
                    returnAmt = new BigDecimal("0");
                }

                BigDecimal taxAmt = info.getTaxBalance();
                if(taxAmt == null){
                    taxAmt = new BigDecimal("0");
                }
                BigDecimal otherAmt = info.getOtherBalance();
                if(otherAmt == null){
                    otherAmt = new BigDecimal("0");
                }
                BigDecimal payAmt = info.getPayBalance();
                if(payAmt == null){
                    payAmt = new BigDecimal("0");
                }
                BigDecimal freightAmt = info.getFreight();
                if(freightAmt == null){
                    freightAmt = new BigDecimal("0");
                }

                //销售/来料金额
                sumCount = sumCount.add(count);
                sumUnitPrice = sumUnitPrice.add(unitPrice);
                sumMaterialAmt = sumMaterialAmt.add(materialAmt);
                //退回金额
                sumReturnCount = sumReturnCount.add(returnCount);
                sumReturnUnitPrice = sumReturnUnitPrice.add(returnUnitPrice);
                sumReturnAmt = sumReturnAmt.add(returnAmt);

                //税金、其他金额 打款金额  运费
                sumTaxAmt = sumTaxAmt.add(taxAmt);
                sumOtherAmt = sumOtherAmt.add(otherAmt);
                sumPayAmt = sumPayAmt.add(payAmt);
                sumFreightAmt = sumFreightAmt.add(freightAmt);

            }
            //添加最后一行合计列
            CustomerPayDetailInfoForExport sumInfo = new CustomerPayDetailInfoForExport();
            sumInfo.setUnitName("合计:");
            sumInfo.setMaterialCount(sumCount);
            sumInfo.setUnitPrice(sumUnitPrice);
            sumInfo.setMaterialBalance(sumMaterialAmt);
            sumInfo.setReturnCount(sumReturnCount);
            sumInfo.setReturnUnitPrice(sumReturnUnitPrice);
            sumInfo.setReturnBalance(sumReturnAmt);
            sumInfo.setTaxBalance(sumTaxAmt);
            sumInfo.setOtherBalance(sumOtherAmt);
            sumInfo.setPayBalance(sumPayAmt);
            sumInfo.setFreight(sumFreightAmt);
            list.add(sumInfo);
        }catch (Exception e){
            log.info("异常:"+e.getMessage());
            throw new Exception(e.getMessage());
        }
        return list;
    }


    /**
     * 导出最后一行增加合计列  往来账明细列表 导出单个客户 使用
     * @param list
     * @return
     */
    public static List<CustomerPayDetailInfo> formatSumObjectForExport(List<CustomerPayDetailInfo> list){
        //合计物料数量
        BigDecimal sumCount = new BigDecimal("0");
        //合计单价
        BigDecimal sumUnitPrice = new BigDecimal("0");
        //合计物料金额
        BigDecimal sumMaterialAmt = new BigDecimal("0");

        //合计退回数量
        BigDecimal sumReturnCount = new BigDecimal("0");
        //合计退回单价
        BigDecimal sumReturnUnitPrice = new BigDecimal("0");
        //合计退回金额
        BigDecimal sumReturnAmt = new BigDecimal("0");

        //合计税金
        BigDecimal sumTaxAmt = new BigDecimal("0");
        //合计其他金额
        BigDecimal sumOtherAmt = new BigDecimal("0");
        //合计打款金额
        BigDecimal sumPayAmt = new BigDecimal("0");
        //合计运费
        BigDecimal sumFreightAmt = new BigDecimal("0");

        for(CustomerPayDetailInfo info: list){
            BigDecimal count = info.getMaterialCount();
            if(count == null){
                count = new BigDecimal("0");
            }
            BigDecimal unitPrice = info.getUnitPrice();
            if(unitPrice == null){
                unitPrice = new BigDecimal("0");
            }
            BigDecimal materialAmt = info.getMaterialBalance();
            if(materialAmt == null){
                materialAmt = new BigDecimal("0");
            }

            BigDecimal returnCount = info.getReturnCount();
            if(returnCount == null){
                returnCount = new BigDecimal("0");
            }
            BigDecimal returnUnitPrice = info.getReturnUnitPrice();
            if(returnUnitPrice == null){
                returnUnitPrice = new BigDecimal("0");
            }
            BigDecimal returnAmt = info.getReturnBalance();
            if(returnAmt == null){
                returnAmt = new BigDecimal("0");
            }

            BigDecimal taxAmt = info.getTaxBalance();
            if(taxAmt == null){
                taxAmt = new BigDecimal("0");
            }
            BigDecimal otherAmt = info.getOtherBalance();
            if(otherAmt == null){
                otherAmt = new BigDecimal("0");
            }
            BigDecimal payAmt = info.getPayBalance();
            if(payAmt == null){
                payAmt = new BigDecimal("0");
            }
            BigDecimal freightAmt = info.getFreight();
            if(freightAmt == null){
                freightAmt = new BigDecimal("0");
            }

            //销售/来料金额
            sumCount = sumCount.add(count);
            sumUnitPrice = sumUnitPrice.add(unitPrice);
            sumMaterialAmt = sumMaterialAmt.add(materialAmt);
            //退回金额
            sumReturnCount = sumReturnCount.add(returnCount);
            sumReturnUnitPrice = sumReturnUnitPrice.add(returnUnitPrice);
            sumReturnAmt = sumReturnAmt.add(returnAmt);

            //税金、其他金额 打款金额  运费
            sumTaxAmt = sumTaxAmt.add(taxAmt);
            sumOtherAmt = sumOtherAmt.add(otherAmt);
            sumPayAmt = sumPayAmt.add(payAmt);
            sumFreightAmt = sumFreightAmt.add(freightAmt);
        }
        CustomerPayDetailInfo info = new CustomerPayDetailInfo();
        info.setUnitName("合计:");
        info.setMaterialCount(sumCount);
        info.setUnitPrice(sumUnitPrice);
        info.setMaterialBalance(sumMaterialAmt);
        info.setReturnCount(sumReturnCount);
        info.setReturnUnitPrice(sumReturnUnitPrice);
        info.setReturnBalance(sumReturnAmt);
        info.setTaxBalance(sumTaxAmt);
        info.setOtherBalance(sumOtherAmt);
        info.setPayBalance(sumPayAmt);
        info.setFreight(sumFreightAmt);
        list.add(info);
        return list;
    }





}
