package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * Upon receiving a message with this object,
 * Telegram clients will hide the current custom keyboard and display the default letter-keyboard.
 * By default, custom keyboards are displayed until a new keyboard is sent by a bot.
 * An exception is made for one-time keyboards that are hidden immediately after the user presses a button
 * (see <a href="https://core.telegram.org/bots/api#replykeyboardmarkup">ReplyKeyboardMarkup</a>).
 */
public class ReplyKeyboardHide {
    private boolean hide_keyboard = true;
    private Optional<Boolean> selective;

    public boolean isHide_keyboard() {
        return hide_keyboard;
    }

    public Optional<Boolean> getSelective() {
        return selective;
    }

    public void setSelective(Optional<Boolean> selective) {
        this.selective = selective;
    }
}
