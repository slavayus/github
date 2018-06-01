package com.job.github.component;

import com.job.github.api.GitHubApi;
import com.job.github.api.GitHubOAuthApi;
import com.job.github.module.GitHubModule;
import com.job.github.module.GitHubOAuthModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GitHubModule.class, GitHubOAuthModule.class})
public interface GitHubApiComponent {
    GitHubApi getGitHubService();

    GitHubOAuthApi getGitHubRegisterService();
}
