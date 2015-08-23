package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.models.Reply;

/**
 * Created by ralph on 23/08/15.
 */
public class SendMessage {

    private SendMessage() {
    }

    public static MessageRequest to(int chatId) {
        return new MessageRequest().to(chatId);
    }

    public static MessageRequest withText(String text) {
        return new MessageRequest().withText(text);
    }

    public static MessageRequest withoutWebPagePreview() {
        return new MessageRequest().withoutWebPagePreview();
    }

    public static MessageRequest isReplyToMessage(int messageId) {
        return new MessageRequest().isReplyToMessage(messageId);
    }

    public static MessageRequest andReplyMarkup(Reply reply) {
        return new MessageRequest().andReplyMarkup(reply);
    }
}
