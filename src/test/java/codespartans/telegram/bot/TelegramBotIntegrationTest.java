package codespartans.telegram.bot;

import codespartans.telegram.bot.models.Update;
import codespartans.telegram.bot.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
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
    public void getMe() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(token);
        User user = bot.getMe();
        Assert.assertNotNull(user);
    }

    @Test
    public void getUpdates() throws IOException {
        TelegramBot bot = TelegramBot.getInstance(token);
        List<Update> updates = bot.getUpdates(Optional.empty(), Optional.empty(), Optional.empty());
        Assert.assertNotNull(updates);
    }
}
