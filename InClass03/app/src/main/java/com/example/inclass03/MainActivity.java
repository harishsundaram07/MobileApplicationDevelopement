
/// Assignment : In Class 03
//File Name : MainActivity.java
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    final static public String USER = "USER";
    final static public String RADIO_BUTTON = "RADIO_BUTTON";
    EditText editName;
    EditText editEmail;
    EditText editID;
    TextView textViewDept;
    Button buttonSelect;
    Button buttonSubmit;
    TextView deptResult;
    final static public int  REQ_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.title_main));

        editName = findViewById(R.id.editTextPersonName);
        editEmail = findViewById(R.id.editTextEmailAddress);
        editID = findViewById(R.id.editTextID);
        buttonSelect = findViewById(R.id.buttonSelect);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        deptResult=findViewById(R.id.textViewDeptResult);

        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.example.inclass03.intent.action.VIEW");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                if(deptResult.getText().length()>0)
                    intent.putExtra(RADIO_BUTTON,deptResult.getText().toString());
                startActivityForResult(intent,REQ_CODE);
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editName.getText().length()>0 && editEmail.getText().length()>0 && editID.getText().length()>0 && deptResult.getText().length()>0){
                    Pattern pattern,pattern2;
                    Matcher matcher1,matcher2;
                    String regexEmail = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
                    pattern = Pattern.compile(regexEmail);
                    matcher1 = pattern.matcher(editEmail.getText().toString());
                    String regexId="^[a-zA-Z0-9]+$";
                    pattern2 = Pattern.compile(regexId);
                    matcher2 = pattern2.matcher(editID.getText().toString());
                    if(matcher1.matches() && matcher2.matches()){
                        Intent intentProfile = new Intent(MainActivity.this, ProfileActivity.class);
                        intentProfile.putExtra(USER, new User(editName.getText().toString(), editEmail.getText().toString(), editID.getText().toString(), deptResult.getText().toString()));
                        startActivity(intentProfile);
                    }
                    else if(!matcher2.matches()){
                        Toast.makeText(MainActivity.this, getString(R.string.ToastSubmitIDError), Toast.LENGTH_SHORT).show();
                    }
                    else if(!matcher1.matches()){
                        Toast.makeText(MainActivity.this, getString(R.string.ToastSubmitEmailError), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,getString(R.string.ToastSubmitNullError),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== RESULT_OK)
        {
            if(data!=null && data.getExtras()!=null && data.hasExtra(DepartmentActivity.departmentString))
            {
                deptResult.setText(data.getStringExtra(DepartmentActivity.departmentString));
            }

        }
    }
}