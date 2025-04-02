package Util


import groovy.util.logging.Slf4j

@Slf4j
class getPropertiesFile {
    private static Properties properties = new Properties();

    static {
        loadProperties()
    }
    private static void loadProperties() {
        try (def stream = getPropertiesFile.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (stream) {
                properties.load(stream)
            }
            else {
                log.error("Файл конфигурации не найден")
            }
        }
        catch (Exception e) {
            log.error("Ошибка при загрузке файла конфигурации", e)
            throw e
        }
    }

    static String get(String key) {
        return properties.get(key)
    }

}
