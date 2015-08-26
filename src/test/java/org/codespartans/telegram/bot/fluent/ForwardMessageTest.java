package org.codespartans.telegram.bot.fluent;

import org.codespartans.telegram.bot.IntegrationTests;
import org.codespartans.telegram.bot.models.Message;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

/**
 * Created by ralph on 26/08/15.
 */
@Category({IntegrationTests.class, FluentIntegrationTests.class})
public class ForwardMessageTest {

    private static final String TOKEN = System.getenv("TOKEN");
    private static final int GROUP_CHAT_ID = Integer.valueOf(System.getenv("GROUP_CHAT_ID"));

    @Test
    public void forwardMessage() throws IOException {

        final String text = "Message to be forwarded.";

        Message msg = SendMessage
                .to(GROUP_CHAT_ID)
                .withText(text)
                .fromBot(TOKEN);

        Message fmsg = ForwardMessage
                .to(GROUP_CHAT_ID)
                .from(msg.getChat().getId())
                .message(msg.getMessage_id())
                .fromBot(TOKEN);

        Assert.assertNotNull(fmsg);
        Assert.assertEquals(fmsg.getText(), text);
    }
}
