package com.example.pagingtestdemo.paging_positional.api;

import com.example.pagingtestdemo.paging_positional.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    /**
     * 获取影院当前上映的电影
     */
    @GET("movie/in_theaters")
    Call<Movies> getMovies(@Query("start") int since,@Query("count") int perPage);


}
