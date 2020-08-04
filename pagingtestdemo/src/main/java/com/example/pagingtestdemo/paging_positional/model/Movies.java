package com.example.pagingtestdemo.paging_positional.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 每次请求 服务端返回的数据
 */
public class Movies {

    public int count;//当前返回的数量

    public int start;//起始位置

    public int total;//一共多少数据

    @SerializedName("subjects")
    public List<Movie> movieList;//返回的电影列表

    @Override
    public String toString() {
        return "Movies{" +
                "count=" + count +
                ", start=" + start +
                ", total=" + total +
                ", movieList=" + movieList +
                '}';
    }
}
