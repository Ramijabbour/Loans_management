package com.example.aspect.EncryptDecrypt;

import javax.persistence.Converter;
@Converter(autoApply = false)
public class DoubleEncryptDecryptConverter
        extends AbstractEncryptDecryptConverter<Double> {


    public DoubleEncryptDecryptConverter() {
        this(new CipherMaker());
    }


    public DoubleEncryptDecryptConverter(CipherMaker cipherMaker) {
        super(cipherMaker);
    }

    @Override
    boolean isNotNullOrEmpty(Double attribute) {
        return  attribute != null;
    }

    @Override
    Double convertStringToEntityAttribute(String dbData) {
        return  Double.parseDouble(dbData);
    }

    @Override
    String convertEntityAttributeToString(Double attribute) {
        return Double.toString(attribute);
    }
}