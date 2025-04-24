package Converter;

import Entity.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenderConverterTest {
    private GenderConverter genderConverter = new GenderConverter();

    @Test
    void convertToDatabaseColumn() {
        String stringMale = genderConverter.convertToDatabaseColumn(Gender.MALE);
        String stringFemale = genderConverter.convertToDatabaseColumn(Gender.FEMALE);

        assertEquals("MALE", stringMale);
        assertEquals("FEMALE", stringFemale);
    }

    @Test
    void convertToEntityAttribute() {
        assertEquals(Gender.MALE, genderConverter.convertToEntityAttribute("MALE"));
        assertEquals(Gender.FEMALE, genderConverter.convertToEntityAttribute("FEMALE"));
    }

    @Test
    void testNullValues()
    {
        assertThrows(NullPointerException.class, () -> {genderConverter.convertToEntityAttribute(null);});
        assertThrows(IllegalArgumentException.class, () -> {genderConverter.convertToEntityAttribute("null");});
    }
}