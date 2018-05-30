package com.job.github.api;

import com.job.github.pojo.Repos;
import com.job.github.pojo.Token;
import com.job.github.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by slavik on 5/26/18.
 */

public interface GitHubApi {
    @Headers({"Accept: application/json"})
    @GET("/login/oauth/access_token")
    Call<Token> getToken(@Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("code") String code);

    @GET("/user")
    Call<User> getUser(@Query("access_token") String token);

    @GET("users/{user_name}/repos?sort=pushed")
    Call<List<Repos>> getRepos(@Path(value = "user_name") String userName, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);
}
