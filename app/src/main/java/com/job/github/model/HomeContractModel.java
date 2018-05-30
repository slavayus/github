package com.job.github.model;

import android.graphics.Bitmap;

import com.job.github.pojo.User;

public interface HomeContractModel {
    void loadUser(String token, OnLoadUser onLoadUser);

    void loadAvatar(String avatarUrl, OnDownloadAvatar onDownloadAvatar);

    interface OnLoadUser {
        void onSuccess(User data);

        void onError();
    }

    interface OnDownloadAvatar {
        void onSuccess(Bitmap iamge);

        void onError();
    }
}
