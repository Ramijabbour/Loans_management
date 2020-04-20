package com.example.aspect.EncryptDecrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
public class CipherMaker {

    private static final String CIPHER_INSTANCE_NAME = "AES/CBC/PKCS5Padding";
    private static final String SECRET_KEY_ALGORITHM = "AES";
    public Cipher configureAndGetInstance(int encryptionMode, String key)
            throws InvalidKeyException, NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, InvalidAlgorithmParameterException {

        Cipher cipher = Cipher.getInstance(CIPHER_INSTANCE_NAME);
        Key secretKey = new SecretKeySpec(key.getBytes(), SECRET_KEY_ALGORITHM);

        byte[] ivBytes = new byte[cipher.getBlockSize()];
        AlgorithmParameterSpec algorithmParameters = new IvParameterSpec(
                ivBytes);

        cipher.init(encryptionMode, secretKey, algorithmParameters);
        return cipher;
    }
}
