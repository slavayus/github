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
        if (viewIsValid()) {
            view.get().showLoaderFragment();
            downloadRepos();
        }
    }

    public void attachView(ReposContractView view) {
        this.view = new WeakReference<>(view);
    }

    private void downloadRepos() {
        if (!viewIsValid()) {
            return;
        }

        model.loadRepos(view.get().getUserLogin(), view.get().getClientId(), view.get().getClientSecret(), new ReposContractModel.OnLoadRepos() {
            @Override
            public void onSuccess(List<Repos> data) {
                if (!viewIsValid()) {
                    return;
                }
                view.get().stopLoaderFragment();
                view.get().showRepos(data);
            }

            @Override
            public void onError() {
                if (viewIsValid()) {
                    view.get().showLoadReposError();
                }
            }
        });
    }

    private boolean viewIsValid() {
        return view.get() != null;
    }

    public void destroyView() {
        view.clear();
    }
}
