package com.bodhi.roomtestdemo.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.bodhi.roomtestdemo.table.Student;

import java.util.List;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/25
 * desc :定义操作学生表的Dao接口文件
 */
@Dao
public interface StudentDaoLiveData {
    @Insert
    void insertStudent(Student student);

    @Delete
    void deleteStudent(Student student);

    @Delete
    void deleteAllStudent(Student student);

    @Update
    void updateStudent(Student student);

    //使用liveData将数据包装起来
    @Query("SELECT * FROM Student")
    LiveData<List<Student>> getStudentList();

    @Query("SELECT * FROM Student WHERE id = :id")
    Student getStudentById(int id);

}
