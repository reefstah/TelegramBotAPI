package codespartans.telegram.bot;

/**
 * Created by ralph on 08/07/15.
 */
public class TelegramBotApiRuntimeException extends RuntimeException {
    public TelegramBotApiRuntimeException() {
    }

    public TelegramBotApiRuntimeException(String message) {
        super(message);
    }

    public TelegramBotApiRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramBotApiRuntimeException(Throwable cause) {
        super(cause);
    }

    public TelegramBotApiRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
