package com.huangjinyuanye.walletserver.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SmsInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String phone;
    private String checkCode;
    private int createtime;
    private int expiredtime;
    private int isused;
    private int usetime;
    private int appId;
    private int errCount;

    public SmsInfo() {
    }

    public SmsInfo(int id, String phone, String checkCode, int createtime, int expiredtime, int isused, int usetime, int appId, int errCount) {
        this.id = id;
        this.phone = phone;
        this.checkCode = checkCode;
        this.createtime = createtime;
        this.expiredtime = expiredtime;
        this.isused = isused;
        this.usetime = usetime;
        this.appId = appId;
        this.errCount = errCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getExpiredtime() {
        return expiredtime;
    }

    public void setExpiredtime(int expiredtime) {
        this.expiredtime = expiredtime;
    }

    public int getIsused() {
        return isused;
    }

    public void setIsused(int isused) {
        this.isused = isused;
    }

    public int getUsetime() {
        return usetime;
    }

    public void setUsetime(int usetime) {
        this.usetime = usetime;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getErrCount() {
        return errCount;
    }

    public void setErrCount(int errCount) {
        this.errCount = errCount;
    }
}
