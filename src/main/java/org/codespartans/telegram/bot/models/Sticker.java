package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents a sticker.
 */
public class Sticker {
    private String file_id;
    private int width;
    private int height;
    private Optional<PhotoSize> thumb;
    private Optional<Integer> file_size;

    public String getFile_id() {
        return file_id;
    }

    public Sticker setFile_id(String file_id) {
        this.file_id = file_id;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public Sticker setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public Sticker setHeight(int height) {
        this.height = height;
        return this;
    }

    public Optional<PhotoSize> getThumb() {
        return thumb;
    }

    public Sticker setThumb(Optional<PhotoSize> thumb) {
        this.thumb = thumb;
        return this;
    }

    public Optional<Integer> getFile_size() {
        return file_size;
    }

    public Sticker setFile_size(Optional<Integer> file_size) {
        this.file_size = file_size;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Sticker{");
        sb.append("file_size=").append(file_size);
        sb.append(", thumb=").append(thumb);
        sb.append(", height=").append(height);
        sb.append(", width=").append(width);
        sb.append(", file_id='").append(file_id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
