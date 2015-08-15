package org.codespartans.telegram.bot.models;

import java.util.List;

/**
 * This object represents a <a href="https://core.telegram.org/bots#keyboards">custom keyboard</a> with reply options
 * (see <a href="https://core.telegram.org/bots#keyboards">Introduction to bots</a> for details and examples).
 */
public class ReplyKeyboardMarkup {
    private List<List<String>> keyboard;
    private boolean resize_keyboard;
    private boolean one_time_keyboard;
    private boolean selective;

    public List<List<String>> getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(List<List<String>> keyboard) {
        this.keyboard = keyboard;
    }

    public boolean isResize_keyboard() {
        return resize_keyboard;
    }

    public void setResize_keyboard(boolean resize_keyboard) {
        this.resize_keyboard = resize_keyboard;
    }

    public boolean isOne_time_keyboard() {
        return one_time_keyboard;
    }

    public void setOne_time_keyboard(boolean one_time_keyboard) {
        this.one_time_keyboard = one_time_keyboard;
    }

    public boolean isSelective() {
        return selective;
    }

    public void setSelective(boolean selective) {
        this.selective = selective;
    }
}
