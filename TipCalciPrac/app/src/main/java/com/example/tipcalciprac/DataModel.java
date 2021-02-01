package com.example.tipcalciprac;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.tipcalciprac.R;

public class DataModel {

    private EditText editText;

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
    }

    public TextView getTipview() {
        return tipview;
    }

    public void setTipview(TextView tipview) {
        this.tipview = tipview;
    }

    public TextView getTotalview() {
        return totalview;
    }

    public void setTotalview(TextView totalview) {
        this.totalview = totalview;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }

    public Button getButtonexit() {
        return buttonexit;
    }

    public void setButtonexit(Button buttonexit) {
        this.buttonexit = buttonexit;
    }

    public TextView getTextViewProgress() {
        return textViewProgress;
    }

    public void setTextViewProgress(TextView textViewProgress) {
        this.textViewProgress = textViewProgress;
    }

    private  RadioGroup radioGroup;
    private TextView tipview;
    private TextView totalview;
    private SeekBar seekBar;
    private Button buttonexit;
    private TextView textViewProgress;
}
