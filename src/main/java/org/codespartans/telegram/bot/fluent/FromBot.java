package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.TelegramBot;
import org.codespartans.telegram.bot.models.Message;

import java.io.IOException;

/**
 * Created by ralph on 25/08/15.
 */
public interface FromBot {

    default Message fromBot(String token) throws IOException {
        return fromBot(TelegramBot.getInstance(token));
    }

    Message fromBot(TelegramBot bot) throws IOException;
}
