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
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {

    View view;
    DataServices.Account account;
    FragmentInterface fragmentInterface;
    EditText editTextTextPersonName;
    EditText editTextTextEmailAddress2;
    EditText editTextTextPassword2;


    static  final String createkey="CREATEKEY";
    static final String createkey1="CREATEKEY1";

    String token;

    public CreateFragment() {
        // Required empty public constructor
    }

    public static CreateFragment newInstance(DataServices.Account account,String token) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putSerializable(createkey,account);
        args.putString(createkey1,token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        /*maccount= (DataServices.Account) getArguments().getSerializable(createkey);
        mtoken=getArguments().getString(createkey1);*/
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_create, container, false);
        editTextTextPersonName=view.findViewById(R.id.editTextTextPersonName);
        editTextTextEmailAddress2=view.findViewById(R.id.editTextTextEmailAddress2);
        editTextTextPassword2=view.findViewById(R.id.editTextTextPassword2);
        getActivity().setTitle(getString(R.string.create_a_new_account));

        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Docreate().execute(editTextTextPersonName.getText().toString(),editTextTextEmailAddress2.getText().toString(),editTextTextPassword2.getText().toString());
            }
        });
        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goHome();
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


    class Docreate extends AsyncTask<String,Integer, DataServices.AuthResponse>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.findViewById(R.id.buttonSubmit).setEnabled(false);
            view.findViewById(R.id.buttonCancel).setEnabled(false);

        }

        @Override
        protected void onPostExecute(DataServices.AuthResponse authResponse) {
            super.onPostExecute(authResponse);
            view.findViewById(R.id.buttonSubmit).setEnabled(true);
            view.findViewById(R.id.buttonCancel).setEnabled(true);
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



        DataServices.AuthResponse authResponse;
        int status;
        int success=0;
        int failed=1;
        String message;

        @Override
        protected DataServices.AuthResponse doInBackground(String... strings) {
            try {
                publishProgress();
                authResponse=DataServices.register(strings[0],strings[1],strings[2]);
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