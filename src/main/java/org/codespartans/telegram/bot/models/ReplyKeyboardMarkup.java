package org.codespartans.telegram.bot.models;

import java.util.List;

/**
 * This object represents a <a href="https://core.telegram.org/bots#keyboards">custom keyboard</a> with reply options
 * (see <a href="https://core.telegram.org/bots#keyboards">Introduction to bots</a> for details and examples).
 */
public class ReplyKeyboardMarkup implements Reply {
    private List<List<String>> keyboard;
    private boolean resize_keyboard = false;
    private boolean one_time_keyboard = false;
    private boolean selective = false;

    public List<List<String>> getKeyboard() {
        return keyboard;
    }

    public ReplyKeyboardMarkup setKeyboard(List<List<String>> keyboard) {
        this.keyboard = keyboard;
        return this;
    }

    public boolean isResize_keyboard() {
        return resize_keyboard;
    }

    public ReplyKeyboardMarkup setResize_keyboard(boolean resize_keyboard) {
        this.resize_keyboard = resize_keyboard;
        return this;
    }

    public boolean isOne_time_keyboard() {
        return one_time_keyboard;
    }

    public ReplyKeyboardMarkup setOne_time_keyboard(boolean one_time_keyboard) {
        this.one_time_keyboard = one_time_keyboard;
        return this;
    }

    public boolean isSelective() {
        return selective;
    }

    public ReplyKeyboardMarkup setSelective(boolean selective) {
        this.selective = selective;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReplyKeyboardMarkup{");
        sb.append("keyboard=").append(keyboard);
        sb.append(", resize_keyboard=").append(resize_keyboard);
        sb.append(", one_time_keyboard=").append(one_time_keyboard);
        sb.append(", selective=").append(selective);
        sb.append('}');
        return sb.toString();
    }
}
