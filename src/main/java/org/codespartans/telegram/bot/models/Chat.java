package org.codespartans.telegram.bot.models;

/**
 * This object can either be a {@link org.codespartans.telegram.bot.models.User} or a {@link org.codespartans.telegram.bot.models.GroupChat}.
 * @see org.codespartans.telegram.bot.models.User  org.codespartans.telegram.bot.models.GroupChat
 */
public interface Chat {
    int getId();
}
