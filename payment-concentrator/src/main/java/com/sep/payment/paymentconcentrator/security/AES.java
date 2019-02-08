package com.sep.payment.paymentconcentrator.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


@Service
public class AES {
    @Value("${encryption.key}")
    private String encryptionKey;

    public String encrypt(String strToEncrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(strToEncrypt.getBytes());

            // get initial vector
            byte[] iv = cipher.getIV();

            String encryptedDataString = Base64.encodeBase64String(encryptedData);
            String ivString = Base64.encodeBase64String(iv);

            return ivString + encryptedDataString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String ivAndStringToDecrypt) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");

            String ivString = ivAndStringToDecrypt.substring(0, 24);
            String strToDecrypt = ivAndStringToDecrypt.substring(24, ivAndStringToDecrypt.length());

            // generate iv from iv byte array
            IvParameterSpec iv = new IvParameterSpec(Base64.decodeBase64(ivString));

            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(strToDecrypt));

            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
