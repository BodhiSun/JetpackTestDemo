package com.bodhi.viewmodeltestdemo;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/23
 * desc :
 */
public class TimerWithLiveDataViewModel extends ViewModel {
    private MutableLiveData<Integer> currentSecond;

    public MutableLiveData<Integer> getCurrentSecond() {
        if(currentSecond == null){
            currentSecond = new MutableLiveData<>();
        }
        return currentSecond;
    }


    private Timer timer;

    public void startTiming(){
        if(timer==null){
            timer = new Timer();
            //setValue用在UI线程更新数据
            currentSecond.setValue(0);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    //postValue用在非UI线程
                    currentSecond.postValue(currentSecond.getValue()+1);
                }
            };
            timer.schedule(timerTask,1000,1000);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        timer.cancel();
    }
}
