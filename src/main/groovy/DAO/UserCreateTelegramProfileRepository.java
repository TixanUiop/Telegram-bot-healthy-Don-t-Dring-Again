package DAO;

import Exception.TelegramProfileException;
import Entity.TelegramUserProfile;
import Util.HibernateUtil;
import groovy.util.logging.Slf4j;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Optional;



@lombok.extern.slf4j.Slf4j
@Slf4j
public class UserCreateTelegramProfileRepository implements DAO<TelegramUserProfile, Long>{
    private static final UserCreateTelegramProfileRepository INSTANCE = new UserCreateTelegramProfileRepository();
    private SessionFactory sessionFactory;
    private Configuration cfg = HibernateUtil.getConfiguration();


    @Override
    @SneakyThrows
    public Optional<TelegramUserProfile> create(TelegramUserProfile user) {
        TelegramUserProfile telegramUserProfile = null;
        try {
            Session currentSession = sessionFactory.getCurrentSession();
            currentSession.beginTransaction();

            telegramUserProfile = currentSession.merge(user);

            currentSession.getTransaction().commit();
            log.info("Успешное создание Profile");

        } catch (Exception e) {
            log.error("Ошибка при создании Profile", e);
            throw new TelegramProfileException("Ошибка при создании Profile", e);
        }

        return Optional.of(telegramUserProfile);
    }

    @Override
    public Optional<TelegramUserProfile> delete(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<TelegramUserProfile> read(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<TelegramUserProfile> update(Long id) {
        return Optional.empty();
    }




    public static UserCreateTelegramProfileRepository getInstance() {
        return INSTANCE;
    }
    private UserCreateTelegramProfileRepository() {
        this.sessionFactory = cfg.buildSessionFactory();
    }

}
