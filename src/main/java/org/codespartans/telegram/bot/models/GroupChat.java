package org.codespartans.telegram.bot.models;

/**
 * Created by ralph on 24/07/15.
 */
public class GroupChat implements Chat {
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
