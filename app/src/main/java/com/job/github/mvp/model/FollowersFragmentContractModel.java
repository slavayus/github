package com.job.github.mvp.model;

import com.job.github.api.pojo.User;

import java.util.List;

/**
 * Created by slavik on 6/11/18.
 */

public interface FollowersFragmentContractModel {
    void downloadAllFollowers(String userLogin, OnDownloadFollowers onDownloadFollowersFollowers);

    void downloadUser(String login, OnDownloadUser onDownloadUser);

    interface OnDownloadFollowers {
        void onSuccess(List<User> users);

        void onError();
    }

    interface OnDownloadUser {
        void onSuccess(User users);

        void onError();
    }
}
