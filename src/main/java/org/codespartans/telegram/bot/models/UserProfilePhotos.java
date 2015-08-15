package org.codespartans.telegram.bot.models;

import java.util.List;

/**
 * This object represent a user's profile pictures.
 */
public class UserProfilePhotos {
    private int total_count;
    private List<List<PhotoSize>> photos;

    public int getTotal_count() {
        return total_count;
    }

    public UserProfilePhotos setTotal_count(int total_count) {
        this.total_count = total_count;
        return this;
    }

    public List<List<PhotoSize>> getPhotos() {
        return photos;
    }

    public UserProfilePhotos setPhotos(List<List<PhotoSize>> photos) {
        this.photos = photos;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserProfilePhotos{");
        sb.append("total_count=").append(total_count);
        sb.append(", photos=").append(photos);
        sb.append('}');
        return sb.toString();
    }
}
