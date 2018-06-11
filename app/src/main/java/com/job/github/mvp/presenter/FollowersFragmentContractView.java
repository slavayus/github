package com.job.github.mvp.presenter;

import com.job.github.api.pojo.User;

/**
 * Created by slavik on 6/11/18.
 */

public interface FollowersFragmentContractView {
    String getUserLogin();

    void addNewUser(User user);
}
