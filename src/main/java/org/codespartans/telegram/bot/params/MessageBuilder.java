package org.codespartans.telegram.bot.params;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author UnAfraid
 */
public class MessageBuilder extends AbstractParamBuilder<MessageBuilder>
{
	private static final String URL = "sendMessage";
	private static final Set<String> REQUIRED_FIELDS = new HashSet<>(Arrays.asList("chat_id", "text"));
	
	/**
	 * @param chatId
	 */
	public MessageBuilder(int chatId)
	{
		super(chatId);
	}
	
	public MessageBuilder text(String text)
	{
		return addGenericParam("text", text);
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
