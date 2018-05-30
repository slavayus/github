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
        ReposContractView savedView = view.get();
        if (savedView != null) {
            savedView.showLoaderFragment();
            downloadRepos();
        }
    }

    public void attachView(ReposContractView view) {
        this.view = new WeakReference<>(view);
    }

    private void downloadRepos() {
        ReposContractView savedView = view.get();
        if (savedView == null) {
            return;
        }
        String mUserLogin = savedView.getUserLogin();
        String mClientId = savedView.getClientId();
        String mClientSecret = savedView.getClientSecret();

        model.loadRepos(mUserLogin, mClientId, mClientSecret, new ReposContractModel.OnLoadRepos() {
            @Override
            public void onSuccess(List<Repos> data) {
                savedView.stopLoaderFragment();
                savedView.showRepos(data);
            }

            @Override
            public void onError() {
                savedView.showLoadReposError();
            }
        });
    }
}
