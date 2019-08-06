package com.huangjinyuanye.walletserver.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class News {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int newsId;

    private String  name;
    private String abstracts;

    //默认是List<String> 形式
    private String converPic;
    private int categoryId;
    private int ownerId;
    private String content;
    private int audit;
    private int createtime;

    public News() {
    }

    public News(int newsId, String name, String abstracts, String converPic, int categoryId, int ownerId, String content, int audit, int createtime) {
        this.newsId = newsId;
        this.name = name;
        this.abstracts = abstracts;
        this.converPic = converPic;
        this.categoryId = categoryId;
        this.ownerId = ownerId;
        this.content = content;
        this.audit = audit;
        this.createtime = createtime;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getConverPic() {
        return converPic;
    }

    public void setConverPic(String converPic) {
        this.converPic = converPic;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }
}
