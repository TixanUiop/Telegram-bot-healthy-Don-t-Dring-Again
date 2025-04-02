package localization

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j


@CompileStatic
@Slf4j
class Localizer {

    private static final String LANG_NAME = "message.json"
    private Map<String, Map<String, String>> translation = [:]

    Localizer() {
        def stream = getClass().getClassLoader().getResourceAsStream(LANG_NAME)
        if (!stream) {log.error("Не удалось найти Json файл с локализацией")}
        else {
            def slurper = new JsonSlurper()
            translation = slurper.parseText(stream.text) as Map<String, Map<String, String>>
        }
    }


    def getMessage(String lang, String key)
    {
        def message = translation.get(lang)?.get(key) ?: translation.get("ru").get(key)
        if (message) {
            message
        }
        else {
            log.error("Ошибка локализации. Выбранный язык не существует")
        }
    }
}
