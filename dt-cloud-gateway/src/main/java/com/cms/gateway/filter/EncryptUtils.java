package com.cms.gateway.filter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lihao on 2019/7/29.
 */
@CommonsLog
public final class EncryptUtils {
    public enum EncodeType{
        Base64,Hex
    }
    private final static String MD5="MD5";
    private final static String SHA="SHA-1";
    private final static String AES="AES";
    private final static String RSA="RSA";
    private final static String AES_Padding="AES/CBC/PKCS5Padding";
    public static final  String md5(String s) {
        if(StringUtils.isEmpty(s))return null;
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes(StandardCharsets.UTF_8);
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance(MD5);
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
    public static String sha(String str){
        String[] hexDigits = { "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
        StringBuffer sb = new StringBuffer();
        try{
            MessageDigest sha = MessageDigest.getInstance(SHA);
            sha.update(str.getBytes());
            byte[] bytes = sha.digest();
            for (int i = 0; i < bytes.length; i++) {

                int ret = bytes[i];
                if (ret < 0) {
                    ret += 256;
                }
                int m = ret / 16;
                int n = ret % 16;
                String s= hexDigits[m] + hexDigits[n];

                sb.append(s);
            }
        }catch (Exception e){
            return null;
        }
        return sb.toString();
    }
    public static String nonStr(){
        String $chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int maxPos = $chars.length();
        String noceStr = "";
        for (int i = 0; i < 32; i++) {
            noceStr += $chars.charAt((int)Math.floor(Math.random() * maxPos));
        }
        return noceStr;
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
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(AES_Padding);
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
            SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
            Cipher cipher = Cipher.getInstance(AES_Padding);
            if(StringUtils.isNotBlank(ivs)){
                byte[] ivs_aa = ArrayUtils.subarray(ivs.getBytes(),0,16);
                IvParameterSpec iv = new IvParameterSpec(ivs_aa);
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            }else {
                cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            }
            byte[] original = cipher.doFinal(encrypted);
            return original;
        } catch (Exception e) {
            log.error("解密错误",e);
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
    public static String encryptAES_CBC_CONTROL(String content,EncodeType encodeType) {
        if(StringUtils.isEmpty(content)) return null;
        return encryptAES_CBC(content,"酷酷的了89890s099sFS","看端口是来看邓老师223AWd",encodeType);
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
            log.error("解密错误",e);
        }
        return null;
    }
    public static String decryptAES_CBC_CONTROL(String content) {
        if(StringUtils.isEmpty(content)) return null;
        return decryptAES_CBC(content,"酷酷的了89890s099sFS","看端口是来看邓老师223AWd", EncodeType.Base64);
    }

    /**
     * RSA生成密钥对
     * @param keysize
     * @return
     * @throws Exception
     */
    public static Map<String, Object> generateKeyPair(int keysize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(keysize, new SecureRandom(UUID.randomUUID().toString().getBytes()));
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put("pub", Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        keyMap.put("pri", Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        return keyMap;
    }
    public static String aes_encrypt(String password, byte[] keyBytes) {
        try {
            if(StringUtils.isEmpty(password)){
                return null;
            }
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cleartext = password.getBytes("UTF-8");
            byte[] ciphertextBytes = cipher.doFinal(cleartext);
            return new String(Hex.encodeHex(ciphertextBytes));
        } catch (Exception e) {
            log.error("aes_encrypt error",e);
        }
        return null;
    }
    public static String aes_decrypt(String passwordhex, byte[] keyBytes) {
        try {
            if(StringUtils.isEmpty(passwordhex)){
                return null;
            }
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher decipher = Cipher.getInstance("AES");
            decipher.init(Cipher.DECRYPT_MODE, key);
            char[] cleartext = passwordhex.toCharArray();
            byte[] decodeHex = Hex.decodeHex(cleartext);
            byte[] ciphertextBytes = decipher.doFinal(decodeHex);
            return new String(ciphertextBytes);
        }  catch (Exception e) {
            log.error("aes_decrypt error",e);
        }
        return null;
    }


    public static void main(String[] args) throws Exception{
//        String pas="12345678900987654321";
//        String iv="12345678900987654321";
//        Params map=new Params().p("openid","123").p("page","facecollect").p("idno","510821198709273711");
//        String content=map.toJSONString();
//        System.out.println(encryptAES_CBC_CONTROL(content));
//        System.out.println(decryptAES_CBC_CONTROL("oGvOMvjbxXmWVmyhC+K2r+xWP3iYcU/FoxkkvDDNJIIX2ZII1ofCsQzqsTUAYZvZOQiWecIqgBOWNMuFwJOjeMwcqgUO0rEy2NYYD02N7rE="));

//        InputStream input= FileUtils.openInputStream(new File("/Users/lihao/smei/icc/icc-server/icpl-server/target/script/get_info.script"));
//        byte[] contents= IOUtils.toByteArray(input);
//        String content= new String(EncryptUtils.decryptAES_CBC(contents,"720cb14b3151490dad2afaf61d2919fb","af3c77c3c6b747e6ad1006e47a1b8422"),"UTF-8");
//        System.out.println(content);
//        System.out.println(content.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", ""));

//        String content="u9su4Mi92g8b2l1GjBEllZQBpjFxRbKYY4MNjgS7qyQBNWCWEZXEbNIsw5B13hK2non1a091EAlgNUngJKrc358gm1sVk8+yu3XGlY72siqOVrAmwq7sfuvgwIPqPJKwWsvkkzmJ4iGYq+1LCFNaRwj8MZIg2wC5PgsbOcLfz+bkAyeJWpR5ELsMRrXgcOntZaDsnffRpWmEAppl/h+n7Qj8MZIg2wC5TK2x9oWeWAED4Qpjtl3W+gRnEHdDcPYKOUJ5nyBOTx2ep4rwQLCmA23mauzCxekugUjAf05i1O9citSuKS3z+i6YRKzQpJ02ouUvb7A7rT3Fx0OAHhoe2AzvULUhOJXBhPOjnXwT9H6Da7EXhCIWB9mAjusns80QlsNAByb8GAb5kASbcP6srpq4mwwdwOObReoXzbLRuHHt1zF4yREDFK1BO0e4JKBXTdYXfe/CKfb9aaQmAEu849/NjPE5A7m8o+7VnMain2Jz4rJGYx5o+V3TToFFt5Vh8gJjx9Z0dh/yALIzFQngYV3bv0ao4WVjBjN1Soy0d3OR8OCbbpjUu9+KjDCGEUOidwR7U7OIMPTLnaggSYouj7jy2F4OZ6vwIEtUFAOP8+o7CKjxW8h4ALF6IzkJZCisuU0w6yZ92Xx7ssVMmWlDzn6Hjl4b+yIezya2XB8pDtMprctEDhZ6u9A7pPgJYyax";

//        System.out.println(EncryptUtils.cmb_des("cmbtest1",content));


    }


}
