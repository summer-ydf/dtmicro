package com.cms.common.core.utils;

import org.apache.commons.lang.ArrayUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;

/**
 * 安全加密工具类
 * @author DT辰白
 * @date 2022/4/17 19:58
 */
public final class EncryptUtils {

    public enum EncodeType {
        Base64,Hex
    }

    public static byte[] hex2byte(String strHex) {
        if (strHex == null) {
            return null;
        }
        int l = strHex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strHex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        int n = 0;
        while (n < b.length) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
            n++;
        }
        return hs.toString().toUpperCase();
    }

    /**
     * AES->>>CBC加密
     * @param content 加密内容
     * @param key 秘钥
     * @param iv 偏移矢量
     * @param encodeType 编码类型
     * @return 返回加密字符串
     */
    public static String encryptAES_CBC(String content, String key,String iv,EncodeType encodeType) {
        try {
            byte[] ketTemp = ArrayUtils.subarray(key.getBytes(),0,16);
            byte[] ivTemp = ArrayUtils.subarray(iv.getBytes(),0,16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(ketTemp, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivTemp);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            if(encodeType.equals(EncodeType.Base64)) {
                return Base64.encodeBase64String(cipher.doFinal(content.getBytes()));
            }else if(encodeType.equals(EncodeType.Hex)) {
                return byte2hex(cipher.doFinal(content.getBytes()));
            }
            return byte2hex(cipher.doFinal(content.getBytes()));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * AES->>>CBC解密
     * @param content 解密内容
     * @param key 秘钥
     * @param iv 偏移矢量
     * @param encodeType 编码类型
     * @return 返回解密字符串
     */
    public static String decryptAES_CBC(String content, String key, String iv, EncodeType encodeType) {
        try {
            byte[] keyTemp = ArrayUtils.subarray(key.getBytes(),0,16);
            byte[] ivTemp = ArrayUtils.subarray(iv.getBytes(),0,16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyTemp, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivTemp);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encrypted = null;
            if(encodeType.equals(EncodeType.Base64)) {
                encrypted = Base64.decodeBase64(content);
            }else if(encodeType.equals(EncodeType.Hex)) {
                encrypted = hex2byte(content);
            }
            byte[] original = cipher.doFinal(encrypted != null ? encrypted : new byte[0]);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * AES->>>CBC加密
     * @param bytes 加密字节
     * @param key 秘钥
     * @param iv 偏移矢量
     * @return 返回加密字节
     */
    public static byte[] encryptAES_CBC(byte[] bytes, String key, String iv) {
        try {
            byte[] keyTemp = ArrayUtils.subarray(key.getBytes(),0,16);
            byte[] ivTemp = ArrayUtils.subarray(iv.getBytes(),0,16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyTemp, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivTemp);
            Cipher cipher =  Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * AES->>>CBC解密
     * @param bytes 加密字节
     * @param key 秘钥
     * @param iv 偏移矢量
     * @return 返回解密字节字节
     */
    public static byte[] decryptAES_CBC(byte[] bytes, String key, String iv) {
        try {
            byte[] keyTemp = ArrayUtils.subarray(key.getBytes(),0,16);
            byte[] ivTemp = ArrayUtils.subarray(iv.getBytes(),0,16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyTemp, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivTemp);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(bytes);
        } catch (Exception e) {
            return null;
        }
    }

}

