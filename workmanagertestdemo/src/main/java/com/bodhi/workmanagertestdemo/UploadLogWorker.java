package com.bodhi.workmanagertestdemo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/27
 * desc :实现一个日志上传的任务
 */
public class UploadLogWorker extends Worker {
    private static int nums = 0;

    public UploadLogWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * 耗时的任务在doWork方法中执行
     * @return
     */
    @NonNull
    @Override
    public Result doWork() {
        System.out.println("doWork 接收传递进来的数据："+getInputData().getString("input_data"));
        try {
            System.out.println("Thread name:"+Thread.currentThread().getName()+"        "+nums++);
            Thread.sleep(1000);
            if(nums<5){
                doWork();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }

        //任务完成后也可以给WorkManager返回数据
        Data data = new Data.Builder()
                .putString("output_data","Task Success!")
                .build();
        return Result.success(data);
    }
}
