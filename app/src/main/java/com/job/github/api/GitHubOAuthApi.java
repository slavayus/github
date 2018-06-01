package com.job.github.api;

import com.job.github.pojo.Token;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by slavik on 6/2/18.
 */

public interface GitHubOAuthApi {
    @Headers({"Accept: application/json"})
    @GET("/login/oauth/access_token")
    Call<Token> getToken(@Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("code") String code);
}
