package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * Upon receiving a message with this object,
 * Telegram clients will display a reply interface to the user (act as if the user has selected the bot‘s message and tapped ’Reply').
 * This can be extremely useful if you want to create user-friendly step-by-step interfaces without having to sacrifice <a href="https://core.telegram.org/bots#privacy-mode">privacy mode</a>.
 */
public class ForceReply {
    private boolean force_relpy = true;
    private Optional<Boolean> selective;

    public boolean isForce_relpy() {
        return force_relpy;
    }

    public Optional<Boolean> getSelective() {
        return selective;
    }

    public void setSelective(Optional<Boolean> selective) {
        this.selective = selective;
    }
}
