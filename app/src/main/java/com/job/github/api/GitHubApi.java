package com.job.github.api;

import com.job.github.api.pojo.Repos;
import com.job.github.api.pojo.Token;
import com.job.github.api.pojo.User;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GitHubApi {
    @Headers({"Accept: application/json"})
    @GET("/login/oauth/access_token")
    Call<Token> getToken(@Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("code") String code);

    @GET("/user")
    Call<User> getUser(@Query("access_token") String token);

    @GET("users/{user_name}/repos?sort=pushed")
    Call<List<Repos>> getRepos(@Path(value = "user_name") String userName, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);

    @GET
    Call<ResponseBody> loadAvatar(@Url String url);

    @PATCH("/user")
    Call<User> updateBio(@Query("access_token") String token, @Body User user);

    @PATCH("/user")
    Single<User> updateUserInfo(@Query("access_token") String token, @Body User user);

    @GET("/users/{user_name}/followers")
    Call<List<User>> getFollowers(@Path("user_name") String userName, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);

    @GET("/users/{user_name}")
    Call<User> getUserByLogin(@Path("user_name") String userName, @Query("client_id") String clientId, @Query("client_secret") String clientSecret);
}
