package com.bodhi.mvvmgithub.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.bodhi.mvvmgithub.model.User;

/**
 * 创建访问User表的Dao文件
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user WHERE name = :name")
    LiveData<User> getUserByName(String name);

}
