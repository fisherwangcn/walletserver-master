package com.huangjinyuanye.walletserver.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccountBook {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private String name;
    private String msg;
    private int creatime;
    private int ownerId;
    private int disable;

    public AccountBook() {
    }

    public AccountBook(int id, String name, String msg, int creatime, int ownerId, int disable) {
        this.id = id;
        this.name = name;
        this.msg = msg;
        this.creatime = creatime;
        this.ownerId = ownerId;
        this.disable = disable;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCreatime() {
        return creatime;
    }

    public void setCreatime(int creatime) {
        this.creatime = creatime;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }
}
