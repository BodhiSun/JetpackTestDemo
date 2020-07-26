package com.bodhi.roomtestdemo.upgrade;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bodhi.roomtestdemo.db.MyDatabase;
import com.bodhi.roomtestdemo.db.MyDatabaseFormAssets;
import com.bodhi.roomtestdemo.table.Student;

import java.util.List;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/25
 * desc :实例化数据库需要context 所以继承AndroidViewModel
 */
public class StudentViewModelAsset extends ViewModel {

    private MyDatabaseFormAssets myDatabase;
    private LiveData<List<Student>> liveDataStudent;
    private Application mApplication;


//    public StudentViewModel(@NonNull Application application) {
//        super(application);
//        myDatabase = MyDatabase.getInstance(application);
//        liveDataStudent = myDatabase.liveDataStudentDao().getStudentList();
//    }

    public void setApplication(Application application){
        mApplication = application;
        myDatabase = MyDatabaseFormAssets.getInstance(mApplication);
        liveDataStudent = myDatabase.liveDataStudentDao().getStudentList();

    }

    public LiveData<List<Student>> getLiveDataStudent() {
        return liveDataStudent;
    }
}
