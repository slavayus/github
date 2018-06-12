package com.job.github.utils;

import android.graphics.Bitmap;

import com.job.github.api.pojo.User;

/**
 * Created by slavik on 6/12/18.
 */

public class UserWithImage {
    private User user;
    private Bitmap bitmap;

    public UserWithImage(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
