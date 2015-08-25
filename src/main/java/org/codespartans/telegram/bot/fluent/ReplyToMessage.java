package org.codespartans.telegram.bot.fluent;

/**
 * Created by ralph on 25/08/15.
 */
public interface ReplyToMessage<T> {
    T isReplyToMessage(int chatId);
}
