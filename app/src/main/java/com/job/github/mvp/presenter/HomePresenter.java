package com.job.github.mvp.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.job.github.api.pojo.User;
import com.job.github.mvp.model.HomeContractModel;
import com.job.github.utils.ClientPreferences;

import java.lang.ref.WeakReference;


public class HomePresenter {
    private static final String TAG = "HomePresenter";
    private final HomeContractModel model;
    private final ClientPreferences clientPreferences;
    private WeakReference<HomeContractView> view;

    public HomePresenter(HomeContractModel homeModel, ClientPreferences clientPreferences) {
        this.model = homeModel;
        this.clientPreferences = clientPreferences;
    }

    public void attachView(HomeContractView view) {
        this.view = new WeakReference<>(view);
    }

    public void viewIsReady() {
        downloadUser();
    }

    private void downloadUser() {
        if (!viewIsValid()) {
            return;
        }
        view.get().showProgressDialog();
        model.loadUser(view.get().getToken(), new HomeContractModel.OnLoadUser() {

            @Override
            public void onSuccess(User data) {
                if (viewIsValid()) {
                    view.get().updateToolbarText(data.getName(), data.getLogin());
                    view.get().updateUserInfo(data);
                    view.get().onUserGet(data);
                    clientPreferences.setUserLogin(data.getLogin());
                    view.get().stopProgressDialog();
                    downloadAvatar(data.getAvatarUrl());
                }
            }

            @Override
            public void onError() {
                if (viewIsValid()) {
                    view.get().showErrorDialog();
                }
            }
        });
    }

    private boolean viewIsValid() {
        return view.get() != null;
    }

    private void downloadAvatar(String avatarUrl) {
        if (!viewIsValid()) {
            return;
        }
        model.loadAvatar(avatarUrl, new HomeContractModel.OnDownloadAvatar() {
            @Override
            public void onSuccess(Bitmap image) {
                if (viewIsValid()) {
                    view.get().showUserAvatar(image);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    // TODO: 5/31/18 save user in model and get the url from the stored user
    public void userBlogButtonClick() {
        if (viewIsValid()) {
            view.get().openBrowser();
            Log.d(TAG, "userBlogButtonClick: ");
        }
    }

    public void userEmailButtonClick() {
        if (viewIsValid()) {
            view.get().openMail();
            Log.d(TAG, "userEmailButtonClick: ");
        }
    }

    public void userBioButtonClick() {
        if (viewIsValid()) {
            view.get().openEditBioDialog();
        }
    }

    public void newBio(String text) {
        if (!viewIsValid()) {
            return;
        }
        User user = new User();
        user.setBio(text);
        model.updateUserInfo(user, view.get().getToken(), new HomeContractModel.UpdateUserInfo() {

            @Override
            public void onSuccess(User user) {
                if (viewIsValid()) {
                    view.get().updateUserInfo(user);
                }
            }

            @Override
            public void onError() {
                if (viewIsValid()) {
                    view.get().showErrorUpdateUserInfoDialog();
                }
            }
        });
    }

    public void destroyView() {
        if (viewIsValid()) {
            view.get().stopProgressDialog();
            view.clear();
        }
    }
}
