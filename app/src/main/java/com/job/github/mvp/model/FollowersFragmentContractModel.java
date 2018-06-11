package com.job.github.mvp.model;

import com.job.github.api.pojo.User;

import java.util.List;

/**
 * Created by slavik on 6/11/18.
 */

public interface FollowersFragmentContractModel {
    void downloadAllFollowers(String userLogin, OnDownloadFollowers onDownloadFollowers);

    interface OnDownloadFollowers {
        void onSuccess(List<User> users);

        void onError();
    }
}
