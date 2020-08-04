package com.example.pagingtestdemo.paging_itemkeyed.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.ItemKeyedDataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.pagingtestdemo.paging_itemkeyed.api.RetrofitClient;
import com.example.pagingtestdemo.paging_itemkeyed.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc :
 */
public class UserDataSource extends ItemKeyedDataSource<Integer, User> {

    public static final int PER_PAGE = 12;

    //首次加载调用该方法
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<User> callback) {

        //请求key为0开始的第一页数据
        int since = 0;

        RetrofitClient.getInstance()
                .getApi()
                .getUsers(since,PER_PAGE)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.body()!=null){
                            callback.onResult(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });


    }

    //加载下一页自动调用该方法
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull final LoadCallback<User> callback) {
        RetrofitClient.getInstance()
                .getApi()
                .getUsers(params.key,PER_PAGE)//下一页的key 通过params获得
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.body()!=null){
                            callback.onResult(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<User> callback) {

    }

    @NonNull
    @Override
    public Integer getKey(@NonNull User item) {
        //此处item的key是User对象的id字段，即为下一页请求的key 所以将item.id返回
        return item.id;
    }
}
