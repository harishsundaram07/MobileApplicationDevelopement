/// Assignment : In Class 03
//File Name : DepartmentActivity.java
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DepartmentActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    Button buttonSelectAD;
    Button buttonCancel;
    final public static String departmentString="dept";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        setTitle(getString(R.string.title_dept));

        radioGroup=findViewById(R.id.radioGroup);
        buttonSelectAD = findViewById(R.id.buttonSelectAD);
        buttonCancel = findViewById(R.id.buttonCancel);

        if(getIntent()!=null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.RADIO_BUTTON)){
            String radioDept = getIntent().getStringExtra(MainActivity.RADIO_BUTTON);
            if(radioDept.equalsIgnoreCase(getString(R.string.rb_cs))){
                radioGroup.check(R.id.radioButtonComputerScience);
            }else if(radioDept.equalsIgnoreCase(getString(R.string.rb_bi))){
                radioGroup.check(R.id.radioButtonBI);
            }else if(radioDept.equalsIgnoreCase(getString(R.string.rb_sis))){
                radioGroup.check(R.id.radioButtonSIS);
            }else if(radioDept.equalsIgnoreCase(getString(R.string.rb_ds))){
                radioGroup.check(R.id.radioButtonDS);
            }
        }

        buttonSelectAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dept="";
                int checkedId=radioGroup.getCheckedRadioButtonId();
                if (checkedId!=-1) {
                    if(checkedId==R.id.radioButtonComputerScience)
                        dept=getString(R.string.rb_cs);
                    else if(checkedId==R.id.radioButtonBI)
                        dept=getString(R.string.rb_bi);
                    else if(checkedId==R.id.radioButtonDS)
                        dept=getString(R.string.rb_ds);
                    else if(checkedId==R.id.radioButtonSIS)
                        dept=getString(R.string.rb_sis);
                    Intent intent=new Intent(DepartmentActivity.this,MainActivity.class);
                    intent.putExtra(departmentString,dept);
                    setResult(RESULT_OK,intent);
                    finish();
                } else {
                    Toast.makeText(DepartmentActivity.this,getString(R.string.ToastRadio),Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });




    }
}