package com.job.github.module;

import com.job.github.annotation.GitHubOAuthService;
import com.job.github.api.GitHubApi;
import com.job.github.model.WebViewContractModel;
import com.job.github.model.WebViewModel;
import com.job.github.presenter.WebViewPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/2/18.
 */

@Module(includes = {GitHubOAuthModule.class})
public class WebViewPresenterModule {

    @Provides
    @Singleton
    WebViewPresenter webViewPresenter(WebViewContractModel webViewContractModel) {
        return new WebViewPresenter(webViewContractModel);
    }

    @Singleton
    @Provides
    WebViewContractModel webViewContractModel(@GitHubOAuthService GitHubApi gitHubApi) {
        return new WebViewModel(gitHubApi);
    }
}
