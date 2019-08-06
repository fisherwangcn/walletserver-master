package com.huangjinyuanye.walletserver.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.huangjinyuanye.walletserver.dao.NewsRepository;
import com.huangjinyuanye.walletserver.dao.PictureRepository;
import com.huangjinyuanye.walletserver.pojo.News;
import com.huangjinyuanye.walletserver.pojo.Picture;
import com.huangjinyuanye.walletserver.utils.ZimgUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import static com.huangjinyuanye.walletserver.utils.Profile.pic_pre_url;

@Service
public class NewsService {
    private static final Logger logger = LoggerFactory.getLogger(AccountBookService.class);

    @Autowired
    NewsRepository newsRepository;

    public JSONObject news_upload(String body) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {

            JSONObject params = JSONObject.parseObject(body);

            String token = params.getString("token");
            //文章ID
            int news_id = params.getIntValue("news_id");

            //文章名称
            String name = params.getString("name");

            //文章简介
            String abstracts = params.getString("abstracts");

            //文章封面图，以List<String>形式传输
            String conver_pic = params.getString("conver_pic");

            //文章所属类别，默认为0
            int category_id = params.getIntValue("category_id");

            //文章所属用户ID,对应user的userId
            int owner_id = params.getIntValue("owner_id");

            //文章内容，字符串形式，其中图片也属于字符串
            String content = params.getString("content");

            //是否通过审批，默认为1，代表通过；-1代表不通过
            int audit = params.getIntValue("audit");

            //更新时间
            int createtime = params.getIntValue("createtime");

            News news = new News();
            News newTmp = newsRepository.findById(news_id);
            if(newTmp!=null) {
                news = newTmp;
            }
            news.setAbstracts(abstracts);
            news.setName(name);
            news.setConverPic(conver_pic);
            news.setCategoryId(category_id);
//            news.setOwnerId(owner_id);
            news.setContent(content);
            news.setCreatetime((int) (System.currentTimeMillis()/1000));

            News news1 = newsRepository.saveAndFlush(news);

            if(news1.getNewsId()>0){
                res.put("code", 0);
                res.put("msg", "success");
                res.put("data", null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }


    public JSONObject newsInfo(String id) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {
            int news_id = Integer.parseInt(id);
            News newTmp = newsRepository.findById(news_id);

            res.put("code", 0);
            res.put("msg", "success");
            res.put("data", newTmp);
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public JSONObject newsList(String start_time) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {
            List<News> newTmp = newsRepository.findAll();

            res.put("code", 0);
            res.put("msg", "success");
            res.put("data", newTmp);
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
