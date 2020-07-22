package com.bodhi.navigationtestdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.Navigation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sendNotification();
            }
        },2000);
    }

    private void sendNotification(){
        //通过sendNotification方法向通知栏发送一条通知，模拟用户收到通知的情况
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O){
            int importanceDefault = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channer_id", "channer_name", importanceDefault);
            channel.setDescription("description");
            NotificationManager notificationManager =getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat
                .Builder(this, "channer_id")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("DeepLinkTest")
                .setContentText("Content Text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(getPendingIntent())
                .setAutoCancel(true);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,builder.build());
    }

    private PendingIntent getPendingIntent(){
        if(this!=null){
            Bundle bundle = new Bundle();
            bundle.putString("params","ParamsFromNotification_HelloMika");
            return Navigation
                    .findNavController(this,R.id.nav_host_fragment_notification)
                    .createDeepLink()
                    .setGraph(R.navigation.nav_graph_notification)
//                    .setDestination(R.id.notificationClickActivity)
                    .setDestination(R.id.deepLinkSettingFragment)
                    .setArguments(bundle)
                    .createPendingIntent();
        }

        return null;
    }
}