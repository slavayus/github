package com.job.github.component;

import com.job.github.HomeFragment;
import com.job.github.module.HomePresenterModule;

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
