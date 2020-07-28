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
public class WorkerC extends Worker {
    public WorkerC(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        System.out.println("WorkerC 执行");
        return Result.success();
    }
}
