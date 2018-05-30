package com.job.github.presenter;

import com.job.github.model.ReposContractModel;
import com.job.github.pojo.Repos;

import java.lang.ref.WeakReference;
import java.util.List;

public class ReposPresenter {

    private final ReposContractModel model;
    private WeakReference<ReposContractView> view;

    public ReposPresenter(ReposContractModel model) {
        this.model = model;
    }

    public void viewIsReady() {
        downloadRepos();
    }

    public void attachView(ReposContractView view) {
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
