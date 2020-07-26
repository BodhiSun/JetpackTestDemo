package com.bodhi.roomtestdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bodhi.roomtestdemo.dao.StudentDao;
import com.bodhi.roomtestdemo.db.MyDatabase;
import com.bodhi.roomtestdemo.db.MyDatabaseFormAssets;
import com.bodhi.roomtestdemo.table.Student;
import com.bodhi.roomtestdemo.upgrade.StudentViewModelAsset;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private TextView textview;
    private StringBuilder sb;
    private MyDatabase myDatabase;
    private ExecutorService threadPoolExecutor;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        textView = findViewById(R.id.textView);


        //获得RoomDatabase对象 然后对数据库进行增删改查
        myDatabase = MyDatabase.getInstance(mContext);
        threadPoolExecutor = Executors.newFixedThreadPool(4);
        sb = new StringBuilder();

        //ViewModel+LiveData+Room 数据库数据发生变化不用手动调用query
        StudentViewModel studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        studentViewModel.setApplication(getApplication());
        studentViewModel.getLiveDataStudent().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                sb.delete(0,sb.length());
                for(Student stu: students){
                    sb.append("id:"+stu.id+"    name:"+stu.name+"  "+"  age:"+stu.age+"\n");
                }
                sb.append("===============");
//                System.out.println(sb.toString());
//                System.out.println("=============");
                textView.setText(sb.toString());
            }
        });

        //通过createFromAsset 创建数据库
        StudentViewModelAsset studentViewModelAsset = new ViewModelProvider(this).get(StudentViewModelAsset.class);
        studentViewModelAsset.setApplication(getApplication());
        studentViewModelAsset.getLiveDataStudent().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                sb.delete(0,sb.length());
                for(Student stu: students){
                    sb.append("id:"+stu.id+"    name:"+stu.name+"  "+"  age:"+stu.age+"\n");
                }
                System.out.println("===从asset数据库中预填充数据库===");
                System.out.println(sb.toString());
                System.out.println("=============");
//                textView.setText(sb.toString());
            }
        });
    }

    private void deleteStudent() {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //以id为准
                myDatabase.studentDao().deleteStudent(new Student(2,"",0+""));
            }
        });
    }

    private void updateStudent() {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                //以id为准
                myDatabase.studentDao().updateStudent(new Student(1,"白妞妞",5+""));
            }
        });
    }

    private void insertStudent() {
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                myDatabase.studentDao().insertStudent(new Student("妞妞",5+""));
                myDatabase.studentDao().insertStudent(new Student("仔仔",26+""));
                myDatabase.studentDao().insertStudent(new Student("宝儿",29+""));
            }
        });
    }

    public void getStudents(){
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                //所有对数据库的操作都应该放在子线程中
                List<Student> studentList = myDatabase.studentDao().getStudentList();
                for(Student stu: studentList){
                    sb.append("id:"+stu.id+"    name:"+stu.name+"  "+"  age:"+stu.age+"\n");
                }
                System.out.println(sb.toString());
                System.out.println("=============");
            }
        });
    }


    public void insertStudent(View view) {
        insertStudent();
        Toast.makeText(this,"insertStudent",Toast.LENGTH_SHORT).show();
    }

    public void deleteStudent(View view) {
        deleteStudent();
        Toast.makeText(this,"deleteStudent",Toast.LENGTH_SHORT).show();
    }

    public void updateStudent(View view) {
        updateStudent();
        Toast.makeText(this,"updateStudent",Toast.LENGTH_SHORT).show();
    }

    public void queryStudent(View view) {
        getStudents();
        Toast.makeText(this,"queryStudent",Toast.LENGTH_SHORT).show();
    }
}
