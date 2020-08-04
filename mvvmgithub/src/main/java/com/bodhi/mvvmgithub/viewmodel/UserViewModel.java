package com.bodhi.mvvmgithub.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bodhi.mvvmgithub.MyApplication;
import com.bodhi.mvvmgithub.db.UserDao;
import com.bodhi.mvvmgithub.db.UserDatabase;
import com.bodhi.mvvmgithub.model.User;
import com.bodhi.mvvmgithub.repository.UserRepository;

/**
 * ViewModel层 负责获取Repository层的数据 并包装返回给View层
 */
public class UserViewModel extends AndroidViewModel {

    private LiveData<User> user;

    private UserRepository userRepository;

    private final String userName = "BodhiSun";

    public UserViewModel(Application application) {
        super(application);

        UserDatabase database = MyApplication.getUserDatabase();
        UserDao userDao = database.userDao();
        userRepository = new UserRepository(userDao,MyApplication.getApi());

        user = userRepository.getUser(userName);
    }

    public LiveData<User> getUser(){
        return user;
    }

    public void refresh(){
        userRepository.refresh(userName);
    }

}
