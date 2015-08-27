package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.models.Reply;

import java.io.File;

/**
 * Created by ralph on 27/08/15.
 */
public class SendAudio {
    private SendAudio() {
    }

    public static SendAudioRequest audio(String fileId) {
        return new SendAudioRequest().audio(fileId);
    }

    public static SendAudioRequest audio(File audio) {
        return new SendAudioRequest().audio(audio);
    }

    public static SendAudioRequest duration(int duration) {
        return new SendAudioRequest().duration(duration);
    }

    public static SendAudioRequest andReplyMarkup(Reply reply) {
        return new SendAudioRequest().andReplyMarkup(reply);
    }

    public static SendAudioRequest isReplyToMessage(int chatId) {
        return new SendAudioRequest().isReplyToMessage(chatId);
    }

    public static SendAudioRequest to(int chatId) {
        return new SendAudioRequest().to(chatId);
    }
}
