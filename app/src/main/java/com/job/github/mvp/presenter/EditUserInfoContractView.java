package com.job.github.mvp.presenter;

import com.job.github.api.pojo.User;

/**
 * Created by slavik on 6/7/18.
 */

public interface EditUserInfoContractView {
    void initializeFields();

    void showProgressDialog();

    User getUser();

    String getToken();

    void stopProgressDialog();

    void makeToast(String message);

    void stopFragment(User user);
}
