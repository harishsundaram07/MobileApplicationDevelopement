// Assignment : In Class 07
//File Name : GroupB09_InClass07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09inclass07;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {


    View view;
    FragmentInterface fragmentInterface;
    TextView textviewname;
    TextView textviewemail;
    TextView textviewphone;
    TextView textviewtype;
    TextView textviewid;
    Contact mcontact;
    public DetailsFragment() {
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface )
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");
    }


    public static DetailsFragment newInstance(Contact contact) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.key,contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mcontact= (Contact) getArguments().getSerializable(MainActivity.key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view= inflater.inflate(R.layout.fragment_details, container, false);
        textviewemail=view.findViewById(R.id.textviewemail);
        textviewname=view.findViewById(R.id.textviewname);
        textviewphone=view.findViewById(R.id.textviewphone);
        textviewtype=view.findViewById(R.id.textviewtype);
        textviewid=view.findViewById(R.id.textviewid);
        getActivity().setTitle(R.string.Contactsinfo);
        textviewemail.setText(mcontact.email);
        textviewname.setText(mcontact.name);
        textviewphone.setText(mcontact.phone);
        textviewtype.setText(mcontact.type);
        textviewid.setText(mcontact.id);

        view.findViewById(R.id.textViewdismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.gohome();
            }
        });

        view.findViewById(R.id.buttonedit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goUpdate(mcontact);
            }
        });
        view.findViewById(R.id.buttondel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder
                        .setMessage(getString(R.string.deleteconfrim)+" "+mcontact.name+" ?")
                        .setPositiveButton(getString(R.string.confirmation), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CallAPI.deletedata(mcontact.id,new CallAPI.CallHandler() {
                                    @Override
                                    public void onSuccess(List<Contact> strings, String status, String message) {
                                        if(getActivity()!=null)  {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    fragmentInterface.gohome();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(Exception e, String status, String message) {
                                        if(getActivity()!=null){
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }
        });


        return view;
    }
}