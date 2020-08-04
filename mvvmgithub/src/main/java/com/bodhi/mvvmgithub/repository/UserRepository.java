package com.bodhi.mvvmgithub.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.bodhi.mvvmgithub.api.Api;
import com.bodhi.mvvmgithub.db.UserDao;
import com.bodhi.mvvmgithub.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 在Model和ViewModel之间加了一个Repository层 用来统一管理Room和Retrofit两个数据源 并对外提供统一方法获取数据
 */
public class UserRepository {

    private String TAG = this.getClass().getName();

    private UserDao userDao;

    private Api api;

    public UserRepository(UserDao userDao,Api api){

        this.api = api;
        this.userDao = userDao;
    }

    public LiveData<User> getUser(String userName){
        refresh(userName);
        return userDao.getUserByName(userName);
    }

    public void refresh(String userName) {
        api.getUser(userName).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.body()!= null){
                    insertUser(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(t!=null){
                    System.out.println(t.getMessage());
                }
            }
        });
    }

    private void insertUser(final User user) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insertUser(user);
            }
        });
    }


}
