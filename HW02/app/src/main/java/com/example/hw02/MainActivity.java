package com.example.hw02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Task> taskList=new ArrayList<>();
    TextView textviewMaintitle;
    TextView textViewUpcoming;
    TextView textViewTaskName;
    TextView textViewDate;
    TextView textViewPriority;
    Button buttonViewTask;
    Button buttonCreateTask;
    int index;
    final public static int REQ_CODE=100;
    final public static int REQ_CODE_DISPLAY=200;
    final public static String listPass = "LISTPASS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.TODOLIST));
        textviewMaintitle=findViewById(R.id.textviewMaintitle);
        textViewUpcoming=findViewById(R.id.textViewUpcoming);
        textViewTaskName=findViewById(R.id.textViewTaskName);
        textViewDate=findViewById(R.id.textViewDate);
        textViewPriority=findViewById(R.id.textViewPriority);
        buttonViewTask=findViewById(R.id.buttonViewTask);
        buttonCreateTask=findViewById(R.id.buttonCreateTask);
        setTextview(getString(R.string.textviewMaintitle),taskList);
        buttonViewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (taskList.size()>0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    CharSequence[] charseq=new CharSequence[taskList.size()];
                    for(int i=0;i<taskList.size();i++)
                        charseq[i]=taskList.get(i).name;
                    builder.setTitle(getString(R.string.SelectTask)).setNegativeButton(getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setItems(charseq, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            index=which;
                            Intent intent=new Intent("com.example.hw02.DISPLAY");
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.putExtra(DisplayActivity.Display_Task,taskList.get(which));
                            startActivityForResult(intent,REQ_CODE_DISPLAY);
                        }
                    });
                    builder.create().show();
                } else {
                    Toast.makeText(getBaseContext(),getString(R.string.ToastNoTask),Toast.LENGTH_SHORT).show();

                }
            }
        });
        buttonCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example.hw02.CREATE");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivityForResult(intent,REQ_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQ_CODE) {
            if(data!=null && data.getExtras()!=null && data.hasExtra(CreateActivity.createTask))
            {
                Task task = data.getParcelableExtra(CreateActivity.createTask);
                taskList.add(task);
                setTextview(getString(R.string.textviewMaintitle),taskList);
            }
        } else if(requestCode==REQ_CODE_DISPLAY){
                taskList.remove(index);
                setTextview(getString(R.string.textviewMaintitle),taskList);
        }
    }

    private void setTextview(String s, List taskList) {
        textviewMaintitle.setText(s.substring(0, 9) +Integer.toString(taskList.size())+ s.substring(8, 15));
        if (taskList.size()>0) {
            Collections.sort(taskList, new Comparator<Task>() {
                @Override
                public int compare(Task o1, Task o2) {
                    Date date1 = new Date();
                    Date date2 = new Date();
                    try {
                        date1 = new SimpleDateFormat("MM/dd/yyyy").parse(o1.date);
                         date2 = new SimpleDateFormat("MM/dd/yyyy").parse(o2.date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return (date1.compareTo(date2));
                }
            });
            Task task= (Task) taskList.get(0);
            textViewTaskName.setText(task.name);
            textViewDate.setText(task.date);
            textViewPriority.setText(task.priority);
        }


    }
}