package com.huangjinyuanye.walletserver.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.huangjinyuanye.walletserver.dao.AccountBookRepository;
import com.huangjinyuanye.walletserver.dao.AccountBookUserMappingRepository;
import com.huangjinyuanye.walletserver.dao.UserRepository;
import com.huangjinyuanye.walletserver.pojo.AccountBook;
import com.huangjinyuanye.walletserver.pojo.AccountBookUserMapping;
import com.huangjinyuanye.walletserver.pojo.User;
import com.huangjinyuanye.walletserver.utils.IThreadPoolService;
import com.huangjinyuanye.walletserver.utils.SMSAppId;
import com.huangjinyuanye.walletserver.web.ApiContorller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountBookRepository accountBookRepository;

    @Autowired
    AccountBookUserMappingRepository accountBookUserMappingRepository;

    @Autowired
    SMSService smsService;

    @Autowired
    private IThreadPoolService threadPoolService;

    private UserService userService;

    @PostConstruct
    public void init () {
        userService = this;
    }

    public JSONObject getOneUserWithOutPassWd(String id) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");

        int userId = Integer.parseInt(id);
        logger.info(String.valueOf(userId));

        User user = userRepository.findById(userId);
        System.out.println(user);
        JSONObject userJson = JSONObject.parseObject(JSONObject.toJSONString(user));
        userJson.remove("passwd");

        res.put("msg", "success");
        res.put("code", 0);
        res.put("data", userJson);

        return res;
    }
    public JSONObject login(String body) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {
            User resUser = new User();
            JSONObject params = JSONObject.parseObject(body);
            String phone = params.getOrDefault("phone","").toString();
            if(phone.length()==0){
                res.put("code",-2);
                res.put("msg","手机号不能为空");
                return res;
            }
            String password = params.getOrDefault("password","").toString();
            String verifyCode = params.getOrDefault("verifyCode","").toString();

            //验证phone是否注册
            User phoneExists = userRepository.findPhoneExists(phone.trim());
            if(phoneExists!=null) {
                //代表用户已经注册
                //登陆逻辑
                if (password.length() > 0) {
                    String syspasswd = phoneExists.getPasswd();
                    if (!password.equals(syspasswd)) {
                        res.put("code", -2);
                        res.put("msg", "账号或密码错误");
                        return res;
                    }
                } else if (verifyCode.length() > 0) {
                    //获取验证码
                    boolean b = smsService.check_code(phone, SMSAppId.login, verifyCode);
                    if (!b) {
                        res.put("code", -2);
                        res.put("msg", "验证码错误");
                        return res;
                    }
                } else {
                    res.put("code", -2);
                    res.put("msg", "请输入密码或者短信验证码");
                    return res;
                }

                phoneExists.setLastlogin((int) (System.currentTimeMillis() / 1000));
                this.userRepository.saveAndFlush(phoneExists);

                //说明验证通过,返回user详细信息
                resUser = phoneExists;
                res.put("code", 0);
                res.put("msg", "success");
                resUser.setPasswd("");
                res.put("data",resUser);
            }else{
                if (verifyCode.length() > 0) {
                    //获取验证码
                    boolean b = smsService.check_code(phone, SMSAppId.login, verifyCode);
                    if (!b) {
                        res.put("code", -2);
                        res.put("msg", "验证码错误");
                        return res;
                    }
                    //这里走注册逻辑
                    User user = new User();
                    user.setPhone(phone.trim());
                    user.setLastlogin((int) (System.currentTimeMillis()/1000));
                    user.setRegister((int) (System.currentTimeMillis()/1000));

                    User user1 = this.userRepository.saveAndFlush(user);

                    //开始添加属于个人的记账本
                    AccountBook accountBook = new AccountBook();
                    accountBook.setName("个人记账本");
                    accountBook.setOwnerId(user1.getId());

                    AccountBook accountBook1 = accountBookRepository.saveAndFlush(accountBook);

                    AccountBookUserMapping accountBookUserMapping = new AccountBookUserMapping();
                    accountBookUserMapping.setUserId(user1.getId());
                    accountBookUserMapping.setAccountBookId(accountBook1.getId());
                    accountBookUserMapping.setCreatetime((int) (System.currentTimeMillis()/1000));
                    this.accountBookUserMappingRepository.saveAndFlush(accountBookUserMapping);
                    resUser = user1;
                    resUser.setPasswd("");

                    res.put("code", 3);
                    res.put("msg", "新用户已注册，请引导用户设置密码");
                    res.put("data",resUser);
                }else{
                    res.put("code", -4);
                    res.put("msg", "请进行注册");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public JSONObject register(String body) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {
            User resUser = new User();
            JSONObject params = JSONObject.parseObject(body);
            String phone = params.getOrDefault("phone","").toString();
            if(phone.length()==0){
                res.put("code",-2);
                res.put("msg","手机号不能为空");
                return res;
            }
            String password = params.getOrDefault("password","").toString();
            String verifyCode = params.getOrDefault("verifyCode","").toString();

            //验证phone是否注册
            User phoneExists = userRepository.findPhoneExists(phone.trim());
            if(phoneExists!=null){
                res.put("code",-2);
                res.put("msg","用户已经注册，请直接登陆");
                return res;
            }else{
                //注册逻辑
                User user = new User();
                user.setPhone(phone.trim());

                if(password.length()<=0 || verifyCode.length()<=0){
                    res.put("code",-2);
                    res.put("msg","验证码或密码不能为空");
                    return res;
                }
                user.setPasswd(password.trim());
                //获取验证码
                boolean b = smsService.check_code(phone, SMSAppId.register, verifyCode);
                if(!b){
                    res.put("code",-2);
                    res.put("msg","验证码错误");
                    return res;
                }

                user.setLastlogin((int) (System.currentTimeMillis()/1000));
                user.setRegister((int) (System.currentTimeMillis()/1000));

                User user1 = this.userRepository.saveAndFlush(user);

                //开始添加属于个人的记账本
                AccountBook accountBook = new AccountBook();
                accountBook.setName("个人记账本");
                accountBook.setOwnerId(user1.getId());

                AccountBook accountBook1 = accountBookRepository.saveAndFlush(accountBook);

                AccountBookUserMapping accountBookUserMapping = new AccountBookUserMapping();
                accountBookUserMapping.setUserId(user1.getId());
                accountBookUserMapping.setAccountBookId(accountBook1.getId());
                accountBookUserMapping.setCreatetime((int) (System.currentTimeMillis()/1000));
                this.accountBookUserMappingRepository.saveAndFlush(accountBookUserMapping);
                resUser = user1;
            }

            res.put("code", 0);
            res.put("msg", "success");
            resUser.setPasswd("");
            res.put("data",resUser);
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }

    public JSONObject after_login_set_passwd(String body) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {
            JSONObject params = JSONObject.parseObject(body);
            String user_id = params.getOrDefault("user_id","").toString();
            String password = params.getOrDefault("password","").toString();
            User user = userRepository.findById(Integer.parseInt(user_id));
            if(user==null){
                res.put("code",-2);
                res.put("msg","用户不存在");
                return res;
            }
            user.setPasswd(password);
            userRepository.saveAndFlush(user);

            res.put("code", 0);
            res.put("msg", "success");
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
