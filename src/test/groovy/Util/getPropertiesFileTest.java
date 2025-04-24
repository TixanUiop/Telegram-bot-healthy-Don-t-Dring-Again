package Util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class getPropertiesFileTest {

    @Test
    void getTest() {
        String string = getPropertiesFile.get("bot.name");
        assertEquals("GroovyCheckingSubscribe_bot", string);
    }

    @Test
    void nullValues() {
        String string = getPropertiesFile.get("INVALID");
        assertNull(string);
    }
}