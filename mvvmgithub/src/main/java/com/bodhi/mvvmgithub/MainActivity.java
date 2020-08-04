package com.bodhi.mvvmgithub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.bodhi.mvvmgithub.databinding.ActivityMainBinding;
import com.bodhi.mvvmgithub.model.User;
import com.bodhi.mvvmgithub.viewmodel.UserViewModel;

/**
 * View层 使用了DataBinding组件 将数据直接交给布局文件进行处理展示
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        final UserViewModel userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user!=null){
                    activityMainBinding.setUser(user);
                }
            }
        });

        final SwipeRefreshLayout swipeRefresh = activityMainBinding.swipeRefresh;
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userViewModel.refresh();
                swipeRefresh.setRefreshing(false);
            }
        });


    }
}
