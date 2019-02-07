package com.sep.bank.bankservice.util;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FieldsGenerator {
    public String generateField(String base, int fieldSize) {
        String hashedBase = new BCryptPasswordEncoder().encode(base);  // base is for uniqueness

        // add some salt
        String generatedField = hashedBase + RandomStringUtils.randomAlphabetic(fieldSize);

        System.out.println(generatedField.length());
        if (generatedField.length() > fieldSize) {
            generatedField = generatedField.substring(0, fieldSize);
        }

        // if field is merchant password hash it again
        if (fieldSize == 100){
            System.out.println("PLAIN SIFRA JE: "+ generatedField);
            return new BCryptPasswordEncoder().encode(generatedField);
        }

        return generatedField;
    }
}
