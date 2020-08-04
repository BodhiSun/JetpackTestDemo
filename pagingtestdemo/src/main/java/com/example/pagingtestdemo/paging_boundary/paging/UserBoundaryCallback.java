package com.example.pagingtestdemo.paging_boundary.paging;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PagedList;

import com.example.pagingtestdemo.paging_boundary.api.RetrofitClient;
import com.example.pagingtestdemo.paging_boundary.db.UserDatabase;
import com.example.pagingtestdemo.paging_boundary.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/4
 * desc :BoundaryCallback 单一数据源
 */
public class UserBoundaryCallback extends PagedList.BoundaryCallback<User> {

    private String TAG = this.getClass().getName();
    private Application application;

    public UserBoundaryCallback(Application application){
        this.application = application;
    }

    //当数据库无数据时 首次加载数据会调用此方法
    @Override
    public void onZeroItemsLoaded() {
        super.onZeroItemsLoaded();
        getTopData();
    }

    //数据库已经展示全部数据 需要加载新数据时调用此方法,itemAtEnd为数据库的最后一条数据
    @Override
    public void onItemAtEndLoaded(@NonNull User itemAtEnd) {
        super.onItemAtEndLoaded(itemAtEnd);
        getTopAfterData(itemAtEnd);
    }

    /**
     * 加载第一页数据
     */
    private void getTopData() {

        int since = 0;
        Log.e("pagingData","paging getTopData    since:"+since);
        RetrofitClient.getInstance()
                .getApi()
                .getUsers(since,UserViewModel.PER_PAGE)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.body()!=null){
                            insertUsers(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
    }

    /**
     * 加载下一页数据，数据库中最后一条数据user的id作为请求下一页的key
     * @param user
     */
    private void getTopAfterData(User user) {
        Log.e("pagingData","paging getTopAfterData    key:"+user.id);

        RetrofitClient.getInstance()
                .getApi()
                .getUsers(user.id,UserViewModel.PER_PAGE)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.body()!=null){
                            insertUsers(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
    }


    /**
     * 将网络请求的数据插入到数据库，数据库作为对外提供数据的唯一数据源(单一数据源减少逻辑复杂度)
     * @param users
     */
    private void insertUsers(final List<User> users) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                UserDatabase.getInstance(application)
                        .userDao()
                        .insertUsers(users);
            }
        });
    }

    @Override
    public void onItemAtFrontLoaded(@NonNull User itemAtFront) {
        super.onItemAtFrontLoaded(itemAtFront);
    }
}
