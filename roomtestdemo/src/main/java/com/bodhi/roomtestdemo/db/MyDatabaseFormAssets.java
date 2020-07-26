package com.bodhi.roomtestdemo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bodhi.roomtestdemo.dao.StudentDao;
import com.bodhi.roomtestdemo.dao.StudentDaoLiveData;
import com.bodhi.roomtestdemo.table.Student;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/26
 * desc :用一个现有的数据库创建本地Room数据库，可以放assets目录，或者sd卡缓存目录下
 */
@Database(entities = {Student.class},exportSchema = false,version = 1)
public abstract class MyDatabaseFormAssets extends RoomDatabase {

    private static final String DATABASE_NAME = "my_db_asset";

    private static MyDatabaseFormAssets databaseInstance;

    public static synchronized MyDatabaseFormAssets getInstance(Context context){
        if(databaseInstance == null){
            databaseInstance = Room.databaseBuilder(context,MyDatabaseFormAssets.class,DATABASE_NAME)
                    .createFromAsset("databases/students.db")
                    .build();
        }
        return databaseInstance;
    }

    public abstract StudentDao studentDao();

    public abstract StudentDaoLiveData liveDataStudentDao();

}
