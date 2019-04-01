package com.github.binarywang.demo.wx.mp.model.po;
import java.util.Date;
public class TrUser {
    private String id;
    private String openId;
    private String nickName;
    private String doItUser;
    private String doItPassWd;
    private Date createTime;
    private Integer isAuto;

    public Integer getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(Integer isAuto) {
        this.isAuto = isAuto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDoItUser() {
        return doItUser;
    }

    public void setDoItUser(String doItUser) {
        this.doItUser = doItUser;
    }

    public String getDoItPassWd() {
        return doItPassWd;
    }

    public void setDoItPassWd(String doItPassWd) {
        this.doItPassWd = doItPassWd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
