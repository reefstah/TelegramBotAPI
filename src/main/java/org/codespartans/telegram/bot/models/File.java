package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents a File.
 */
public class File {
	private String file_id;
	private Optional<Integer> file_size = Optional.empty();
	private Optional<String> file_path = Optional.empty();

	public String getId() {
		return file_id;
	}

	public File setId(String file_id) {
		this.file_id = file_id;
		return this;
	}

	public Optional<Integer> getSize() {
		return file_size;
	}

	public File setSize(Optional<Integer> file_size) {
		this.file_size = file_size;
		return this;
	}

	public Optional<String> getPath() {
		return file_path;
	}

	public File setPath(Optional<String> file_path) {
		this.file_path = file_path;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("File{");
		sb.append("id=").append(file_id);
		sb.append(", size='").append(file_size).append('\'');
		sb.append(", path=").append(file_path);
		sb.append('}');
		return sb.toString();
	}
}
