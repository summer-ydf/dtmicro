package com.cms.common.tool.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author ydf Created by 2022/1/22 11:19
 */
public final class EncryptUtils {

    public enum EncodeType {
        Base64,Hex
    }

    private final static String AES = "AES";
    private final static String AES_Padding = "AES/CBC/PKCS5Padding";

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * AES CBC加密
     *
     */
    public static String encryptAES_CBC(String content, String password,String ivs,EncodeType encodeType) {
        byte[] raw = ArrayUtils.subarray(password.getBytes(),0,16);
        byte[] ivs_aa = ArrayUtils.subarray(ivs.getBytes(),0,16);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(AES_Padding);
            IvParameterSpec iv = new IvParameterSpec(ivs_aa);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            if(encodeType== EncodeType.Base64){
                return org.apache.commons.codec.binary.Base64.encodeBase64String(cipher.doFinal(content.getBytes("UTF-8")));
            }else if(encodeType== EncodeType.Hex){
                return byte2hex(cipher.doFinal(content.getBytes()));
            }
            return byte2hex(cipher.doFinal(content.getBytes()));
        } catch (Exception e) {
        }
        return null;
    }

    /**
     *  AES CBC解密
     *
     */
    public static String decryptAES_CBC(String content, String password,String ivs,EncodeType encodeType) {
        if(StringUtils.isEmpty(content)){
            return null;
        }
        try {
            byte[] raw = ArrayUtils.subarray(password.getBytes(),0,16);
            byte[] ivs_aa = ArrayUtils.subarray(ivs.getBytes(),0,16);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(AES_Padding);
            IvParameterSpec iv = new IvParameterSpec(ivs_aa);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1=null;
            if(encodeType== EncodeType.Base64){
                encrypted1 = org.apache.commons.codec.binary.Base64.decodeBase64(content);
            }else if(encodeType== EncodeType.Hex){
                encrypted1 = hex2byte(content);
            }
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "UTF-8");
        } catch (Exception e) {
            SysCmsUtils.log.info("解密错误",e);
        }
        return null;
    }

}
