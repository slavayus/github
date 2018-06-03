package com.job.github.dagger.module;

import com.job.github.api.GitHubApi;
import com.job.github.dagger.annotation.GitHubOAuthService;
import com.job.github.mvp.model.WebViewContractModel;
import com.job.github.mvp.model.WebViewModel;
import com.job.github.mvp.presenter.WebViewPresenter;

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
