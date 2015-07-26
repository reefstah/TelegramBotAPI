package codespartans.telegram.bot.models;

import codespartans.telegram.bot.ChatDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Optional;

/**
 * Created by ralph on 24/07/15.
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

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public Optional<User> getForward_from() {
        return forward_from;
    }

    public void setForward_from(Optional<User> forward_from) {
        this.forward_from = forward_from;
    }

    public Optional<Integer> getForward_date() {
        return forward_date;
    }

    public void setForward_date(Optional<Integer> forward_date) {
        this.forward_date = forward_date;
    }

    public Optional<Message> getReply_to_message() {
        return reply_to_message;
    }

    public void setReply_to_message(Optional<Message> reply_to_message) {
        this.reply_to_message = reply_to_message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Optional<Audio> getAudio() {
        return audio;
    }

    public void setAudio(Optional<Audio> audio) {
        this.audio = audio;
    }

    public Optional<Document> getDocument() {
        return document;
    }

    public void setDocument(Optional<Document> document) {
        this.document = document;
    }

    public Optional<List<PhotoSize>> getPhoto() {
        return photo;
    }

    public void setPhoto(Optional<List<PhotoSize>> photo) {
        this.photo = photo;
    }

    public Optional<Sticker> getSticker() {
        return sticker;
    }

    public void setSticker(Optional<Sticker> sticker) {
        this.sticker = sticker;
    }

    public Optional<Video> getVideo() {
        return video;
    }

    public void setVideo(Optional<Video> video) {
        this.video = video;
    }

    public Optional<Contact> getContact() {
        return contact;
    }

    public void setContact(Optional<Contact> contact) {
        this.contact = contact;
    }

    public Optional<Location> getLocation() {
        return location;
    }

    public void setLocation(Optional<Location> location) {
        this.location = location;
    }

    public Optional<User> getNew_chat_participant() {
        return new_chat_participant;
    }

    public void setNew_chat_participant(Optional<User> new_chat_participant) {
        this.new_chat_participant = new_chat_participant;
    }

    public Optional<User> getLeft_chat_participant() {
        return left_chat_participant;
    }

    public void setLeft_chat_participant(Optional<User> left_chat_participant) {
        this.left_chat_participant = left_chat_participant;
    }

    public Optional<String> getNew_chat_title() {
        return new_chat_title;
    }

    public void setNew_chat_title(Optional<String> new_chat_title) {
        this.new_chat_title = new_chat_title;
    }

    public Optional<List<PhotoSize>> getNew_chat_photo() {
        return new_chat_photo;
    }

    public void setNew_chat_photo(Optional<List<PhotoSize>> new_chat_photo) {
        this.new_chat_photo = new_chat_photo;
    }

    public Optional<Boolean> getDelete_chat_photo() {
        return delete_chat_photo;
    }

    public void setDelete_chat_photo(Optional<Boolean> delete_chat_photo) {
        this.delete_chat_photo = delete_chat_photo;
    }

    public Optional<Boolean> getGroup_chat_created() {
        return group_chat_created;
    }

    public void setGroup_chat_created(Optional<Boolean> group_chat_created) {
        this.group_chat_created = group_chat_created;
    }

    public Optional<String> getCaption() {
        return caption;
    }

    public void setCaption(Optional<String> caption) {
        this.caption = caption;
    }
}
