package com.teamthayhung.superbrain;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by Admin on 17/1/2018.
 */

public class User implements Comparable<User>{

    private String id, email, name, point, photoUrl;


    public User(String email, String name, String point, String photoUrl) {
        this.email = email;
        this.name = name;
        this.point = point;
        this.photoUrl = photoUrl;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public int compareTo(@NonNull User user) {
        return Integer.parseInt(user.getPoint()) - Integer.parseInt(this.getPoint());
    }
}
