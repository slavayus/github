package com.job.github.mvp.model;

import android.graphics.Bitmap;

import com.job.github.api.pojo.User;

import java.util.List;

/**
 * Created by slavik on 6/11/18.
 */

public interface FollowersFragmentContractModel {
    void downloadAllFollowers(String userLogin, String clientId, String clientSecret, OnDownloadFollowers onDownloadFollowersFollowers);

    void downloadUser(String login, String clientId, String clientSecret, OnDownloadUser onDownloadUser);

    void downloadUserAvatar(String url, OnDownloadUserAvatar onDownloadUserAvatar);

    interface OnDownloadFollowers {
        void onSuccess(List<User> users);

        void onError();
    }

    interface OnDownloadUser {
        void onSuccess(User users);

        void onError();
    }

    interface OnDownloadUserAvatar {
        void onSuccess(Bitmap bm);

        void onError();
    }
}
