package DAO

import Entity.TelegramUser
import Util.HibernateUtil
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration


@Slf4j
@CompileStatic
class UserRepository implements DAO<TelegramUser, Long> {
    private Configuration configuration = HibernateUtil.getConfiguration();
    private static UserRepository INSTANCE = new UserRepository();

    @Override
    Optional<TelegramUser> create(TelegramUser telegramUser) {
        Optional<TelegramUser> user;

        try (SessionFactory factory = configuration.buildSessionFactory();
          def open = factory.openSession()) {
            open.beginTransaction()
            open.merge(telegramUser)
            open.getTransaction().commit()

        }
        catch (Exception e) {
            log.error("Ошибка при занесения пользователя в Бд ddfdfdfdfdfdfdfdffd", e)
        }
        return user
    }



    @Override
    Optional<TelegramUser> read(Long id) {
        Optional<TelegramUser> user;
        Session session;

        try
        {
            SessionFactory factory = configuration.buildSessionFactory()
            session = factory.openSession()

            session.beginTransaction()
            user = session.get(TelegramUser.class, id) as Optional<TelegramUser>
            session.getTransaction().commit()
        }
        catch (Exception e) {
            log.error("Ошибка поиска пользователя по Id", e)

            // Откат транзакции в случае ошибки
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close()
            }
        }

        return user
    }

    @Override
    Optional<TelegramUser> delete(Long id) {
        return null
    }

    @Override
    Optional<TelegramUser> update(Long id) {
        return null
    }

    static UserRepository getInstance(){
        return INSTANCE
    }
    private UserRepository() {}
}
