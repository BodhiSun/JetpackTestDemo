package com.example.pagingtestdemo.paging_pagekeyed.api;

import com.example.pagingtestdemo.paging_pagekeyed.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc :
 */
public interface Api {
    @GET("users")
    Call<UserResponse> getUsers(
            @Query("page")int page,
            @Query("pagesize")int pageSize,
            @Query("site")String site
    );

}
