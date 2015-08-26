package org.codespartans.telegram.bot.fluent;

/**
 * Created by ralph on 26/08/15.
 */
public class ForwardMessage {

    private ForwardMessage() {
    }

    public static ForwardMessageRequest to(int chatId) {
        return new ForwardMessageRequest().to(chatId);
    }

    public static ForwardMessageRequest from(int chatId) {
        return new ForwardMessageRequest().from(chatId);
    }

    public static ForwardMessageRequest message(int messageId) {
        return new ForwardMessageRequest().message(messageId);
    }
}
