package com.hq.gptstore.bean;

import cn.bmob.v3.BmobObject;

public class Commodity extends BmobObject {

    private String name;
    private String introduce;
    private String imgUrl;

    private String cpu;

    private String runningMemory;

    private String storage;

    private String price;

    private Classification classification;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRunningMemory() {
        return runningMemory;
    }

    public void setRunningMemory(String runningMemory) {
        this.runningMemory = runningMemory;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(Classification classification) {
        this.classification = classification;
    }
}
