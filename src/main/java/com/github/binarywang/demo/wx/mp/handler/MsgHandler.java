package com.github.binarywang.demo.wx.mp.handler;

import com.github.binarywang.demo.wx.mp.Cache.MapCacheManager;
import com.github.binarywang.demo.wx.mp.builder.TextBuilder;
import com.github.binarywang.demo.wx.mp.common.StringConstant;
import com.github.binarywang.demo.wx.mp.model.po.TrUser;
import com.github.binarywang.demo.wx.mp.service.IWeChatService;
import com.github.binarywang.demo.wx.mp.utils.WechatUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Autowired
    private IWeChatService weChatService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {


        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.getKefuService().kfOnlineList()
                .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        TrUser trUser = null;
        if (wxMessage != null){
            if ( ! MapCacheManager.listUserId().contains(wxMessage.getOpenId())){
                trUser = weChatService.addUser(wxMessage.getOpenId());
            } else {
                trUser = weChatService.getUserByOpenId(wxMessage.getOpenId());
            }
        }
        String content = "";
        if (StringUtils.startsWithAny(wxMessage.getContent(), "小工具")){
            content = weChatService.getTip(StringConstant.Tips_Little_Tools);
        } else if (StringUtils.startsWithAny(wxMessage.getContent(), "同意")) {
            content = weChatService.getTip(StringConstant.Tips_Agree);
        } else if (wxMessage.getContent().contains("passWd") && wxMessage.getContent().contains("user")) {
            weChatService.saveDoit(trUser ,wxMessage);
            content = weChatService.getTip(StringConstant.Tips_Status);
            content = content.replace("status",weChatService.getAutoStatus(trUser));
        } else if (StringUtils.startsWithAny(wxMessage.getContent(), "开始自动报工")||StringUtils.startsWithAny(wxMessage.getContent(), "停止自动报工")){
            weChatService.updateStatus(trUser,wxMessage);
        } else {
                //TODO 组装验证码verify回复消息
                content = "收到信息内容：" + WechatUtils.toJson(wxMessage);
                Map<String, String> map  = MapCacheManager.getMapCache();
                List<String> cacheList = MapCacheManager.getListCache();
                Map<String, String> ypmap  = MapCacheManager.getypMapCache();
                List<String> ypcacheList = MapCacheManager.getypListCache();
                String temp1 = cacheList.get(cacheList.size()-1);
                String temp2 = ypcacheList.get(ypcacheList.size()-1);
                String value1 = "暂时没有";
                String value2 = "暂时没有";

                if (!temp1.isEmpty()){
                    value1 = map.get(temp1);
                }

                if (!temp2.isEmpty()){
                    value2 = ypmap.get(temp2);
                }
                content = value1+"\n" +value2+"\n" + weChatService.getTip(StringConstant.Tips_News);
        }
        this.logger.info("\n返回的消息为：[{}]",content);
        return new TextBuilder().build(content, wxMessage, weixinService);

    }

}
