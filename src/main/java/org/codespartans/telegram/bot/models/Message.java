package org.codespartans.telegram.bot.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.codespartans.telegram.bot.ChatDeserializer;

import java.util.List;
import java.util.Optional;

/**
 * This object represents a message.
 */
public class Message {
    private int message_id;
    private User from;
    private int date;
    @JsonDeserialize(using = ChatDeserializer.class)
    private Chat chat;
    private Optional<User> forward_from;
    private Optional<Integer> forward_date;
    private Optional<Message> reply_to_message;
    private String text;
    private Optional<Audio> audio;
    private Optional<Document> document;
    private Optional<List<PhotoSize>> photo;
    private Optional<Sticker> sticker;
    private Optional<Video> video;
    private Optional<Contact> contact;
    private Optional<Location> location;
    private Optional<User> new_chat_participant;
    private Optional<User> left_chat_participant;
    private Optional<String> new_chat_title;
    private Optional<List<PhotoSize>> new_chat_photo;
    private Optional<Boolean> delete_chat_photo;
    private Optional<Boolean> group_chat_created;
    private Optional<String> caption;

    public int getMessage_id() {
        return message_id;
    }

    public Message setMessage_id(int message_id) {
        this.message_id = message_id;
        return this;
    }

    public User getFrom() {
        return from;
    }

    public Message setFrom(User from) {
        this.from = from;
        return this;
    }

    public int getDate() {
        return date;
    }

    public Message setDate(int date) {
        this.date = date;
        return this;
    }

    public Chat getChat() {
        return chat;
    }

    public Message setChat(Chat chat) {
        this.chat = chat;
        return this;
    }

    public Optional<User> getForward_from() {
        return forward_from;
    }

    public Message setForward_from(Optional<User> forward_from) {
        this.forward_from = forward_from;
        return this;
    }

    public Optional<Integer> getForward_date() {
        return forward_date;
    }

    public Message setForward_date(Optional<Integer> forward_date) {
        this.forward_date = forward_date;
        return this;
    }

    public Optional<Message> getReply_to_message() {
        return reply_to_message;
    }

    public Message setReply_to_message(Optional<Message> reply_to_message) {
        this.reply_to_message = reply_to_message;
        return this;
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public Optional<Audio> getAudio() {
        return audio;
    }

    public Message setAudio(Optional<Audio> audio) {
        this.audio = audio;
        return this;
    }

    public Optional<Document> getDocument() {
        return document;
    }

    public Message setDocument(Optional<Document> document) {
        this.document = document;
        return this;
    }

    public Optional<List<PhotoSize>> getPhoto() {
        return photo;
    }

    public Message setPhoto(Optional<List<PhotoSize>> photo) {
        this.photo = photo;
        return this;
    }

    public Optional<Sticker> getSticker() {
        return sticker;
    }

    public Message setSticker(Optional<Sticker> sticker) {
        this.sticker = sticker;
        return this;
    }

    public Optional<Video> getVideo() {
        return video;
    }

    public Message setVideo(Optional<Video> video) {
        this.video = video;
        return this;
    }

    public Optional<Contact> getContact() {
        return contact;
    }

    public Message setContact(Optional<Contact> contact) {
        this.contact = contact;
        return this;
    }

    public Optional<Location> getLocation() {
        return location;
    }

    public Message setLocation(Optional<Location> location) {
        this.location = location;
        return this;
    }

    public Optional<User> getNew_chat_participant() {
        return new_chat_participant;
    }

    public Message setNew_chat_participant(Optional<User> new_chat_participant) {
        this.new_chat_participant = new_chat_participant;
        return this;
    }

    public Optional<User> getLeft_chat_participant() {
        return left_chat_participant;
    }

    public Message setLeft_chat_participant(Optional<User> left_chat_participant) {
        this.left_chat_participant = left_chat_participant;
        return this;
    }

    public Optional<String> getNew_chat_title() {
        return new_chat_title;
    }

    public Message setNew_chat_title(Optional<String> new_chat_title) {
        this.new_chat_title = new_chat_title;
        return this;
    }

    public Optional<List<PhotoSize>> getNew_chat_photo() {
        return new_chat_photo;
    }

    public Message setNew_chat_photo(Optional<List<PhotoSize>> new_chat_photo) {
        this.new_chat_photo = new_chat_photo;
        return this;
    }

    public Optional<Boolean> getDelete_chat_photo() {
        return delete_chat_photo;
    }

    public Message setDelete_chat_photo(Optional<Boolean> delete_chat_photo) {
        this.delete_chat_photo = delete_chat_photo;
        return this;
    }

    public Optional<Boolean> getGroup_chat_created() {
        return group_chat_created;
    }

    public Message setGroup_chat_created(Optional<Boolean> group_chat_created) {
        this.group_chat_created = group_chat_created;
        return this;
    }

    public Optional<String> getCaption() {
        return caption;
    }

    public Message setCaption(Optional<String> caption) {
        this.caption = caption;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("message_id=").append(message_id);
        sb.append(", from=").append(from);
        sb.append(", date=").append(date);
        sb.append(", chat=").append(chat);
        sb.append(", forward_from=").append(forward_from);
        sb.append(", forward_date=").append(forward_date);
        sb.append(", reply_to_message=").append(reply_to_message);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
