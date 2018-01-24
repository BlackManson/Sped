package com.dell.sped;

/**
 * Created by Dell on 16.01.2018.
 */

public class User {
    private String name;
    private String image;
    private String email;
    private String online;
    private String lat;
    private String lo;

    public User() {
    }

    public User(String name, String image, String email, String online, String lat, String lo) {
        this.name = name;
        this.image = image;
        this.email = email;
        this.online = online;
        this.lat = lat;
        this.lo = lo;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLo() {
        return lo;
    }

    public void setLo(String lo) {
        this.lo = lo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
}
