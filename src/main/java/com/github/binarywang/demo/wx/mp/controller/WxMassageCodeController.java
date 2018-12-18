package com.github.binarywang.demo.wx.mp.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.demo.wx.mp.Cache.MapCacheManager;
import com.github.binarywang.demo.wx.mp.utils.WechatUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/massage")
public class WxMassageCodeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    mode=sms (sms模式 保持不变)
//    smsrn=%SMSRN (发件人姓名 保持不变)
//    smsrf=%SMSRF (发件人号码 保持不变)
//    smsrb=%SMSRB (短信内容 保持不变)
//    smsrd=%SMSRD (发件日期 保持不变)
//    smsrt=%SMSRT (发件时间 保持不变)

    @ResponseBody
    @RequestMapping(value = "/code", method = RequestMethod.POST)
    public void menuCreate(@RequestParam("smsrf") String smsrf, @RequestBody String requestParamStr) throws WxErrorException {
        Map<String, String> massageMap = new HashMap<String, String>();
        this.logger.info("\n接收到来自短信的消息：[{},{}]",smsrf, requestParamStr);
        requestParamStr = WechatUtils.parmDecode(requestParamStr);
        this.logger.info("\n接收到来自短信的消息：[{},{}]",smsrf, requestParamStr);
        JSONObject ethCalRouteParamJson=JSON.parseObject(requestParamStr);
        String smsrd = (String)ethCalRouteParamJson.get("smsrd");
        String smsrt = (String)ethCalRouteParamJson.get("smsrt");
        String smsrb = (String)ethCalRouteParamJson.get("smsrb");
        String from = (String)ethCalRouteParamJson.get("from");
        String key = smsrd+"_"+smsrt;

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("当前时间：" + sdf.format(d));

        String user = smsrb.substring(smsrb.indexOf("[")+1, smsrb.lastIndexOf("]"));
        String code = this.getYzmFromSms(smsrb);
        String time = sdf.format(d);

        String str = time + "\n[" + user +"]:----"+ code;
        if ((smsrb.indexOf("tangrui")>=1) || (smsrb.indexOf("yangpeng")>=1)){
            if (from.equals("tr")){
                massageMap.put(key,str);
                MapCacheManager.setMapCache(massageMap);
                MapCacheManager.addCacheList(key);
            } else {
                massageMap.put(key,str);
                MapCacheManager.setypMapCache(massageMap);
                MapCacheManager.addypCacheList(key);
            }
        }
        this.logger.info("\n接收到来自短信的消息：[{}, {}, {}, {}]",smsrf, smsrd, smsrt ,smsrb);
    }

    private String getYzmFromSms(String smsBody) {
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(smsBody);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
