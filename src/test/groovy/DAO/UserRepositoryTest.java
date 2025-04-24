package DAO;

import Entity.TelegramUser;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import Exception.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    public static UserRepository userRepository;
    private static SessionFactory sessionFactory;
    @BeforeAll
    public static void setUp() {
        sessionFactory = HibernateUtil.getConfiguration().buildSessionFactory();
        userRepository = UserRepository.getInstance();
    }
    @AfterAll
    public static void tearDown() {
        sessionFactory.close();
    }

    @Test
    void createSuccess() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            TelegramUser user = new TelegramUser();
            user.setTelegramId(123L);

            session.save(user);

            Integer id = user.getId();

            TelegramUser telegramUser = session.get(TelegramUser.class, id);
            session.getTransaction().commit();

            assertNotNull(telegramUser);
            assertEquals(telegramUser.getTelegramId(), 123L);

        }
    }
    @Test
    void createFailed() {
        TelegramUser user = new TelegramUser(null);
        assertThrows(TelegramUserException.class, () -> {
            userRepository.create(user);
        });
    }

    @Test
    void read() {
        Long testId = 123L;
        TelegramUser user = new TelegramUser(testId);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }

        Optional<TelegramUser> fetchedUser = userRepository.read(testId);

        assertTrue(fetchedUser.isPresent());
        assertEquals(testId, fetchedUser.get().getTelegramId());
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }
}