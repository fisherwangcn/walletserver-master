package com.huangjinyuanye.walletserver.web;

import com.alibaba.fastjson.JSONObject;
import com.huangjinyuanye.walletserver.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.tartarus.snowball.Among;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping(value = "/api")
public class ApiContorller {
    private static final Logger logger = LoggerFactory.getLogger(ApiContorller.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountBookService accountBookService;

    @Autowired
    private AccountMoneyService accountMoneyService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private SMSService smsService;

    private String getUrl(){
        String url = "";
        url = request.getScheme() +"://" + request.getServerName()
                + ":" +request.getServerPort()
                + request.getServletPath();
        if (request.getQueryString() != null){
            url += "?" + request.getQueryString();
        }
        return url;
    }
    @GetMapping("/user")
    public JSONObject getOneUserWithOutPassWd(@RequestParam(value = "token",required = false,defaultValue = "3"
    ) String token, @RequestParam(value = "id")String userId) {
        logger.info("path:{}",this.getUrl());

        return userService.getOneUserWithOutPassWd(userId);
    }

    @PostMapping("/login")
    public  JSONObject login(@RequestBody String body ) {
        logger.info("path:{} , body:{}",this.getUrl(),body);

        return userService.login(body);
    }

    @PostMapping("/register")
    public  JSONObject register(@RequestBody String body ) {
        logger.info("path:{} , body:{}",this.getUrl(),body);

        return userService.register(body);
    }

    @PostMapping("/account_book")
    public  JSONObject add_account_book(@RequestBody String body ) {
        logger.info("path:{} , body:{}",this.getUrl(),body);

        return accountBookService.add_account_book(body);
    }

    @PostMapping("/account_book/add_user")
    public  JSONObject account_book_add_user(@RequestBody String body ) {
        logger.info("path:{} , body:{}",this.getUrl(),body);

        return accountBookService.account_book_add_user(body);
    }

    @GetMapping("/account_book/add_user_list")
    public JSONObject account_book_add_user_list(@RequestParam(value = "token",required = false,defaultValue = "3") String token, @RequestParam(value = "user_id")String userId,@RequestParam(value = "type",required = false,defaultValue = "3")String type) {
        logger.info("path:{}",this.getUrl());

        return accountBookService.account_book_add_user_list(userId,type);
    }

    @GetMapping("/account_book/add_user_aduit_list")
    public JSONObject account_book_add_user_aduit_list(@RequestParam(value = "token",required = false,defaultValue = "3") String token, @RequestParam(value = "user_id")String userId,@RequestParam(value = "type",required = false,defaultValue = "0")String type) {
        logger.info("path:{}",this.getUrl());

        return accountBookService.account_book_add_user_aduit_list(userId,type);
    }

    @GetMapping("/account_book/user_list")
    public JSONObject account_book_user_list(@RequestParam(value = "token",required = false,defaultValue = "3") String token, @RequestParam(value = "book_id")String bookId) {
        logger.info("path:{}",this.getUrl());

        return accountBookService.account_book_user_list(bookId);
    }

    @GetMapping("/account_book/all")
    public JSONObject account_book_user_all(@RequestParam(value = "token",required = false,defaultValue = "3") String token, @RequestParam(value = "user_id")String user_id) {
        logger.info("path:{}",this.getUrl());

        return accountBookService.account_book_user_all(user_id);
    }

    @PostMapping("/account_book/auditing")
    public  JSONObject account_book_auditing(@RequestBody String body ) {
        logger.info("path:{} , body:{}",this.getUrl(),body);

        return accountBookService.account_book_auditing(body);
    }

    @PostMapping("/account_money")
    public  JSONObject account_money_update(@RequestBody String body ) {
        logger.info("path:{} , body:{}",this.getUrl(),body);

        return accountMoneyService.account_money_update(body);
    }

    @GetMapping("/account_money/detail")
    public JSONObject account_money_detail(@RequestParam(value = "token",required = false,defaultValue = "3") String token,@RequestParam(value = "money_id")String money_id) {
        logger.info("path:{}",this.getUrl());

        return accountMoneyService.account_money_detail(money_id);
    }

    @GetMapping("/account_money/list")
    public JSONObject account_money_list(@RequestParam(value = "token",required = false,defaultValue = "3") String token,@RequestParam(value = "user_id")String user_id,@RequestParam(value = "year",required = false,defaultValue = "0")String year,@RequestParam(value = "month",required = false,defaultValue = "0")String month,@RequestParam(value = "day",required = false,defaultValue = "-1")String day) {
        logger.info("path:{}",this.getUrl());

        return accountMoneyService.account_money_list(user_id,year,month,day);
    }

    @PostMapping("/picture/upload")
    public  JSONObject picture_upload(@RequestBody String body ) {
        logger.info("path:{} , body:{}",this.getUrl(),body);

        return pictureService.picture_upload(body);
    }

    @PostMapping("/news")
    public  JSONObject news_upload(@RequestBody String body ) {
        logger.info("path:{} , body:{}",this.getUrl(),body);

        return newsService.news_upload(body);
    }

    @GetMapping("/news")
    public JSONObject newsInfo(@RequestParam(value = "token",required = false,defaultValue = "3") String token,@RequestParam(value = "id")String id) {
        logger.info("path:{}",this.getUrl());

        return newsService.newsInfo(id);
    }

    @GetMapping("/news_list")
    public JSONObject newsList(@RequestParam(value = "token" ,required = false,defaultValue = "3") String token,@RequestParam(value = "start_time",required = false,defaultValue = "3")String start_time) {
        logger.info("path:{}",this.getUrl());

        return newsService.newsList(start_time);
    }

    @RequestMapping("/download/public_key")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        try {
            String fileName = "public.key";

            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            File file = new File(System.getProperty("user.dir")
                    + "/"+fileName);
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] content = new byte[(int) file.length()];
            fileInputStream.read(content);
            StreamUtils.copy(content, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/sms/send_code")
    public  JSONObject sms_send_code(@RequestBody String body ) {
        logger.info("path:{} , body:{}",this.getUrl(),body);

        return smsService.send_code(body);
    }

    @PostMapping("/after_login_set_passwd")
    public  JSONObject after_login_set_passwd(@RequestBody String body ) {
        logger.info("path:{} , body:{}",this.getUrl(),body);

        return userService.after_login_set_passwd(body);
    }
}
