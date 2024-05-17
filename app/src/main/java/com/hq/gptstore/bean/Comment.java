package com.hq.gptstore.bean;

import com.example.login.UserInfo;

import cn.bmob.v3.BmobObject;

public class Comment extends BmobObject {

    private UserInfo sender;

    private String content;

    private Commodity commodity;

    public UserInfo getSender() {
        return sender;
    }

    public void setSender(UserInfo sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }
}
