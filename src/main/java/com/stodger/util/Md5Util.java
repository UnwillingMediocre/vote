package com.stodger.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密
 * @author Stodger
 * @date 2019-06-28 19:57
 * @version V1.0
 */
public class Md5Util {
    private static final String PASSWORD_SALT = "geelysdafaqj23ou89ZXcj@#$@#$#@KJdjklj;D../dSF.,";
    private static  final String [] HEX_DIGEST =  {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public static String getMd5EnCode(String origin){
        return md5Encode(origin + PASSWORD_SALT);
    }

    /**
     * m5d加密
     * @param origin 源
     * @return String
     */
    private static String md5Encode(String origin){
        String resultStr = new String(origin );
        try {
            MessageDigest  messageDigest = MessageDigest.getInstance("MD5");
            resultStr =  byteArrayToHexStr(messageDigest.digest(resultStr.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return resultStr.toUpperCase();
    }

    /**
     * 将字节数组转换成十六进制字符串
     * @param bytes 字节数组
     * @return String
     */
    private static String byteArrayToHexStr(byte[] bytes) {
        StringBuilder resultStr = new StringBuilder();
        for(byte b : bytes){
            resultStr.append(byteToHexStr(b));
        }
        return resultStr.toString();
    }


    /**
     * 将字节转换成十六进制字符串
     * @param b 字节
     * @return String
     */
    private static String byteToHexStr(byte b) {
        int n = b;
        if(n < 0){
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIGEST[d1] + HEX_DIGEST[d2];
    }
}
