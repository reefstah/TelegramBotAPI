package org.codespartans.telegram.bot.models;

/**
 * Upon receiving a message with this object,
 * Telegram clients will display a reply interface to the user (act as if the user has selected the bot‘s message and tapped ’Reply').
 * This can be extremely useful if you want to create user-friendly step-by-step interfaces without having to sacrifice <a href="https://core.telegram.org/bots#privacy-mode">privacy mode</a>.
 */
public class ForceReply implements Reply {
    private boolean force_relpy = true;
    private boolean selective = false;

    public boolean isForce_relpy() {
        return force_relpy;
    }

    public boolean isSelective() {
        return selective;
    }

    public ForceReply setSelective(boolean selective) {
        this.selective = selective;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ForceReply{");
        sb.append("force_relpy=").append(force_relpy);
        sb.append(", selective=").append(selective);
        sb.append('}');
        return sb.toString();
    }
}
