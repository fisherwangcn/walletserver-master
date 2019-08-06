package com.huangjinyuanye.walletserver.pojo;

import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Component
public class UserLevel {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private int userId;

    private int type;

    private int level;

    public UserLevel() {
    }

    public UserLevel(int id, int userId, int type, int level) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
