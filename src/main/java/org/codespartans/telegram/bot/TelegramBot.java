package org.codespartans.telegram.bot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.codespartans.telegram.bot.models.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Telegrams bot api.
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
     * To create a bot and get a token key look at Telegrams documentation about <a href="https://core.telegram.org/bots#botfather">botfather(doc)</a>
     * Or interact with <a href="https://telegram.me/botfather">botfather</a> straight away
     *
     * @param token
     * @return Returns an instance of TelegramBot
     * @throws NullPointerException when inserting null as token
     * @See NullPointerException
     */
    public static TelegramBot getInstance(String token) {
        token = Optional.ofNullable(token)
                .orElseThrow(() -> new NullPointerException("Don't put null in my API's im nullergic"));
        try {
            return new TelegramBot(token);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A simple method for testing your bot's auth token. Requires no parameters.
     * Returns basic information about the bot in form of a User object.
     *
     * @return This returns the bot's user object.
     * @throws ClientProtocolException in case of an http protocol error
     * @throws IOException             in case of a problem or the connection was aborted
     */
    public User getMe() throws IOException {
        return Request.Get(ApiUri.resolve("getMe"))
                .execute()
                .handleResponse(getResponseHandler(new TypeReference<Response<User>>() {
                }));
    }

    public Message sendMessage(int chat_id,
                               String text,
                               Optional<Boolean> disable_web_page_preview,
                               Optional<Integer> reply_to_message_id,
                               Optional<Reply> reply_markup) throws IOException {
        return Request.Post(ApiUri.resolve("sendMessage"))
                .bodyForm(Form.form().add("chat_id", String.valueOf(chat_id)).add("text", text).build())
                .execute()
                .handleResponse(getResponseHandler(new TypeReference<Response<Message>>() {
                }));
    }

    public List<Update> getUpdates(int offset, int limit, int timeout) throws IOException {
        List<NameValuePair> nvps = Form.form()
                .add("offset", String.valueOf(offset))
                .add("limit", String.valueOf(limit))
                .add("timeout", String.valueOf(timeout))
                .build();
        return getUpdates(nvps);
    }

    public List<Update> getUpdates(int offset, int limit) throws IOException {
        List<NameValuePair> nvps = Form.form()
                .add("offset", String.valueOf(offset))
                .add("limit", String.valueOf(limit))
                .build();
        return getUpdates(nvps);
    }

    public List<Update> getUpdates(int timeout) throws IOException {
        List<NameValuePair> nvps = Form.form()
                .add("timeout", String.valueOf(timeout))
                .build();
        return getUpdates(nvps);
    }

    public List<Update> getUpdates() throws IOException {
        return getUpdates(Collections.emptyList());
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
            if (code == 404) throw new TelegramBotApiRuntimeException("API out of date.");
            if (code == 500) throw new ClientProtocolException("Telegram servers having issues.");
            Response<T> entityResponse = mapper.readValue(response.getEntity().getContent(), reference);
            if (entityResponse.isOk() && entityResponse.getResult() != null) return entityResponse.getResult();
            throw new TelegramBotApiRuntimeException(String.format("error_code: %s description: %s", entityResponse.getError_code(), entityResponse.getDescription()));
        };
    }
}
