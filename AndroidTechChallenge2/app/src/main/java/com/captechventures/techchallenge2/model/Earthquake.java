package com.captechventures.techchallenge2.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mluansing on 9/20/17.
 */

public class Earthquake {

    private String title;
    private double latitude;
    private double longitude;
    private Date time;
    private double magnitude;
    private String url;
    private String id;
    private boolean isChecked;

//    private String place;
//    private Date updated;
//    private int tz;
//    private String jsonUrl;
//    private int felt;
//    private int cdi;

    public Earthquake(String title, double latitude, double longitude, Date time, double magnitude, String url, String id) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.magnitude = magnitude;
        this.url = url;
        this.id = id;
        this.isChecked = false;
    }

    public Earthquake(String title, double latitude, double longitude, Date time, double magnitude, String url, String id, boolean isChecked) {
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
        this.magnitude = magnitude;
        this.url = url;
        this.id = id;
        this.isChecked = isChecked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getTime() {
        return time;
    }

    // pass in long from JSON -- use getDateTime() to format
    public void setTime(Date time) {
        this.time = time;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    // format Date from long to String
    public String getDateTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = df.format(this.time);
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Earthquake that = (Earthquake) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;
        if (Double.compare(that.magnitude, magnitude) != 0) return false;
        if (isChecked != that.isChecked) return false;
        if (!title.equals(that.title)) return false;
        if (!time.equals(that.time)) return false;
        if (!url.equals(that.url)) return false;
        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = title.hashCode();
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + time.hashCode();
        temp = Double.doubleToLongBits(magnitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + url.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (isChecked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Earthquake{" +
                "title='" + title + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", time=" + time +
                ", magnitude=" + magnitude +
                ", url='" + url + '\'' +
                ", id='" + id + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
