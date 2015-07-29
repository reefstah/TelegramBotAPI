package org.codespartans.telegram.bot;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.codespartans.telegram.bot.models.Chat;
import org.codespartans.telegram.bot.models.GroupChat;
import org.codespartans.telegram.bot.models.User;

import java.io.IOException;

/**
 * Created by ralph on 26/07/15.
 */
public class ChatDeserializer extends JsonDeserializer<Chat> {

    @Override
    public Chat deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        TreeNode tree = jsonParser.getCodec().readTree(jsonParser);



        jsonParser.nextFieldName();
        String fieldName = jsonParser.nextFieldName();
        return tree.get("first_name") != null ? jsonParser.getCodec().treeToValue(tree, User.class) : jsonParser.getCodec().treeToValue(tree, GroupChat.class);
    }
}
