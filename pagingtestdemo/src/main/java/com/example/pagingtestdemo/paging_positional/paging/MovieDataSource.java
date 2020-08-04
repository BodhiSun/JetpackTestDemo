package com.example.pagingtestdemo.paging_positional.paging;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PositionalDataSource;

import com.example.pagingtestdemo.paging_positional.api.RetrofitClient;
import com.example.pagingtestdemo.paging_positional.model.Movie;
import com.example.pagingtestdemo.paging_positional.model.Movies;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Paging支持的三种数据结构之一 PositionalDataSource 适用于从任意位置开始往后取固定条数 且 目标数据源数量固定的情况
 */
public class MovieDataSource extends PositionalDataSource<Movie> {

    public static final int PER_PAGE = 8;

    @Override
    public void loadInitial(@NonNull LoadInitialParams params, @NonNull final LoadInitialCallback<Movie> callback) {
        //首次加载从第一条数据开始加载
        int startPosition = 0;

        RetrofitClient.getInstance()
                .getApi()
                .getMovies(startPosition,PER_PAGE)
                .enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {
                        if(response.body()!=null){
                            Log.e("pagingData","paging loadInitial data:"+response.body().toString());
                            //加载数据成功后 通过callback.onResult方法将数据返回给PagedList
                            callback.onResult(response.body().movieList,
                                    response.body().start,
                                    response.body().total);//第三个参数为当前服务器数据的总数
                        }
                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {

                    }
                });


    }

    @Override
    public void loadRange(@NonNull LoadRangeParams params, @NonNull final LoadRangeCallback<Movie> callback) {

        //除首次加载外 加载下一页的工作会在loadRange此方法进行
        RetrofitClient.getInstance()
                .getApi()
                .getMovies(params.startPosition,PER_PAGE)//start参数paging内部会自己维护
                .enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(Call<Movies> call, Response<Movies> response) {
                        if(response.body()!=null){
                            Log.e("pagingData","paging loadRange data:"+response.body().toString());
                            callback.onResult(response.body().movieList);
                        }
                    }

                    @Override
                    public void onFailure(Call<Movies> call, Throwable t) {

                    }
                });
    }
}
