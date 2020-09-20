package com.zqy.mange.zhuan.utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: zhangqingyou
 * Date: 2020/5/13 10:42
 * Des:
 */
public class RSAEncrypt {

    /**
     * RSA算法
     */
    public static final String RSA = "RSA";
    /**
     * 加密方式，android的
     */
    // public static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    // public static final String TRANSFORMATION = "RSA/None/NoPadding";
    /**
     * 加密方式，标准jdk的
     */
    public static final String TRANSFORMATION = "RSA/ECB/PKCS1Padding";
    
    public static final int DEFAULT_KEY_SIZE = 512;//秘钥默认长度

    public static final String charsetName = "ISO8859-1";//指定编码ISO8859-1  密文实质就是乱码  UTF-8 无法解码

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = (DEFAULT_KEY_SIZE / 8) - 11;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = (DEFAULT_KEY_SIZE / 8);

    /**
     * 获取公钥的key
     */
    public static final String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJQnFakBlzpyx6MDHd3pGki4uZXxTn6YSVbAWDtf7X8Q/vt3YJLIB+QGwM8/7FxF14pNb6kSNBPpg9pgkEnxSHcCAwEAAQ==";
    public static final String PRIVATE_KEY = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAlCcVqQGXOnLHowMd3ekaSLi5lfFOfphJVsBYO1/tfxD++3dgksgH5AbAzz/sXEXXik1vqRI0E+mD2mCQSfFIdwIDAQABAkBFv5jc9ljCNaNzRVZcURAEyKLmyrJPu1ZbtzZSzbi8JwbetLol8EruA/kp2JHjuc9rzsVyLADpd4yfs8VlSS3RAiEAxeT0wuaFO49wiT/nsm98ZBBnGk5Sy505WFqFGYYwZAMCIQC/pzc4kzMs/VOZCUgZTFKcIqU5QKZCPBtEx1vYQ47RfQIgLdo0ACjLfBZrO9HrzINabhEC4qx9MKJ4VAl9R4G5GqMCIQCDR6uP2WUIwE5vWehFS/6abTD4UsyQOlQDfPvw2/YMkQIhAJGlDx7dHzoQ9GZXeStg99nqD1yQQfvctEJ67C57w8St";


    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static Map<String, String> genKeyPair() {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        Map<String, String> stringMap = new LinkedHashMap<>();
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(DEFAULT_KEY_SIZE, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥

            Base64.Encoder encoder = Base64.getEncoder();
            String publicKeyString = encoder.encodeToString(publicKey.getEncoded());
            String privateKeyString = encoder.encodeToString(privateKey.getEncoded());

            // 将公钥和私钥保存到Map
            stringMap.put("公钥", publicKeyString);  //0表示公钥
            stringMap.put("私钥", privateKeyString);  //1表示私钥
        } catch (Exception e) {
        }
        return stringMap;
    }

    /**
     * 使用默认公钥 加密
     *
     * @param str 加密字符串
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encryptPublicKey(String str) {
        return encryptPublicKey(str, PUBLIC_KEY);
    }

    /**
     * 公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encryptPublicKey(String str, String publicKey) {
        try {
            byte[] inputByte = str.getBytes(charsetName);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decoded = decoder.decode(publicKey);//基本64位解码

            // 得到公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey publicK = keyFactory.generatePublic(x509KeySpec);

            //分段加密 或  解密
            String segment = getSegment(Cipher.ENCRYPT_MODE, publicK, inputByte);
            return segment;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 公钥解密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decryptPublicKey(String str, String publicKey) {
        //64位解码加密后的字符串
        try {
            byte[] inputByte = str.getBytes(charsetName);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decoded = decoder.decode(publicKey);//基本64位解码

            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PublicKey publicK = keyFactory.generatePublic(x509KeySpec);

            //分段加密 或  解密
            String segment = getSegment(Cipher.DECRYPT_MODE, publicK, inputByte);
            return segment;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 使用默认私钥 加密
     *
     * @param str 加密字符串
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encryptPrivateKey(String str) {
        return encryptPrivateKey(str, PRIVATE_KEY);
    }

    /**
     * 私钥加密
     *
     * @param str       加密字符串
     * @param privateKey 私钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encryptPrivateKey(String str, String privateKey) {
        try {
            byte[] inputByte = str.getBytes(charsetName);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decoded = decoder.decode(privateKey);//基本64位解码

            // 得到私钥对象
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey priKey = keyFactory.generatePrivate(keySpec);

            //分段加密 或  解密
            String segment = getSegment(Cipher.ENCRYPT_MODE, priKey, inputByte);
            return segment;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return
     * @throws Exception 解密过程中的异常信息
     */
    public static String decryptPrivateKey(String str, String privateKey) {
        //64位解码加密后的字符串
        try {
            byte[] inputByte = str.getBytes(charsetName);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decoded = decoder.decode(privateKey);//基本64位解码


            // 得到私钥对象
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey priKey = keyFactory.generatePrivate(keySpec);
            //分段加密 或  解密
            String segment = getSegment(Cipher.DECRYPT_MODE, priKey, inputByte);
            return segment;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 分段加密 或  解密
     *
     * @param mode      Cipher.ENCRYPT_MODE ：加密   Cipher.DECRYPT_MODE ：解密
     * @param key       PublicKey：公钥   PrivateKey：私钥
     * @param inputByte 加密 或 解密 的字符
     * @return
     */
    private static String getSegment(int mode, Key key, byte[] inputByte) {
        String data = null;

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(mode, key);


            int segmentLen = 0;//分段长度
            switch (mode) {
                case Cipher.ENCRYPT_MODE://加密
                    segmentLen = MAX_ENCRYPT_BLOCK;
                    break;
                case Cipher.DECRYPT_MODE://解密
                    segmentLen = MAX_DECRYPT_BLOCK;
                    break;
            }

            int inputLen = inputByte.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密 或  解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > segmentLen) {
                    cache = cipher.doFinal(inputByte, offSet, segmentLen);
                } else {
                    cache = cipher.doFinal(inputByte, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * segmentLen;
            }
            byte[] bytes = out.toByteArray();
            data = new String(bytes, charsetName);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
