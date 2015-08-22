package org.codespartans.telegram.bot.params;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author UnAfraid
 */
public class UserProfilePhotosBuilder extends AbstractParamBuilder<UserProfilePhotosBuilder>
{
	private static final String URL = "getUserProfilePhotos";
	private static final Set<String> REQUIRED_FIELDS = new HashSet<>(Arrays.asList("user_id"));
	
	public UserProfilePhotosBuilder()
	{
		super(0);
	}
	
	public UserProfilePhotosBuilder userId(int userId)
	{
		return addGenericParam("user_id", userId);
	}
	
	public UserProfilePhotosBuilder offset(int offset)
	{
		return addGenericParam("offset", offset);
	}
	
	public UserProfilePhotosBuilder limit(int limit)
	{
		return addGenericParam("limit", limit);
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
