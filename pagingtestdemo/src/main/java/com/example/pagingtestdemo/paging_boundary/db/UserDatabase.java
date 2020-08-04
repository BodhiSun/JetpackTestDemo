package com.example.pagingtestdemo.paging_boundary.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pagingtestdemo.paging_boundary.model.User;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/4
 * desc :创建Room数据库
 */
@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "user_db";

    private static UserDatabase databaseInstance;

    public static synchronized UserDatabase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    UserDatabase.class,
                    DATABASE_NAME
            ).build();
        }
        return databaseInstance;
    }

    public abstract UserDao userDao();


}
