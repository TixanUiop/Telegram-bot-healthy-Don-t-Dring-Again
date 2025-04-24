package Util

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.hibernate.cfg.Configuration


@Slf4j
@CompileStatic
final class HibernateUtil {
    private static Configuration configuration;

    private static Configuration getConfig() {
        try {
            log.info("Загрузка конфигурации файла");
            configuration = new Configuration().configure("hibernate.test.cfg.xml");
            log.info("Конфигурация успешно загружена");
        } catch (Exception e) {
            log.error("Ошибка конфигурации файла Configuration", e);
        }
        return configuration
    }


    static Configuration getConfiguration() {
        def configuration = getConfig()
        if (configuration != null)
        {
            log.error("Файл конфигурации успешно создан")
            return configuration
        }
        else {
            log.error("Файл конфигурации null")
            return null
        }
    }
}
