package com.kuaishou.kalaxydev.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtils {
    public static String transHostName2IP(String hostName){
        InetAddress address = null;
        String ip = null;
        try {
            address = InetAddress.getByName(hostName);
            ip = address.getHostAddress().toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("获取失败");
        }
        return ip ;
    }

}
