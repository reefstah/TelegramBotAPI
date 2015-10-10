package org.codespartans.telegram.bot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.codespartans.telegram.bot.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Implementation of Telegrams bot API.
 *
 * @author Ralph Broers
 */
public class TelegramBot {

	private static final ObjectMapper mapper = new ObjectMapper()
		.registerModule(new Jdk8Module())
		.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

	private static final String HOST = "api.telegram.org";
	private static final String SCHEME = "https";
	private final URI ApiUri;

	private TelegramBot(String token) throws URISyntaxException {
		this.ApiUri = new URIBuilder()
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
	public static TelegramBot getInstance(String token) {
		token = Optional.ofNullable(token)
			.orElseThrow(() -> new NullPointerException("Token can't be null."));
		try {
			return new TelegramBot(token);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * A simple method for testing your bot's auth token. Requires no parameters.
	 *
	 * @return Returns basic information about the bot in form of a <a href="https://core.telegram.org/bots/api#user">User</a> object.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public User getMe() throws IOException {
		return Request.Get(ApiUri.resolve("getMe"))
			.execute()
			.handleResponse(getResponseHandler(new TypeReference<Response<User>>() {}));
	}

	/**
	 * Use this method to send text messages.
	 *
	 * @param chat_id Unique identifier for the message recipient - User or GroupChat id
	 * @param text    Text of the message to be sent
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public Message sendMessage(int chat_id, String text) throws IOException {
		return sendMessage(chat_id, text, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	}

	/**
	 * Use this method to send text messages.
	 *
	 * @param chat_id                  Unique identifier for the message recipient - User or GroupChat id
	 * @param text                     Text of the message to be sent
	 * @param parse_mode			   Send Markdown, if you want Telegram apps to show bold, italic and inline URLs in your bot's message. For the moment, only Telegram for Android supports this.
	 * @param disable_web_page_preview Disables link previews for links in this message
	 * @param reply_to_message_id      If the message is a reply, ID of the original message
	 * @param reply_markup             Additional interface options.
	 *                                 A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
	 *                                 instructions to hide keyboard or to force a reply from the user.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public Message sendMessage(int chat_id, String text, Optional<String> parse_mode, Optional<Boolean> disable_web_page_preview, 
			Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		if (text == null || disable_web_page_preview == null || reply_to_message_id == null || reply_markup == null)
			throw new NullPointerException("No null params allowed in sendMessage.");

		if (chat_id == 0)
			throw new IllegalArgumentException("Parameter chat_id shouldn't be zero.");

		final List<BasicNameValuePair> fields = new ArrayList<>(2);
		fields.add(new BasicNameValuePair("text", text));
		parse_mode.map(parseMode -> fields.add(new BasicNameValuePair("parse_mode", parseMode)));
		disable_web_page_preview.map(preview -> fields.add(new BasicNameValuePair("disable_web_page_preview", preview.toString())));

		return sendMessage("sendMessage", chat_id, fields, reply_to_message_id, reply_markup);
	}

	/**
	 * Use this method to receive incoming updates using long polling (<a href="http://en.wikipedia.org/wiki/Push_technology#Long_polling">wiki</a>).
	 * <p>
	 * Notes
	 * 1. This method will not work if an outgoing webhook is set up.
	 * 2. In order to avoid getting duplicate updates, recalculate offset after each server response.
	 *
	 * @param offset  Identifier of the first update to be returned.
	 *                Must be greater by one than the highest among the identifiers of previously received updates.
	 *                By default, updates starting with the earliest unconfirmed update are returned.
	 *                An update is considered confirmed as soon as <a href="https://core.telegram.org/bots/api#getupdates">getUpdates</a> is called with an offset higher than its update_id.
	 * @param limit   Limits the number of updates to be retrieved. Values between 1-100 are accepted. Defaults to 100
	 * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
	 * @return An Array of <a href="https://core.telegram.org/bots/api#update">Update</a> objects is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public List<Update> getUpdates(int offset, int limit, int timeout) throws IOException {
		if (limit == 0 || timeout == 0)
			throw new IllegalArgumentException("Parameters limit and timeout shouldn't be zero.");

		final List<NameValuePair> nvps = new ArrayList<>(3);
		nvps.add(new BasicNameValuePair("offset", String.valueOf(offset)));
		nvps.add(new BasicNameValuePair("limit", String.valueOf(limit)));
		nvps.add(new BasicNameValuePair("timeout", String.valueOf(timeout)));
		return getUpdates(nvps);
	}

	/**
	 * Use this method to receive incoming updates using long polling (<a href="http://en.wikipedia.org/wiki/Push_technology#Long_polling">wiki</a>).
	 * <p>
	 * Notes
	 * 1. This method will not work if an outgoing webhook is set up.
	 * 2. In order to avoid getting duplicate updates, recalculate offset after each server response.
	 *
	 * @param offset Identifier of the first update to be returned.
	 *               Must be greater by one than the highest among the identifiers of previously received updates.
	 *               By default, updates starting with the earliest unconfirmed update are returned.
	 *               An update is considered confirmed as soon as <a href="https://core.telegram.org/bots/api#getupdates">getUpdates</a> is called with an offset higher than its update_id.
	 * @param limit  Limits the number of updates to be retrieved. Values between 1-100 are accepted. Defaults to 100
	 * @return An Array of <a href="https://core.telegram.org/bots/api#update">Update</a> objects is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public List<Update> getUpdates(int offset, int limit) throws IOException {
		if (limit == 0)
			throw new IllegalArgumentException("Parameter limit shouldn't be zero.");

		final List<NameValuePair> nvps = new ArrayList<>(2);
		nvps.add(new BasicNameValuePair("offset", String.valueOf(offset)));
		nvps.add(new BasicNameValuePair("limit", String.valueOf(limit)));
		return getUpdates(nvps);
	}

	/**
	 * Use this method to receive incoming updates using long polling (<a href="http://en.wikipedia.org/wiki/Push_technology#Long_polling">wiki</a>).
	 * <p>
	 * Notes
	 * 1. This method will not work if an outgoing webhook is set up.
	 * 2. In order to avoid getting duplicate updates, recalculate offset after each server response.
	 *
	 * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
	 * @return An Array of <a href="https://core.telegram.org/bots/api#update">Update</a> objects is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public List<Update> getUpdates(int timeout) throws IOException {
		if (timeout == 0)
			throw new IllegalArgumentException("Parameter timeout shouldn't be zero.");

		return getUpdates(Arrays.asList(new BasicNameValuePair("timeout", String.valueOf(timeout))));
	}

	/**
	 * Use this method to receive incoming updates using long polling (<a href="http://en.wikipedia.org/wiki/Push_technology#Long_polling">wiki</a>).
	 * <p>
	 * Notes
	 * 1. This method will not work if an outgoing webhook is set up.
	 * 2. In order to avoid getting duplicate updates, recalculate offset after each server response.
	 *
	 * @return An Array of <a href="https://core.telegram.org/bots/api#update">Update</a> objects is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public List<Update> getUpdates() throws IOException {
		return getUpdates(Collections.emptyList());
	}

	/**
	 * Use this method to specify a url and receive incoming updates via an outgoing webhook.
	 * Whenever there is an update for the bot, we will send an HTTPS POST request to the specified url,
	 * containing a JSON-serialized <a href="https://core.telegram.org/bots/api#update">Update</a>.
	 * In case of an unsuccessful request, we will give up after a reasonable amount of attempts.
	 * <p>
	 * If you'd like to make sure that the Webhook request comes from Telegram,
	 * we recommend using a secret path in the URL, e.g. www.example.com/<token>.
	 * Since nobody else knows your bot's token, you can be pretty sure it's us.
	 * <p>
	 * Notes
	 * 1. You will not be able to receive updates using <a href="https://core.telegram.org/bots/api#getupdates">getUpdates</a> for as long as an outgoing webhook is set up.
	 * 2. We currently do not support self-signed certificates.
	 * 3. Ports currently supported for Webhooks: 443, 80, 88, 8443.
	 *
	 * @param url HTTPS url to send updates to. Use an empty string to remove webhook integration
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public void setWebHook(String url) throws IOException {
		url = Optional.ofNullable(url).orElseThrow(() -> new NullPointerException("Url can't be null."));

		final StatusLine statusLine = Request.Post(ApiUri.resolve("setWebHook"))
			.bodyForm(Arrays.asList(new BasicNameValuePair("url", url)), StandardCharsets.UTF_8)
			.execute()
			.returnResponse()
			.getStatusLine();

		if (statusLine.getStatusCode() != 200)
			throw new HttpResponseException(statusLine.hashCode(), statusLine.getReasonPhrase());
	}

	/**
	 * Use this method to forward messages of any kind.
	 *
	 * @param chat_id      Unique identifier for the message recipient - User or GroupChat id
	 * @param from_chat_id Unique identifier for the chat where the original message was sent - User or GroupChat id
	 * @param message_id   Unique message identifier
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public Message forwardMessage(int chat_id, int from_chat_id, int message_id) throws IOException {
		if (chat_id == 0 || from_chat_id == 0 || message_id == 0)
			throw new IllegalArgumentException("Parameters shouldn't be zero.");

		final List<NameValuePair> params = new ArrayList<>(3);
		params.add(new BasicNameValuePair("chat_id", String.valueOf(chat_id)));
		params.add(new BasicNameValuePair("from_chat_id", String.valueOf(from_chat_id)));
		params.add(new BasicNameValuePair("message_id", String.valueOf(message_id)));

		return Request.Post(ApiUri.resolve("forwardMessage"))
			.bodyForm(params, StandardCharsets.UTF_8)
			.execute()
            .handleResponse(getResponseHandler(new TypeReference<Response<Message>>() {}));
    }

	/**
	 * Use this method to send photos.
	 *
	 * @param chat_id             Unique identifier for the message recipient - User or GroupChat id
	 * @param photo               Photo to send.
	 *                            You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                            or upload a new photo using multipart/form-data.
	 * @param caption             Photo caption (may also be used when resending photos by file_id).
	 * @param reply_to_message_id If the message is a reply, ID of the original message
	 * @param reply_markup        Additional interface options. A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
	 *                            instructions to hide keyboard or to force a reply from the user.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public Message sendPhoto(int chat_id, File photo, Optional<String> caption, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		if (caption == null)
			throw new NullPointerException("Parameter caption cannot be null.");

		final List<BasicNameValuePair> extraFields = caption
			.map(cptn -> Arrays.asList(new BasicNameValuePair("caption", cptn)))
			.orElseGet(Collections::emptyList);

		return sendMedia("sendPhoto", chat_id, photo, reply_to_message_id, reply_markup, extraFields);
	}

	/**
	 * Use this method to send photos.
	 *
	 * @param chat_id             Unique identifier for the message recipient - User or GroupChat id
	 * @param photo               Photo to send.
	 *                            You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                            or upload a new photo using multipart/form-data.
	 * @param caption             Photo caption (may also be used when resending photos by file_id).
	 * @param reply_to_message_id If the message is a reply, ID of the original message
	 * @param reply_markup        Additional interface options. A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
	 *                            instructions to hide keyboard or to force a reply from the user.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public Message sendPhoto(int chat_id, String photo, Optional<String> caption, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		if (caption == null)
			throw new NullPointerException("Parameter caption cannot be null.");

		final List<BasicNameValuePair> fields = new ArrayList<>(2);
		fields.add(new BasicNameValuePair("photo", photo));
		caption.map(cptn -> fields.add(new BasicNameValuePair("caption", cptn)));

		return sendMessage("sendPhoto", chat_id, fields, reply_to_message_id, reply_markup);
	}

	/**
	 * Use this method to send photos.
	 *
	 * @param chat_id Unique identifier for the message recipient - User or GroupChat id
	 * @param photo   Photo to send.
	 *                You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                or upload a new photo using multipart/form-data.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public Message sendPhoto(int chat_id, File photo) throws IOException {
		return sendPhoto(chat_id, photo, Optional.empty(), Optional.empty(), Optional.empty());
	}

	/**
	 * Use this method to send photos.
	 *
	 * @param chat_id Unique identifier for the message recipient - User or GroupChat id
	 * @param photo   Photo to send.
	 *                You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                or upload a new photo using multipart/form-data.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @throws HttpResponseException
	 */
	public Message sendPhoto(int chat_id, String photo) throws IOException {
		return sendPhoto(chat_id, photo, Optional.empty(), Optional.empty(), Optional.empty());
	}

	/**
	 * Use this method to send audio files,if you want Telegram clients to display the file as a playable voice message.
	 * For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Document).
	 *
	 * @param chat_id             Unique identifier for the message recipient - User or GroupChat id
	 * @param audio               Audio file to send.
	 *                            You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                            or upload a new audio file using multipart/form-data.
	 * @param duration            Duration of sent audio in seconds
	 * @param reply_to_message_id If the message is a reply, ID of the original message
	 * @param reply_markup        Additional interface options. A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
	 *                            instructions to hide keyboard or to force a reply from the user.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendAudio(int chat_id, String audio, Optional<Integer> duration, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		if (duration == null)
			throw new NullPointerException("Parameter duration cannot be null.");

		final List<BasicNameValuePair> fields = new ArrayList<>(2);
		fields.add(new BasicNameValuePair("audio", audio));
		duration.map(drtn -> fields.add(new BasicNameValuePair("duration", String.valueOf(drtn))));

		return sendMessage("sendAudio", chat_id, fields, reply_to_message_id, reply_markup);
	}

	/**
	 * Use this method to send audio files,if you want Telegram clients to display the file as a playable voice message.
	 * For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Document).
	 *
	 * @param chat_id Unique identifier for the message recipient - User or GroupChat id
	 * @param audio   Audio file to send.
	 *                You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                or upload a new audio file using multipart/form-data.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendAudio(int chat_id, String audio) throws IOException {
		return sendMessage("sendAudio", chat_id, Arrays.asList(new BasicNameValuePair("audio", audio)));
	}

	/**
	 * Use this method to send audio files,if you want Telegram clients to display the file as a playable voice message.
	 * For this to work, your audio must be in an .ogg file encoded with OPUS (other formats may be sent as Document).
	 *
	 * @param chat_id             Unique identifier for the message recipient - User or GroupChat id
	 * @param audio               Audio file to send.
	 *                            You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                            or upload a new audio file using multipart/form-data.
	 * @param duration            Duration of sent audio in seconds
	 * @param reply_to_message_id If the message is a reply, ID of the original message
	 * @param reply_markup        Additional interface options. A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
	 *                            instructions to hide keyboard or to force a reply from the user.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendAudio(int chat_id, File audio, Optional<Integer> duration, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		if (duration == null)
			throw new NullPointerException("Parameter duration cannot be null.");

		final List<BasicNameValuePair> extraFields = duration
				.map(drtn -> Arrays.asList(new BasicNameValuePair("duration", String.valueOf(drtn))))
				.orElseGet(Collections::emptyList);

		return sendMedia("sendAudio", chat_id, audio, reply_to_message_id, reply_markup, extraFields);
	}

	/**
	 * Use this method to send general files.
	 *
	 * @param chat_id             Unique identifier for the message recipient - User or GroupChat id
	 * @param document            File to send.
	 *                            You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                            or upload a new file using multipart/form-data.
	 * @param reply_to_message_id If the message is a reply, ID of the original message
	 * @param reply_markup        Additional interface options. A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
	 *                            instructions to hide keyboard or to force a reply from the user.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendDocument(int chat_id, String document, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		return sendMessage("sendDocument", chat_id, Arrays.asList(new BasicNameValuePair("document", document)),
				reply_to_message_id, reply_markup);
	}

	/**
	 * Use this method to send general files.
	 *
	 * @param chat_id  Unique identifier for the message recipient - User or GroupChat id
	 * @param document File to send.
	 *                 You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                 or upload a new file using multipart/form-data.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendDocument(int chat_id, String document) throws IOException {
		return sendMessage("sendDocument", chat_id, Arrays.asList(new BasicNameValuePair("document", document)));
	}

	/**
	 * Use this method to send general files.
	 *
	 * @param chat_id             Unique identifier for the message recipient - User or GroupChat id
	 * @param document            File to send.
	 *                            You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                            or upload a new file using multipart/form-data.
	 * @param reply_to_message_id If the message is a reply, ID of the original message
	 * @param reply_markup        Additional interface options. A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
	 *                            instructions to hide keyboard or to force a reply from the user.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendDocument(int chat_id, File document, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		return sendMedia("sendDocument", chat_id, document, reply_to_message_id, reply_markup, Collections.emptyList());
	}

	/**
	 * Use this method to send general files.
	 *
	 * @param chat_id  Unique identifier for the message recipient - User or GroupChat id
	 * @param document File to send.
	 *                 You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                 or upload a new file using multipart/form-data.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendDocument(int chat_id, File document) throws IOException {
		return sendMedia("sendDocument", chat_id, document);
	}

	/**
	 * Use this method to send general files.
	 *
	 * @param chat_id             Unique identifier for the message recipient - User or GroupChat id
	 * @param sticker             Sticker to send.
	 *                            You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                            or upload a new sticker using multipart/form-data.
	 * @param reply_to_message_id If the message is a reply, ID of the original message
	 * @param reply_markup        Additional interface options. A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
	 *                            instructions to hide keyboard or to force a reply from the user.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendSticker(int chat_id, File sticker, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		return sendMedia("sendSticker", chat_id, sticker, reply_to_message_id, reply_markup, Collections.emptyList());
	}

	/**
	 * Use this method to send general files.
	 *
	 * @param chat_id Unique identifier for the message recipient - User or GroupChat id
	 * @param sticker Sticker to send.
	 *                You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                or upload a new sticker using multipart/form-data.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendSticker(int chat_id, File sticker) throws IOException {
		return sendMedia("sendSticker", chat_id, sticker);
	}

	/**
	 * Use this method to send general files.
	 *
	 * @param chat_id             Unique identifier for the message recipient - User or GroupChat id
	 * @param sticker             Sticker to send.
	 *                            You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                            or upload a new sticker using multipart/form-data.
	 * @param reply_to_message_id If the message is a reply, ID of the original message
	 * @param reply_markup        Additional interface options. A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
	 *                            instructions to hide keyboard or to force a reply from the user.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendSticker(int chat_id, String sticker, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		return sendMessage("sendSticker", chat_id, Arrays.asList(new BasicNameValuePair("sticker", sticker)),
				reply_to_message_id, reply_markup);
	}

	/**
	 * Use this method to send general files.
	 *
	 * @param chat_id Unique identifier for the message recipient - User or GroupChat id
	 * @param sticker Sticker to send.
	 *                You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
	 *                or upload a new sticker using multipart/form-data.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 * @implNote Bots can currently send files of any type of up to 50 MB in size, this limit may be changed in the future.
	 */
	public Message sendSticker(int chat_id, String sticker) throws IOException {
		return sendMessage("sendSticker", chat_id, Arrays.asList(new BasicNameValuePair("sticker", sticker)));
	}

	/**
	 * Use this method to send point on the map.
	 *
	 * @param chat_id             Unique identifier for the message recipient - User or GroupChat id
	 * @param latitude            Latitude of location
	 * @param longitude           Longitude of location
	 * @param reply_to_message_id If the message is a reply, ID of the original message
	 * @param reply_markup        Additional interface options. A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
	 *                            instructions to hide keyboard or to force a reply from the user.
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 */
	public Message sendLocation(int chat_id, float latitude, float longitude, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		if (latitude == 0 || longitude == 0)
            throw new IllegalArgumentException("Latitude or longitude cannot be zero.");

		final List<BasicNameValuePair> fields = Arrays.asList(
			new BasicNameValuePair("latitude", String.valueOf(latitude)),
			new BasicNameValuePair("longitude", String.valueOf(longitude)));

		return sendMessage("sendLocation", chat_id, fields, reply_to_message_id, reply_markup);
	}

	/**
	 * Use this method to send point on the map.
	 *
	 * @param chat_id   Unique identifier for the message recipient - User or GroupChat id
	 * @param latitude  Latitude of location
	 * @param longitude Longitude of location
	 * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
	 * @throws IOException
	 */
	public Message sendLocation(int chat_id, float latitude, float longitude) throws IOException {
		if (latitude == 0 || longitude == 0)
            throw new IllegalArgumentException("Latitude or longitude cannot be zero.");

		final List<BasicNameValuePair> fields = Arrays.asList(
			new BasicNameValuePair("latitude", String.valueOf(latitude)),
			new BasicNameValuePair("longitude", String.valueOf(longitude)));

		return sendMessage("sendLocation", chat_id, fields);
	}

	/**
	 * Use this method when you need to tell the user that something is happening on the bot's side.
	 * The status is set for 5 seconds or less (when a message arrives from your bot, Telegram clients clear its typing status).
	 *
	 * @param chat_id Unique identifier for the message recipient - User or GroupChat id
	 * @param action  Type of action to broadcast.
	 *                Choose one, depending on what the user is about to receive:
	 *                <i>typing</i> for <a href="https://core.telegram.org/bots/api#sendmessage">text messages</a>,
	 *                <i>upload_photo</i> for <a href="https://core.telegram.org/bots/api#sendphoto">photos</a>,
	 *                <i>record_video</i> or <i>upload_video</i> for <a href="https://core.telegram.org/bots/api#sendvideo">videos</a>,
	 *                <i>record_audio</i> or <i>upload_audio</i> for <a href="https://core.telegram.org/bots/api#sendaudio">audio files</a>,
	 *                <i>upload_document</i> for <a href="https://core.telegram.org/bots/api#senddocument">general files</a>,
	 *                <i>find_location</i> for <a href="https://core.telegram.org/bots/api#sendlocation">location data</a>.
	 * @implNote We only recommend using this method when a response from the bot will take a noticeable amount of time to arrive.
	 */
	public void sendChatAction(int chat_id, Action action) throws IOException {
		if (chat_id == 0)
			throw new IllegalArgumentException("Parameter chat_id can't be zero.");

		final List<NameValuePair> params = Arrays.asList(
				new BasicNameValuePair("chat_id", String.valueOf(chat_id)),
				new BasicNameValuePair("action", action.toString().toLowerCase()));

		Request.Post(ApiUri.resolve("sendChatAction"))
			.bodyForm(params, StandardCharsets.UTF_8)
			.execute();
	}
	
	public org.codespartans.telegram.bot.models.File getFile(String file_id) throws IOException {
		if (file_id == null)
			throw new IllegalArgumentException("Parameter file_id can't be null.");

		final List<NameValuePair> params = Arrays.asList(new BasicNameValuePair("file_id", file_id));
		return Request.Post(ApiUri.resolve("getFile"))
			.bodyForm(params, StandardCharsets.UTF_8)
			.execute()
			.handleResponse(getResponseHandler(new TypeReference<Response<org.codespartans.telegram.bot.models.File>>() {}));
	}

	/**
	 * Use this method to get a list of profile pictures for a user.
	 *
	 * @param user_id Unique identifier of the target user
	 * @return Returns a <a href="https://core.telegram.org/bots/api#userprofilephotos">UserProfilePhotos</a> object.
	 * @throws IOException
	 */
	public UserProfilePhotos getUserProfilePhotos(int user_id) throws IOException {
		return getUserProfilePhotos(user_id, Optional.empty(), Optional.empty());
	}

	/**
	 * Use this method to get a list of profile pictures for a user.
	 *
	 * @param user_id Unique identifier of the target user
	 * @param offset  Sequential number of the first photo to be returned. By default, all photos are returned.
	 * @param limit   Limits the number of photos to be retrieved. Values between 1—100 are accepted. Defaults to 100.
	 * @return Returns a <a href="https://core.telegram.org/bots/api#userprofilephotos">UserProfilePhotos</a> object.
	 * @throws IOException
	 */
	public UserProfilePhotos getUserProfilePhotos(int user_id, Optional<Integer> offset, Optional<Integer> limit) throws IOException {
		final URI uri;

		final List<NameValuePair> nvps = new ArrayList<>(3);
		nvps.add(new BasicNameValuePair("user_id", String.valueOf(user_id)));
		offset.ifPresent(off -> nvps.add(new BasicNameValuePair("offset", String.valueOf(off))));
		limit.ifPresent(lim -> nvps.add(new BasicNameValuePair("limit", String.valueOf(lim))));

		try {
			uri = new URIBuilder(ApiUri.resolve("getUserProfilePhotos")).addParameters(nvps).build();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		return Request.Get(uri)
			.execute()
			.handleResponse(getResponseHandler(new TypeReference<Response<UserProfilePhotos>>() {}));
	}

	private Message sendMedia(String method, int chat_id, File media) throws IOException {
		return sendMedia(method, chat_id, media, Optional.empty(), Optional.empty(), Collections.emptyList());
	}

	private Message sendMedia(String method, int chat_id, File media, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup, List<BasicNameValuePair> extraFields) throws IOException {
		String mediaFieldName = method.toLowerCase().replace("send", "");

		if (chat_id == 0)
			throw new IllegalArgumentException("Parameter chat_id can't be zero.");
		if (media == null || reply_to_message_id == null || reply_markup == null)
			throw new NullPointerException("Parameters of method " + method + " cannot be null.");
		if (!media.isFile() && media.exists())
			throw new IllegalArgumentException("Parameter " + mediaFieldName + " must be an existing file.");

		final MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create().setLaxMode()
				.setContentType(TelegramContentType.MULTIPART_FORM_DATA).setCharset(StandardCharsets.UTF_8)
				.addTextBody("chat_id", String.valueOf(chat_id), TelegramContentType.PAIN_TEXT)
				.addBinaryBody(mediaFieldName, media, TelegramContentType.MULTIPART_FORM_DATA, media.getName());

		reply_to_message_id.ifPresent(id -> entityBuilder.addTextBody("reply_to_message_id", id.toString()));
		reply_markup.ifPresent(reply -> {
			try {
				entityBuilder.addTextBody("reply_markup", mapper.writeValueAsString(reply));
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		});

		extraFields.stream().forEach(field -> entityBuilder.addTextBody(field.getName(), field.getValue()));

		return Request.Post(ApiUri.resolve(method))
			.body(entityBuilder.build())
			.execute()
            .handleResponse(getResponseHandler(new TypeReference<Response<Message>>() {}));
    }

	private Message sendMessage(String method, int chat_id, List<BasicNameValuePair> fields) throws IOException {
		return sendMessage(method, chat_id, fields, Optional.empty(), Optional.empty());
	}

	private Message sendMessage(String method, int chat_id, List<BasicNameValuePair> fields, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
		if (chat_id == 0)
			throw new IllegalArgumentException("Parameter chat_id can't be zero.");
		if (reply_to_message_id == null || reply_markup == null)
			throw new NullPointerException("Parameters of method " + method + " cannot be null.");

		final List<NameValuePair> params = new ArrayList<>(3 + fields.size());
		params.add(new BasicNameValuePair("chat_id", String.valueOf(chat_id)));
        params.addAll(fields);

        reply_to_message_id.ifPresent(id -> params.add(new BasicNameValuePair("reply_to_message_id", id.toString())));
        reply_markup.ifPresent(reply -> {
            try {
                params.add(new BasicNameValuePair("reply_markup", mapper.writeValueAsString(reply)));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

		return Request.Post(ApiUri.resolve(method))
			.bodyForm(params, StandardCharsets.UTF_8)
			.execute()
            .handleResponse(getResponseHandler(new TypeReference<Response<Message>>() {}));
    }

	private List<Update> getUpdates(List<NameValuePair> nvps) throws IOException {
		final URI uri;

		try {
			uri = new URIBuilder(ApiUri.resolve("getUpdates")).addParameters(nvps).build();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}

		return Request.Get(uri)
			.execute()
            .handleResponse(getResponseHandler(new TypeReference<Response<List<Update>>>() {}));
    }

	private <T> ResponseHandler<T> getResponseHandler(TypeReference<Response<T>> reference) {
		return (HttpResponse response) -> {
			int code = response.getStatusLine().getStatusCode();
			if (code == 404)
				throw new HttpResponseException(404, "Telegram bot API out of date.");
			if (code == 200) {
				Response<T> entityResponse = mapper.readValue(response.getEntity().getContent(), reference);
				if (entityResponse.isOk() && entityResponse.getResult() != null)
					return entityResponse.getResult();
				throw new NullPointerException();
			}
			throw new HttpResponseException(code, response.getStatusLine().getReasonPhrase());
		};
	}
}
