package com.huangjinyuanye.walletserver.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    //昵称
    private String name;

    //微信id
    private String wxid;

    //手机号
    private String phone;

    //地区
    private String area;

    //密码
    private String passwd;

    //邮箱
    private String email;

    //上次登陆
    private int lastlogin;

    //注册时间
    private int register;

    //用户状态
    private int status;

    //头像
    private String headurl;

    //性别
    private int gender;

    public User() {
    }

    public User(int id, String name, String wxid, String phone, String area, String passwd, String email, int lastlogin, int register, int status, String headurl, int gender) {
        this.id = id;
        this.name = name;
        this.wxid = wxid;
        this.phone = phone;
        this.area = area;
        this.passwd = passwd;
        this.email = email;
        this.lastlogin = lastlogin;
        this.register = register;
        this.status = status;
        this.headurl = headurl;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(int lastlogin) {
        this.lastlogin = lastlogin;
    }

    public int getRegister() {
        return register;
    }

    public void setRegister(int register) {
        this.register = register;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wxid='" + wxid + '\'' +
                ", phone='" + phone + '\'' +
                ", area='" + area + '\'' +
                ", passwd='" + passwd + '\'' +
                ", email='" + email + '\'' +
                ", lastlogin=" + lastlogin +
                ", register=" + register +
                ", status=" + status +
                ", headurl='" + headurl + '\'' +
                ", gender=" + gender +
                '}';
    }
}
