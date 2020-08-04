package com.example.pagingtestdemo.paging_pagekeyed.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc :
 */
public class User {

    @SerializedName("account_id")
    public int id;

    @SerializedName("display_name")
    public String name;

    @SerializedName("profile_image")
    public String avatar;

    public User(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
