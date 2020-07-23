package com.bodhi.viewmodeltestdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponent();
    }

    private void initComponent() {
        textView = findViewById(R.id.textview);
        TimerViewModel timerViewModel = new ViewModelProvider(this).get(TimerViewModel.class);
        timerViewModel.setOnTimeChangeListener(new TimerViewModel.OnTimeChangeListener() {
            @Override
            public void onTimeChange(final int second) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("TIME:"+second);
                    }
                });
            }
        });
        timerViewModel.startTiming();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("key", "I am going to be killed");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        textView.setText("TIME:" + savedInstanceState.getString("key"));
    }

    public void clickToLiveData(View view) {
        startActivity(new Intent(this,TimerWithLiveDataActivity.class));
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        outPersistentState.putString("key", "I am going to be killed");
//    }
//    @Override
//    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onRestoreInstanceState(savedInstanceState, persistentState);
//        textView.setText("TIME:" + persistentState.getString("key"));
//    }
}
