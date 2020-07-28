package com.bodhi.workmanagertestdemo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/**
 * @author : Sun
 * @version : 1.0
 * @time : 2020/7/29
 * desc :
 */
public class WorkerE extends Worker {
    public WorkerE(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        System.out.println("WorkerE 执行");
        return Result.success();
    }
}
