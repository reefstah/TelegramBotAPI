package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.TelegramBot;
import org.codespartans.telegram.bot.models.Message;
import org.codespartans.telegram.bot.models.Reply;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by ralph on 27/08/15.
 */
public class SendAudioRequest implements To<Object>, ReplyToMessage<Object>, ReplyMarkup<Object>, FromBot {
    private int chat_id;
    private File audio;
    private String file_id;
    private Optional<Integer> duration = Optional.empty();
    private Optional<Integer> reply_to_message_id = Optional.empty();
    private Optional<Reply> reply_markup = Optional.empty();

    SendAudioRequest() {
    }

    public SendAudioRequest audio(String fileId) {
        this.file_id = fileId;
        return this;
    }

    public SendAudioRequest audio(File audio) {
        this.audio = audio;
        return this;
    }

    public SendAudioRequest duration(int duration) {
        this.duration = Optional.of(duration);
        return this;
    }

    @Override
    public Message fromBot(TelegramBot bot) throws IOException {
        if (audio == null && file_id == null)
            throw new IllegalStateException("No audio is set to send.");
        if (audio != null && file_id != null)
            throw new IllegalStateException("Both a file_id as audio and a file is set.");

        return audio == null ?
                bot.sendAudio(chat_id, file_id, duration, reply_to_message_id, reply_markup) :
                bot.sendAudio(chat_id, audio, duration, reply_to_message_id, reply_markup);
    }

    @Override
    public SendAudioRequest andReplyMarkup(Reply reply) {
        this.reply_markup = Optional.of(reply);
        return this;
    }

    @Override
    public SendAudioRequest isReplyToMessage(int chatId) {
        this.reply_to_message_id = Optional.of(chatId);
        return this;
    }

    @Override
    public SendAudioRequest to(int chatId) {
        this.chat_id = chatId;
        return this;
    }
}
