package com.job.github.dagger.component;

import com.job.github.dagger.module.EditUserInfoModule;
import com.job.github.mvp.view.EditUserInfoFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by slavik on 6/7/18.
 */

@Singleton
@Component(modules = {EditUserInfoModule.class})
public interface EditUserInfoComponent {
    void injectEditUserInfo(EditUserInfoFragment fragment);
}
