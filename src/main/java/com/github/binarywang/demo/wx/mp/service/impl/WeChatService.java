package com.github.binarywang.demo.wx.mp.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.demo.wx.mp.Cache.MapCacheManager;
import com.github.binarywang.demo.wx.mp.common.CommonMethod;
import com.github.binarywang.demo.wx.mp.dao.WeChatDao;
import com.github.binarywang.demo.wx.mp.model.po.TrUser;
import com.github.binarywang.demo.wx.mp.service.IWeChatService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.api.impl.WxMpUserServiceImpl;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class WeChatService implements IWeChatService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WeChatDao weChatDao;

    @Autowired
    private WxMpService wxMpService;

    @Override
    public String getTip(String tips_little_tools) {
        return weChatDao.getTipByKeyWord(tips_little_tools).getTip();
    }

    @Override
    public void init() {
        MapCacheManager.initUserList(weChatDao.listUserId());
    }

    @Override
    public TrUser addUser(String openId) {
        TrUser result = null;
        try {
            WxMpUserService userService = wxMpService.getUserService();
            WxMpUser user = userService.userInfo(openId);
            result = new TrUser();
            result.setId(CommonMethod.generator());
            result.setNickName(user.getNickname());
            result.setOpenId(user.getOpenId());
            result.setIsAuto(0);
            weChatDao.addUser(result);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String saveDoit(TrUser trUser, WxMpXmlMessage wxMessage) {
        String result = "";
        Map doit = JSON.parseObject(wxMessage.getContent());
        if ( ! doit.isEmpty()){
            trUser.setDoItUser(String.valueOf(doit.get("user")));
            trUser.setDoItPassWd(String.valueOf(doit.get("passWd")));
            weChatDao.updateUser(trUser);
        }
        return null;
    }

    @Override
    public String getAutoStatus(TrUser trUser) {
        if (trUser.getIsAuto() == 0){
            return "关闭";
        } else {
            return "开启";
        }
    }

    @Override
    public void updateStatus(TrUser trUser, WxMpXmlMessage wxMessage) {
        if (wxMessage.getContent().contains("开始")){
            trUser.setIsAuto(1);
        } else {
            trUser.setIsAuto(0);
        }
        weChatDao.updateUser(trUser);
    }

    @Override
    public TrUser getUserByOpenId(String openId) {
        return weChatDao.getUser(openId);
    }

    public void getTrUsers(){
        weChatDao.listUser();
        this.logger.info("\n[{},{}]",1, 2);
    }

}
