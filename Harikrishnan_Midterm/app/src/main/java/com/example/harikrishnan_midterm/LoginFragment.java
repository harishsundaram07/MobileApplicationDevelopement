package com.example.harikrishnan_midterm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginFragment extends Fragment {

    View view;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    TextView TextCreate;
    String token;
    DataServices.Account account;
    FragmentInterface fragmentInterface;


    public LoginFragment() {

    }



    public static LoginFragment newInstance(String param1, String param2) {
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

        view= inflater.inflate(R.layout.fragment_login, container, false);
        editTextTextEmailAddress=view.findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword=view.findViewById(R.id.editTextTextPassword);
        getActivity().setTitle(getString(R.string.login));

        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DoLogin().execute(editTextTextEmailAddress.getText().toString(),editTextTextPassword.getText().toString());

            }
        });

        view.findViewById(R.id.TextCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.gotocreate();
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



    class DoLogin extends AsyncTask<String,Integer, DataServices.AuthResponse> {

        DataServices.AuthResponse authResponse;
        int status;
        int success=0;
        int failed=1;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.findViewById(R.id.buttonLogin).setEnabled(false);
            view.findViewById(R.id.TextCreate).setEnabled(false);

        }

        @Override
        protected void onPostExecute(DataServices.AuthResponse authResponse) {
            view.findViewById(R.id.buttonLogin).setEnabled(true);
            view.findViewById(R.id.TextCreate).setEnabled(true);
            if(status==success)
            {
                account=authResponse.getAccount();
                token=authResponse.getToken();
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                fragmentInterface.gotoforums(account,token);
                Log.d("TAG", "onPostExecute: "+account.toString());

            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected DataServices.AuthResponse doInBackground(String... strings) {
            try {
                publishProgress();
                authResponse=DataServices.login(strings[0],strings[1]);
                status=success;
                message=getString(R.string.welcome);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                status=failed;
                message=e.getMessage();
            }

            return authResponse;
        }
    }
}