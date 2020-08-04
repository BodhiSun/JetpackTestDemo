package com.example.pagingtestdemo.paging_boundary.db;

import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pagingtestdemo.paging_boundary.model.User;

import java.util.List;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/4
 * desc : 数据库操作类 方便对数据库model表进行增改
 */
@Dao
public interface UserDao {

    @Insert
    void insertUsers(List<User> users);

    @Query("DELETE FROM user")
    void clear();

    @Query("SELECT * FROM user")
    DataSource.Factory<Integer,User> getUserList();




}
