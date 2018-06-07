package com.job.github.mvp.presenter;

import com.job.github.mvp.model.EditUserInfoContractModel;

import java.lang.ref.WeakReference;

/**
 * Created by slavik on 6/7/18.
 */

public class EditUserInfoPresenter {
    private final EditUserInfoContractModel model;
    private WeakReference<EditUserInfoContractView> view;

    public EditUserInfoPresenter(EditUserInfoContractModel model) {
        this.model = model;
    }

    public void attachView(EditUserInfoContractView view) {
        this.view = new WeakReference<>(view);
    }

    public void destroyView() {
        view.clear();
    }

    public void viewIsReady() {
        if (viewIsValid()) {
            view.get().initializeFields();
        }
    }

    private boolean viewIsValid() {
        return view.get() != null;
    }
}
