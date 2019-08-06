package com.huangjinyuanye.walletserver.upload_pic;

import com.alibaba.fastjson.JSONObject;
import com.huangjinyuanye.walletserver.utils.HttpRequestUtils;
import org.apache.commons.codec.binary.Base64;

import java.io.*;

public class UploadPictureTest {
    public static void main(String... args) throws Exception{
        String pic_name = "apple.png";
        String[] split = pic_name.split("\\.");
        String imgFile = "/Users/wangyongshuai/work/own/wallet-server/src/test/java/com/huangjinyuanye/walletserver/"+pic_name;//待处理的图片
        String imgbese=getImgStr(imgFile);
        System.out.println(imgbese);
        String jsonStr = String.format("{\n" +
                "    \"token\":\"xx\",\n" +
                "    \"pic_name\":\"%s\",\n" +
                "    \"pic_content\":\"%s\"\n" +
                "}",pic_name,imgbese);

        JSONObject jsonObject = HttpRequestUtils.httpPost("http://188.131.189.159:7006/api/picture/upload", JSONObject.parseObject(jsonStr));
//        JSONObject jsonObject = HttpRequestUtils.httpPost("http://localhost:7006/api/picture/upload", JSONObject.parseObject(jsonStr));
        System.out.println(jsonObject);

    }

    public static String getImgStr(String imgFile){
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理


        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }
}
