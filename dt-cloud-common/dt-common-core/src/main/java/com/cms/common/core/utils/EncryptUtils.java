package com.cms.common.core.utils;

import org.apache.commons.lang.ArrayUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author DT
 * @date 2022/4/17 19:58
 */
public final class EncryptUtils {

    public enum EncodeType{
        Base64,Hex
    }

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
    public static byte[] encryptAES_CBC(byte[] anslBytes, String password,String ivs) {
        byte[] raw = ArrayUtils.subarray(password.getBytes(),0,16);
        byte[] ivs_aa = ArrayUtils.subarray(ivs.getBytes(),0,16);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs_aa);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            return cipher.doFinal(anslBytes);
        } catch (Exception e) {
        }
        return null;
    }

    public static byte[] decryptAES_CBC(byte[] encrypted, String password,String ivs) {
        try {
            byte[] raw = ArrayUtils.subarray(password.getBytes(),0,16);
            byte[] ivs_aa = ArrayUtils.subarray(ivs.getBytes(),0,16);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs_aa);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(encrypted);
            return original;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * AES CBC加密
     *
     */
    public static String encryptAES_CBC(String content, String password,String ivs,EncodeType encodeType) {
        byte[] raw = ArrayUtils.subarray(password.getBytes(),0,16);
        byte[] ivs_aa = ArrayUtils.subarray(ivs.getBytes(),0,16);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs_aa);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            if(encodeType== EncodeType.Base64){
                return org.apache.commons.codec.binary.Base64.encodeBase64String(cipher.doFinal(content.getBytes()));
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
        try {
            byte[] raw = ArrayUtils.subarray(password.getBytes(),0,16);
            byte[] ivs_aa = ArrayUtils.subarray(ivs.getBytes(),0,16);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
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
        }
        return null;
    }

    /**
     * RSA生成密钥对
     * @param keysize
     * @return
     * @throws Exception
     */
    public static Map<String, Object> generateKeyPair(int keysize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keysize, new SecureRandom(UUID.randomUUID().toString().getBytes()));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put("pub", Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        keyMap.put("pri", Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        return keyMap;
    }

    /**
     * base64转公钥
     * @param base64s
     * @return
     */
    public static RSAPublicKey getRSAPublidKeyBybase64(String base64s, String algorithm) {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(org.apache.commons.codec.binary.Base64.decodeBase64(base64s));
        RSAPublicKey publicKey = null;
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(algorithm);
            publicKey = (RSAPublicKey)keyFactory.generatePublic(keySpec);
        } catch (Exception var4) {
        }
        return publicKey;
    }

    /**
     * base64转私钥
     * @param base64s
     * @return
     */
    public static RSAPrivateKey getRSAPrivateKeyBybase64(String base64s, String algorithm) {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(org.apache.commons.codec.binary.Base64.decodeBase64(base64s));
        RSAPrivateKey privateKey = null;
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(algorithm);
            privateKey = (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
        } catch (Exception var4) {
        }
        return privateKey;
    }


    public static void main(String[] args) throws Exception{
//        String cbc = encryptAES_CBC("123456", "klkskhiioiow0993829848398479e", "8982983jklskdhy8iiowieoiwhhuu", EncodeType.Hex);
//        System.out.println(cbc);
        // B63DFEB55C1CE917B802A1CA179AA6DF
        String s = decryptAES_CBC("B63DFEB55C1CE917B802A1CA179AA6DF", "klkskhiioiow0993829848398479e", "8982983jklskdhy8iiowieoiwhhuu", EncodeType.Hex);
        System.out.println(s);
    }


}

