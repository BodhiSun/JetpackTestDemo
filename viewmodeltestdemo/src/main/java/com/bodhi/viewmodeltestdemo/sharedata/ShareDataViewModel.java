package com.bodhi.viewmodeltestdemo.sharedata;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/23
 * desc :
 */
public class ShareDataViewModel extends ViewModel {

    private MutableLiveData<Integer> progress;

    public MutableLiveData<Integer> getProgress() {
        if(progress==null){
            progress = new MutableLiveData<>();
        }
        return progress;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        progress = null;
    }
}
