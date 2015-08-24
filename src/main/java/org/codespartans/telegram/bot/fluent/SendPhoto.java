package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.models.Reply;

import java.io.File;

/**
 * Created by ralph on 24/08/15.
 */
public class SendPhoto {
    public static SendPhotoRequest to(int chatId) {
        return new SendPhotoRequest().to(chatId);
    }

    public static SendPhotoRequest photo(File photo) {
        return new SendPhotoRequest().photo(photo);
    }

    public static SendPhotoRequest photo(String fileId) {
        return new SendPhotoRequest().photo(fileId);
    }

    public static SendPhotoRequest withCaption(String caption) {
        return new SendPhotoRequest().withCaption(caption);
    }

    public static SendPhotoRequest isReplyToMessage(int chatId) {
        return new SendPhotoRequest().isReplyToMessage(chatId);
    }

    public static SendPhotoRequest andReplyMarkup(Reply reply) {
        return new SendPhotoRequest().andReplyMarkup(reply);
    }
}
