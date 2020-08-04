package com.example.pagingtestdemo.paging_positional.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.pagingtestdemo.paging_positional.model.Movie;

/**
 * 负责创建MovieDataSource，并用LiveData包装MovieDataSource 以便暴露给MovieViewModel
 */
public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    private MutableLiveData<MovieDataSource> liveDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        MovieDataSource dataSource = new MovieDataSource();
        liveDataSource.postValue(dataSource);
        return dataSource;
    }
}
