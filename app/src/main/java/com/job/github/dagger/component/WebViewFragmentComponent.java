package com.job.github.dagger.component;

import com.job.github.dagger.module.WebViewPresenterModule;
import com.job.github.mvp.view.WebViewFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by slavik on 6/2/18.
 */

@Singleton
@Component(modules = {WebViewPresenterModule.class})
public interface WebViewFragmentComponent {
    void injectWebViewFragment(WebViewFragment webViewFragment);
}
