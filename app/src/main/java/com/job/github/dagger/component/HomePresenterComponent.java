package com.job.github.dagger.component;

import com.job.github.dagger.module.HomePresenterModule;
import com.job.github.mvp.view.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by slavik on 6/2/18.
 */

@Singleton
@Component(modules = {HomePresenterModule.class})
public interface HomePresenterComponent {
    void injectHomeFragment(HomeFragment homeFragment);
}
