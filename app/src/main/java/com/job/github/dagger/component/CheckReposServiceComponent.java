package com.job.github.dagger.component;

import com.job.github.dagger.module.CheckReposServiceModule;
import com.job.github.dagger.module.GitHubModule;
import com.job.github.utils.CheckReposJobService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by slavik on 6/11/18.
 */

@Singleton
@Component(modules = {CheckReposServiceModule.class, GitHubModule.class})
public interface CheckReposServiceComponent {
    void injectReposService(CheckReposJobService service);
}
