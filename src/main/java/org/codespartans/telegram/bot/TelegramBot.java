package org.codespartans.telegram.bot;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codespartans.telegram.bot.models.Message;
import org.codespartans.telegram.bot.models.Response;
import org.codespartans.telegram.bot.models.Update;
import org.codespartans.telegram.bot.models.User;
import org.codespartans.telegram.bot.models.UserProfilePhotos;
import org.codespartans.telegram.bot.params.AbstractParamBuilder;
import org.codespartans.telegram.bot.params.AudioBuilder;
import org.codespartans.telegram.bot.params.ChatActionBuilder;
import org.codespartans.telegram.bot.params.DocumentBuilder;
import org.codespartans.telegram.bot.params.ForwardMessageBuilder;
import org.codespartans.telegram.bot.params.LocationBuilder;
import org.codespartans.telegram.bot.params.MessageBuilder;
import org.codespartans.telegram.bot.params.PhotoBuilder;
import org.codespartans.telegram.bot.params.StickerBuilder;
import org.codespartans.telegram.bot.params.UserProfilePhotosBuilder;
import org.codespartans.telegram.bot.params.VideoBuilder;
import org.codespartans.telegram.bot.params.VoiceBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

/**
 * Implementation of Telegram's bot API.
 * 
 * @author Ralph Broers, UnAfraid
 */
public class TelegramBot {
	private static final ObjectMapper MAPPER = new ObjectMapper()
			.registerModule(new Jdk8Module())
			.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

	private static final String HOST = "api.telegram.org";
	private static final String SCHEME = "https";
	private final URI apiUri;

	private TelegramBot(String token) throws URISyntaxException {
		apiUri = new URIBuilder()
				.setScheme(SCHEME)
				.setHost(HOST)
				.setPath(String.format("/bot%s/", token))
				.setCharset(StandardCharsets.UTF_8)
				.build();
	}

	/**
	 * To create a bot and get a token key look at Telegrams documentation about <a href="https://core.telegram.org/bots#botfather">botfather</a>.
	 * Or interact with him <a href="https://telegram.me/botfather">botfather</a> straight away.
	 *
	 * @param token Each bot is given a unique authentication token <a href="https://core.telegram.org/bots#botfather">when it is created</a>.
	 *              The token looks something like 123456:ABC-DEF1234ghIkl-zyx57W2v1u123ew11,
	 *              but we'll use simply <token> in this document instead.
	 *              You can learn about obtaining tokens and generating new ones in <a href="https://core.telegram.org/bots#botfather">this document</a>.
	 * @return Returns an instance of TelegramBot
	 */
	public static TelegramBot newInstance(String token) {
		Objects.requireNonNull(token, "Token can't be null");
		try {
			return new TelegramBot(token);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Use this method to send text messages.
	 * @param builder
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public Message sendMessage(MessageBuilder builder) throws IOException {
		return sendPost(builder);
	}

	/**
	 * Use this method to forward messages of any kind.
	 * @param builder
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public Message forwardMessage(ForwardMessageBuilder builder) throws IOException {
		return sendPost(builder);
	}

	/**
	 * Use this method to send photos.
	 * @param builder
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public Message sendPhoto(PhotoBuilder builder) throws IOException {
		return sendPost(builder);
	}

	/**
	 * Use this method to send audio files,if you want Telegram clients to display the file as a playable voice message. For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Document).
	 * @param builder
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendAudio(AudioBuilder builder) throws IOException {
		return sendPost(builder);
	}

	/**
	 * Use this method to send general files.
	 * @param builder
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendDocument(DocumentBuilder builder) throws IOException {
		return sendPost(builder);
	}

	/**
	 * Use this method to send general files.
	 * @param builder
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendSticker(StickerBuilder builder) throws IOException {
		return sendPost(builder);
	}

	/**
	 * Use this method to send video files.
	 * @param builder
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 */
	public Message sendVideo(VideoBuilder builder) throws IOException {
		return sendPost(builder);
	}

	/**
	 * Use this method to send voice files.
	 * @param builder
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 */
	public Message sendVoice(VoiceBuilder builder) throws IOException {
		return sendPost(builder);
	}

	/**
	 * Use this method to send point on the map.
	 * @param builder
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 */
	public Message sendLocation(LocationBuilder builder) throws IOException {
		return sendPost(builder);
	}

	/**
	 * Use this method when you need to tell the user that something is happening on the bot's side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status).
	 * @param builder
	 * @return
	 * @throws IOException
	 * @implNote We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
	 */
	public Boolean sendChatAction(ChatActionBuilder builder) throws IOException {
		return sendGenericPost(builder);
	}

	/**
	 * Use this method to get a list of profile pictures for a user.
	 * @param builder
	 * @return Returns a <a href="https://core.telegram.org/bots/api#userprofilephotos">UserProfilePhotos</a> object.
	 * @throws IOException
	 */
	public UserProfilePhotos getUserProfilePhotos(UserProfilePhotosBuilder builder) throws IOException {
		builder.validate();

		return Request
				.Get(apiUri.resolve(builder.getURL()))
				.body(builder.build())
				.execute()
				.handleResponse(getResponseHandler(new TypeReference<Response<UserProfilePhotos>>() {}));
	}

	/**
	 * Use this method to receive incoming updates using long polling (<a href="http://en.wikipedia.org/wiki/Push_technology#Long_polling">wiki</a>).
	 * <ul>
	 * <li>This method will not work if an outgoing webhook is set up.</li>
	 * <li>In order to avoid getting duplicate updates, recalculate offset after each server response.</li>
	 * </ul>
	 * @param offset
	 * @param limit
	 * @param timeout
	 * @return An Array of <a href="https://core.telegram.org/bots/api#update">Update</a> objects is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public List<Update> getUpdates(int offset, int limit, int timeout) throws IOException {
		final URI uri;

		try {
			final URIBuilder builder = new URIBuilder(apiUri.resolve("getUpdates"));
			builder.addParameter("offset", String.valueOf(offset));
			builder.addParameter("limit", String.valueOf(limit));
			builder.addParameter("timeout", String.valueOf(timeout));
			uri = builder.build();

		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		return Request
				.Get(uri)
				.execute()
				.handleResponse(getResponseHandler(new TypeReference<Response<List<Update>>>() {}));
	}

	/**
	 * Use this method to send param builder.
	 * @param <T>
	 * @param builder
	 * @return Returns a <a href="https://core.telegram.org/bots/api#userprofilephotos">UserProfilePhotos</a> object.
	 * @throws IOException
	 */
	private Message sendPost(AbstractParamBuilder<?> builder) throws IOException {
		builder.validate();

		return Request
				.Post(apiUri.resolve(builder.getURL()))
					.body(builder.build())
					.execute()
					.handleResponse(getResponseHandler(new TypeReference<Response<Message>>() {}));
	}
	
	/**
	 * Use this method to send param builder.
	 * @param <T>
	 * @param builder
	 * @return Returns a <a href="https://core.telegram.org/bots/api#userprofilephotos">UserProfilePhotos</a> object.
	 * @throws IOException
	 */
	private <T> T sendGenericPost(AbstractParamBuilder<?> builder) throws IOException
	{
		builder.validate();
		
		//@formatter:off
		return Request
				.Post(apiUri.resolve(builder.getURL()))
					.body(builder.build())
					.execute()
					.handleResponse(getResponseHandler(new TypeReference<Response<T>>() {}));
		//@formatter:on
	}

	/**
	 * Use this method to specify a url and receive incoming updates via an outgoing webhook. <br>
	 * Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url, containing a JSON-serialized <a href="https://core.telegram.org/bots/api#update">Update</a>.<br>
	 * In case of an unsuccessful request, we will give up after a reasonable amount of attempts.
	 * <p>
	 * If you'd like to make sure that the Webhook request comes from Telegram, we recommend using a secret path in the URL, e.g. www.example.com/<token>. Since nobody else knows your bot's token, you can be pretty sure it's us.
	 * </p>
	 * Notes:<br>
	 * <ul>
	 * <li>You will not be able to receive updates using <a href="https://core.telegram.org/bots/api#getupdates">getUpdates</a> for as long as an outgoing webhook is set up.</li>
	 * <li>We currently do not support self-signed certificates.</li>
	 * <li>Ports currently supported for Webhooks: 443, 80, 88, 8443</li>
	 * </ul>
	 * @param url HTTPS url to send updates to. Use an empty string to remove webhook integration
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public void setWebHook(String url) throws IOException {
		Objects.requireNonNull(url, "Url can't be null");

		final StatusLine statusLine = Request
				.Post(apiUri.resolve("setWebHook"))
				.bodyForm(Arrays.asList(new BasicNameValuePair("url", url)))
				.execute()
				.returnResponse()
				.getStatusLine();
		if (statusLine.getStatusCode() != 200) {
			throw new HttpResponseException(statusLine.hashCode(), statusLine.getReasonPhrase());
		}
	}

	/**
	 * A simple method for testing your bot's auth token. Requires no parameters.
	 * @return Returns basic information about the bot in form of a <a href="https://core.telegram.org/bots/api#user">User</a> object.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public User getMe() throws IOException {
		return Request
				.Get(apiUri.resolve("getMe"))
				.execute()
				.handleResponse(getResponseHandler(new TypeReference<Response<User>>() {}));
	}

	private <T> ResponseHandler<T> getResponseHandler(TypeReference<Response<T>> reference) {
		return (HttpResponse response) -> {
			int code = response.getStatusLine().getStatusCode();
			if (code == 404) {
				throw new HttpResponseException(404, "Telegram bot API out of date.");
			}

			if (code == 200) {
				final Response<T> entityResponse = MAPPER.readValue(response.getEntity().getContent(), reference);
				if (entityResponse.isOk() && (entityResponse.getResult() != null)) {
					return entityResponse.getResult();
				}
				throw new NullPointerException();
			}
			throw new HttpResponseException(code, response.getStatusLine().getReasonPhrase());
		};
	}
}