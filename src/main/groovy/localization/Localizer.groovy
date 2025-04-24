package localization

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j


@CompileStatic
@Slf4j
class Localizer {
    private String language;
    private static final String LANG_NAME = "message.json"
    private static Map<String, Map<String, String>> translation = [:]

//    static  {
//        def stream = getClass().getClassLoader().getResourceAsStream(LANG_NAME)
//        if (!stream) {log.error("Не удалось найти Json файл с локализацией")}
//        else {
//            def slurper = new JsonSlurper()
//            translation = slurper.parseText(stream.text) as Map<String, Map<String, String>>
//        }
//    }

    Localizer(String lang) {
        this.language = lang

        def stream = getClass().getClassLoader().getResourceAsStream(LANG_NAME)
        if (!stream) {log.error("Не удалось найти Json файл с локализацией")}
        else {
            def slurper = new JsonSlurper()
            translation = slurper.parseText(stream.text) as Map<String, Map<String, String>>
        }
    }

    String getMessage(String key)
    {
        def message = translation.get(language)?.get(key) ?: translation.get("ru").get(key)
        return message
    }
}
