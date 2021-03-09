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


public class LoginFragment extends Fragment {


    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    Button buttonLogin;
    TextView textCreate;
    FragmentInterface fragmentInterface;
    DoLogin doLogin;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(DataServices.Account account) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
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
        View view= inflater.inflate(R.layout.fragment_login, container, false);
        editTextTextEmailAddress=view.findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword=view.findViewById(R.id.editTextTextPassword);
        textCreate=view.findViewById(R.id.TextCreate);
        buttonLogin=view.findViewById(R.id.buttonLogin);
        getActivity().setTitle(getString(R.string.logintitle));

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin=new DoLogin();
                doLogin.execute(editTextTextEmailAddress.getText().toString(),editTextTextPassword.getText().toString());
            }
        });
        textCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goRegister();
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

        if(doLogin != null){
            doLogin.cancel(true);
        }
    }


    class DoLogin extends AsyncTask<String,Integer,Integer>
    {
         String token;
         int success=100;
         int failed=400;
         String errormessage;

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            buttonLogin.setEnabled(false);
            textCreate.setEnabled(false);
        }

        @Override
        protected Integer doInBackground(String... strings) {

            if(doLogin.isCancelled()!=true){
                try {
                    token = DataServices.login(strings[0], strings[1]);
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
            if(doLogin.isCancelled()!=true) {
                if (integer == success) {
                    Toast.makeText(getActivity(), getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                    fragmentInterface.goProfile(token);
                } else {
                    Toast.makeText(getActivity(), errormessage, Toast.LENGTH_SHORT).show();
                    buttonLogin.setEnabled(true);
                    textCreate.setEnabled(true);
                }
            }

        }
    }
}