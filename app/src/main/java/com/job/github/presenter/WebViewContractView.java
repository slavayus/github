package com.job.github.presenter;

import com.job.github.pojo.Token;

/**
 * Created by slavik on 5/30/18.
 */

public interface WebViewContractView {
    void loadUrl(String path);

    void showProgressDialog();

    void dismissDialog();

    void onGetToken(String token);

    void showErrorDialog();

    interface OnAuthenticate {
        void onSuccess(Token token);

        void onFailure();
    }
}
