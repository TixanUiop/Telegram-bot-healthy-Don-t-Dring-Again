import DAO.UserRepository
import Entity.TelegramUser
import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j
import localization.Localizer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import Util.getPropertiesFile
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Slf4j
@CompileStatic

class TelegramBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "bot.token"
    private static final String BOT_NAME = "bot.name"
    private static Localizer localizer = new Localizer()
    private UserRepository repository = UserRepository.getInstance();



    @Override
    void onUpdateReceived(Update update) {
        def message = update.getMessage()
        def chatId = update.getMessage().getChatId()

        //If user got in the first time
        //TODO Добавить Git и реализовать заполнения данных об имени.
        if (checkUserOnFirstLogin(chatId)) {
            //Сохранён
        }
        else {
            //уже такая сессия есть
        }


        //If user write /start
        if (message && message.getText() == '/start') {
            sendDefaultMessageToStart(message)
        }
    }



    private void sendMessage(Long chatId, String message) {
        def sendMessage = new SendMessage(chatId.toString(), message)
        try {
            execute(sendMessage)
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения")
            e.printStackTrace()
        }
    }

    private void sendDefaultMessageToStart(Message message) {

        Long id = message.getChatId()
        String answer = localizer.getMessage("ru", "start_bot_message")

        sendMessage(id, answer)

    }


    @Override
    String getBotToken() {
        getPropertiesFile.get(BOT_TOKEN)
    }


    @Override
    String getBotUsername() {
        getPropertiesFile.get(BOT_NAME)
    }



    private boolean checkUserOnFirstLogin(Long chatId) {
        def read = repository.read(chatId);
        if (read == null) {
            def create = repository.create(buildTelegramUser(chatId))
            log.info("Сессия пользователя сохранена в базе данных")
            true
        }
        else {
            log.info("Сессия пользователя уже существует в базе данных")
            false
        }
    }

    private static TelegramUser buildTelegramUser(Long id) {
        new TelegramUser(id)
    }
}
