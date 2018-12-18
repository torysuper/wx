package com.github.binarywang.demo.wx.mp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TEST {

//    public static void main(String[] args) {
//        String smsrb = "[dwyangpeng6]，短信口令399612，用于您本次登录广东4A网络安全管控平台。请在5分钟内使用";
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        System.out.println("当前时间：" + sdf.format(d));
//
//        String user = smsrb.substring(smsrb.indexOf("[")+1, smsrb.lastIndexOf("]"));
//        String code = getYzmFromSms(smsrb);
//        String time = sdf.format(d);
//
//        String str = time + "\n" + user +":["+ code +"]";
//        System.out.println("str：" + str);
//    }
//
//    private static String getYzmFromSms(String smsBody) {
//        Pattern pattern = Pattern.compile("\\d{6}");
//        Matcher matcher = pattern.matcher(smsBody);
//        if (matcher.find()) {
//            return matcher.group();
//        }
//        return null;
//    }

}
