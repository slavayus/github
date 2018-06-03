package com.job.github.mvp.presenter;

import com.job.github.api.URLHelper;
import com.job.github.api.pojo.Token;
import com.job.github.mvp.model.WebViewContractModel;

import java.lang.ref.WeakReference;

public class WebViewPresenter {
    private final WebViewContractModel model;
    private WeakReference<WebViewContractView> view;

    public WebViewPresenter(WebViewContractModel model) {
        this.model = model;
    }

    public void attachView(WebViewContractView view) {
        this.view = new WeakReference<>(view);
    }


    public void viewIsReady() {

    }

    public void resume(String clientId) {
        if (viewIsValid()) {
            view.get().loadUrl(URLHelper.REGISTER_BASE_URL + URLHelper.AUTHORIZE_URL + "?client_id=" + clientId + "&redirect_uri=" + URLHelper.REDIRECT_URL + "&scope=user,public_repo");
        }
    }

    private boolean viewIsValid() {
        return view.get() != null;
    }

    public void authenticate(String clientId, String clientSecret, String url) {
        if (!viewIsValid()) {
            return;
        }
        view.get().showProgressDialog();

        model.authenticate(clientId, clientSecret, url, new WebViewContractView.OnAuthenticate() {

            @Override
            public void onSuccess(Token token) {
                if (viewIsValid()) {
                    view.get().dismissDialog();
                    view.get().onGetToken(token.getAccessToken());
                }
            }

            @Override
            public void onFailure() {
                if (viewIsValid()) {
                    view.get().dismissDialog();
                    view.get().showErrorDialog();
                }
            }
        });

    }

    public void destroyView() {
        if (viewIsValid()) {
            view.get().dismissDialog();
            view.clear();
        }
    }
}
