package com.example.aspect.EncryptDecrypt;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.AttributeConverter;

public abstract class AbstractEncryptDecryptConverter<X>
        implements AttributeConverter<X, String> {
    private static final String SECRET_ENCRYPTION_KEY = "MySuperSecretKey";
    private CipherMaker cipherMaker;

    public AbstractEncryptDecryptConverter(CipherMaker cipherMaker) {
        this.cipherMaker = cipherMaker;
    }
    public AbstractEncryptDecryptConverter() {
        this(new CipherMaker());
    }

    @Override
    public String convertToDatabaseColumn(X attribute) {
        if (isNotEmpty(SECRET_ENCRYPTION_KEY) && isNotNullOrEmpty(attribute)) {
            try {
                Cipher cipher = cipherMaker.configureAndGetInstance(
                        Cipher.ENCRYPT_MODE,
                        SECRET_ENCRYPTION_KEY);
                return encryptData(cipher, attribute);
            } catch (NoSuchAlgorithmException
                    | InvalidKeyException
                    | InvalidAlgorithmParameterException
                    | BadPaddingException
                    | NoSuchPaddingException
                    | IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            }
        }
        return convertEntityAttributeToString(attribute);
    }

    @Override
    public X convertToEntityAttribute(String dbData) {
        if (isNotEmpty(SECRET_ENCRYPTION_KEY) && isNotEmpty(dbData)) {
            try {
                Cipher cipher = cipherMaker.configureAndGetInstance(
                        Cipher.DECRYPT_MODE,
                        SECRET_ENCRYPTION_KEY);
                return decryptData(cipher, dbData);
            } catch (NoSuchAlgorithmException
                    | InvalidAlgorithmParameterException
                    | InvalidKeyException
                    | BadPaddingException
                    | NoSuchPaddingException
                    | IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            }
        }
        return convertStringToEntityAttribute(dbData);
    }

    abstract boolean isNotNullOrEmpty(X attribute);


    abstract X convertStringToEntityAttribute(String dbData);


    abstract String convertEntityAttributeToString(X attribute);


    private String encryptData(Cipher cipher, X attribute)
            throws IllegalBlockSizeException, BadPaddingException {
        byte[] bytesToEncrypt = convertEntityAttributeToString(attribute)
                .getBytes();
        byte[] encryptedBytes = cipher.doFinal(bytesToEncrypt);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private X decryptData(Cipher cipher, String dbData)
            throws IllegalBlockSizeException, BadPaddingException {
        byte[] bytesToDecrypt = Base64.getDecoder().decode(dbData);
        byte[] decryptedBytes = cipher.doFinal(bytesToDecrypt);
        return convertStringToEntityAttribute(new String(decryptedBytes));
    }
}