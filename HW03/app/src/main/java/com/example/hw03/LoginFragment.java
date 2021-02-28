// Assignment : In Class 05
//File Name : InClass05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.hw03;

import android.content.Context;
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


public class LoginFragment extends Fragment implements DataServices.AuthResponse {


    private static final String ARG_PARAM1 = "param1";
    private DataServices.Account account1;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    Button buttonLogin;
    TextView textCreate;
    FragmentInterface fragmentInterface;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(DataServices.Account account) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.key,account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account1 = (DataServices.Account) getArguments().getSerializable(MainActivity.key);
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
                    DataServices.login(editTextTextEmailAddress.getText().toString(),editTextTextPassword.getText().toString(), LoginFragment.this);
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
    public void onSuccess(String token) {
        fragmentInterface.goProfile(token);
    }


    @Override
    public void onFailure(DataServices.RequestException exception) {
        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();

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