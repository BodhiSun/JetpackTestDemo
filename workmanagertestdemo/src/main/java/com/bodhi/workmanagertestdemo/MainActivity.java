package com.bodhi.workmanagertestdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //使用WorkRequest配置WorkManager任务
        //设置任务触发条件 充电、网络已链接，且电池电量充足状态下才触发任务
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .build();

        //WorkManager也可以通过Data对象给Work传递数据 ，但仅限小的10KB以内的基本数据类型的数据
        Data data = new Data.Builder()
                .putString("input_data","Hello WorkManager")
                .build();

        //将任务触发条件设置到WorkRequest ,没有设置条件或设置的条件符合系统要求 系统可能会立刻执行任务
        final OneTimeWorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadLogWorker.class)
                .setConstraints(constraints)
                .setInitialDelay(5, TimeUnit.SECONDS)//设置符合触发条件后延迟10s执行
                .setBackoffCriteria(BackoffPolicy.LINEAR,OneTimeWorkRequest.MIN_BACKOFF_MILLIS,TimeUnit.MICROSECONDS)//自定义设置指数退避策略（重试策略）
                .addTag("UpLoadTag")//为任务设置标签
                .setInputData(data)//给Work传递数据
                .build();

        //将配置好的任务提交给系统
        WorkManager.getInstance(this).enqueue(uploadWorkRequest);

        //设置标签后 可以通过标签(或id)获取任务状态
        WorkManager.getInstance(this).getWorkInfosByTag("UpLoadTag");
        WorkManager.getInstance(this).getWorkInfoById(uploadWorkRequest.getId());
        //也有对应的liveData方法可以实时获取状态，即任务状态发生变化时收到通知
        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(uploadWorkRequest.getId())
                .observe(MainActivity.this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        System.out.println("Work state onChanged:"+workInfo);
                        if(workInfo!=null&&workInfo.getState()==WorkInfo.State.SUCCEEDED){
                            System.out.println("Work success return data:"+workInfo.getOutputData().getString("output_data"));
                        }
                    }
                });


        //也可以通过标签(或id)取消任务
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                WorkManager.getInstance(MainActivity.this).cancelAllWorkByTag("UpLoadTag");
//                WorkManager.getInstance(MainActivity.this).cancelWorkById(uploadWorkRequest.getId());
//                WorkManager.getInstance(MainActivity.this).cancelAllWork();
//
//            }
//        },10000);

        //周期性任务PeriodicWorkRequest与一次性任务OneTimeWorkRequest使用配置一样 ，注意周期间隔不能小于15分钟
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(UploadLogWorker.class, 15, TimeUnit.MINUTES).build();

        //多任务的任务链和以及多任务间的执行顺序
        OneTimeWorkRequest workerA = new OneTimeWorkRequest.Builder(WorkerA.class).build();
        OneTimeWorkRequest workerB = new OneTimeWorkRequest.Builder(WorkerB.class).build();
        OneTimeWorkRequest workerC = new OneTimeWorkRequest.Builder(WorkerC.class).build();
        OneTimeWorkRequest workerD = new OneTimeWorkRequest.Builder(WorkerD.class).build();
        OneTimeWorkRequest workerE = new OneTimeWorkRequest.Builder(WorkerE.class).build();
        //beginWith优先于then先执行 beginWith内按先后顺序 then之间也按先后顺序
        WorkManager.getInstance(this)
                .beginWith(workerA)
                .then(workerB)
                .then(workerC)
                .then(workerD)
                .then(workerE)
                .enqueue();

//        List<OneTimeWorkRequest> workRequests = new ArrayList<>();
//        workRequests.add(workerB);
//        workRequests.add(workerA);
//        workRequests.add(workerC);
//        WorkManager.getInstance(this)
//                .beginWith(workRequests)
//                .then(workerE)
//                .then(workerD)
//                .enqueue();

        //也可以使用WorkContinuation 将任务组合起来
//        WorkContinuation continuation1 = WorkManager.getInstance(this)
//                .beginWith(workerA)
//                .then(workerB);
//        WorkContinuation continuation2 = WorkManager.getInstance(this)
//                .beginWith(workerC)
//                .then(workerD);
//        List<WorkContinuation> taskList = new ArrayList<>();
//        taskList.add(continuation1);
//        taskList.add(continuation2);
//        WorkContinuation.combine(taskList)
//                .then(workerE)
//                .enqueue();


    }
}
