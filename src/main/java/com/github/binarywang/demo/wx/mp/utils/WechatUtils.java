package com.github.binarywang.demo.wx.mp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
public class WechatUtils {
    public static String toJson(Object obj) {
        Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();
        return gson.toJson(obj);
    }

    //与接口配置信息中的ToKen一致
    private static String token = "tory"; //该Token值为自己定义

    public static boolean checkSignature(String timestamp,String nonce ,String signature){
        // 1. 将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = new String[] { token, timestamp, nonce };
        Arrays.sort(arr);

        // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder content = new StringBuilder();
        for(int i=0; i<arr.length; i++){
            content.append(arr[i]);
        }
        MessageDigest messageDigest = null;
        String tmpStr = null;
        try {
            // 将三个参数字符串拼接成一个字符串进行sha1加密
            messageDigest = MessageDigest.getInstance("SHA-1");
            byte[] digest = messageDigest.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        // 3. 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray){
        String strDigest = "";
        for(int i=0; i < byteArray.length; i++){
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte){
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F'};;
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    public static String parmDecode(String input) {
        // deal with the input
        String output = "";
        try {
            input = URLDecoder.decode(input, "UTF-8");
            if (!input.isEmpty()) {
                int startIndexOf = input.indexOf("{");
                int endIndexOf = input.lastIndexOf("}");
                if ((startIndexOf != -1) && (endIndexOf != -1) && (endIndexOf > startIndexOf)) {
                    output = input.substring(startIndexOf, endIndexOf + 1);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return output;
    }
}
