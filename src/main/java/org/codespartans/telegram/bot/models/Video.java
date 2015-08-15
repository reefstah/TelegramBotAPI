package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents a video file.
 */
public class Video {
    private String file_id;
    private int width;
    private int height;
    private int duration;
    private Optional<PhotoSize> thumb;
    private Optional<String> mime_type;
    private Optional<Integer> file_size;

    public String getFile_id() {
        return file_id;
    }

    public Video setFile_id(String file_id) {
        this.file_id = file_id;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Video setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Video setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public Video setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public Optional<PhotoSize> getThumb() {
        return thumb;
    }

    public Video setThumb(Optional<PhotoSize> thumb) {
        this.thumb = thumb;
        return this;
    }

    public Optional<String> getMime_type() {
        return mime_type;
    }

    public Video setMime_type(Optional<String> mime_type) {
        this.mime_type = mime_type;
        return this;
    }

    public Optional<Integer> getFile_size() {
        return file_size;
    }

    public Video setFile_size(Optional<Integer> file_size) {
        this.file_size = file_size;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Video{");
        sb.append("file_id='").append(file_id).append('\'');
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", duration=").append(duration);
        sb.append(", thumb=").append(thumb);
        sb.append(", mime_type=").append(mime_type);
        sb.append(", file_size=").append(file_size);
        sb.append('}');
        return sb.toString();
    }
}
