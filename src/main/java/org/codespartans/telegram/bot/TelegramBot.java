package org.codespartans.telegram.bot;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.codespartans.telegram.bot.models.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Telegrams bot api.
 *
 * @author Ralph Broers
 *
 */
public class TelegramBot {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new Jdk8Module());
    private static final String APIURL = "https://api.telegram.org/bot";
    private final String tokenizedApiUrl;

    private TelegramBot(String token) {
        this.tokenizedApiUrl = String.format("%s%s/", APIURL, token);
    }

    /**
     * To create a bot and get a token key look at Telegrams documentation about <a href="https://core.telegram.org/bots#botfather">botfather(doc)</a>
     * Or interact with <a href="https://telegram.me/botfather">botfather</a> straight away
     *
     * @param token
     * @return Returns an instance of TelegramBot
     * @throws NullPointerException when inserting null as token
     * @throws NullPointerException when inserting null as token
     * @See NullPointerException
     */
    public static TelegramBot getInstance(String token) {
        token = Optional.ofNullable(token)
                .orElseThrow(() -> new NullPointerException("Don't put null in my API's im nullergic"));

        return new TelegramBot(token);
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
        return Request.Get(String.format("%sgetMe", tokenizedApiUrl)).execute()
                .handleResponse(getResponseHandler(new TypeReference<Response<User>>() {
                }));
    }

    public Message sendMessage(int chat_id,
                               String text,
                               Optional<Boolean> disable_web_page_preview,
                               Optional<Integer> reply_to_message_id,
                               Optional<Reply> reply_markup) throws IOException {
        return Request.Post(String.format("%sendMessage", tokenizedApiUrl))
                .bodyForm(Form.form().add("chat_id", String.valueOf(chat_id)).add("text", text).build())
                .execute()
                .handleResponse(getResponseHandler(new TypeReference<Response<Message>>() {
                }));
    }

    public List<Update> getUpdates(Optional<Integer> offset, Optional<Integer> limit, Optional<Integer> timeout) throws IOException {
        return Request.Get(String.format("%sgetUpdates", tokenizedApiUrl)).execute()
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
