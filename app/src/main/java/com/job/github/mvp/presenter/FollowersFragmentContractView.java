package com.job.github.mvp.presenter;

import com.job.github.utils.UserWithImage;

/**
 * Created by slavik on 6/11/18.
 */

public interface FollowersFragmentContractView {
    String getUserLogin();

    void addNewUser(UserWithImage user);

    void updateUserAvatar(int i);

    String getClientId();

    String getClientSecret();
}
