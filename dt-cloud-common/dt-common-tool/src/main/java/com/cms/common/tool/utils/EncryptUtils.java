package com.cms.common.tool.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.cms.common.tool.constant.ConstantCommonCode.TOKEN_CLAIMS_IVS;
import static com.cms.common.tool.constant.ConstantCommonCode.TOKEN_CLAIMS_PWD;

/**
 * @author ydf Created by 2022/1/22 11:19
 */
public final class EncryptUtils {

    /**
     * 算法/模式/补码方式
     */
    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/NoPadding";
    private static final String AES_ENC = "AES";

    /**
     * 加密方法
     * @param data 要加密的数据
     * @return     加密的结果
     */
    public static String encrypt(String data){
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keySpec = new SecretKeySpec(TOKEN_CLAIMS_PWD.getBytes(), AES_ENC);
            IvParameterSpec ivSpec = new IvParameterSpec(TOKEN_CLAIMS_IVS.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return new Base64().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 解密方法
     * @param data 解密的数据
     * @return     解密的结果
     */
    public static String desEncrypt(String data) {
        try {
            byte[] encrypted1 = new Base64().decode(data);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            SecretKeySpec keySpec = new SecretKeySpec(TOKEN_CLAIMS_PWD.getBytes(), AES_ENC);
            IvParameterSpec ivSpec = new IvParameterSpec(TOKEN_CLAIMS_IVS.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
