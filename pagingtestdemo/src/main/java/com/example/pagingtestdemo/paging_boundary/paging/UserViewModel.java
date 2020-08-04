package com.example.pagingtestdemo.paging_boundary.paging;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.pagingtestdemo.paging_boundary.db.UserDatabase;
import com.example.pagingtestdemo.paging_boundary.model.User;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc : 页面通过LiveData订阅数据库中的数据 数据库变化直接在页面上展现
 */
public class UserViewModel extends AndroidViewModel {
    public static final int PER_PAGE = 8;
    private Application mApplication;

    public LiveData<PagedList<User>> userPagedList;

    public UserViewModel(Application application){
        super(application);
        UserDatabase database = UserDatabase.getInstance(application);
        userPagedList = new LivePagedListBuilder<>(
                database.userDao().getUserList(),
                UserViewModel.PER_PAGE)
                .setBoundaryCallback(new UserBoundaryCallback(application))
                .build();
    }

    /**
     * 刷新数据 清空数据库，请求新的网络数据，在存入数据库
     */
    public void refresh(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                UserDatabase.getInstance(mApplication)
                        .userDao()
                        .clear();
            }
        });
    }



}
