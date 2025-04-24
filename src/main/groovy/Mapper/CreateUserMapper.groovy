package Mapper

import DTO.CreateUserDto
import Entity.TelegramUserProfile

class CreateUserMapper implements Mapper<CreateUserDto, TelegramUserProfile> {

    private static CreateUserMapper INSTANCE = new CreateUserMapper()


    @Override
    TelegramUserProfile ToMap(CreateUserDto userDTO) {
        TelegramUserProfile profile = new TelegramUserProfile()
        profile.setNickName(userDTO.nickname)
        profile.setBirthday(userDTO.birthday)
        profile.setGender(userDTO.gender)
        return profile
    }



    private CreateUserMapper() {}
    static CreateUserMapper getINSTANCE() {
        INSTANCE
    }
}
