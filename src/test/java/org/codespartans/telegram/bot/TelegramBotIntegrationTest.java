package org.codespartans.telegram.bot;

import org.codespartans.telegram.bot.models.Message;
import org.codespartans.telegram.bot.models.Update;
import org.codespartans.telegram.bot.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by ralph on 08/07/15.
 */

interface IntegrationTest {
}

@Category(IntegrationTest.class)
public class TelegramBotIntegrationTest {

    private static final String token = System.getenv("token");

    @Test(expected = NullPointerException.class)
    public void getInstanceWithNullToken() {
        TelegramBot.getInstance(null);
    }

    @Test
    public void getInstanceWithValidToken() {
        TelegramBot bot = TelegramBot.getInstance(token);
        Assert.assertNotNull(bot);
    }

    @Test
    public void getMe() throws IOException, URISyntaxException {
        TelegramBot bot = TelegramBot.getInstance(token);
        User user = bot.getMe();
        Assert.assertNotNull(user);
    }

    @Test
    public void getUpdatesWithoutParams() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(token);
        List<Update> updates = bot.getUpdates();
        Assert.assertNotNull(updates);
    }

    @Test
    public void getUpdatesWithTimeOut() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(token);
        List<Update> updates = bot.getUpdates(5);
        Assert.assertNotNull(updates);
    }

    @Test
    public void getUpdatesWithLimitAndOffset() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(token);
        List<Update> updates = bot.getUpdates(5, 5);
        Assert.assertNotNull(updates);
    }

    @Test
    public void sendPhoto() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(token);
        File file = new File(this.getClass().getResource("/5411648.png").getPath());
        Message message = bot.sendPhoto(24, file, Optional.empty(), Optional.empty(), Optional.empty());
        Assert.assertNotNull(message);
    }

}
