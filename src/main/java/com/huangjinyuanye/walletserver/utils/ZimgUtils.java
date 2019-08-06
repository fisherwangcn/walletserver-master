package com.huangjinyuanye.walletserver.utils;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;

/**
 * 功能：ZIMG图片上传
 * */
public class ZimgUtils {

    protected static Logger logger = LoggerFactory.getLogger(ZimgUtils.class);

    public static void main(String[] args) {
        System.out.println(ZimgUtils.uploadImage("http://images.renlr.com", "/Users/liang/Downloads/timg.jpg"));
    }

    /**
     * 功能：将图片上传图片资源中心
     * */
    public static String uploadImage(String url, String file) {
        try {
            String[] split = file.trim().split("\\.");
            String ext = split[split.length-1];
            List<String> picType = Arrays.asList(new String[]{"jpg","jpeg","png"});
            if(!picType.contains(ext)){
                throw new Exception("图片类型不支持");
            }

            URL $url = new URL(url);
            URLConnection connection = $url.openConnection();
            connection.setReadTimeout(50000);
            connection.setConnectTimeout(25000);
            HttpURLConnection uc = (HttpURLConnection) connection;
            uc.setRequestMethod("POST");
            uc.setRequestProperty("Connection", "Keep-Alive");
            uc.setRequestProperty("Cache-Control", "no-cache");
            uc.setRequestProperty("Content-Type", ext.toLowerCase());
            uc.setRequestProperty("COOKIE", "william");
            uc.setDoOutput(true);
            uc.setDoInput(true);
            uc.connect();
            OutputStream output = uc.getOutputStream();
            FileInputStream input = new FileInputStream(file);
            byte[] buf = new byte[8192];
            while (true) {
                int len = input.read(buf);
                if (len <= 0)
                    break;
                output.write(buf, 0, len);
            }
            StringBuffer resp = new StringBuffer();
            InputStreamReader inReader = new InputStreamReader(uc.getInputStream(), "UTF-8");
            char[] bb = new char[8192];
            while (true) {
                int length = inReader.read(bb);
                if (length == -1)
                    break;
                char[] bc = new char[length];
                for (int i = 0; i < length; i++)
                    bc[i] = bb[i];
                resp.append(new String(bc));
            }
            output.close();
            input.close();
            inReader.close();
            uc.disconnect();
            JSONObject json = JSONObject.parseObject(resp.toString());
            if (json.getBooleanValue("ret")) {
                JSONObject info = json.getJSONObject("info");
                String md5 = info.getString("md5");
                return String.format("%s", md5);
            }
        } catch (Exception e) {
            logger.error("上传图片失败", e);
        }
        return null;
    }

    /**
     * 功能：从网络下载图片上传图片资源中心
     * */
    public static String downloadUploadImage(String url, String imageUrl) {
        try {
            String file = downloadImage(imageUrl);
            if (file != null && file.trim().length() > 0) {
                return uploadImage(url, file);
            }
        } catch (Exception e) {
            logger.error("从网络下载图片上传图片资源中心失败", e);
        }
        return null;
    }

    /**
     * 功能：从网络下载图片缓存到本地
     * */
    public static String downloadImage(String imageUrl) {
        try {
            URL $url = new URL(imageUrl);
            DataInputStream input = new DataInputStream($url.openStream());
            String $filename = UUID.randomUUID().toString();
            String path = System.getProperty("java.io.tmpdir");
            String file = String.format("%s%s%s", path, File.separator, $filename);
            FileOutputStream output = new FileOutputStream(new File(file));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            input.close();
            output.close();
            return file;
        } catch (Exception e) {
            logger.error("从网络下载图片失败", e);
        }
        return null;
    }
}