package com.example.tipcalciprac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="TipCalulator" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataModel dataModel=new DataModel();
        dataModel.setEditText(findViewById(R.id.editTextInput));
        dataModel.setRadioGroup(findViewById(R.id.radioGroup));
        dataModel.setTipview(findViewById(R.id.textViewTipValue));
        dataModel.setTotalview(findViewById(R.id.textViewTotalValue));
        dataModel.setSeekBar(findViewById(R.id.seekBar));
        dataModel.setButtonexit(findViewById(R.id.buttonExit));
        dataModel.setTextViewProgress(findViewById(R.id.textViewProgress));
        dataModel.getRadioGroup().check(R.id.radioButton10);
        dataModel.getSeekBar().setEnabled( dataModel.getRadioGroup().getCheckedRadioButtonId()==R.id.radioButtonCustom?true:false);
        CalculateValue calculateValue;

        Toast.makeText(this,this.getString(R.string.ToastWelcome),Toast.LENGTH_SHORT).show();
        dataModel.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataModel.getSeekBar().setEnabled(dataModel.getRadioGroup().getCheckedRadioButtonId()==R.id.radioButtonCustom?true:false);
                Log.d(TAG, "onTextChanged: Entered");
                new CalculateValue(dataModel,getBaseContext());
                Log.d(TAG, "onTextChanged: exit");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dataModel.getRadioGroup().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "onCheckedChanged: entered :" +checkedId);
                dataModel.getSeekBar().setEnabled(dataModel.getRadioGroup().getCheckedRadioButtonId()==R.id.radioButtonCustom?true:false);
                new CalculateValue(dataModel,getBaseContext());
                Log.d(TAG, "onCheckedChanged: Exit");
            }
        });
        dataModel.getSeekBar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: Entered");
                new CalculateValue(dataModel,getBaseContext());
                Log.d(TAG, "onProgressChanged: Exit");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dataModel.getButtonexit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


    }
}