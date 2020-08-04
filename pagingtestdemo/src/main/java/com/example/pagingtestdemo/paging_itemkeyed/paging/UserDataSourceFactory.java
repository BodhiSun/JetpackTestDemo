package com.example.pagingtestdemo.paging_itemkeyed.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.pagingtestdemo.paging_itemkeyed.model.User;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/8/3
 * desc :
 */
public class UserDataSourceFactory extends DataSource.Factory<Integer, User> {

    private MutableLiveData<UserDataSource> liveDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, User> create() {
        UserDataSource dataSource = new UserDataSource();
        liveDataSource.postValue(dataSource);
        return dataSource;
    }
}
