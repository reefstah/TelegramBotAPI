package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.TelegramBot;
import org.codespartans.telegram.bot.models.Message;
import org.codespartans.telegram.bot.models.Reply;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by ralph on 23/08/15.
 */
public class MessageRequest {
    protected int chat_id;
    protected String text;
    protected Optional<Boolean> disable_web_page_preview = Optional.empty();
    protected Optional<Integer> reply_to_message_id = Optional.empty();
    protected Optional<Reply> reply_markup = Optional.empty();

    MessageRequest() {
    }

    public MessageRequest to(int chatId) {
        this.chat_id = chatId;
        return this;
    }

    public MessageRequest withText(String text) {
        this.text = text;
        return this;
    }

    public MessageRequest withoutWebPagePreview() {
        this.disable_web_page_preview = Optional.of(true);
        return this;
    }

    public MessageRequest isReplyToMessage(int messageId) {
        this.reply_to_message_id = Optional.of(messageId);
        return this;
    }

    public MessageRequest andReplyMarkup(Reply reply) {
        this.reply_markup = Optional.of(reply);
        return this;
    }

    public Message withBot(String token) throws IOException {
        return withBot(TelegramBot.getInstance(token));
    }

    public Message withBot(TelegramBot bot) throws IOException {
        return bot.sendMessage(chat_id, text, disable_web_page_preview, reply_to_message_id, reply_markup);
    }
}
