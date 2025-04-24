package Converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

import java.sql.Date

import java.time.LocalDate

@Converter(autoApply = true)
class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    Date convertToDatabaseColumn(LocalDate localDate) {
        Date.valueOf(localDate)
    }

    @Override
    LocalDate convertToEntityAttribute(Date date) {
        date.toLocalDate()
    }
}
