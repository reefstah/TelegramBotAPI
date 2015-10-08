package org.codespartans.telegram.bot.models;

import java.util.Optional;

public class Chat
{
	private int id;
	private String type;
	private Optional<String> title = Optional.empty();
	private Optional<String> first_name = Optional.empty();
	private Optional<String> last_name = Optional.empty();
	private Optional<String> username = Optional.empty();
	
	public int getId()
	{
		return id;
	}
	
	public Chat setId(int id)
	{
		this.id = id;
		return this;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public Optional<String> getTitle()
	{
		return title;
	}
	
	public Chat setTitle(Optional<String> title)
	{
		this.title = title;
		return this;
	}
	
	public Optional<String> getFirst_name()
	{
		return first_name;
	}
	
	public Chat setFirst_name(Optional<String> first_name)
	{
		this.first_name = first_name;
		return this;
	}
	
	public Optional<String> getLast_name()
	{
		return last_name;
	}
	
	public Chat setLast_name(Optional<String> last_name)
	{
		this.last_name = last_name;
		return this;
	}
	
	public Optional<String> getUsername()
	{
		return username;
	}
	
	public Chat setUsername(Optional<String> username)
	{
		this.username = username;
		return this;
	}
	
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("Chat{");
		sb.append("id=").append(id);
		sb.append(", type='").append(type).append('\'');
		sb.append(", title='").append(title).append('\'');
		sb.append(", first_name='").append(first_name).append('\'');
		sb.append(", last_name=").append(last_name);
		sb.append(", username=").append(username);
		sb.append('}');
		return sb.toString();
	}
}
