package org.codespartans.telegram.bot.params;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.codespartans.telegram.bot.models.Action;

/**
 * @author UnAfraid
 */
public class ChatActionBuilder extends AbstractParamBuilder<ChatActionBuilder>
{
	private static final String URL = "sendChatAction";
	private static final Set<String> REQUIRED_FIELDS = new HashSet<>(Arrays.asList("chat_id", "action"));
	
	/**
	 * @param chatId
	 */
	public ChatActionBuilder(int chatId)
	{
		super(chatId);
	}
	
	public ChatActionBuilder action(Action action)
	{
		return addGenericParam("action", action.name().toLowerCase());
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
