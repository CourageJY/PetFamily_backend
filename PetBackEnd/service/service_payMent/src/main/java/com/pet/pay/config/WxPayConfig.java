package com.pet.pay.config;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.*;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;


@Configuration
@PropertySource("classpath:wxpay.properties") //读取配置文件
@ConfigurationProperties(prefix="wxpay") //读取wxpay节点
@Data //使用set方法将wxpay节点中的值填充到当前类的属性中
@Slf4j
public class WxPayConfig {

    // 商户号
    private String mchId;

    // 商户API证书序列号
    private String mchSerialNo;

    // 商户私钥文件
    //private String privateKeyPath;

    // APIv3密钥
    private String apiV3Key;

    // APPID
    private String appid;

    // 微信服务器地址
    private String domain;

    // 接收结果通知地址
    private String notifyDomain;

    // APIv2密钥
    private String partnerKey;

    //privateKey
    private String Key="-----BEGIN PRIVATE KEY-----\n" +
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDnSAKI8sea8p+d\n" +
            "OBVPWlZmxqJfPbdhzZxdI5Kx1j5SJNZwXWtr43/giw38pwzSlBI+bubBcYlkFTI0\n" +
            "guigMZO/yueb1mZChaY/JG1vsT02Ubj0xkVvBwKNbYS48NEpZhK61Mia09R4n1iH\n" +
            "1vip9kt8J6Zrx+xIqwmuCNWigyivGrvY9AdevCNlNSVdHVOZUJiJ6UGtvVmgZb0u\n" +
            "RTwBzfkjnwTgEcsrZMmF15nFubFsyJLyF/zY4NhrISc8H/rbjgleqa8ybYL26iTS\n" +
            "gfPCXe4U9f8fNFF2bSA06GTiB2R93q2B0zHeUYrpgF4XOGlIAqH+Ea4Vn+aOj6I0\n" +
            "pduh03idAgMBAAECggEBAJ+4SB/hYd1szrPZhkXtwhtp87pIObtuLhzYMzdjGFjM\n" +
            "HdctfMDeNHKSNU+U4bMPFOZO2kcfLF2Ukb5X5WSzuDBMZNRnJOmtuJiEhJsM0JQR\n" +
            "reREhLDfK3EWAAFkNV4corSpu/vIbEP87zuoRsPBVnHgQ/rM7y1kCORKL5bycwcw\n" +
            "5BI4xhULKAu14LEcDL3+xDJo39w+WCFlxuP+6Bs7+vIeavs+AC3TJkA4kg2nyWd3\n" +
            "W07xPjHl64f17icqsFhuFZ+VuSf5CAgQGWDbC7BHqRkDStUDSiiUiFushouKCLdK\n" +
            "MpA0x4ogb2ZwfZDRhZHiLNAGe4QovYCcXWBydzuT0WECgYEA828Bo1JAHE5kdnsO\n" +
            "E9+enH/yMcOKTRnuYPiXsFXNvqofc5tZiXJmVE/+EKv7LFmtUA6qqKC7FDek8TpP\n" +
            "SkfXmSDAgfM6AdzT0YoHH23FRVewnFMEYumtogXsXJTyI5siBSJp16s9Rn/YwESt\n" +
            "JqjW5+9Ck1dkU+UJCZ4lOw4HeGkCgYEA8zho2BKQTh3P/xcFcoTcunVZpRayVkHM\n" +
            "g8Ef6RGGo4vM1oshQLvXyPqCmhAIf6j71I9WPqUwjmeGyaR7Hir0dbgTCm2fJPFW\n" +
            "lxAvgbCISxEPz10RYBcR2umMSlJLfZfhqv1CyfU4vfCTbdOimgsz2039E3oLTbzg\n" +
            "eDe/mdzu2BUCgYEAleKjf4wFLWiXMtxRrqrhXjrpRPrBDPgKbmqh+1DZfawB8YyV\n" +
            "dKublg4qwNkjrgsJS2G8cleE2M3qIR1l9LaHaSFhZqH79WmigkIaYJ+V9zwm4hm7\n" +
            "eaun3TsIbXjIHmRGbiLiSIiHEgFl0/x1IHiU2fnXZCFLBNzg06ssAVCCCQECgYA1\n" +
            "4BfxTONkOlxZgAr33BBcySPLWuS0EK0xvjTIVtaBIbWFDJqYEUPyQ/NsFwMa7B6k\n" +
            "bf/HrqW71ZjYz7Np8k/mR5kIJVIsR71Lhw1O6AC4yBW9dDsmEtYkrLkjuWj5cAxP\n" +
            "6PvDaqtf/4tYt5l8D+Ezwem+R7l7RcxfNNIfTf4mJQKBgE57dnRx+Ijx7VHjJvjl\n" +
            "X2jB/VSVGpK5OADykmmZ/wvHPlQcyzd+5kAIoJhSuY48CFeI1DOogR2p01LEFQEL\n" +
            "j4AI5FqOOQwRJvNmfoKcKwO36tSxSEGSM8POKOsa21PG/gvDpJjVFo2hn5QcMHWn\n" +
            "z5SjsgA/1YbXejubdLxT/3pl\n" +
            "-----END PRIVATE KEY-----";

    /**
     * 获取商户的私钥文件
     */
//    private PrivateKey getPrivateKey(String filename){
//
//        try {
//            return PemUtil.loadPrivateKey(new FileInputStream(filename));
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException("私钥文件不存在", e);
//        }
//    }

    /**
     * 获取签名验证器
     */
    @Bean
    public ScheduledUpdateCertificatesVerifier getVerifier(){

        log.info("获取签名验证器");

        //获取商户私钥
        //PrivateKey privateKey = getPrivateKey(privateKeyPath);
        PrivateKey privateKey = PemUtil.loadPrivateKey(Key);

        //私钥签名对象
        PrivateKeySigner privateKeySigner = new PrivateKeySigner(mchSerialNo, privateKey);

        //身份认证对象
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(mchId, privateKeySigner);

        // 使用定时更新的签名验证器，不需要传入证书
        ScheduledUpdateCertificatesVerifier verifier = new ScheduledUpdateCertificatesVerifier(
                wechatPay2Credentials,
                apiV3Key.getBytes(StandardCharsets.UTF_8));

        return verifier;
    }


    /**
     * 获取http请求对象
     */
    @Bean(name = "wxPayClient")
    public CloseableHttpClient getWxPayClient(ScheduledUpdateCertificatesVerifier verifier){

        log.info("获取httpClient");

        //获取商户私钥
        PrivateKey privateKey = PemUtil.loadPrivateKey(Key);

        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, privateKey)
                .withValidator(new WechatPay2Validator(verifier));
        // ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        CloseableHttpClient httpClient = builder.build();

        return httpClient;
    }

    /**
     * 获取HttpClient，无需进行应答签名验证，跳过验签的流程
     */
    @Bean(name = "wxPayNoSignClient")
    public CloseableHttpClient getWxPayNoSignClient(){

        //获取商户私钥
        PrivateKey privateKey = PemUtil.loadPrivateKey(Key);

        //用于构造HttpClient
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                //设置商户信息
                .withMerchant(mchId, mchSerialNo, privateKey)
                //无需进行签名验证、通过withValidator((response) -> true)实现
                .withValidator((response) -> true);

        // 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签，并进行证书自动更新
        CloseableHttpClient httpClient = builder.build();

        log.info("== getWxPayNoSignClient END ==");

        return httpClient;
    }

}
