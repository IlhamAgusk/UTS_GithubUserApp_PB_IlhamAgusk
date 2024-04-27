package com.example.githubuserapp.data.retrofit;

import com.example.githubuserapp.data.response.GithubSearchResponse;
import com.example.githubuserapp.data.response.GithubUser;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface api_services {

    @Headers({"Authorization: token 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"})
    @GET("search/users")
    Call<GithubSearchResponse> searchUsers(@Query("q") String query);

    @Headers({"Authorization: token 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"})
    @GET("users/{username}")
    Call<GithubUser> getUser(@Path("username") String username);


}