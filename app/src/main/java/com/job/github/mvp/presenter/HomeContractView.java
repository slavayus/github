package com.job.github.mvp.presenter;

import android.graphics.Bitmap;

import com.job.github.api.pojo.User;


public interface HomeContractView {
    void showProgressDialog();

    String getToken();

    void showErrorDialog();

    void updateToolbarText(String name, String login);

    void stopProgressDialog();

    void onUserGet(User data);

    void showUserAvatar(Bitmap image);

    void updateUserInfo(User user);

    void openBrowser();

    void openMail();

    void openEditBioDialog();

    void showErrorUpdateUserInfoDialog();
}
