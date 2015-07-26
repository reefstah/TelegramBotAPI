package codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * Created by ralph on 08/07/15.
 */
public class User implements Chat {
    private int id;
    private String first_name;
    private Optional<String> last_name;
    private Optional<String> username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public Optional<String> getLast_name() {
        return last_name;
    }

    public void setLast_name(Optional<String> last_name) {
        this.last_name = last_name;
    }

    public Optional<String> getUsername() {
        return username;
    }

    public void setUsername(Optional<String> username) {
        this.username = username;
    }
}
