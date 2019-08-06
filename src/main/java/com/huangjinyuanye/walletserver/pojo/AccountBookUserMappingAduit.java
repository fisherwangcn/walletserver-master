package com.huangjinyuanye.walletserver.pojo;

import javax.persistence.*;

@Entity
public class AccountBookUserMappingAduit {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private int accountBookId;
    private int userId;
    private int createtime;
    private int disable;
    private int type;

    public AccountBookUserMappingAduit() {
    }

    public AccountBookUserMappingAduit(int id, int accountBookId, int userId, int createtime, int disable, int type) {
        this.id = id;
        this.accountBookId = accountBookId;
        this.userId = userId;
        this.createtime = createtime;
        this.disable = disable;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountBookId() {
        return accountBookId;
    }

    public void setAccountBookId(int accountBookId) {
        this.accountBookId = accountBookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
