package com.example.pagingtestdemo.paging_itemkeyed.api;

import com.example.pagingtestdemo.paging_itemkeyed.model.User;

import java.util.List;

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
    Call<List<User>> getUsers(
            @Query("since") int itemkeyed,
            @Query("per_page") int pageSize
    );

}
