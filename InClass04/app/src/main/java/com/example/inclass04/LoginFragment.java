// Assignment : In Class 04
//File Name : InClass04
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final String ARG_PARAM1 = "mailid";
    private static final String ARG_PARAM2 = "password";
    private String memailid;
    private String mpassword;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    Button buttonLogin;
    TextView buttonCreate;
    FragmentInterface fragmentInterface;
    DataServices.Account account;


    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String email, String password) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, email);
        args.putString(ARG_PARAM2, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            memailid = getArguments().getString(ARG_PARAM1);
            mpassword = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_login, container, false);
        editTextTextEmailAddress=view.findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword=view.findViewById(R.id.editTextTextPassword);
        buttonCreate=view.findViewById(R.id.buttonCreate);
        buttonLogin=view.findViewById(R.id.buttonLogin);
        getActivity().setTitle(R.string.Loginpage);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTextEmailAddress.getText().length()<=0 || editTextTextPassword.getText().length()<=0)
                    Toast.makeText(getActivity(), editTextTextEmailAddress.getText().length()<=0?getString(R.string.Enteremail):getString(R.string.enterpassword), Toast.LENGTH_SHORT).show();
                else
                {
                    if(DataServices.login(editTextTextEmailAddress.getText().toString(),editTextTextPassword.getText().toString())==null)
                    {
                        Toast.makeText(getActivity(), getString(R.string.Inavalid), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity(), getString(R.string.welcome1), Toast.LENGTH_SHORT).show();
                        account =DataServices.login(editTextTextEmailAddress.getText().toString(),editTextTextPassword.getText().toString());
                        fragmentInterface.onLogin(account);
                    }

                }
            }
        });
            buttonCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentInterface.onCreate();
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
}