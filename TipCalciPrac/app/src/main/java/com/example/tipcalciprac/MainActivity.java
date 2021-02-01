package com.example.tipcalciprac;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="TipCalulator" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText=findViewById(R.id.editTextInput);
        RadioGroup radioGroup=findViewById(R.id.radioGroup);
        TextView tipview=findViewById(R.id.textViewTipValue);
        TextView totalview=findViewById(R.id.textViewTotalValue);
        SeekBar seekBar=findViewById(R.id.seekBar);
        radioGroup.check(R.id.radioButton10);
        Button buttonexit=findViewById(R.id.buttonExit);
        TextView textViewProgress=findViewById(R.id.textViewProgress);
        seekBar.setEnabled(radioGroup.getCheckedRadioButtonId()==R.id.radioButtonCustom?true:false);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                seekBar.setEnabled(radioGroup.getCheckedRadioButtonId()==R.id.radioButtonCustom?true:false);
                Log.d(TAG, "onTextChanged: Entered");
                new CalculateValue(radioGroup,editText, radioGroup.getCheckedRadioButtonId(),tipview,totalview,seekBar,getBaseContext(),textViewProgress);
                Log.d(TAG, "onTextChanged: exit");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d(TAG, "onCheckedChanged: entered :" +checkedId);
                seekBar.setEnabled(radioGroup.getCheckedRadioButtonId()==R.id.radioButtonCustom?true:false);
                new CalculateValue(radioGroup,editText, checkedId,tipview,totalview,seekBar,getBaseContext(), textViewProgress);
                Log.d(TAG, "onCheckedChanged: Exit");
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "onProgressChanged: Entered");
                new CalculateValue(radioGroup,editText,R.id.radioButtonCustom,tipview,totalview,seekBar,getBaseContext(), textViewProgress);
                Log.d(TAG, "onProgressChanged: Exit");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


    }
}