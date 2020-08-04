package com.example.pagingtestdemo.paging_boundary.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc : 数据库Model类
 */
@Entity(tableName = "user")
public class User {

    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("login")
    public String name;

    @ColumnInfo(name = "avatar", typeAffinity = ColumnInfo.TEXT)
    @SerializedName("avatar_url")
    public String avatar;

    public User(int id, String name, String avatar) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }
}
