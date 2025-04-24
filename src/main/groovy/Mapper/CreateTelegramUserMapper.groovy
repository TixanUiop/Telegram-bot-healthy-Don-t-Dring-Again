package Mapper

import DTO.CreateTelegramUser
import Entity.TelegramUser

class CreateTelegramUserMapper implements Mapper<CreateTelegramUser, TelegramUser> {

    private static CreateTelegramUserMapper INSTANCE = new CreateTelegramUserMapper()


    @Override
    TelegramUser ToMap(CreateTelegramUser entity) {
        new TelegramUser(entity.chatId)
    }


    static CreateTelegramUserMapper getINSTANCE() {
        INSTANCE
    }
    CreateTelegramUserMapper() {}
}
