package com.example.pagingtestdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.pagingtestdemo.paging_boundary.model.User;
import com.example.pagingtestdemo.paging_boundary.paging.UserPagedListAdapter;
import com.example.pagingtestdemo.paging_boundary.paging.UserViewModel;

public class UserActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);

        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);

        final UserPagedListAdapter userPagedListAdapter = new UserPagedListAdapter(this);

        final UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.userPagedList.observe(this,
                new Observer<PagedList<User>>() {
                    @Override
                    public void onChanged(PagedList<User> users) {
                        userPagedListAdapter.submitList(users);
                    }
                });

        recyclerview.setAdapter(userPagedListAdapter);

        final SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setProgressBackgroundColor(R.color.colorAccent);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userViewModel.refresh();
                swipeRefresh.setRefreshing(false);
            }
        });
    }
}