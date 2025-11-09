package com.panda.academy.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;


@Converter(autoApply = true) // Apply this converter automatically to all StudentStatus fields
public class StudentStatusConverter implements AttributeConverter<StudentStatus, String> {

    // Converts the Java enum to the database string
    @Override
    public String convertToDatabaseColumn(StudentStatus status) {
        if (status == null) {
            return null;
        }
        return status.name().toLowerCase(); // store lowercase
    }

    @Override
    public StudentStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return StudentStatus.valueOf(dbData.toUpperCase()); // convert to enum
    }
}
