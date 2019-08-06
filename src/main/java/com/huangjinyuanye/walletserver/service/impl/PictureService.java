package com.huangjinyuanye.walletserver.service.impl;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.huangjinyuanye.walletserver.dao.PictureRepository;
import com.huangjinyuanye.walletserver.pojo.Picture;
import com.huangjinyuanye.walletserver.utils.ZimgUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;

import static com.huangjinyuanye.walletserver.utils.Profile.pic_pre_url;

@Service
public class PictureService {
    private static final Logger logger = LoggerFactory.getLogger(AccountBookService.class);

    @Autowired
    PictureRepository pictureRepository;

    /**
     * 通过BASE64Decoder解码，并生成图片
     * @param imgStr 解码后的string
     */
    public String string2Image(String imgStr, String imgFilePath) {
        // 对字节数组字符串进行Base64解码并生成图片
        try {
            String[] split = imgFilePath.split("\\.");

            System.out.println(split.length);
            String type = split[split.length-1];

            imgFilePath = System.currentTimeMillis()+"_1_"+imgFilePath;
            Picture picture = new Picture();
            picture.setName(imgFilePath);

            // Base64解码
            imgFilePath = System.getProperty("user.dir")+"/"+imgFilePath;

            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();

            String cmd = "";
            String imgFileTrue = null;
            if(!type.equals("jpg")){
                imgFileTrue = imgFilePath.replace("." + type, ".jpg");
                cmd = String.format("convert %s %s",imgFilePath,imgFileTrue);
                Runtime.getRuntime().exec(cmd).waitFor();
            }


            if(imgFileTrue!=null){
                imgFilePath = imgFileTrue;
            }
            String url = ZimgUtils.uploadImage("http://188.131.189.159:4869/upload", imgFilePath);

            picture.setUrl(url);
            picture.setCreatetime((int) (System.currentTimeMillis()/1000));
            Picture savedPicture = pictureRepository.saveAndFlush(picture);
            return savedPicture.getUrl();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String loadPic2Zimg(String imgFilePath){

        return null;
    }

    public JSONObject picture_upload(String body) {
        JSONObject res = new JSONObject();
        res.put("code", -1);
        res.put("msg", "server fail");
        try {

            JSONObject params = JSONObject.parseObject(body);

            String token = params.getString("token");
            String pic_name = params.getString("pic_name");
            String pic_content = params.getString("pic_content");

            if(pic_name==null || pic_content==null){
                res.put("msg","must upload the picture name or pic_content!");
                return res;
            }

            //验证token

            String url = string2Image(pic_content, pic_name);

            if(url!=null){
                res.put("code", 0);
                res.put("msg", "success");
                res.put("data", pic_pre_url+url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            res.put("msg", "json解析失败");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
