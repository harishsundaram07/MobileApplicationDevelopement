package com.example.inclass05;

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

public class RegisterFragment extends Fragment implements DataServices.AuthResponse {

    EditText editTextTextPersonName;
    EditText editTextTextEmailAddress2;
    EditText editTextTextPassword2;
    Button buttonSubmit;
    TextView buttonCancel;
    FragmentInterface fragmentInterface;

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
                DataServices.register(editTextTextPersonName.getText().toString(),editTextTextEmailAddress2.getText().toString(),editTextTextPassword2.getText().toString(),RegisterFragment.this);
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