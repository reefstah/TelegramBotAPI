package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents a general file (as opposed to <a href="https://core.telegram.org/bots/api#photosize">photos</a>
 * and <a href="https://core.telegram.org/bots/api#audio">audio files</a>).
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

    public Document setFile_id(String file_id) {
        this.file_id = file_id;
        return this;
    }

    public Optional<PhotoSize> getThumb() {
        return thumb;
    }

    public Document setThumb(Optional<PhotoSize> thumb) {
        this.thumb = thumb;
        return this;
    }

    public Optional<String> getFile_name() {
        return file_name;
    }

    public Document setFile_name(Optional<String> file_name) {
        this.file_name = file_name;
        return this;
    }

    public Optional<String> getMime_type() {
        return mime_type;
    }

    public Document setMime_type(Optional<String> mime_type) {
        this.mime_type = mime_type;
        return this;
    }

    public Optional<Integer> getFile_size() {
        return file_size;
    }

    public Document setFile_size(Optional<Integer> file_size) {
        this.file_size = file_size;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Document{");
        sb.append("file_size=").append(file_size);
        sb.append(", mime_type=").append(mime_type);
        sb.append(", file_name=").append(file_name);
        sb.append(", thumb=").append(thumb);
        sb.append(", file_id='").append(file_id).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
