package com.job.github.dagger.component;

import com.job.github.dagger.module.ReposPresenterModule;
import com.job.github.mvp.presenter.ReposPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by slavik on 6/2/18.
 */

@Singleton
@Component(modules = {ReposPresenterModule.class})
public interface ReposPresenterComponent {
    ReposPresenter reposPresenter();
}
