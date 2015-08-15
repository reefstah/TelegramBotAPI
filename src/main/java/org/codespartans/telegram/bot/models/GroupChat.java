package org.codespartans.telegram.bot.models;

/**
 * This object represents a group chat.
 */
public class GroupChat implements Chat {
    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public GroupChat setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public GroupChat setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroupChat{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
