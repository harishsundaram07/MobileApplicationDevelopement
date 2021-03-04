// Assignment : In Class 06
//File Name : GroupB09_InClass06
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09_inclass06;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    TextView textAverage;
    TextView textViewSeekbarCount;
    TextView textViewprogressCount;
    SeekBar seekBar;
    ProgressBar progressBar;
    ListView listView;
    ListViewAdapter listViewAdapter;
    int complexitycount;
    ArrayList<Double> result=new ArrayList<>();
    Handler handler;
    ExecutorService executorService;
    private ObjectAnimator progressAnimator;
    private final String key="KEY";
    private final String key1="KEY1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.app));
        //Instantiate
        seekBar=findViewById(R.id.seekBar);;
        progressBar=findViewById(R.id.progressBar);
        textAverage=findViewById(R.id.textAverage);
        textViewSeekbarCount=findViewById(R.id.textViewSeekbarCount);
        textViewprogressCount=findViewById(R.id.textViewprogressCount);
        listView=findViewById(R.id.ListView);
        progressBar.setVisibility(View.GONE);

        executorService = Executors.newFixedThreadPool(2);
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                return false;
            }
        });




        //Set Seekbar
        textViewSeekbarCount.setText(Integer.toString(0)+" "+getString(R.string.times));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewSeekbarCount.setText(Integer.toString(progress)+" "+getString(R.string.times));
                complexitycount= progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //OnclickAsyncButton
        findViewById(R.id.buttonAsync).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(complexitycount>0)
                {
                    result=new ArrayList<>();
                   listViewAdapter=new ListViewAdapter(MainActivity.this,R.layout.listviewholder,result);
                    listView.setAdapter(listViewAdapter);
                    new GetDetails().execute(complexitycount);
                }
                else
                    Toast.makeText(MainActivity.this, getString(R.string.toastmessage), Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.buttonThread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(complexitycount>0)
                {
                    result=new ArrayList<>();
                    listViewAdapter=new ListViewAdapter(MainActivity.this,R.layout.listviewholder,result);
                    listView.setAdapter(listViewAdapter);
                    new Thread(new GetDetailsThread()).start();


                }
                else
                    Toast.makeText(MainActivity.this, getString(R.string.toastmessage), Toast.LENGTH_SHORT).show();

            }
        });


        //Sethandler

        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case GetDetailsThread.STATUS_START:
                        findViewById(R.id.buttonAsync).setEnabled(false);
                        findViewById(R.id.buttonThread).setEnabled(false);
                        progressBar.setProgress(0);
                        seekBar.setEnabled(false);
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setMax(complexitycount);
                        textViewprogressCount.setText(0+"/"+complexitycount);
                        textAverage.setText(getString(R.string.average)+" 0");
                        break;

                    case GetDetailsThread.STATUS_STOP:
                        progressBar.setVisibility(View.GONE);
                        textViewprogressCount.setText("");
                        listViewAdapter=new ListViewAdapter(MainActivity.this,R.layout.listviewholder,result);
                        listView.setAdapter(listViewAdapter);
                        textAverage.setText(getString(R.string.average)+" "+Double.toString((Double) msg.obj));
                        findViewById(R.id.buttonAsync).setEnabled(true);
                        findViewById(R.id.buttonThread).setEnabled(true);
                        seekBar.setEnabled(true);

                        break;


                    case GetDetailsThread.STATUS_PROGRESS:
                        progressBar.setProgress((msg.getData().getInt(key)));
                        textViewprogressCount.setText(msg.getData().getInt(key)+"/"+complexitycount);
                        listViewAdapter=new ListViewAdapter(MainActivity.this,R.layout.listviewholder,result);
                        textAverage.setText(getString(R.string.average)+" "+Double.toString( msg.getData().getDouble(key1)));
                        listView.setAdapter(listViewAdapter);
                        break;

                }
                return false;
            }
        });
    }

    class GetDetails extends AsyncTask<Integer,Integer,ArrayList<Double>>
    {
        int max=0;
        double average;


        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(complexitycount);
            progressBar.setProgress(0);
            textViewprogressCount.setText(0+"/"+complexitycount);
            textAverage.setText(getString(R.string.average)+" 0");
            findViewById(R.id.buttonAsync).setEnabled(false);
            findViewById(R.id.buttonThread).setEnabled(false);
            seekBar.setEnabled(false);
            result.clear();


        }

        @Override
        protected void onPostExecute(ArrayList<Double> doubles) {
            progressBar.setVisibility(View.GONE);
            textViewprogressCount.setText("");
            listViewAdapter=new ListViewAdapter(MainActivity.this,R.layout.listviewholder,doubles);
            listView.setAdapter(listViewAdapter);
            textAverage.setText(getString(R.string.average)+" "+Double.toString(average));
            findViewById(R.id.buttonAsync).setEnabled(true);
            findViewById(R.id.buttonThread).setEnabled(true);
            seekBar.setEnabled(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            textViewprogressCount.setText(values[0]+"/"+complexitycount);
            listViewAdapter=new ListViewAdapter(MainActivity.this,R.layout.listviewholder,result);
            textAverage.setText(getString(R.string.average)+" "+Double.toString(average));
            listView.setAdapter(listViewAdapter);
        }

        @Override
        protected ArrayList<Double> doInBackground(Integer... integers) {
            double sum=0;
            max=integers[0];
            for(int i=0;i<integers[0];i++) {
                double j=HeavyWork.getNumber();
                result.add(j);
                sum=sum+j;
                average=sum/(i+1);
                publishProgress(i+1);
            }
            return result;
        }
    }

    class GetDetailsThread implements Runnable{

        private static final int STATUS_STOP = 0x02;
        private static final int STATUS_START = 0x00;
        private static final int STATUS_PROGRESS = 0x01;
        double average;

        @Override
        public void run() {
            result.clear();
            Message startmessage=new Message();
            startmessage.what=STATUS_START;
            handler.sendMessage(startmessage);

            double sum=0;
            for(int i=0;i<complexitycount;i++) {
                double j=HeavyWork.getNumber();
                result.add(j);
                sum=sum+j;
                Message progressmessage=new Message();
                average=sum/(i+1);
                progressmessage.what=STATUS_PROGRESS;
                Bundle bundle=new Bundle();
                bundle.putInt(key, i+1);
                bundle.putDouble(key1,average);
                progressmessage.obj=average;
                progressmessage.setData(bundle);
                handler.sendMessage(progressmessage);
            }
            Message stopmessage=new Message();
            stopmessage.what=STATUS_STOP;
            stopmessage.obj=average;
            handler.sendMessage(stopmessage);


        }
    }
}