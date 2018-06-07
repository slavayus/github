package com.job.github.mvp.presenter;

import com.job.github.api.pojo.User;
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

    public void editIsDone() {
        if (!viewIsValid()) {
            return;
        }
        view.get().showProgressDialog();
        model.updateUserInfo(view.get().getUser(), view.get().getToken(), new EditUserInfoContractModel.OnUpdateUserInfo() {

            @Override
            public void onSuccess(User user) {
                if (viewIsValid()) {
                    view.get().stopProgressDialog();
                    view.get().makeToast("Data successfully updated");
                    view.get().stopFragment(user);
                }

            }

            @Override
            public void onError() {
                view.get().stopProgressDialog();
                view.get().makeToast("An error occurred while updating the data");
            }
        });
    }
}
