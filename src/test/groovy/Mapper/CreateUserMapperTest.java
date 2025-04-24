package Mapper;

import DTO.CreateUserDto;
import Entity.Gender;
import Entity.TelegramUserProfile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserMapperTest {

    private static CreateUserDto createUserDto;
    private static TelegramUserProfile telegramUserProfileEx = new TelegramUserProfile();
    @BeforeAll
    static void setUp() {
        createUserDto = CreateUserDto.builder()
                .nickname("Test")
                .chatId(12321L)
                .gender(Gender.MALE)
                .birthday(LocalDate.of(2003,  12, 3))
                .build();

        telegramUserProfileEx.setNickName(createUserDto.getNickname());
        telegramUserProfileEx.setBirthday(createUserDto.getBirthday());
        telegramUserProfileEx.setGender(createUserDto.getGender());
    }
    @Test
    void toMap() {
        CreateUserMapper instance = CreateUserMapper.getINSTANCE();

        TelegramUserProfile telegramUserProfileResult = instance.ToMap(createUserDto);

        assertEquals(telegramUserProfileEx.getNickName(), telegramUserProfileResult.getNickName());
        assertEquals(telegramUserProfileEx.getBirthday(), telegramUserProfileResult.getBirthday());
        assertEquals(telegramUserProfileEx.getGender(), telegramUserProfileResult.getGender());
    }

    @Test
    void nullValues() {
        CreateUserMapper instance = CreateUserMapper.getINSTANCE();
        assertThrows(NullPointerException.class, () -> instance.ToMap(null));
    }
}