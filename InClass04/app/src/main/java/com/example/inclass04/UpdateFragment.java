// Assignment : In Class 04
//File Name : InClass04
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass04;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFragment extends Fragment {
    FragmentInterface fragmentInterface;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
/*    private String name;
    private String email;
    private String password;*/
    DataServices.Account account1;
    EditText editTextPersonNameChange;
    EditText editTextChangepassword;
    Button buttonSubmitUpdate;
    TextView buttonCancelUpdate;
    TextView textViewHeading;


    public UpdateFragment() {
        // Required empty public constructor
    }

    public static UpdateFragment newInstance(DataServices.Account account) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.key,account);
       /* args.putString(ARG_PARAM1, account.getName());
        args.putString(ARG_PARAM2, account.getEmail());
        args.putString(ARG_PARAM3, account.getPassword());*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          /*  name = getArguments().getString(ARG_PARAM1);
            email = getArguments().getString(ARG_PARAM2);
            password = getArguments().getString(ARG_PARAM3);*/
            account1= (DataServices.Account) getArguments().getSerializable(MainActivity.key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_update, container, false);
        textViewHeading=view.findViewById(R.id.textViewHeading);
        buttonCancelUpdate=view.findViewById(R.id.buttonCancelUpdate);
        buttonSubmitUpdate=view.findViewById(R.id.buttonSubmitUpdate);
        editTextChangepassword=view.findViewById(R.id.editTextChangepassword);
        editTextPersonNameChange=view.findViewById(R.id.editTextPersonNameChange);
        getActivity().setTitle(R.string.UpdateProfile);
        textViewHeading.setText(account1.getEmail());
        buttonCancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.profile(account1);
            }
        });
        buttonSubmitUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPersonNameChange.getText().length()>0 && editTextChangepassword.getText().length()>0) {
                    account1=DataServices.update(account1,editTextPersonNameChange.getText().toString(),editTextChangepassword.getText().toString());
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack();
                    fragmentInterface.profile(account1);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.Entervalue), Toast.LENGTH_SHORT).show();
                }
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