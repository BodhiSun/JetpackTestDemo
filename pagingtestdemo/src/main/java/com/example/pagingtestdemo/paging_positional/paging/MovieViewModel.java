package com.example.pagingtestdemo.paging_positional.paging;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.pagingtestdemo.paging_positional.model.Movie;

/**
 * 使用LivePagedListBuilder配置和创建PagedList，并用LiveData包装PagedList 以便暴露给页面Activity
 */
public class MovieViewModel extends ViewModel {

    public LiveData<PagedList<Movie>> moviePagedList;

    public MovieViewModel(){
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)//是否开启item占位 配合MovieDataSource中首次加载时callback.onResult第三个参数影响
                .setPageSize(MovieDataSource.PER_PAGE)//每页大小，通常与MovieDataSource中请求数据的参数保持一致
                .setPrefetchDistance(3)//距离底部多少条数据时开始加载下一页
                .setInitialLoadSizeHint(MovieDataSource.PER_PAGE * 1)//首次加载数据的数据量，为PageSize的整倍数，默认3倍
                .setMaxSize(65535 * MovieDataSource.PER_PAGE)//PageList所能承受的最大数量
                .build();

        moviePagedList = new LivePagedListBuilder<>(new MovieDataSourceFactory(),config).build();
    }
}
