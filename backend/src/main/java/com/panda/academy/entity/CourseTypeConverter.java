package com.panda.academy.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CourseTypeConverter implements AttributeConverter<CourseType, String>{
    @Override
    public String convertToDatabaseColumn(CourseType courseType) {
        if (courseType == null) {
            return null;
        }
        return courseType.name().toLowerCase(); // store lowercase
    }

    @Override
    public CourseType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return CourseType.valueOf(dbData.toUpperCase()); // convert to enum
    }
}
