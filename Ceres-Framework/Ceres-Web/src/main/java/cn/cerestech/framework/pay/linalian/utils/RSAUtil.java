package cn.cerestech.framework.pay.linalian.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * RSA签名公共类
 * @author shmily
 */
public class RSAUtil{

    private static RSAUtil instance;

    private RSAUtil()
    {

    }

    public static RSAUtil getInstance()
    {
        if (null == instance)
            return new RSAUtil();
        return instance;
    }

    /**
     * 公钥、私钥文件生成
     * @param keyPath：保存文件的路径
     * @param keyFlag：文件名前缀
     */
    private void generateKeyPair(String key_path, String name_prefix)
    {
        java.security.KeyPairGenerator keygen = null;
        try
        {
            keygen = java.security.KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e1)
        {
           e1.printStackTrace();
        }
        SecureRandom secrand = new SecureRandom();
        secrand.setSeed("21cn".getBytes()); // 初始化随机产生器
        keygen.initialize(1024, secrand);
        KeyPair keys = keygen.genKeyPair();
        PublicKey pubkey = keys.getPublic();
        PrivateKey prikey = keys.getPrivate();

        String pubKeyStr = Base64.getBASE64(pubkey.getEncoded());
        String priKeyStr = Base64.getBASE64(prikey.getEncoded());
        File file = new File(key_path);
        if (!file.exists())
        {
            file.mkdirs();
        }
        try
        {
            // 保存私钥
            FileOutputStream fos = new FileOutputStream(new File(key_path
                    + name_prefix + "_RSAKey_private.txt"));
            fos.write(priKeyStr.getBytes());
            fos.close();
            // 保存公钥
            fos = new FileOutputStream(new File(key_path + name_prefix
                    + "_RSAKey_public.txt"));
            fos.write(pubKeyStr.getBytes());
            fos.close();
        } catch (IOException e)
        {
           e.printStackTrace();
        }
    }

    /**
     * 读取密钥文件内容
     * @param key_file:文件路径
     * @return
     */
    private static String getKeyContent(String key_file)
    {
        File file = new File(key_file);
        BufferedReader br = null;
        InputStream ins = null;
        StringBuffer sReturnBuf = new StringBuffer();
        try
        {
            ins = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            String readStr = null;
            readStr = br.readLine();
            while (readStr != null)
            {
                sReturnBuf.append(readStr);
                readStr = br.readLine();
            }
        } catch (IOException e)
        {
            return null;
        } finally
        {
            if (br != null)
            {
                try
                {
                    br.close();
                    br = null;
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (ins != null)
            {
                try
                {
                    ins.close();
                    ins = null;
                } catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        return sReturnBuf.toString();
    }

    /**
     * 签名处理
     * @param prikeyvalue：私钥文件
     * @param sign_str：签名源内容
     * @return
     */
    public static String sign(String prikeyvalue, String sign_str)
    {
        try
        {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64
                    .getBytesBASE64(prikeyvalue));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey myprikey = keyf.generatePrivate(priPKCS8);
            // 用私钥对信息生成数字签名
            java.security.Signature signet = java.security.Signature
                    .getInstance("MD5withRSA");
            signet.initSign(myprikey);
            signet.update(sign_str.getBytes("UTF-8"));
            byte[] signed = signet.sign(); // 对信息的数字签名
            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(signed));
        } catch (java.lang.Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 签名验证
     * @param pubkeyvalue：公钥
     * @param oid_str：源串
     * @param signed_str：签名结果串
     * @return
     */
    public static boolean checksign(String pubkeyvalue, String oid_str,
            String signed_str)
    {
        try
        {
            X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64
                    .getBytesBASE64(pubkeyvalue));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
            byte[] signed = Base64.getBytesBASE64(signed_str);// 这是SignatureData输出的数字签名
            java.security.Signature signetcheck = java.security.Signature
                    .getInstance("MD5withRSA");
            signetcheck.initVerify(pubKey);
            signetcheck.update(oid_str.getBytes("UTF-8"));
            return signetcheck.verify(signed);
        } catch (java.lang.Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args)
    {
        // 商户（RSA）私钥 TODO 强烈建议将私钥
        String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANRpDW7JQ1xwgDxjsnEmxlNqDSK16tDy/v5ARr5Y+DuDUAN+dHr5LzLlS9KasBo2hu1ALSa+gUZ0OHOXdsik40h0B7v3ktUSvVBOHFQy6wN57+nAwz5tftxzeYnaCzZgDpxjK8Shn2c0SvcWM1gMKhhvY5fuVMAjU2pPAb3rA5hjAgMBAAECgYAhnPo+NOVPBJKWe+RqGYu6+YZYntco97s4eu13A9dMe6w20VUXfMVmVXjZPBdwHn7dnpFGl1EX2B5y1F48xDMfIweW4YHjCxvStdjmLuNUhncn5VOWZM9LauSKxAvZnxgaaXopJCPeylX4OkapgYoap8TLtNmgRrT5DnH50ZKpCQJBAP00HLT4dFmAUE1bupNmtKvPgCpcMVLD5VpzKQKmnCVIwXvC464xfMh9fDVMlIjKKHOmx8i6NRznBpghKmcrf+cCQQDWwZrW3LckpIFhjH2vktYtr+STuYcuyBd0Qj71yeookA5B6Z3HGKIqUkRk9vZCSsc7m/JX4JGuffhzfRFRIYQlAkBrtBAF9q1fKNJ/pWYerxBpCNGmsyKT5xoXOGcYZpCC14jdwQ+iGBDRI3eDIHkKGpvMXgQbYQGYsri+W1UzH3C/AkB2e1fu5NSR/cR3yifpfsx1Zk5ohfokADaYaJgNyLlMabXD/ZyTpG6LhNnBDlNs3Y6vv2jjvL0DFPLG3KB6L1CVAkEA6qn7bV04ffyvDMWGJrl0ra1lSVEAHfFlYPtpa+X51Z7tbwgAr0tCZ/0U5lczy7jtNXkEetUAUFPTxEiGrht3hg==";
        // 银通支付（RSA）公      "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSS/DiwdCf/aZsxxcacDnooGph3d2JOj5GXWi+q3gznZauZjkNP8SKl3J2liP0O6rU/Y/29+IUe+GTMhMOFJuZm1htAtKiu5ekW0GlBMWxf4FPkYlQkPE0FtaoMP3gYfh+OwI+fIRrpW3ySn3mScnc6Z700nU/VYrRkfcSCbSnRwIDAQAB
        String RSA_YT_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDUaQ1uyUNccIA8Y7JxJsZTag0iterQ8v7+QEa+WPg7g1ADfnR6+S8y5UvSmrAaNobtQC0mvoFGdDhzl3bIpONIdAe795LVEr1QThxUMusDee/pwMM+bX7cc3mJ2gs2YA6cYyvEoZ9nNEr3FjNYDCoYb2OX7lTAI1NqTwG96wOYYwIDAQAB";

//         RSAUtil.getInstance().generateKeyPair("/Users/bird/Downloads/RSA/RSA/xx/",
//         "ll_yt");
        String sign = RSAUtil.sign(RSA_PRIVATE, "busi_partner=101001&dt_order=20130521175800&money_order=12.10&name_goods=%E5%95%86%E5%93%81%E5%90%8D%E7%A7%B0&notify_url=http%3A%2F%2Fwww.baidu.com&no_order=20130521175800&oid_partner=201507021000394502&sign_type=RSA");
//        
        System.out.println(sign);
        System.out.println(RSAUtil.checksign(RSA_YT_PUBLIC, "busi_partner=101001&dt_order=20130521175800&money_order=12.10&name_goods=%E5%95%86%E5%93%81%E5%90%8D%E7%A7%B0&notify_url=http%3A%2F%2Fwww.baidu.com&no_order=20130521175800&oid_partner=201507021000394502&sign_type=RSA", sign));
    }
}
