package com.example.pagingtestdemo.paging_itemkeyed.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc :
 */
public class User {

    @SerializedName("id")
    public int id;

    @SerializedName("login")
    public String name;

    @SerializedName("avatar_url")
    public String avatar;

    public User(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
