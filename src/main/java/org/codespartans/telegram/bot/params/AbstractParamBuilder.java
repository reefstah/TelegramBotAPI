package org.codespartans.telegram.bot.params;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.codespartans.telegram.bot.TelegramContentType;
import org.codespartans.telegram.bot.models.Reply;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

/**
 * @author UnAfraid
 * @param <T>
 */
public abstract class AbstractParamBuilder<T extends AbstractParamBuilder<?>>
{
	private static final ObjectMapper MAPPER = new ObjectMapper().registerModule(new Jdk8Module());
	private final MultipartEntityBuilder _builder = MultipartEntityBuilder.create().setLaxMode().setContentType(TelegramContentType.MULTIPART_FORM_DATA).setCharset(StandardCharsets.UTF_8);
	private final Set<String> _fieldsAdded = new HashSet<>();
	
	public AbstractParamBuilder(int chatId)
	{
		if (chatId != 0)
		{
			addGenericParam("chat_id", chatId);
		}
	}
	
	public final T replyToMessage(int messageId)
	{
		return addGenericParam("reply_to_message_id", messageId);
	}
	
	public final T disableWebPagePreview(boolean value)
	{
		return addGenericParam("disable_web_page_preview", value);
	}
	
	public final T replyMarkUp(Reply reply) throws JsonProcessingException
	{
		return addGenericParam("reply_markup", MAPPER.writeValueAsString(reply));
	}
	
	protected final T addGenericParam(String name, Object value)
	{
		return addContentBodyParam(name, new StringBody(String.valueOf(value), TelegramContentType.PAIN_TEXT));
	}
	
	@SuppressWarnings("unchecked")
	protected final T addContentBodyParam(String name, ContentBody body)
	{
		_builder.addPart(name, body);
		_fieldsAdded.add(name);
		return (T) this;
	}
	
	public final void validate()
	{
		for (String fieldName : getRequiredFields())
		{
			if (!_fieldsAdded.contains(fieldName))
			{
				throw new IllegalStateException("Required field " + fieldName + " is not set for " + getClass().getSimpleName() + " !");
			}
		}
	}
	
	public final HttpEntity build()
	{
		return _builder.build();
	}
	
	public abstract String getURL();
	
	public abstract Set<String> getRequiredFields();
}
