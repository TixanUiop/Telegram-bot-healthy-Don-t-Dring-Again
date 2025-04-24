package Converter

import Entity.Gender
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter


@Converter(autoApply = true)
class GenderConverter implements AttributeConverter<Gender, String> {

    @Override
    String convertToDatabaseColumn(Gender gender) {
        gender.name()
    }

    @Override
    Gender convertToEntityAttribute(String s) {
        Gender.valueOf(s)
    }

}
