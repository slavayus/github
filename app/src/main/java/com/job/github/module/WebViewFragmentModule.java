package com.job.github.module;

import com.job.github.model.WebViewContractModel;
import com.job.github.model.WebViewModel;
import com.job.github.presenter.WebViewPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/2/18.
 */

@Module
public class WebViewFragmentModule {

    @Provides
    @Singleton
    WebViewPresenter webViewPresenter(WebViewContractModel webViewContractModel) {
        return new WebViewPresenter(webViewContractModel);
    }

    @Singleton
    @Provides
    WebViewContractModel webViewContractModel() {
        return new WebViewModel();
    }
}
