package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.models.Reply;

/**
 * Created by ralph on 25/08/15.
 */
interface ReplyMarkup<T> {
    T andReplyMarkup(Reply reply);
}
