package com.job.github.presenter;

import com.job.github.api.URLHelper;
import com.job.github.model.WebViewContractModel;
import com.job.github.pojo.Token;

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
        WebViewContractView savedView = view.get();
        if (savedView != null) {
            savedView.loadUrl(URLHelper.REGISTER_BASE_URL + URLHelper.AUTHORIZE_URL + "?client_id=" + clientId + "&redirect_uri=" + URLHelper.REDIRECT_URL);
        }
    }

    public void authenticate(String clientId, String clientSecret, String url) {
        final WebViewContractView savedView = view.get();
        if (savedView == null) {
            return;
        }
        savedView.showProgressDialog();

        model.authenticate(clientId, clientSecret, url, new WebViewContractView.OnAuthenticate() {

            @Override
            public void onSuccess(Token token) {
                savedView.dismissDialog();
                savedView.onGetToken(token.getAccessToken());
            }

            @Override
            public void onFailure() {
                savedView.dismissDialog();
                savedView.showErrorDialog();
            }
        });

    }
}
