package com.example.aspect.EncryptDecrypt;

import javax.persistence.Converter;




@Converter(autoApply = false)
public class LongEncryptDecryptConverter
        extends AbstractEncryptDecryptConverter<Long> {


    public LongEncryptDecryptConverter() {
        this(new CipherMaker());
    }


    public LongEncryptDecryptConverter(CipherMaker cipherMaker) {
        super(cipherMaker);
    }

    @Override
    boolean isNotNullOrEmpty(Long attribute) {
        return  attribute != null;
    }

    @Override
    Long convertStringToEntityAttribute(String dbData) {
        return Long.parseLong(dbData);
    }

    @Override
    String convertEntityAttributeToString(Long attribute) {
        return Long.toString(attribute);
    }
}