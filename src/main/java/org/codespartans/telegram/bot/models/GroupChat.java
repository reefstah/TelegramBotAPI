package org.codespartans.telegram.bot.models;

/**
 * This object represents a group chat.
 */
public class GroupChat implements Chat {
	private int id;
	private String type;
	private String title;

	@Override
	public int getId() {
		return id;
	}

	public GroupChat setId(int id) {
		this.id = id;
		return this;
	}

	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public GroupChat setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("GroupChat{");
		sb.append("id=").append(id);
		sb.append(", title='").append(title).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
