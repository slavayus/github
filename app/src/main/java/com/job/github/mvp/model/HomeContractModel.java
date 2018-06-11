package com.job.github.mvp.model;

import android.graphics.Bitmap;

import com.job.github.api.pojo.User;

public interface HomeContractModel {
    void loadUser(String token, OnLoadUser onLoadUser);

    void loadAvatar(String avatarUrl, OnDownloadAvatar onDownloadAvatar);

    void updateUserInfo(User user, String token, UpdateUserInfo updateUserInfo);

    interface OnLoadUser {
        void onSuccess(User data);

        void onError();
    }

    interface OnDownloadAvatar {
        void onSuccess(Bitmap image);

        void onError();
    }

    interface UpdateUserInfo {
        void onSuccess(User user);

        void onError();
    }
}
