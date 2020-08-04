package com.example.pagingtestdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagingtestdemo.paging_itemkeyed.model.User;
import com.example.pagingtestdemo.paging_itemkeyed.paging.UserPagedListAdapter;
import com.example.pagingtestdemo.paging_itemkeyed.paging.UserViewModel;

public class UserActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);

        final UserPagedListAdapter userPagedListAdapter = new UserPagedListAdapter(this);

        UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.userPagedList.observe(this,
                new Observer<PagedList<User>>() {
                    @Override
                    public void onChanged(PagedList<User> users) {
                        userPagedListAdapter.submitList(users);
                    }
                });

        recyclerview.setAdapter(userPagedListAdapter);
    }
}