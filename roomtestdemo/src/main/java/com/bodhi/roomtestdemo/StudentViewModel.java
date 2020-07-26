package com.bodhi.roomtestdemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bodhi.roomtestdemo.db.MyDatabase;
import com.bodhi.roomtestdemo.table.Student;

import java.util.List;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/25
 * desc :实例化数据库需要context 所以继承AndroidViewModel
 */
public class StudentViewModel extends ViewModel {

    private MyDatabase myDatabase;
    private LiveData<List<Student>> liveDataStudent;
    private Application mApplication;


//    public StudentViewModel(@NonNull Application application) {
//        super(application);
//        myDatabase = MyDatabase.getInstance(application);
//        liveDataStudent = myDatabase.liveDataStudentDao().getStudentList();
//    }

    public void setApplication(Application application){
        mApplication = application;
        myDatabase = MyDatabase.getInstance(mApplication);
        liveDataStudent = myDatabase.liveDataStudentDao().getStudentList();

    }

    public LiveData<List<Student>> getLiveDataStudent() {
        return liveDataStudent;
    }
}
