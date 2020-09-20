package com.zqy.common;


import com.zqy.common.utils.LogUtil;
import com.zqy.common.utils.RSAEncrypt;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component //所在的类交个spring容器扫描(@Component),并且在要执行的方法上添加@PostConstruct注解
public class MainTest {
    private static LogUtil logs = new LogUtil();


    public static void main(String[] args) {
       // rsa();
    }

    /**
     * RSA测试
     */
    private static void rsa() {
        Map<String, String> stringStringMap = RSAEncrypt.genKeyPair();
        logs.debug("公钥&私钥", stringStringMap.toString(), false);

//        String encrypt = RSAEncrypt.encryptPublicKey("123");//公钥加密
//        String decrypt = RSAEncrypt.decryptPrivateKey(encrypt, RSAEncrypt.PRIVATE_KEY);//私钥解密

        String encrypt = RSAEncrypt.encryptPrivateKey("123");//私钥加密
        String decrypt = RSAEncrypt.decryptPublicKey(encrypt, RSAEncrypt.PUBLIC_KEY);//公钥解密

        logs.debug("加密后", encrypt, false);
        logs.debug("解密后", decrypt, false);
    }


}
