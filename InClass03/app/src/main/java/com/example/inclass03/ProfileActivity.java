/// Assignment : In Class 03
//File Name : ProfileActivity.java
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle(getString(R.string.title_profile));
        TextView editName = findViewById(R.id.textViewNameResult);
        TextView editEmail = findViewById(R.id.textViewEmailResult);
        TextView editID = findViewById(R.id.textViewIDResult);
        TextView editDept = findViewById(R.id.textViewDeptResultAP);

        if(getIntent()!=null && getIntent().getExtras() != null && getIntent().hasExtra(MainActivity.USER)){
            User user = getIntent().getParcelableExtra(MainActivity.USER);
            editName.setText(user.name);
            editEmail.setText(user.email);
            editID.setText(user.id);
            editDept.setText(user.dept);
        }
    }
}