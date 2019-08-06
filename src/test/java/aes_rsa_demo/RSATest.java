package aes_rsa_demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import org.junit.Test;

public class RSATest {
    private static final String ALGOGRITHM = "RSA";
    private static final String PUBLIC_KEY_PATH = "public.key";
    private static final String PRIVATE_KEY_PATH = "private.key";

    @Test
    public void testGenerate() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //获取公钥
        PublicKey publicKey = keyPair.getPublic();
        //获取私钥
        PrivateKey privateKey = keyPair.getPrivate();

        //将公钥与私钥写入文件，以备后用
        writeKey(PUBLIC_KEY_PATH, publicKey);
        writeKey(PRIVATE_KEY_PATH, privateKey);
    }

    @Test
    public void testEncryptAndDecrypt() throws Exception {
        Cipher cipher = Cipher.getInstance(ALGOGRITHM);
        //读取公钥，进行加密
        PublicKey publicKey = (PublicKey) readKey(PUBLIC_KEY_PATH);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        //加密
        String sendInfo = "987432";
        System.out.println("加密前："+ sendInfo);

        byte[] results = cipher.doFinal(sendInfo.getBytes());
        String hexStrResult = ParseSystemUtil.parseByte2HexStr(results);

        System.out.println("加密后："+hexStrResult);

        //--------服务端
        //读取私钥，进行解密
        PrivateKey privateKey = (PrivateKey) readKey(PRIVATE_KEY_PATH);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        //解密
        byte[] twoStrResult = ParseSystemUtil.parseHexStr2Byte(hexStrResult);

        byte[] deciphered = cipher.doFinal(twoStrResult);

        //得到明文
        String recvInfo = new String(deciphered);
        System.out.println("解密后："+recvInfo);
    }

    public void writeKey(String path, Key key) throws Exception {
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(key);
        oos.close();
    }

    public Key readKey(String path) throws Exception {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream bis = new ObjectInputStream(fis);
        Object object = bis.readObject();
        bis.close();
        return (Key) object;
    }
}