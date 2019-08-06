package com.huangjinyuanye.walletserver.pojo;

import javax.persistence.Id;
import java.io.Serializable;

public class ABMappingPK implements Serializable {
    private int accountBookId;

    private int userId;

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
}
