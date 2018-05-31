package com.job.github.presenter;

import android.graphics.Bitmap;

import com.job.github.model.HomeContractModel;
import com.job.github.pojo.User;

import java.lang.ref.WeakReference;


public class HomePresenter {
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
}
