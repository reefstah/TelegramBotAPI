package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.IntegrationTests;
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
        SendMessage
                .to(GROUP_CHAT_ID)
                .withText("Sexy fluent TelegramBotAPI.")
                .withBot(TOKEN);
    }
}
