package com.job.github.component;

import com.job.github.WebViewFragment;
import com.job.github.module.WebViewPresenterModule;

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
