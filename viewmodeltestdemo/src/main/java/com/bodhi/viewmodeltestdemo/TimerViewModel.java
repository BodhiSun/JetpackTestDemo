package com.bodhi.viewmodeltestdemo;

import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/23
 * desc :
 */
public class TimerViewModel extends ViewModel {
    private Timer timer;
    private int currentSecond;

    public void startTiming(){
        if(timer==null){
            currentSecond = 0;
            timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    currentSecond++;
                    if(onTimeChangeListener!=null){
                        onTimeChangeListener.onTimeChange(currentSecond);
                    }

                }
            };
            timer.schedule(timerTask,1000,1000);
        }
    }

    private OnTimeChangeListener onTimeChangeListener;

    public void setOnTimeChangeListener(OnTimeChangeListener onTimeChangeListener) {
        this.onTimeChangeListener = onTimeChangeListener;
    }

    public interface OnTimeChangeListener{
        void onTimeChange(int second);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
        timer.cancel();
    }
}
