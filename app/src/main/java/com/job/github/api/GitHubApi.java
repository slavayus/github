package com.job.github.api;

import com.job.github.pojo.Repos;
import com.job.github.pojo.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GitHubApi {
    @GET("/user")
    Call<User> getUser(@Query("access_token") String token);

    @GET("users/{user_name}/repos?sort=pushed")
    Call<List<Repos>> getRepos(@Path(value = "user_name") String userName, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);

    @GET
    Call<ResponseBody> loadAvatar(@Url String url);

    @PATCH("/user")
    Call<User> updateBio(@Query("access_token") String token, @Body User user);
}
