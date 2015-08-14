package org.codespartans.telegram.bot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.codespartans.telegram.bot.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Telegrams bot API.
 *
 * @author Ralph Broers
 */
public class TelegramBot {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());
    private static final String HOST = "api.telegram.org";
    private static final String SCHEME = "https";
    private final URI ApiUri;

    private TelegramBot(String token) throws URISyntaxException {
        this.ApiUri = new URIBuilder()
                .setScheme(SCHEME)
                .setHost(HOST)
                .setPath(String.format("/bot%s/", token))
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
                .handleResponse(getResponseHandler(new TypeReference<Response<User>>() {
                }));
    }

    /**
     * Use this method to send text messages.
     *
     * @param chat_id Unique identifier for the message recipient — User or GroupChat id
     * @param text    Text of the message to be sent
     * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
     * @throws IOException
     * @throws HttpResponseException
     */
    public Message sendMessage(int chat_id, String text) throws IOException {
        return sendMessage(chat_id, text, Optional.empty(), Optional.empty(), Optional.empty());
    }

    /**
     * Use this method to send text messages.
     *
     * @param chat_id                  Unique identifier for the message recipient — User or GroupChat id
     * @param text                     Text of the message to be sent
     * @param disable_web_page_preview Disables link previews for links in this message
     * @param reply_to_message_id      If the message is a reply, ID of the original message
     * @param reply_markup             Additional interface options.
     *                                 A JSON-serialized object for a <a href="https://core.telegram.org/bots#keyboards">custom reply keyboard</a>,
     *                                 instructions to hide keyboard or to force a reply from the user.
     * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
     * @throws IOException
     * @throws HttpResponseException
     */
    public Message sendMessage(int chat_id,
                               String text,
                               Optional<Boolean> disable_web_page_preview,
                               Optional<Integer> reply_to_message_id,
                               Optional<Reply> reply_markup) throws IOException {

        if (text == null || disable_web_page_preview == null || reply_to_message_id == null || reply_markup == null)
            throw new NullPointerException("No null params allowed in sendMessage.");

        if (chat_id == 0) throw new IllegalArgumentException("Parameter chat_id shouldn't be zero.");

        Form form = Form.form().add("chat_id", String.valueOf(chat_id)).add("text", text);
        return Request.Post(ApiUri.resolve("sendMessage"))
                .bodyForm(form.build())
                .execute()
                .handleResponse(getResponseHandler(new TypeReference<Response<Message>>() {
                }));
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
     * @param limit   Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
     * @param timeout Timeout in seconds for long polling. Defaults to 0, i.e. usual short polling
     * @return An Array of <a href="https://core.telegram.org/bots/api#update">Update</a> objects is returned.
     * @throws IOException
     * @throws HttpResponseException
     */
    public List<Update> getUpdates(int offset, int limit, int timeout) throws IOException {

        if (limit == 0 || timeout == 0)
            throw new IllegalArgumentException("Parameters limit and timeout shouldn't be zero.");

        List<NameValuePair> nvps = Form.form()
                .add("offset", String.valueOf(offset))
                .add("limit", String.valueOf(limit))
                .add("timeout", String.valueOf(timeout))
                .build();
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
     * @param limit  Limits the number of updates to be retrieved. Values between 1—100 are accepted. Defaults to 100
     * @return An Array of <a href="https://core.telegram.org/bots/api#update">Update</a> objects is returned.
     * @throws IOException
     * @throws HttpResponseException
     */
    public List<Update> getUpdates(int offset, int limit) throws IOException {

        if (limit == 0) throw new IllegalArgumentException("Parameter limit shouldn't be zero.");

        List<NameValuePair> nvps = Form.form()
                .add("offset", String.valueOf(offset))
                .add("limit", String.valueOf(limit))
                .build();
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

        if (timeout == 0) throw new IllegalArgumentException("Parameter timeout shouldn't be zero.");

        List<NameValuePair> nvps = Form.form()
                .add("timeout", String.valueOf(timeout))
                .build();
        return getUpdates(nvps);
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
     * Since nobody else knows your bot‘s token, you can be pretty sure it’s us.
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
        url = Optional.ofNullable(url)
                .orElseThrow(() -> new NullPointerException("Url can't be null."));

        StatusLine statusLine = Request.Post(ApiUri.resolve("setWebHook"))
                .bodyForm(Form.form().add("url", url).build())
                .execute()
                .returnResponse()
                .getStatusLine();

        if (!(statusLine.getStatusCode() == 200))
            throw new HttpResponseException(statusLine.hashCode(), statusLine.getReasonPhrase());
    }

    /**
     * Use this method to forward messages of any kind.
     *
     * @param chat_id      Unique identifier for the message recipient — User or GroupChat id
     * @param from_chat_id Unique identifier for the chat where the original message was sent — User or GroupChat id
     * @param message_id   Unique message identifier
     * @return On success, the sent <a href="https://core.telegram.org/bots/api#message">Message</a> is returned.
     * @throws IOException
     * @throws HttpResponseException
     */
    public Message forwardMessage(int chat_id, int from_chat_id, int message_id) throws IOException {

        if (chat_id == 0 || from_chat_id == 0 || message_id == 0)
            throw new IllegalArgumentException("Parameters shouldn't be zero.");

        Form form = Form.form()
                .add("chat_id", String.valueOf(chat_id))
                .add("from_chat_id", String.valueOf(from_chat_id))
                .add("message_id", String.valueOf(message_id));

        return Request.Post(ApiUri.resolve("forwardMessage"))
                .bodyForm(form.build())
                .execute()
                .handleResponse(getResponseHandler(new TypeReference<Response<Message>>() {
                }));
    }

    /**
     * Use this method to send photos.
     *
     * @param chat_id             Unique identifier for the message recipient — User or GroupChat id
     * @param photo               Photo to send.
     *                            You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
     *                            or upload a new photo using multipart/form-data.
     * @param caption             Photo caption (may also be used when resending photos by file_id).
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. A JSON-serialized object for a custom reply keyboard,
     *                            instructions to hide keyboard or to force a reply from the user.
     * @return Use this method to send photos.
     * @throws IOException
     * @throws HttpResponseException
     */
    public Message sendPhoto(int chat_id, File photo, Optional<String> caption, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
        if (photo == null) throw new NullPointerException("Parameter photo cannot be null.");
        if (!photo.isFile() && photo.exists())
            throw new IllegalArgumentException("Parameter photo must be an existing file.");
        return sendPhoto(chat_id, Source.of(photo), caption, reply_to_message_id, reply_markup);
    }

    /**
     * Use this method to send photos.
     *
     * @param chat_id             Unique identifier for the message recipient — User or GroupChat id
     * @param photo               Photo to send.
     *                            You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
     *                            or upload a new photo using multipart/form-data.
     * @param caption             Photo caption (may also be used when resending photos by file_id).
     * @param reply_to_message_id If the message is a reply, ID of the original message
     * @param reply_markup        Additional interface options. A JSON-serialized object for a custom reply keyboard,
     *                            instructions to hide keyboard or to force a reply from the user.
     * @return Use this method to send photos.
     * @throws IOException
     * @throws HttpResponseException
     */
    public Message sendPhoto(int chat_id, String photo, Optional<String> caption, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
        return sendPhoto(chat_id, Source.of(photo), caption, reply_to_message_id, reply_markup);
    }

    /**
     * Use this method to send photos.
     *
     * @param chat_id Unique identifier for the message recipient — User or GroupChat id
     * @param photo   Photo to send.
     *                You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
     *                or upload a new photo using multipart/form-data.
     * @return Use this method to send photos.
     * @throws IOException
     * @throws HttpResponseException
     */
    public Message sendPhoto(int chat_id, File photo) throws IOException {
        return sendPhoto(chat_id, photo, Optional.empty(), Optional.empty(), Optional.empty());
    }

    /**
     * Use this method to send photos.
     *
     * @param chat_id Unique identifier for the message recipient — User or GroupChat id
     * @param photo   Photo to send.
     *                You can either pass a file_id as String to <a href="https://core.telegram.org/bots/api#resending-files-without-reuploading">resend</a> a photo that is already on the Telegram servers,
     *                or upload a new photo using multipart/form-data.
     * @return Use this method to send photos.
     * @throws IOException
     * @throws HttpResponseException
     */
    public Message sendPhoto(int chat_id, String photo) throws IOException {
        return sendPhoto(chat_id, photo, Optional.empty(), Optional.empty(), Optional.empty());
    }

    private Message sendPhoto(int chat_id, Source photo, Optional<String> caption, Optional<Integer> reply_to_message_id, Optional<Reply> reply_markup) throws IOException {
        if (photo == null || caption == null || reply_to_message_id == null || reply_markup == null)
            throw new NullPointerException("Parameters can't be null.");
        if (chat_id == 0) throw new IllegalArgumentException("Parameter chat_id can't be zero.");

        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .addTextBody("chat_id", String.valueOf(chat_id));

        photo.setField(multipartEntityBuilder, "photo");

        caption.ifPresent(capt -> multipartEntityBuilder.addTextBody("caption", capt));
        reply_to_message_id.ifPresent(id -> multipartEntityBuilder.addTextBody("reply_to_message_id", id.toString()));
        reply_markup.ifPresent(reply -> {
            try {
                multipartEntityBuilder.addTextBody("reply_markup", mapper.writeValueAsString(reply));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

        return Request.Post(ApiUri.resolve("sendPhoto"))
                .body(multipartEntityBuilder.build())
                .execute()
                .handleResponse(getResponseHandler(new TypeReference<Response<Message>>() {
                }));
    }

    private List<Update> getUpdates(List<NameValuePair> nvps) throws IOException {
        URI uri;

        try {
            uri = new URIBuilder(ApiUri.resolve("getUpdates"))
                    .addParameters(nvps)
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return Request.Get(uri)
                .execute()
                .handleResponse(getResponseHandler(new TypeReference<Response<List<Update>>>() {
                }));
    }

    private <T> ResponseHandler<T> getResponseHandler(TypeReference<Response<T>> reference) {
        return (HttpResponse response) -> {
            int code = response.getStatusLine().getStatusCode();
            if (code == 404) throw new HttpResponseException(404, "Telegram bot API out of date.");
            if (code == 200) {
                Response<T> entityResponse = mapper.readValue(response.getEntity().getContent(), reference);
                if (entityResponse.isOk() && entityResponse.getResult() != null) return entityResponse.getResult();
                throw new NullPointerException();
            }
            throw new HttpResponseException(code, response.getStatusLine().getReasonPhrase());
        };
    }
}
