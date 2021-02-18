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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    EditText editTextTextPersonName;
    EditText editTextTextEmailAddress2;
    EditText editTextTextPassword2;
    Button buttonSubmit;
    TextView buttonCancel;
    DataServices.Account account1;
    FragmentInterface fragmentInterface;



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        getActivity().setTitle(R.string.Createaccount);
        editTextTextEmailAddress2=view.findViewById(R.id.editTextTextEmailAddress2);
        editTextTextPassword2=view.findViewById(R.id.editTextTextPassword2);
        editTextTextPersonName=view.findViewById(R.id.editTextTextPersonName);
        buttonCancel=view.findViewById(R.id.buttonCancel);
        buttonSubmit=view.findViewById(R.id.buttonSubmit);
        DataServices.register(editTextTextPersonName.getText().toString(),editTextTextEmailAddress2.getText().toString(),editTextTextPassword2.getText().toString());
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextTextPersonName.getText().length()>0 &&
                        editTextTextEmailAddress2.getText().length()>0 &&
                        editTextTextPassword2.getText().length()>0)
                {
                        account1=DataServices.register(editTextTextPersonName.getText().toString(),editTextTextEmailAddress2.getText().toString(),editTextTextPassword2.getText().toString());
                    if (account1!=null) {
                        Toast.makeText(getActivity(), getString(R.string.Usercreated)+editTextTextPersonName.getText().toString(), Toast.LENGTH_SHORT).show();
                        fragmentInterface.profile(account1);
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.UserExist)+editTextTextPersonName.getText().toString(), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    if(editTextTextPersonName.getText().length()==0)
                        Toast.makeText(getActivity(), getString(R.string.enterName), Toast.LENGTH_SHORT).show();
                    else if( editTextTextEmailAddress2.getText().length()==0)
                        Toast.makeText(getActivity(), getString(R.string.enteremail), Toast.LENGTH_SHORT).show();
                    else if( editTextTextPassword2.getText().length()==0)
                        Toast.makeText(getActivity(), getString(R.string.enterpass), Toast.LENGTH_SHORT).show();

                }
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
}