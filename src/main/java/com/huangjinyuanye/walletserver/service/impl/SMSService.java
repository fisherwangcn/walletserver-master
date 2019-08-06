package com.huangjinyuanye.walletserver.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.huangjinyuanye.walletserver.dao.SmsInfoRepository;
import com.huangjinyuanye.walletserver.pojo.SmsInfo;
import com.huangjinyuanye.walletserver.utils.SMSAppId;
import com.huangjinyuanye.walletserver.web.ApiContorller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSService {
    private static final Logger logger = LoggerFactory.getLogger(SMSService.class);

    @Autowired
    SmsInfoRepository smsInfoRepository;

    public JSONObject send_code(String body) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {
            JSONObject params = JSONObject.parseObject(body);
            String phone = params.getString("phone");
            int app_code = params.getIntValue("app_code");
            int expired_time = 2;
            if(app_code== SMSAppId.login ||app_code== SMSAppId.register){
                expired_time = 5;
            }

            //开始验证是否可以发送
            //todo

            //开始发送验证码

            //开始发送
            SmsInfo smsInfo = new SmsInfo();
            smsInfo.setAppId(app_code);
            smsInfo.setPhone(phone);
            int times = (int) (System.currentTimeMillis()/1000);
            smsInfo.setCreatetime(times);
            smsInfo.setExpiredtime(times+60*expired_time);//120秒过期

            //获得验证码，开始发送
            String checkCode = "1234";

            smsInfo.setCheckCode(checkCode);

            smsInfoRepository.saveAndFlush(smsInfo);
            //将验证码存入数据库

            res.put("code", 0);
            res.put("msg", "success");
            res.put("data", null);
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }


    public boolean check_code(String phone,int appCode,String checkCode) {
        boolean flag = false;
        try {
            //获得验证码
            SmsInfo smsInfo = smsInfoRepository.findByPhoneAndAppIdAndIsused(phone, appCode, 0);

            if(smsInfo==null){
                logger.info("没有对应的验证码信息");
                return flag;
            }

            String trueCheckCode = smsInfo.getCheckCode();
            if(trueCheckCode.equals(checkCode)){
                //验证成功
                smsInfo.setIsused(1);
                smsInfo.setUsetime((int) (System.currentTimeMillis()/1000));

                smsInfoRepository.saveAndFlush(smsInfo);
                flag = true;
            }else{
                smsInfo.setErrCount(smsInfo.getErrCount()+1);
                smsInfoRepository.saveAndFlush(smsInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }
}
