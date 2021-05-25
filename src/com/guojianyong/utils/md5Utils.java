package com.guojianyong.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class md5Utils {
    //增加原来字符串，进一步保障加密
    private static final String SLAT = "2021john";

    public static String getDigest(String originText) {
        MessageDigest md = null;
        byte[] digest = null;
        try {
            md = MessageDigest.getInstance("md5");
            digest = md.digest(originText.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无法支持md5加密", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("无法支持UTF-8编码格式", e);
        }
        return Base64.getEncoder().encodeToString(digest);
    }


}
