package org.codespartans.telegram.bot.models;

/**
 * Upon receiving a message with this object,
 * Telegram clients will hide the current custom keyboard and display the default letter-keyboard.
 * By default, custom keyboards are displayed until a new keyboard is sent by a bot.
 * An exception is made for one-time keyboards that are hidden immediately after the user presses a button
 * (see <a href="https://core.telegram.org/bots/api#replykeyboardmarkup">ReplyKeyboardMarkup</a>).
 */
public class ReplyKeyboardHide implements Reply {
    private boolean hide_keyboard = true;
    private boolean selective = false;

    public boolean isHide_keyboard() {
        return hide_keyboard;
    }

    public boolean isSelective() {
        return selective;
    }

    public ReplyKeyboardHide setSelective(boolean selective) {
        this.selective = selective;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReplyKeyboardHide{");
        sb.append("hide_keyboard=").append(hide_keyboard);
        sb.append(", selective=").append(selective);
        sb.append('}');
        return sb.toString();
    }
}
