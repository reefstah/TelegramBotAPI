package org.codespartans.telegram.bot.models;

/**
 * This object represents a point on the map.
 */
public class Location {
    private float longitude;
    private float latitude;

    public float getLongitude() {
        return longitude;
    }

    public Location setLongitude(float longitude) {
        this.longitude = longitude;
        return this;
    }

    public float getLatitude() {
        return latitude;
    }

    public Location setLatitude(float latitude) {
        this.latitude = latitude;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Location{");
        sb.append("longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append('}');
        return sb.toString();
    }
}
