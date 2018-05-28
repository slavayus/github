package com.job.github.api;

import com.job.github.UserModel;
import com.job.github.models.TokenModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by slavik on 5/26/18.
 */

public interface GitHubApi {
    @Headers({"Accept: application/json"})
    @GET("/login/oauth/access_token")
    Call<TokenModel> getToken(@Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("code") String code);

    @GET("/user")
    Call<UserModel> getUser(@Query("access_token") String token);
}
