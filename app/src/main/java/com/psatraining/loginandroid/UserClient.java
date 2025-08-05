package com.psatraining.loginandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserClient {
    @GET("/users")
    Call<List<UserModel>> getAllUsers();

    @POST("/users")
    Call<UserModel> addUser(@Body UserModel user);

    @GET("/users")
    Call<List<UserModel>> findUserByNameAndPassword(
            @Query("userName") String userName,
            @Query("userPassword") String userPassword
    );

    @GET("/users")
    Call<List<UserModel>> findUserByEmail(
            @Query("userEmail") String userEmail
    );
}