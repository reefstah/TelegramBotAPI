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
public class StickerBuilder extends AbstractParamBuilder<StickerBuilder>
{
	private static final String URL = "sendSticker";
	private static final Set<String> REQUIRED_FIELDS = new HashSet<>(Arrays.asList("chat_id", "sticker"));
	
	/**
	 * @param chatId
	 */
	public StickerBuilder(int chatId)
	{
		super(chatId);
	}
	
	public StickerBuilder stickerFile(String fileName)
	{
		return addGenericParam("sticker", fileName);
	}
	
	public StickerBuilder stickerFile(File file)
	{
		return addContentBodyParam("sticker", new FileBody(file, TelegramContentType.MULTIPART_FORM_DATA, file.getName()));
	}
	
	public StickerBuilder stickerStream(InputStream stream, String fileName)
	{
		return addContentBodyParam("sticker", new InputStreamBody(stream, TelegramContentType.MULTIPART_FORM_DATA, fileName));
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
