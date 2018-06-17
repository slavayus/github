package com.job.github.mvp.presenter;

import com.job.github.mvp.model.EditUserInfoContractModel;

import java.lang.ref.WeakReference;

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

    public void editIsDone() {
        if (!viewIsValid()) {
            return;
        }
        model.updateUserInfo(view.get().getUser(), view.get().getToken())
                .doOnSubscribe(disposable -> view.get().showProgressDialog())
                .doFinally(() -> {
                    if (viewIsValid()) {
                        view.get().stopProgressDialog();
                    }
                })
                .subscribe(user -> {
                    if (viewIsValid()) {
                        view.get().makeToast("Data successfully updated");
                        view.get().stopFragment(user);
                    }
                }, throwable -> {
                    if (viewIsValid()) {
                        view.get().makeToast("An error occurred while updating the data");
                    }
                });
    }
}
