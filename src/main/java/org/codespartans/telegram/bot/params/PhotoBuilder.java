package org.codespartans.telegram.bot.params;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.codespartans.telegram.bot.TelegramContentType;

/**
 * @author UnAfraid
 */
public class PhotoBuilder extends AbstractParamBuilder<PhotoBuilder>
{
	private static final String URL = "sendPhoto";
	private static final Set<String> REQUIRED_FIELDS = new HashSet<>(Arrays.asList("chat_id", "photo"));
	
	/**
	 * @param chatId
	 */
	public PhotoBuilder(int chatId)
	{
		super(chatId);
	}
	
	public PhotoBuilder photoFile(String fileName)
	{
		return addGenericParam("photo", fileName);
	}
	
	public PhotoBuilder photoFile(File file)
	{
		return addContentBodyParam("photo", new FileBody(file, TelegramContentType.MULTIPART_FORM_DATA, file.getName()));
	}
	
	public PhotoBuilder photoStream(InputStream stream, String fileName)
	{
		return addContentBodyParam("photo", new InputStreamBody(stream, TelegramContentType.MULTIPART_FORM_DATA, fileName));
	}
	
	public PhotoBuilder caption(String caption)
	{
		return addGenericParam("caption", caption);
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
