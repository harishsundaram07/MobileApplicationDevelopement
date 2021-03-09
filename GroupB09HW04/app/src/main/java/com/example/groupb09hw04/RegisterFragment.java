// Assignment : Homework 04
//File Name : HW04;
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09hw04;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterFragment extends Fragment  {

    EditText editTextTextPersonName;
    EditText editTextTextEmailAddress2;
    EditText editTextTextPassword2;
    Button buttonSubmit;
    TextView buttonCancel;
    FragmentInterface fragmentInterface;
    DoRegister doRegister;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        editTextTextPersonName=view.findViewById(R.id.editTextTextPersonName);
        editTextTextEmailAddress2=view.findViewById(R.id.editTextTextEmailAddress2);
        editTextTextPassword2=view.findViewById(R.id.editTextTextPassword2);
        buttonSubmit=view.findViewById(R.id.buttonSubmit);
        buttonCancel=view.findViewById(R.id.buttonCancel);
        getActivity().setTitle(getString(R.string.registertitle));

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister=new DoRegister();
                doRegister.execute(editTextTextPersonName.getText().toString(),editTextTextEmailAddress2.getText().toString(),editTextTextPassword2.getText().toString());
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.home();
            }
        });
        return view;
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface )
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(doRegister != null){
            doRegister.cancel(true);
        }
    }

   //TODO AYNCCLASS

    class DoRegister extends AsyncTask<String,Integer,Integer>
    {
        @Override
        protected void onPreExecute() {
            buttonSubmit.setEnabled(false);
            buttonCancel.setEnabled(false);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        String token;
        int success=100;
        int failed=400;
        String errormessage;


        @Override
        protected Integer doInBackground(String... strings) {
            if(doRegister.isCancelled()!=true){
                try {
                    token = DataServices.register(strings[0], strings[1], strings[2]);
                    return success;
                } catch (DataServices.RequestException e) {
                    e.printStackTrace();
                    errormessage = e.getMessage();
                    return failed;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {

            if(doRegister.isCancelled()!=true){
                if (integer == success)
                    fragmentInterface.goProfile(token);
                else {
                    buttonSubmit.setEnabled(true);
                    buttonCancel.setEnabled(true);
                    Toast.makeText(getActivity(), errormessage, Toast.LENGTH_SHORT).show();
                }
            }

        }




    }
}