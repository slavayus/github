package com.job.github.dagger.component;

import com.job.github.api.GitHubApi;
import com.job.github.dagger.annotation.GitHubOAuthService;
import com.job.github.dagger.annotation.GitHubService;
import com.job.github.dagger.module.GitHubModule;
import com.job.github.dagger.module.GitHubOAuthModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {GitHubModule.class, GitHubOAuthModule.class})
public interface GitHubApiComponent {
    @GitHubService
    GitHubApi getGitHubService();

    @GitHubOAuthService
    GitHubApi getGitHubRegisterService();
}
