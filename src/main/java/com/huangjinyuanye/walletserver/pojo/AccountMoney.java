package com.huangjinyuanye.walletserver.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AccountMoney {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    private int userId;
    private int type;
    private int catecoryId;
    private double money;
    private int createtime;
    private int updatetime;
    private String comment;
    private int bookId;
    private int year;
    private int month;
    private int day;

    private int disable;


    public AccountMoney() {
    }

    public AccountMoney(int id, int userId, int type, int catecoryId, double money, int createtime, int updatetime, String comment, int bookId, int year, int month, int day, int disable) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.catecoryId = catecoryId;
        this.money = money;
        this.createtime = createtime;
        this.updatetime = updatetime;
        this.comment = comment;
        this.bookId = bookId;
        this.year = year;
        this.month = month;
        this.day = day;
        this.disable = disable;
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

    public int getCatecoryId() {
        return catecoryId;
    }

    public void setCatecoryId(int catecoryId) {
        this.catecoryId = catecoryId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(int updatetime) {
        this.updatetime = updatetime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }
}
