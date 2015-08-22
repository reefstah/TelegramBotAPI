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
public class VideoBuilder extends AbstractParamBuilder<VideoBuilder>
{
	private static final String URL = "sendVideo";
	private static final Set<String> REQUIRED_FIELDS = new HashSet<>(Arrays.asList("chat_id", "video"));
	
	/**
	 * @param chatId
	 */
	public VideoBuilder(int chatId)
	{
		super(chatId);
	}
	
	public VideoBuilder videoFile(String fileName)
	{
		return addGenericParam("video", fileName);
	}
	
	public VideoBuilder videoFile(File file)
	{
		return addContentBodyParam("video", new FileBody(file, TelegramContentType.MULTIPART_FORM_DATA, file.getName()));
	}
	
	public VideoBuilder videoStream(InputStream stream, String fileName)
	{
		return addContentBodyParam("video", new InputStreamBody(stream, TelegramContentType.MULTIPART_FORM_DATA, fileName));
	}
	
	public VideoBuilder duration(int duration)
	{
		return addGenericParam("duration", duration);
	}
	
	public VideoBuilder caption(String caption)
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
