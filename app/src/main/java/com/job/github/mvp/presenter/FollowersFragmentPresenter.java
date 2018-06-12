package com.job.github.mvp.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.job.github.api.pojo.User;
import com.job.github.mvp.model.FollowersFragmentContractModel;
import com.job.github.utils.UserWithImage;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by slavik on 6/11/18.
 */

public class FollowersFragmentPresenter {
    private static final String TAG = "FollowersFragment";
    private final FollowersFragmentContractModel model;
    private WeakReference<FollowersFragmentContractView> view;

    public FollowersFragmentPresenter(FollowersFragmentContractModel model) {
        this.model = model;
    }


    public void attachView(FollowersFragmentContractView view) {
        this.view = new WeakReference<>(view);
    }


    private boolean viewIsValid() {
        return view.get() != null;
    }

    public void viewIsReady() {
        downloadFollowers();
    }

    private void downloadFollowers() {
        if (!viewIsValid()) {
            return;
        }

        model.downloadAllFollowers(view.get().getUserLogin(), view.get().getClientId(), view.get().getClientSecret(), new FollowersFragmentContractModel.OnDownloadFollowers() {

            @Override
            public void onSuccess(List<User> users) {
                for (int i = 0; i < users.size(); i++) {
                    downloadUser(users.get(i));
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void downloadUser(User user) {
        if (!viewIsValid()) {
            return;
        }
        model.downloadUser(user.getLogin(), view.get().getClientId(), view.get().getClientSecret(), new FollowersFragmentContractModel.OnDownloadUser() {
            @Override
            public void onSuccess(User resultUser) {
                if (viewIsValid()) {
                    UserWithImage userWithImage = new UserWithImage(resultUser);
                    if (viewIsValid()) {
                        view.get().addNewUser(userWithImage);
                        downloadUserAvatar(userWithImage);
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void downloadUserAvatar(final UserWithImage userWithImage) {
        model.downloadUserAvatar(userWithImage.getUser().getAvatarUrl(), new FollowersFragmentContractModel.OnDownloadUserAvatar() {

            @Override
            public void onSuccess(Bitmap bm) {
                userWithImage.setBitmap(bm);
                if (viewIsValid()) {
                    Log.d(TAG, "onSuccess: download avatar number: " + userWithImage.getIndex() + " " + userWithImage.getUser().getName());
                    view.get().updateUserAvatar(userWithImage.getIndex());
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    public void viewIsDestroyed() {
        view.clear();
    }

    public void itemClicked(UserWithImage userWithImage) {
        if (viewIsValid()) {
            view.get().followerSelected(userWithImage.getUser());
        }
    }
}
