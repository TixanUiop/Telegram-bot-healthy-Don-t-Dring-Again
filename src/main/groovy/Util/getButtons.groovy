package Util

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import localization.Localizer
import org.telegram.telegrambots.meta.api.methods.send.SendMessage


@Slf4j
@CompileStatic
class getButtons {

    private TelegramLongPollingBot bot
    private Localizer localizer

    getButtons(TelegramLongPollingBot bot, Localizer loc) {
        this.bot = bot
        this.localizer = loc
    }

    //Для новых
     void sendMainButtonsForNewUsers(Long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup()
        keyboardMarkup.setResizeKeyboard(true)  // Подгоняет размер клавиатуры под экран
        keyboardMarkup.setOneTimeKeyboard(false)  // Клавиатура остаётся после выбора
        keyboardMarkup.setSelective(true)  // Показывается только нужным пользователям

        KeyboardRow row1 = new KeyboardRow()
        row1.add(localizer.getMessage("startDayOfSobrietyButton"))
        row1.add(localizer.getMessage("quoteButton"))

        keyboardMarkup.setKeyboard([row1])

        SendMessage message = new SendMessage()
        message.setChatId(chatId)
        message.setText("Этот пункт для новых пользователей")
        message.setReplyMarkup(keyboardMarkup)
        bot.execute(message)
    }

    //Старые пользователи
     void sendMainButtonsForOldUsers(Long chatId) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup()
        keyboardMarkup.setResizeKeyboard(true)  // Подгоняет размер клавиатуры под экран
        keyboardMarkup.setOneTimeKeyboard(false)  // Клавиатура остаётся после выбора
        keyboardMarkup.setSelective(true)  // Показывается только нужным пользователям

        KeyboardRow row1 = new KeyboardRow()
        row1.add(localizer.getMessage("statisticsButton"))
        row1.add(localizer.getMessage("quoteButton"))

        KeyboardRow row2 = new KeyboardRow()
        row2.add(localizer.getMessage("settingButton"))

        keyboardMarkup.setKeyboard([row1, row2])

        SendMessage message = new SendMessage()
        message.setChatId(chatId)
        message.setReplyMarkup(keyboardMarkup)
        message.setText("Этот пункт для старых пользователей")
         bot.execute(message)
    }

     void sendSettingsButtonsOldUsers(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Это общий и настройки");

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText(localizer.getMessage("settingButtonDeleteSession"));
        button1.setCallbackData("button_1_pressed_delete");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(localizer.getMessage("settingButtonUpdateSession"));
        button2.setCallbackData("button_2_pressed_update");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button1);
        row.add(button2);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);

        sendMessage.setReplyMarkup(keyboardMarkup);

        try {
            bot.execute(sendMessage)
            log.info("Кнопки отправились успешно")
        }
        catch (Exception e) {
            log.error("Ошибка отправки кнопок пользователю", e)
        }
    }


     void sendGendersButtons(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите Gender");

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText(localizer.getMessage("genderMale"));
        button1.setCallbackData("button_1_pressed_genderMale");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(localizer.getMessage("genderFemale"));
        button2.setCallbackData("button_2_pressed_genderFemale");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button1);
        row.add(button2);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);

        sendMessage.setReplyMarkup(keyboardMarkup);

        try {
            bot.execute(sendMessage)
            log.info("Кнопки отправились успешно")
        }
        catch (Exception e) {
            log.error("Ошибка отправки кнопок пользователю", e)
        }
    }


     void sendAcceptButtons(Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Эти данные верные?");

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText(localizer.getMessage("acceptYes"));
        button1.setCallbackData("button_1_pressed_acceptYes");

        InlineKeyboardButton button2 = new InlineKeyboardButton();
        button2.setText(localizer.getMessage("acceptNo"));
        button2.setCallbackData("button_2_pressed_acceptNo");

        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button1);
        row.add(button2);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        keyboardMarkup.setKeyboard(keyboard);

        sendMessage.setReplyMarkup(keyboardMarkup);

        try {
            bot.execute(sendMessage)
            log.info("Кнопки отправились успешно")
        }
        catch (Exception e) {
            log.error("Ошибка отправки кнопок пользователю", e)
        }

    }
}
