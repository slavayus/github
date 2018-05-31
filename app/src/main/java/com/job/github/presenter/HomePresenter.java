package com.job.github.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.job.github.model.HomeContractModel;
import com.job.github.pojo.User;

import java.lang.ref.WeakReference;


public class HomePresenter {
    private static final String TAG = "HomePresenter";
    private final HomeContractModel model;
    private WeakReference<HomeContractView> view;

    public HomePresenter(HomeContractModel homeModel) {
        this.model = homeModel;
    }

    public void attachView(HomeContractView view) {
        this.view = new WeakReference<>(view);
    }

    public void viewIsReady() {
        downloadUser();
    }

    private void downloadUser() {
        final HomeContractView savedView = this.view.get();
        savedView.showProgressDialog();
        String token = savedView.getToken();
        model.loadUser(token, new HomeContractModel.OnLoadUser() {

            @Override
            public void onSuccess(User data) {
                savedView.updateToolbarText(data.getName(), data.getLogin());
                savedView.updateUserInfo(data);
                savedView.onUserGet(data);
                savedView.stopProgressDialog();
                downloadAvatar(data.getAvatarUrl());
            }

            @Override
            public void onError() {
                savedView.showErrorDialog();
            }
        });
    }

    private void downloadAvatar(String avatarUrl) {
        final HomeContractView savedView = view.get();
        if (savedView == null) {
            return;
        }
        model.loadAvatar(avatarUrl, new HomeContractModel.OnDownloadAvatar() {
            @Override
            public void onSuccess(Bitmap image) {
                savedView.showUserAvatar(image);
            }

            @Override
            public void onError() {

            }
        });
    }

    // TODO: 5/31/18 save user in model and get the url from the stored user
    public void userBlogButtonClick() {
        HomeContractView storedView = view.get();
        if (storedView == null) {
            return;
        }
        storedView.openBrowser();
        Log.d(TAG, "userBlogButtonClick: ");
    }
}
