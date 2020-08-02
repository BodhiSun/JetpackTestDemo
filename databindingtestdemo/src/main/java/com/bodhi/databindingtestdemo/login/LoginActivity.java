package com.bodhi.databindingtestdemo.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.bodhi.databindingtestdemo.R;
import com.bodhi.databindingtestdemo.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //双向绑定
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        activityLoginBinding.setViewModel(new TwoWayBindingViewModel());

        activityLoginBinding.setViewMode2(new TwoWayBindingViewModel2());


        //recycler + databinding
        activityLoginBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        activityLoginBinding.recyclerView.setHasFixedSize(true);
        MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(new RecyclerViewModel().getBooks());
        activityLoginBinding.recyclerView.setAdapter(myRecyclerAdapter);

    }
}