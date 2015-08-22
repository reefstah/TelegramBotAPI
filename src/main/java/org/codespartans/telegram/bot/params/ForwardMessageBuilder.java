package org.codespartans.telegram.bot.params;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author UnAfraid
 */
public class ForwardMessageBuilder extends AbstractParamBuilder<ForwardMessageBuilder>
{
	private static final String URL = "forwardMessage";
	private static final Set<String> REQUIRED_FIELDS = new HashSet<>(Arrays.asList("chat_id", "from_chat_id", "message_id"));
	
	/**
	 * @param chatId
	 */
	public ForwardMessageBuilder(int chatId)
	{
		super(chatId);
	}
	
	public ForwardMessageBuilder fromChatId(int from_chat_id)
	{
		return addGenericParam("from_chat_id", from_chat_id);
	}
	
	public ForwardMessageBuilder messageId(int messageId)
	{
		return addGenericParam("message_id", messageId);
	}
	
	@Override
	public String getURL()
	{
		return URL;
	}
	
	@Override
	public Set<String> getRequiredFields()
	{
		return REQUIRED_FIELDS;
	}
}
