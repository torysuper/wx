package com.github.binarywang.demo.wx.mp.dao;

import com.github.binarywang.demo.wx.mp.model.po.Tips;
import com.github.binarywang.demo.wx.mp.model.po.TrUser;
import com.github.binarywang.demo.wx.mp.model.po.Verify;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
//@Mapper
public interface WeChatDao {

    TrUser getUser(String oppenId);
    List<TrUser> listUser();
    List<String> listUserId();
    Tips getTipByKeyWord(String keyword);
    int updateUser(TrUser trUser);
    int addUser(TrUser trUser);
    Verify getVerify(String from);
    int addVerify(Verify verify);
}
