package Mapper;

import DTO.CreateTelegramUser;
import Entity.TelegramUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateTelegramUserMapperTest {

    private CreateTelegramUserMapper createTelegramUserMapper = CreateTelegramUserMapper.getINSTANCE();

    @Test
    void toMap() {
        CreateTelegramUser input = CreateTelegramUser.builder()
                .chatId(23L)
                .build();

        TelegramUser output = createTelegramUserMapper.ToMap(input);

        assertEquals(23L, output.getTelegramId());
    }
}