import groovy.transform.CompileStatic
import localization.Localizer
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession


@CompileStatic
static void main(String[] args) {

//    Localizer localizer = new Localizer()
//    localizer.getMessage("ru", "start_bot_message").each {
//        print(it)
//    }

    try {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession)
        botsApi.registerBot(new TelegramBot())
        println("Бот запущен!")
    } catch (Exception e) {
        e.printStackTrace()
    }

}