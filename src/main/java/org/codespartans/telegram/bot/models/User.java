package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents a Telegram user or bot.
 */
public class User implements Chat {
    private int id;
    private String first_name;
    private Optional<String> last_name;
    private Optional<String> username;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirst_name() {
        return first_name;
    }

    public User setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public Optional<String> getLast_name() {
        return last_name;
    }

    public User setLast_name(Optional<String> last_name) {
        this.last_name = last_name;
        return this;
    }

    public Optional<String> getUsername() {
        return username;
    }

    public User setUsername(Optional<String> username) {
        this.username = username;
        return this;
    }
}
