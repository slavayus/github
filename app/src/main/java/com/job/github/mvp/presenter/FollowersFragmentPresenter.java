package com.job.github.mvp.presenter;

import com.job.github.mvp.model.FollowersFragmentContractModel;

import java.lang.ref.WeakReference;

/**
 * Created by slavik on 6/11/18.
 */

public class FollowersFragmentPresenter {
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
    }

    public void viewIsDestroyed() {
        view.clear();
    }
}
