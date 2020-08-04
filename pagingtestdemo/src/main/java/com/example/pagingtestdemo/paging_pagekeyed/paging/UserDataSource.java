package com.example.pagingtestdemo.paging_pagekeyed.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.pagingtestdemo.paging_pagekeyed.api.RetrofitClient;
import com.example.pagingtestdemo.paging_pagekeyed.model.User;
import com.example.pagingtestdemo.paging_pagekeyed.model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc :
 */
public class UserDataSource extends PageKeyedDataSource<Integer, User> {

    public static final int FIRST_PAGE = 1;
    public static final int PER_PAGE = 8;
    public static final String SITE = "stackoverflow";//此参数无意义

    //首次加载第一页调用该方法
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, User> callback) {
        RetrofitClient.getInstance()
                .getApi()
                .getUsers(FIRST_PAGE,PER_PAGE,SITE)
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if(response.body()!=null){
                            Log.e("pagingData","paging loadInitial hasMore:"+response.body().hasMore);

                            //第二个参数为上一页的页码，当前是第一页所以不存在传空，第三个参数为下一页页码
                            callback.onResult(response.body().users,null,FIRST_PAGE+1);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, User> callback) {

    }

    //加载下一页调用该方法
    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, User> callback) {
        RetrofitClient.getInstance()
                .getApi()
                .getUsers(params.key,PER_PAGE,SITE)//page参数每次都是通过callback.onResult传递过来的
                .enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        if(response.body()!=null){
                            Integer nextKey = response.body().hasMore?params.key+1:null;
                            Log.e("pagingData","paging loadAfter hasMore:"+response.body().hasMore+"    nextKey:"+nextKey);

                            callback.onResult(response.body().users,nextKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable t) {

                    }
                });
    }
}
