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
public class DocumentBuilder extends AbstractParamBuilder<DocumentBuilder>
{
	private static final String URL = "sendDocument";
	private static final Set<String> REQUIRED_FIELDS = new HashSet<>(Arrays.asList("chat_id", "document"));
	
	/**
	 * @param chatId
	 */
	public DocumentBuilder(int chatId)
	{
		super(chatId);
	}
	
	public DocumentBuilder documentFile(String fileName)
	{
		return addGenericParam("document", fileName);
	}
	
	public DocumentBuilder documentFile(File file)
	{
		return addContentBodyParam("document", new FileBody(file, TelegramContentType.MULTIPART_FORM_DATA, file.getName()));
	}
	
	public DocumentBuilder documentStream(InputStream stream, String fileName)
	{
		return addContentBodyParam("document", new InputStreamBody(stream, TelegramContentType.MULTIPART_FORM_DATA, fileName));
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
