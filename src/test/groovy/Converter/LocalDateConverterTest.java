package Converter;

import groovy.lang.GroovyRuntimeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class LocalDateConverterTest {

    private LocalDateConverter localDateConverter;
    private LocalDate date;
    private Date dateSQL;

    @BeforeEach
    void setUp() {
        localDateConverter = new LocalDateConverter();
        date = LocalDate.of(2020, 1, 1);
        dateSQL = Date.valueOf(date);

    }
    @AfterEach
    void tearDown() {
        localDateConverter = null;
    }

    @Test
    void convertToDatabaseColumnTest() {
        Date dateResult = localDateConverter.convertToDatabaseColumn(date);
        assertEquals(dateSQL, dateResult);
    }

    @Test
    void convertToEntityAttributeTest() {
        LocalDate localDate = localDateConverter.convertToEntityAttribute(dateSQL);
        assertEquals(date, localDate);
    }

    @Test
    void nullValuesTest_convertToDatabaseColumn() {
        assertThrows(GroovyRuntimeException.class, () -> localDateConverter.convertToDatabaseColumn(null));
    }

    @Test
    void nullValuesTest_convertToEntityAttribute() {
        assertThrows(NullPointerException.class, () -> localDateConverter.convertToEntityAttribute(null));
    }

}