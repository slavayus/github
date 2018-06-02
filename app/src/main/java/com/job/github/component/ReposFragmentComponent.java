package com.job.github.component;

import com.job.github.ReposFragment;
import com.job.github.annotation.ReposFragmentScope;
import com.job.github.module.ReposFragmentModule;

import dagger.Component;

/**
 * Created by slavik on 6/2/18.
 */

@ReposFragmentScope
@Component(modules = {ReposFragmentModule.class}, dependencies = {ReposPresenterComponent.class})
public interface ReposFragmentComponent {
    void injectReposFragment(ReposFragment reposFragment);
}
