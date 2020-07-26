package com.bodhi.roomtestdemo.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bodhi.roomtestdemo.dao.StudentDao;
import com.bodhi.roomtestdemo.dao.StudentDaoLiveData;
import com.bodhi.roomtestdemo.table.Student;
import com.bodhi.roomtestdemo.upgrade.MigrationManager;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/24
 * desc :Room数据库对象，entities指定该数据库有哪些表，exportSchema表示是否导出数据库信息，操作表的Dao对象以抽象方法形式返回
 */
@Database(entities = {Student.class}, version = 4,exportSchema = true)
public abstract class MyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "my_db";

    private static MyDatabase databaseInstance;

    public static synchronized MyDatabase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MyDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()//升级异常处理方案，当没有对应的版本升级策略时防止崩溃，但数据仍会丢失
                    .addMigrations(MigrationManager.MIGRATION_1_2, MigrationManager.MIGRATION_2_3, MigrationManager.MIGRATION_1_3,MigrationManager.MIGRATION_1_4).build();
        }
        return databaseInstance;
    }

    public abstract StudentDao studentDao();

    public abstract StudentDaoLiveData liveDataStudentDao();

}
