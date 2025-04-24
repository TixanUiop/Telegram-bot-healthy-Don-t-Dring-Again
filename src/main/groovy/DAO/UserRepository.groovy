package DAO

import Entity.TelegramUser
import Exception.TelegramUserException
import Util.HibernateUtil
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration


@Slf4j
@CompileStatic
class UserRepository implements DAO<TelegramUser, Long> {
    private Configuration configuration = HibernateUtil.getConfiguration()
    private static UserRepository INSTANCE = new UserRepository()
    private SessionFactory factory


    @Override
    Optional<TelegramUser> create(TelegramUser telegramUser) {
        Optional<TelegramUser> user

        try (SessionFactory factory = configuration.buildSessionFactory()
          def open = factory.getCurrentSession()) {
            open.beginTransaction()
            open.merge(telegramUser)
            open.getTransaction().commit()

        }
        catch (Exception e) {
            log.error("Ошибка при занесения пользователя в Бд", e)
            throw new TelegramUserException(e.toString())
        }
        return user
    }



    @Override
    Optional<TelegramUser> read(Long id) {
        TelegramUser user;
        Session session;

        try
        {
            SessionFactory factory = configuration.buildSessionFactory()
            session = factory.openSession()

            session.beginTransaction()
            def query = session.createQuery("FROM TelegramUser WHERE telegramId = :id", TelegramUser.class);
            query.setParameter("id", id)
            user = query.uniqueResult()
            session.flush()
            session.getTransaction().commit()
        }
        catch (Exception e) {
            log.error("Ошибка поиска пользователя по Id", e)

            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
        }
        finally {
            if (session != null && session.isOpen()) {
                session.close()
            }
        }

        return Optional<TelegramUser>.ofNullable(user)

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
    private UserRepository() {
        factory = configuration.buildSessionFactory()
    }
}
