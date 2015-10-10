package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents a Voice file
 */
public class Voice {
	private String file_id;
	private int duration;
	private Optional<String> mime_type = Optional.empty();
	private Optional<Integer> file_size = Optional.empty();

	public int getDuration() {
		return duration;
	}

	public String getId() {
		return file_id;
	}

	public Voice setId(String file_id) {
		this.file_id = file_id;
		return this;
	}

	public Voice setDuration(int duration) {
		this.duration = duration;
		return this;
	}

	public Optional<String> getMime_type() {
		return mime_type;
	}

	public Voice setMime_type(Optional<String> mime_type) {
		this.mime_type = mime_type;
		return this;
	}

	public Optional<Integer> getFile_size() {
		return file_size;
	}

	public Voice setFile_size(Optional<Integer> file_size) {
		this.file_size = file_size;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Voice{");
		sb.append("file_id='").append(file_id).append('\'');
		sb.append(", duration='").append(duration).append('\'');
		sb.append(", mime_type=").append(mime_type);
		sb.append(", file_size=").append(file_size);
		sb.append('}');
		return sb.toString();
	}
}
