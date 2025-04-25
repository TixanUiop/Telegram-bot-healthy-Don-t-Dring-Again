package Service

import DAO.UserCreateTelegramProfileRepository
import DAO.UserRepository
import DTO.CreateTelegramUser
import DTO.CreateUserDto
import Exception.TelegramUserException
import Exception.TelegramUserExceptionNonId
import Mapper.CreateTelegramUserMapper
import Mapper.CreateUserMapper
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation

@Slf4j
@CompileStatic
class UserService {
    private static UserService INSTANCE = new UserService()

    private UserRepository repository = UserRepository.instance
    private CreateTelegramUserMapper createTelegramUserMapper = CreateTelegramUserMapper.getINSTANCE()
    private CreateUserMapper createUserMapper = CreateUserMapper.getINSTANCE()
    private UserCreateTelegramProfileRepository profile = UserCreateTelegramProfileRepository.getInstance()


    Long saveTelegramUser(CreateTelegramUser createUserDto) throws TelegramUserException {
        if (createUserDto.chatId != null) {
            def map = createTelegramUserMapper.ToMap(createUserDto)
            def create = repository.create(map)
            return create.get().telegramId
        }
        else {
            log.error("Id is empty")
            throw new TelegramUserExceptionNonId("Id is empty")
        }

    }

    Long saveUserProfile(CreateUserDto createUserDto) {
        //validation
        def factory = Validation.buildDefaultValidatorFactory()
        def validator = factory.getValidator()
        def validate = validator.validate(createUserDto)
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate)
        }

        //Mapper
        def map = createUserMapper.ToMap(createUserDto)

        def create = profile.create(map)
        return create.get().getId()
    }


    //Testing
    UserService(UserRepository repository, CreateTelegramUserMapper createTelegramUserMapper) {
        this.repository = repository
        this.createTelegramUserMapper = createTelegramUserMapper
    }


    private UserService() {}
    static UserService getINSTANCE() {
        INSTANCE
    }
}
