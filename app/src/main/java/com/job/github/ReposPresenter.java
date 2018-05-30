package com.job.github;

import com.job.github.models.Repos;

import java.lang.ref.WeakReference;
import java.util.List;

class ReposPresenter {

    private final ReposContractModel model;
    private WeakReference<ReposContractView> view;

    ReposPresenter(ReposContractModel model) {
        this.model = model;
    }

    void viewIsReady() {
        downloadRepos();
    }

    void attachView(ReposContractView view) {
        this.view = new WeakReference<>(view);
    }

    private void downloadRepos() {
        final ReposContractView view = this.view.get();
        String mUserLogin = view.getUserLogin();
        String mClientId = view.getClientId();
        String mClientSecret = view.getClientSecret();

        model.loadRepos(mUserLogin, mClientId, mClientSecret, new ReposContractModel.OnLoadRepos() {
            @Override
            public void onSuccess(List<Repos> data) {
                view.showRepos(data);
            }

            @Override
            public void onError() {
                view.showLoadReposError();
            }
        });
    }
}
