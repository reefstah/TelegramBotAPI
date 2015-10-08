package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.TelegramBot;
import org.codespartans.telegram.bot.models.Message;

import java.io.IOException;

/**
 * Created by ralph on 26/08/15.
 */
public class ForwardMessageRequest implements To<Object>, FromBot {

    private int chat_id;
    private int from_chat_id;
    private int message_id;

    ForwardMessageRequest() {
    }

    @Override
    public ForwardMessageRequest to(int chatId) {
        this.chat_id = chatId;
        return this;
    }

    public ForwardMessageRequest from(int chatId) {
        this.from_chat_id = chatId;
        return this;
    }

    public ForwardMessageRequest message(int messageId) {
        this.message_id = messageId;
        return this;
    }

    @Override
    public Message fromBot(TelegramBot bot) throws IOException {
        return bot.forwardMessage(chat_id, from_chat_id, message_id);
    }
}
