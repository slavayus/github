package com.job.github.presenter;

import android.graphics.Bitmap;

import com.job.github.pojo.User;


public interface HomeContractView {
    void showProgressDialog();

    String getToken();

    void showErrorDialog();

    void updateToolbarText(String name);

    void stopProgressDialog();

    void onUserGet(User data);

    void showUserAvatar(Bitmap image);
}
