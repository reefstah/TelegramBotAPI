package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents one size of a photo or a <a href="https://core.telegram.org/bots/api#document">file</a> / <a href="https://core.telegram.org/bots/api#sticker">sticker</a> thumbnail.
 */
public class PhotoSize {
    private String file_id;
    private int width;
    private int height;
    private Optional<Integer> file_size;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Optional<Integer> getFile_size() {
        return file_size;
    }

    public void setFile_size(Optional<Integer> file_size) {
        this.file_size = file_size;
    }
}
