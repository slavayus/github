package com.job.github.dagger.component;

import com.job.github.dagger.annotation.ReposFragmentScope;
import com.job.github.dagger.module.ReposFragmentModule;
import com.job.github.mvp.view.ReposFragment;

import dagger.Component;

/**
 * Created by slavik on 6/2/18.
 */

@ReposFragmentScope
@Component(modules = {ReposFragmentModule.class}, dependencies = {ReposPresenterComponent.class})
public interface ReposFragmentComponent {
    void injectReposFragment(ReposFragment reposFragment);
}
