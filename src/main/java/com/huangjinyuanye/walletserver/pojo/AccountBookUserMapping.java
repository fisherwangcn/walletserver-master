package com.huangjinyuanye.walletserver.pojo;

import javax.persistence.*;

@Entity
@IdClass(ABMappingPK.class)
public class AccountBookUserMapping {
    @Id
    @Column(name = "accountBookId", nullable = false)
    private int accountBookId;

    @Id
    @Column(name = "userId", nullable = false)
    private int userId;

    private int createtime;

    private int disable;

    public AccountBookUserMapping() {
    }

    public AccountBookUserMapping(int accountBookId, int userId, int createtime, int disable) {
        this.accountBookId = accountBookId;
        this.userId = userId;
        this.createtime = createtime;
        this.disable = disable;
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
}
