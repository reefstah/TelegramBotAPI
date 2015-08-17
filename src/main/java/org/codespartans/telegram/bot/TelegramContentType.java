package org.codespartans.telegram.bot;

import java.nio.charset.StandardCharsets;

import org.apache.http.entity.ContentType;

/**
 * @author UnAfraid
 */
public class TelegramContentType
{
	public static final ContentType PAIN_TEXT = ContentType.create("text/plain", StandardCharsets.UTF_8);
	public static final ContentType MULTIPART_FORM_DATA = ContentType.create("multipart/form-data", StandardCharsets.UTF_8);
}
