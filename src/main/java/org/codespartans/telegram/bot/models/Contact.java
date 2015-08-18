package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents a phone contact.
 */
public class Contact {
    private String phone_number;
    private String first_name;
    private Optional<String> last_name = Optional.empty();
    private Optional<Integer> user_id = Optional.empty();

    public String getPhone_number() {
        return phone_number;
    }

    public Contact setPhone_number(String phone_number) {
        this.phone_number = phone_number;
        return this;
    }

    public String getFirst_name() {
        return first_name;
    }

    public Contact setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public Optional<String> getLast_name() {
        return last_name;
    }

    public Contact setLast_name(Optional<String> last_name) {
        this.last_name = last_name;
        return this;
    }

    public Optional<Integer> getUser_id() {
        return user_id;
    }

    public Contact setUser_id(Optional<Integer> user_id) {
        this.user_id = user_id;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Contact{");
        sb.append("phone_number='").append(phone_number).append('\'');
        sb.append(", first_name='").append(first_name).append('\'');
        sb.append(", last_name=").append(last_name);
        sb.append(", user_id=").append(user_id);
        sb.append('}');
        return sb.toString();
    }
}
