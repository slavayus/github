package com.job.github.model;

import com.job.github.presenter.WebViewContractView;

/**
 * Created by slavik on 5/30/18.
 */

public interface WebViewContractModel {
    void authenticate(String clientId, String clientSecret, String url, WebViewContractView.OnAuthenticate onAuthenticate);
}
