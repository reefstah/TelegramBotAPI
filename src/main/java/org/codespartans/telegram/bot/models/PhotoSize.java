package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents one size of a photo or a <a href="https://core.telegram.org/bots/api#document">file</a> / <a href="https://core.telegram.org/bots/api#sticker">sticker</a> thumbnail.
 */
public class PhotoSize {
    private String file_id;
    private int width;
    private int height;
    private Optional<Integer> file_size = Optional.empty();

    public String getFile_id() {
        return file_id;
    }

    public PhotoSize setFile_id(String file_id) {
        this.file_id = file_id;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public PhotoSize setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public PhotoSize setHeight(int height) {
        this.height = height;
        return this;
    }

    public Optional<Integer> getFile_size() {
        return file_size;
    }

    public PhotoSize setFile_size(Optional<Integer> file_size) {
        this.file_size = file_size;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhotoSize{");
        sb.append("file_id='").append(file_id).append('\'');
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", file_size=").append(file_size);
        sb.append('}');
        return sb.toString();
    }
}
