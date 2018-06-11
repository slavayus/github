package com.job.github.mvp.model;

import com.job.github.api.pojo.User;

/**
 * Created by slavik on 6/7/18.
 */

public interface EditUserInfoContractModel {
    void updateUserInfo(User user, String token, OnUpdateUserInfo onUpdateUserInfo);

    interface OnUpdateUserInfo {
        void onSuccess(User user);

        void onError();
    }
}
