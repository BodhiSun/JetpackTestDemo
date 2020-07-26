package com.bodhi.roomtestdemo.upgrade;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/26
 * desc :Android提供Migration类对Room进行升级,编写完升级方案后通过addMigrations添加到Room
 */
public class MigrationManager {

    //数据库版本从1升到2的实现逻辑
    public static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //执行与升级相关的操作
            System.out.println("数据库版本从1升级到2了");
        }
    };

    //数据库版本从2升到3的实现逻辑
    public static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            System.out.println("数据库版本从2升级到3了");
        }
    };

    //数据库版本从1升到3的实现逻辑,如果此逻辑没有则会先从1升到2，在从2升到3
    public static final Migration MIGRATION_1_3 = new Migration(1,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            System.out.println("数据库版本从1升级到3了");
        }
    };

    //数据库版本从1升到4的实现逻辑(需求修改表结构 将age从integer变为text)
    public static final Migration MIGRATION_1_4 = new Migration(1,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
             //修改表结构一般采用 销毁重建策略
            //创建临时表
            database.execSQL("CREATE TABLE temp_stu ("+
                    " id INTEGER PRIMARY KEY NOT NULL,"+
                    " name TEXT,"+
                    " age TEXT)");
            //将数据从旧表复制到临时表
            database.execSQL("INSERT INTO temp_stu (id,name,age) "+
                    " SELECT id,name,age FROM Student");
            //删除旧表
            database.execSQL("DROP TABLE Student");
            //将临时表重命名为旧表名
            database.execSQL("ALTER TABLE temp_stu RENAME TO Student");


            System.out.println("数据库版本从1升级到4了");
        }
    };





}
