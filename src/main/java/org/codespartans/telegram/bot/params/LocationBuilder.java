package org.codespartans.telegram.bot.params;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author UnAfraid
 */
public class LocationBuilder extends AbstractParamBuilder<LocationBuilder>
{
	private static final String URL = "sendLocation";
	private static final Set<String> REQUIRED_FIELDS = new HashSet<>(Arrays.asList("chat_id", "latitude", "longitude"));
	
	/**
	 * @param chatId
	 */
	public LocationBuilder(int chatId)
	{
		super(chatId);
	}
	
	public LocationBuilder latitude(float latitude)
	{
		return addGenericParam("latitude", latitude);
	}
	
	public LocationBuilder longitude(float longitude)
	{
		return addGenericParam("longitude", longitude);
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
