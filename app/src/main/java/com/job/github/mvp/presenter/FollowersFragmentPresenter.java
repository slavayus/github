package com.job.github.mvp.presenter;

import com.job.github.api.pojo.User;
import com.job.github.mvp.model.FollowersFragmentContractModel;

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

        model.downloadAllFollowers(view.get().getUserLogin(), new FollowersFragmentContractModel.OnDownloadFollowers() {

            @Override
            public void onSuccess(List<User> users) {
                for (int i = 0; i < users.size(); i++) {
                    downloadUser(users.get(i), i);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void downloadUser(User user, final int i) {
        model.downloadUser(user.getLogin(), new FollowersFragmentContractModel.OnDownloadUser() {
            @Override
            public void onSuccess(User resultUser) {
                if (viewIsValid()) {
                    view.get().addNewUser(resultUser);
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
}
