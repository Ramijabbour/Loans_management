package com.example.aspect.EncryptDecrypt;

import javax.persistence.Converter;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Converter(autoApply = false)
public class LocalDateEncryptDecryptConverter
        extends AbstractEncryptDecryptConverter<LocalDateTime> {

    public LocalDateEncryptDecryptConverter() {
        this(new CipherMaker());
    }

    public LocalDateEncryptDecryptConverter(CipherMaker cipherMaker) {
        super(cipherMaker);
    }

    @Override
    boolean isNotNullOrEmpty(LocalDateTime attribute) {
        return attribute != null;
    }

    @Override
    LocalDateTime convertStringToEntityAttribute(String dbData) {
        return isEmpty(dbData) ? null : LocalDateTime.parse(dbData, ISO_DATE);
    }

    @Override
    String convertEntityAttributeToString(LocalDateTime attribute) {
        return ((attribute == null) ? null : attribute.format(ISO_DATE));
    }
}
