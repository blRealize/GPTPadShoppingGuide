package com.hq.gptstore.bean;

import cn.bmob.v3.BmobObject;

public class Classification extends BmobObject {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
