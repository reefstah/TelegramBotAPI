package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents a Telegram user or bot.
 */
public class User implements Chat {
    private int id;
	private String type;
    private String first_name;
    private Optional<String> last_name = Optional.empty();
    private Optional<String> username = Optional.empty();

    @Override
    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }
    
    @Override
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", first_name='").append(first_name).append('\'');
        sb.append(", last_name=").append(last_name);
        sb.append(", username=").append(username);
        sb.append('}');
        return sb.toString();
    }
}
