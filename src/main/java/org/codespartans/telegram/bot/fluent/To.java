package org.codespartans.telegram.bot.fluent;

/**
 * Created by ralph on 25/08/15.
 */
public interface To<T> {
    T to(int chatId);
}
