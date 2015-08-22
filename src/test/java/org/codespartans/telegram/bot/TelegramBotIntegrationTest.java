package org.codespartans.telegram.bot;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.codespartans.telegram.bot.models.Message;
import org.codespartans.telegram.bot.models.Reply;
import org.codespartans.telegram.bot.models.ReplyKeyboardMarkup;
import org.codespartans.telegram.bot.models.Update;
import org.codespartans.telegram.bot.models.User;
import org.codespartans.telegram.bot.params.DocumentBuilder;
import org.codespartans.telegram.bot.params.LocationBuilder;
import org.codespartans.telegram.bot.params.MessageBuilder;
import org.codespartans.telegram.bot.params.PhotoBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Created by ralph on 08/07/15.
 */

interface IntegrationTests {
}

@Category(IntegrationTests.class)
public class TelegramBotIntegrationTest {

	private static final String TOKEN = System.getenv("TOKEN");
	private static final int GROUP_CHAT_ID = Integer.valueOf(System.getenv("GROUP_CHAT_ID"));
	
	@Test(expected = NullPointerException.class)
	public void getInstanceWithNullToken() {
		TelegramBot.newInstance(null);
	}

	@Test
	public void getInstanceWithValidToken() {
		TelegramBot bot = TelegramBot.newInstance(TOKEN);
		Assert.assertNotNull(bot);
	}

	@Test
	public void getMe() throws IOException, URISyntaxException {
		TelegramBot bot = TelegramBot.newInstance(TOKEN);
		User user = bot.getMe();
		Assert.assertNotNull(user);
	}

	@Test
	public void getUpdatesWithoutParams() throws IOException {
		TelegramBot bot = TelegramBot.newInstance(TOKEN);
		List<Update> updates = bot.getUpdates(1, 100, 0);
		Assert.assertNotNull(updates);
	}

	@Test
	public void getUpdatesWithTimeOut() throws IOException {
		TelegramBot bot = TelegramBot.newInstance(TOKEN);
		List<Update> updates = bot.getUpdates(1, 100, 0);
		Assert.assertNotNull(updates);
	}

	@Test
	public void getUpdatesWithLimitAndOffset() throws IOException {
		TelegramBot bot = TelegramBot.newInstance(TOKEN);
		List<Update> updates = bot.getUpdates(5, 5, 0);
		Assert.assertNotNull(updates);
	}

	@Test
	public void sendPhoto() throws IOException {
		TelegramBot bot = TelegramBot.newInstance(TOKEN);
		File file = new File(this.getClass().getResource("/5411648.png").getPath());
		Message message = bot.sendPhoto(new PhotoBuilder(GROUP_CHAT_ID).photoFile(file));
		Assert.assertNotNull(message);
	}

	@Test
	public void sendDocument() throws IOException {
		TelegramBot bot = TelegramBot.newInstance(TOKEN);
		File file = new File(this.getClass().getResource("/5411648.png").getPath());
		Message message = bot.sendDocument(new DocumentBuilder(GROUP_CHAT_ID).documentFile(file));
		Assert.assertNotNull(message);
	}

	@Test
	public void sendLocation() throws IOException {
		TelegramBot bot = TelegramBot.newInstance(TOKEN);
		Message message = bot
				.sendLocation(new LocationBuilder(GROUP_CHAT_ID).latitude(52.3747157F).longitude(4.8986142F));
		Assert.assertNotNull(message);
	}

	@Test
	public void sendChatAction() throws IOException {
		TelegramBot bot = TelegramBot.newInstance(TOKEN);
		final String txt = "Hello, this a integration test message.";
		Message message = bot.sendMessage(new MessageBuilder(GROUP_CHAT_ID).text(txt));
		Assert.assertNotNull(message);
		Assert.assertEquals(message.getText(), txt);
		Assert.assertNotNull(message.getFrom().getUsername());
	}

	@Test
	public void testReply() throws IOException {
		final TelegramBot bot = TelegramBot.newInstance(TOKEN);

		final Reply reply = new ReplyKeyboardMarkup()
				.setKeyboard(Arrays.asList(Arrays.asList("Reply1", "Reply2"), Arrays.asList("Reply3", "Reply4")))
				.setOne_time_keyboard(true);

		final Message message = bot
				.sendMessage(new MessageBuilder(GROUP_CHAT_ID)
					.text("Testing reply options.")
					.replyMarkUp(reply));

		Assert.assertNotNull(message);
	}
}