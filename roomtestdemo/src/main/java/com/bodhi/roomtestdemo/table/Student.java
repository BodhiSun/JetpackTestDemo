package com.bodhi.roomtestdemo.table;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/24
 * desc :定义学生表
 */
@Entity(tableName = "Student")//不设置tableName 默认表名与类名相同
public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id",typeAffinity = ColumnInfo.INTEGER)
    @NonNull
    public int id;

    @ColumnInfo(name = "name",typeAffinity = ColumnInfo.TEXT)
    @NonNull
    public String name;

    @ColumnInfo(name = "age",typeAffinity = ColumnInfo.TEXT)
    @NonNull
    public String age;


    /**
     * Room默认会使用这个构造器操作数据
     * @param id
     * @param name
     * @param age
     */
    public Student(int id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    //告诉Room忽略该字段或方法
    @Ignore
    public Student(String name, String age) {
        this.name = name;
        this.age = age;
    }
}
