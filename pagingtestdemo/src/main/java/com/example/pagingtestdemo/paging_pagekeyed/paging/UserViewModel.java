package com.example.pagingtestdemo.paging_pagekeyed.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.pagingtestdemo.paging_pagekeyed.model.User;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc :
 */
public class UserViewModel extends ViewModel {

    public LiveData<PagedList<User>> userPagedList;

    public UserViewModel(){
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(UserDataSource.PER_PAGE)
                .setPrefetchDistance(3)
                .setInitialLoadSizeHint(UserDataSource.PER_PAGE * 4)
                .setMaxSize(65535 * UserDataSource.PER_PAGE)
                .build();

        userPagedList = new LivePagedListBuilder<>(new UserDataSourceFactory(),config).build();
    }



}
