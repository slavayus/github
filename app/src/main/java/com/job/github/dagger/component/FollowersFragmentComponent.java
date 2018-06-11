package com.job.github.dagger.component;

import com.job.github.dagger.module.FollowersFragmentModule;
import com.job.github.mvp.view.FollowersFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by slavik on 6/11/18.
 */

@Singleton
@Component(modules = {FollowersFragmentModule.class})
public interface FollowersFragmentComponent {
    void injectFollowersFragment(FollowersFragment fragment);
}
