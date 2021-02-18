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
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    TextView textViewName;
 /*   private String name;
    private String email;
    private String password;*/
    Button buttonLogout;
    Button buttonEditProfile;
    FragmentInterface fragmentInterface;
    DataServices.Account account1;



    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(DataServices.Account account) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
      /*  args.putString(ARG_PARAM1, account.getName());
        args.putString(ARG_PARAM2, account.getEmail());
        args.putString(ARG_PARAM3, account.getPassword());*/
        args.putSerializable(MainActivity.key,account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
         /*   name = getArguments().getString(ARG_PARAM1);
            email = getArguments().getString(ARG_PARAM2);
            password = getArguments().getString(ARG_PARAM3);*/
            account1= (DataServices.Account) getArguments().getSerializable(MainActivity.key);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        getActivity().setTitle(R.string.profileview);
        textViewName=view.findViewById(R.id.textViewName);
        buttonLogout=view.findViewById(R.id.buttonLogout);
        buttonEditProfile=view.findViewById(R.id.buttonEditProfile);
        textViewName.setText(account1.getName().toUpperCase());
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), getString(R.string.loggedout), Toast.LENGTH_SHORT).show();
                fragmentInterface.home();
            }
        });
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.Editprofile(account1);
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