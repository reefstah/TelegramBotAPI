package org.codespartans.telegram.bot;

import org.codespartans.telegram.bot.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by ralph on 08/07/15.
 */

interface IntegrationTests { /* category marker */
}

@Category(IntegrationTests.class)
public class TelegramBotIntegrationTest {

    private static final String TOKEN = System.getenv("TOKEN");
    private static final int GROUP_CHAT_ID = Integer.valueOf(System.getenv("GROUP_CHAT_ID"));

    @Test(expected = NullPointerException.class)
    public void getInstanceWithNullToken() {
        TelegramBot.getInstance(null);
    }

    @Test
    public void getInstanceWithValidToken() {
        TelegramBot bot = TelegramBot.getInstance(TOKEN);
        Assert.assertNotNull(bot);
    }

    @Test
    public void getMe() throws IOException, URISyntaxException {
        TelegramBot bot = TelegramBot.getInstance(TOKEN);
        User user = bot.getMe();
        Assert.assertNotNull(user);
    }

    @Test
    public void getUpdatesWithoutParams() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(TOKEN);
        List<Update> updates = bot.getUpdates();
        Assert.assertNotNull(updates);
    }

    @Test
    public void getUpdatesWithTimeOut() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(TOKEN);
        List<Update> updates = bot.getUpdates(1);
        Assert.assertNotNull(updates);
    }

    @Test
    public void getUpdatesWithLimitAndOffset() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(TOKEN);
        List<Update> updates = bot.getUpdates(5, 5);
        Assert.assertNotNull(updates);
    }

    @Test
    public void sendPhoto() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(TOKEN);
        File file = new File(this.getClass().getResource("/5411648.png").getPath());
        Message message = bot.sendPhoto(GROUP_CHAT_ID, file, Optional.empty(), Optional.empty(), Optional.empty());
        Assert.assertNotNull(message);
    }

    @Test
    public void sendDocument() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(TOKEN);
        File file = new File(this.getClass().getResource("/5411648.png").getPath());
        Message message = bot.sendDocument(GROUP_CHAT_ID, file);
        Assert.assertNotNull(message);
    }

    @Test
    public void sendLocation() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(TOKEN);
        Message message = bot.sendLocation(GROUP_CHAT_ID, 52.3747157F, 4.8986142F);
        Assert.assertNotNull(message);
    }

    @Test
    public void sendMessage() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(TOKEN);
        final String txt = "Hello, this a integration test message.";
        Message message = bot.sendMessage(GROUP_CHAT_ID, txt);
        Assert.assertNotNull(message);
        Assert.assertEquals(message.getText(), txt);
        Assert.assertNotNull(message.getFrom().getUsername());
    }

    @Test
    public void testReply() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(TOKEN);

        Reply reply = new ReplyKeyboardMarkup()
                .setKeyboard(Arrays.asList(Arrays.asList("Reply1", "Reply2"), Arrays.asList("Reply3", "Reply4")))
                .setOne_time_keyboard(true);

        Message message = bot.sendMessage(GROUP_CHAT_ID, "Testing reply options.", Optional.empty(), Optional.empty(), Optional.of(reply));

        Assert.assertNotNull(message);
    }
}
