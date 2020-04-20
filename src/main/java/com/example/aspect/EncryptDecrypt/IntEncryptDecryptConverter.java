package com.example.aspect.EncryptDecrypt;

import javax.persistence.Converter;




@Converter(autoApply = false)
public class IntEncryptDecryptConverter
        extends AbstractEncryptDecryptConverter<Integer> {


    public IntEncryptDecryptConverter() {
        this(new CipherMaker());
    }


    public IntEncryptDecryptConverter(CipherMaker cipherMaker) {
        super(cipherMaker);
    }

    @Override
    boolean isNotNullOrEmpty(Integer attribute) {
        return  attribute != null;
    }

    @Override
    Integer convertStringToEntityAttribute(String dbData) {
        return Integer.parseInt(dbData);
    }

    @Override
    String convertEntityAttributeToString(Integer attribute) {
        return Integer.toString(attribute);
    }
}