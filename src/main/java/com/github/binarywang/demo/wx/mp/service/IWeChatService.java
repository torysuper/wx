package com.github.binarywang.demo.wx.mp.service;

import com.github.binarywang.demo.wx.mp.model.po.TrUser;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

public interface IWeChatService {

    String getTip(String tips_little_tools);

    void init();

    TrUser addUser(String openId);

    TrUser getUserByOpenId(String openId);

    String saveDoit(TrUser trUser, WxMpXmlMessage wxMessage);

    String getAutoStatus(TrUser trUser);

    void updateStatus(TrUser trUser, WxMpXmlMessage wxMessage);
}
