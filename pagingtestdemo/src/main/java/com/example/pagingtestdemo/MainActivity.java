package com.example.pagingtestdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.pagingtestdemo.R;
import com.example.pagingtestdemo.paging_positional.model.Movie;
import com.example.pagingtestdemo.paging_positional.paging.MoviePagedListAdapter;
import com.example.pagingtestdemo.paging_positional.paging.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);

        final MoviePagedListAdapter moviePagedListAdapter = new MoviePagedListAdapter(this);

        //数据发生变化时 通过LiveData传递过来 然后通过PagedListAdapter 的submitList方法刷新数据
        MovieViewModel movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.moviePagedList.observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(PagedList<Movie> movies) {
                moviePagedListAdapter.submitList(movies);
            }
        });

        recyclerview.setAdapter(moviePagedListAdapter);
    }

    public void clickToUser(View view) {
        startActivity(new Intent(this, UserActivity.class));
    }

    public void clickToUser2(View view) {
        startActivity(new Intent(this, UserActivity2.class));
    }

    public void clickToUser3(View view) {
        startActivity(new Intent(this, UserActivity3.class));
    }
}
