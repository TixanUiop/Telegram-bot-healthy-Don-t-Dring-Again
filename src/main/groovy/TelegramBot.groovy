import DAO.UserRepository
import DTO.CreateUserDto
import Entity.Gender
import Entity.SessionStatus
import Entity.TelegramUser
import Service.UserService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import jakarta.validation.ConstraintViolationException
import localization.Localizer
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import Util.getPropertiesFile
import Util.getButtons

import org.telegram.telegrambots.meta.exceptions.TelegramApiException

import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Slf4j
@CompileStatic

class TelegramBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "bot.token"
    private static final String BOT_NAME = "bot.name"

    private static Localizer localizer
    private UserRepository repository = UserRepository.getInstance()
    private Set<Long> sessionArray = new HashSet<>()
    private Map<Long, String> sessionScope = new HashMap<>()
    //session collector data list
    private Map<Long, CreateUserDto> sessionScopeDatalist = new HashMap<>()
    private getButtons buttons
    private UserService service = UserService.INSTANCE



    @Override
    void onUpdateReceived(Update update) {

        if (update.getMessage() != null) {
            def message = update.getMessage()
            def chatId = update.getMessage().getChatId()
            def languageCode = update.getMessage().getFrom().getLanguageCode()
            localizer = new Localizer(languageCode)
            buttons = new getButtons(this, localizer)


            if (message.getText() == "/start") {
                sendMessage(chatId, localizer.getMessage("start_bot_message"))
            }

            //Добавлять в массив только когда есть в БД
            if (checkSessionInSession(chatId) || checkUserOnDB(chatId)) {
                buttons.sendMainButtonsForOldUsers(chatId)
                handleButtonClickForOldUsers(chatId, message.getText())
            }
            else {


                //Если пользователь в ожидании имени
                if (sessionScope.containsKey(chatId)) {
                    //Обрабатываем
                    handleRegistration(chatId, message.getText())
                }
                else {
                    //Начало регистрации
                    handleButtonClickForNewUsers(chatId, message.getText())
                    buttons.sendMainButtonsForNewUsers(chatId)
                }

            }

        }

        //Setting Button
        if (update.hasCallbackQuery()) {
            def callback = update.getCallbackQuery()
            def data = callback.getData()
            def id = callback.from.getId()

            switch (data) {
                case "button_1_pressed_delete":
                    sendText(id, "Сессия удалена 🗑️")
                    break
                case "button_1_pressed_update":
                    sendText(id, "Сессия удалена 🗑️")
                    break
                case "button_1_pressed_genderMale":
                    def get = sessionScope.get(id)

                    def createUserDto = sessionScopeDatalist.get(id)
                    createUserDto.setGender(Gender.valueOf("MALE"))
                    sessionScopeDatalist.put(
                            id,
                            createUserDto
                    )
                    sessionScope.replace(id, get, SessionStatus.DONE.name())
                    finishRegistration(id)
                    break
                case "button_2_pressed_genderFemale":
                    def get = sessionScope.get(id)

                    def createUserDto = sessionScopeDatalist.get(id)
                    createUserDto.setGender(Gender.valueOf("FEMALE"))
                    sessionScopeDatalist.put(
                            id,
                            createUserDto
                    )
                    sessionScope.replace(id, get, SessionStatus.DONE.name())
                    finishRegistration(id)
                    break
                case "button_1_pressed_acceptYes":
                    //Создаем
                    def createUserDto = sessionScopeDatalist.get(id)
                    try {
                        def user = service.saveUserProfile(createUserDto)
                    }
                    catch (ConstraintViolationException e) {
                        e.constraintViolations.each { v ->
                            sendText(id, v.message)
                        }
                        log.info("Данные не валидны")
                    }

                    break
                case "button_1_pressed_acceptNo":
                    sessionScopeDatalist.remove(id)
                    sendText(id, "Данные стерты. Пожалуйста пройдите регистрацию повторно")
                    break
                default:
                    sendText(id, "Неизвестное действие 🤔")
                    break
                break
            }
        }

    }

    private void handleRegistration(Long id, String message) {
        def get = sessionScope.get(id)

        switch (get) {
                case [SessionStatus.WAITING_FOR_NAME.name()]:
                    sessionScopeDatalist.put(
                            id,
                            CreateUserDto.builder()
                                    .nickname(message)
                                    .build()
                    )
                    sessionScope.replace(id, get, SessionStatus.WAITING_FOR_BIRTHDAY.name())
                    sendText(id, localizer.getMessage("finishNickName"))
                    break

                case [SessionStatus.WAITING_FOR_BIRTHDAY.name()]:
                def pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                boolean isValid = {
                    try {
                        LocalDate.parse(message, pattern)
                        return true
                    }
                    catch (e) {
                        return false
                    }
                }.call()

                if (isValid) {
                    def parse = LocalDate.parse(message, pattern)
                    def createUserDto = sessionScopeDatalist.get(id)
                    createUserDto.setBirthday(parse)
                    sessionScopeDatalist.put(
                            id,
                            createUserDto
                    )
                    sessionScope.replace(id, get, SessionStatus.WAITING_FOR_GENDER.name())
                    buttons.sendGendersButtons(id)
                    break
                }
                else {
                    sendText(id, localizer.getMessage("errorInputBirthday"))
                    break
                }
                default: {
                    sendMessage(id, localizer.getMessage("switchDefaultSection"))
                }
        }
    }

    private void finishRegistration(Long id) {
        def createUserDto = sessionScopeDatalist.get(id)
        String gender = createUserDto.gender
        if (gender == "MALE") {
            gender = "Мужской"
        }
        else {
            gender = "Женский"
        }

        sendText(id, localizer.getMessage("finishRegistration").formatted(createUserDto.getNickname(), createUserDto.birthday, gender))
        sessionScope.remove(id)
        buttons.sendAcceptButtons(id)
    }

    private void sendText(Long chatId, String text) {
        def msg = new SendMessage()
        msg.setChatId(chatId)
        msg.setText(text)
        execute(msg)
    }

    private boolean checkSessionInSession(Long id) {
        return sessionArray.contains(id)
    }

    private void handleButtonClickForOldUsers(Long id, String message) {

        switch (message) {
            case ["⚙\uFE0F Настройки", "⚙\uFE0F Settings"]:
                buttons.sendSettingsButtonsOldUsers(id)
                break
            case ["\uD83E\uDD43\uD83D\uDEAB Статистика трезвости", "\uD83E\uDD43\uD83D\uDEAB Sobriety statistics"]:
                println("Статистка")
                break
            case ["\uD83E\uDDE0 Цитата дня", "\uD83E\uDDE0 Quote of the day"]:
                println("Цитата")
                break
            default: {
                sendMessage(id, localizer.getMessage("switchDefaultSection"))
            }
        }

    }

    private void handleButtonClickForNewUsers(Long id, String message) {

        switch (message) {
            case ["\uD83E\uDDE0 Цитата дня", "\uD83E\uDDE0 Quote of the day"]:
                //Цитата даня
                println("Цитата")
                break
            case ["\uD83C\uDF77\uD83D\uDCC9 Начать день трезвости", "\uD83C\uDF77\uD83D\uDCC9 Start a day of sobriety"]:
                creatSessionRegistration(id)
                break
            default: {
                sendMessage(id, localizer.getMessage("switchDefaultSection"))
            }
        }
    }
    private creatSessionRegistration(Long id)
    {
        sessionScope[id] = SessionStatus.WAITING_FOR_NAME.name()
        sendMessage(id, localizer.getMessage("startRegistration"))
        sendMessage(id, localizer.getMessage("waitingName"))
    }
    private void sendDefaultMessageToStart(Message message) {
        Long id = message.getChatId()
        String answer = localizer.getMessage("start_bot_message")
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
    private boolean checkUserOnDB(Long chatId) {
        def read = repository.read(chatId);
        if (read.isEmpty()) {
            false
        }
        else {
            true
        }
    }

    private static TelegramUser buildTelegramUser(Long id) {
        new TelegramUser(id)
    }

    private void sendMessage(Long chatId, String message) {
        def sendMessage = new SendMessage(chatId.toString(), message)
        try {
            execute(sendMessage)
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения в sendMassage", e)
            e.printStackTrace()
        }
    }
}
