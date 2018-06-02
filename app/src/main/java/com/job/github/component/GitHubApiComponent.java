package com.job.github.component;

import com.job.github.annotation.GitHubOAuthService;
import com.job.github.annotation.GitHubService;
import com.job.github.api.GitHubApi;
import com.job.github.module.GitHubModule;
import com.job.github.module.GitHubOAuthModule;

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
