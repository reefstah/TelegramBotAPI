package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.TelegramBot;
import org.codespartans.telegram.bot.models.Message;
import org.codespartans.telegram.bot.models.Reply;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by ralph on 24/08/15.
 */
public class SendPhotoRequest implements To, ReplyToMessage, ReplyMarkup, FromBot {
    private int chat_id;
    private File photo;
    private String file_id;
    private Optional<String> caption = Optional.empty();
    private Optional<Integer> reply_to_message_id = Optional.empty();
    private Optional<Reply> reply_markup = Optional.empty();

    public SendPhotoRequest() {
    }

    public SendPhotoRequest to(int chatId) {
        this.chat_id = chatId;
        return this;
    }

    public SendPhotoRequest photo(File photo) {
        this.photo = photo;
        return this;
    }

    public SendPhotoRequest photo(String fileId) {
        this.file_id = fileId;
        return this;
    }

    public SendPhotoRequest withCaption(String caption) {
        this.caption = Optional.of(caption);
        return this;
    }

    public SendPhotoRequest isReplyToMessage(int chatId) {
        this.reply_to_message_id = Optional.of(chatId);
        return this;
    }

    public SendPhotoRequest andReplyMarkup(Reply reply) {
        this.reply_markup = Optional.of(reply);
        return this;
    }

    public Message fromBot(String token) throws IOException {
        return fromBot(TelegramBot.getInstance(token));
    }

    public Message fromBot(TelegramBot bot) throws IOException {
        if (photo == null && file_id == null)
            throw new IllegalStateException("No photo is set to send.");
        if (photo != null && file_id != null)
            throw new IllegalStateException("Both a file_id as photo and a file is set.");

        return photo == null ?
                bot.sendPhoto(chat_id, file_id, caption, reply_to_message_id, reply_markup) :
                bot.sendPhoto(chat_id, photo, caption, reply_to_message_id, reply_markup);
    }
}
