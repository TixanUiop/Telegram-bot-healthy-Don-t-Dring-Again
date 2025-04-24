package Exception;

public class TelegramUserExceptionNonId extends RuntimeException {
    public TelegramUserExceptionNonId(String message) {
        super(message);
    }
}
