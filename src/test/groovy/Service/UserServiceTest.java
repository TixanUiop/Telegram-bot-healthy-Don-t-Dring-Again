package Service;

import DAO.UserRepository;
import DTO.CreateTelegramUser;
import DTO.CreateUserDto;
import Entity.Gender;
import Entity.TelegramUser;
import Mapper.CreateTelegramUserMapper;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import Exception.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CreateTelegramUserMapper createTelegramUserMapper;


    private UserService userService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, createTelegramUserMapper);
    }


    @Test
    void saveTelegramUser() {
        Long testId = 123L;

        CreateTelegramUser createTelegramUser = CreateTelegramUser.builder()
                .chatId(testId)
                .build();
        TelegramUser mockUser = new TelegramUser(testId);

        when(createTelegramUserMapper.ToMap(createTelegramUser)).thenReturn(mockUser);
        when(userRepository.create(mockUser)).thenReturn(Optional.of(mockUser));


        Long result = userService.saveTelegramUser(createTelegramUser);
        assertNotNull(result);
        assertEquals(123L, result);

    }

    @Test
    void nullValuesSaveTelegramUser() {
        Long testId = 123L;
        CreateTelegramUser createTelegramUser = CreateTelegramUser.builder()
                .chatId(null)
                .build();
        assertThrows(TelegramUserExceptionNonId.class, () -> userService.saveTelegramUser(createTelegramUser));
    }

    @Test
    void saveUserProfileOnException() {
        assertThrows(ConstraintViolationException.class, () -> userService.saveUserProfile(getCreateUserDTO()));
    }


    private static CreateUserDto getCreateUserDTO() {
        CreateUserDto user = CreateUserDto.builder()
                .nickname(null)
                .birthday(LocalDate.of(2003, 12, 3))
                .gender(Gender.FEMALE)
                .build();
        return user;
    }
}