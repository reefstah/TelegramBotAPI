package org.codespartans.telegram.bot.models;

/**
 * Created by ralph on 24/07/15.
 */
public class Update {
    private int update_id;
    private Message message;

    public int getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(int update_id) {
        this.update_id = update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
