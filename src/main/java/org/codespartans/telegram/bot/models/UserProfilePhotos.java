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

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<List<PhotoSize>> getPhotos() {
        return photos;
    }

    public void setPhotos(List<List<PhotoSize>> photos) {
        this.photos = photos;
    }
}
