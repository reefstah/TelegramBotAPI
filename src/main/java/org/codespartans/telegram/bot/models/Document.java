package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * Created by ralph on 24/07/15.
 */
public class Document {
    private String file_id;
    private Optional<PhotoSize> thumb;
    private Optional<String> file_name;
    private Optional<String> mime_type;
    private Optional<Integer> file_size;

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public Optional<PhotoSize> getThumb() {
        return thumb;
    }

    public void setThumb(Optional<PhotoSize> thumb) {
        this.thumb = thumb;
    }

    public Optional<String> getFile_name() {
        return file_name;
    }

    public void setFile_name(Optional<String> file_name) {
        this.file_name = file_name;
    }

    public Optional<String> getMime_type() {
        return mime_type;
    }

    public void setMime_type(Optional<String> mime_type) {
        this.mime_type = mime_type;
    }

    public Optional<Integer> getFile_size() {
        return file_size;
    }

    public void setFile_size(Optional<Integer> file_size) {
        this.file_size = file_size;
    }
}
