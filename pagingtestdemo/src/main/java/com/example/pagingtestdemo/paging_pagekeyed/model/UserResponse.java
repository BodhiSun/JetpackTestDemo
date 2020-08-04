package com.example.pagingtestdemo.paging_pagekeyed.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc :
 */
public class UserResponse {

    @SerializedName("items")
    public List<User> users;

    @SerializedName("hase_more")
    public boolean hasMore;


}
