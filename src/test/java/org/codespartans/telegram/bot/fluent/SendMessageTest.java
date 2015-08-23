package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.IntegrationTests;
import org.codespartans.telegram.bot.models.Message;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

/**
 * Created by ralph on 23/08/15.
 */
@Category({IntegrationTests.class, FluentIntegrationTests.class})
public class SendMessageTest {

    private static final String TOKEN = System.getenv("TOKEN");
    private static final int GROUP_CHAT_ID = Integer.valueOf(System.getenv("GROUP_CHAT_ID"));

    @Test
    public void sendMessage() throws IOException {
        final String text = "Sexy fluent TelegramBotAPI.";
        Message message = SendMessage
                .to(GROUP_CHAT_ID)
                .withText(text)
                .fromBot(TOKEN);

        Assert.assertNotNull(message);
        Assert.assertEquals(message.getText(), text);
        Assert.assertEquals(message.getChat().getId(), GROUP_CHAT_ID);
    }
}
