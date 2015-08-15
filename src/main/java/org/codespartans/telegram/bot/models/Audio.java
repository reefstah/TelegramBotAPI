package org.codespartans.telegram.bot.models;

import java.util.Optional;

/**
 * This object represents an audio file (voice note).
 */
public class Audio {
    private String file_id;
    private int duration;
    private Optional<String> mime_type;
    private Optional<Integer> file_size;

    public String getFile_id() {
        return file_id;
    }

    public Audio setFile_id(String file_id) {
        this.file_id = file_id;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public Audio setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public Optional<String> getMime_type() {
        return mime_type;
    }

    public Audio setMime_type(Optional<String> mime_type) {
        this.mime_type = mime_type;
        return this;
    }

    public Optional<Integer> getFile_size() {
        return file_size;
    }

    public Audio setFile_size(Optional<Integer> file_size) {
        this.file_size = file_size;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Audio{");
        sb.append("file_id='").append(file_id).append('\'');
        sb.append(", duration=").append(duration);
        sb.append(", mime_type=").append(mime_type);
        sb.append(", file_size=").append(file_size);
        sb.append('}');
        return sb.toString();
    }
}
