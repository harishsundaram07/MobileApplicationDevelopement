// Assignment : In Class 07
//File Name : GroupB09_InClass07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09inclass07;

import android.accounts.Account;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateContact#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateContact extends Fragment {


    View view;
    FragmentInterface fragmentInterface;
    static List<Contact> contactlist=new ArrayList<>();
    EditText editTextTextPersonName;
    EditText editTextTextEmailAddress;
    EditText editTextPhone;
    EditText editTextType;




    public CreateContact() {
        // Required empty public constructor
    }

    public static CreateContact newInstance() {
        CreateContact fragment = new CreateContact();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface )
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");
    }



    //Create Contact
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_create_contact, container, false);
        editTextType=view.findViewById(R.id.editTextType);
        editTextTextPersonName=view.findViewById(R.id.editTextTextPersonName);
        editTextTextEmailAddress=view.findViewById(R.id.editTextTextEmailAddress);
        editTextPhone=view.findViewById(R.id.editTextPhone);
        view.findViewById(R.id.buttonsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTextPersonName.getText().length()>0 && editTextTextEmailAddress.getText().length()>0 && editTextPhone.getText().length()>0 && editTextType.getText().length()>0)
                {
                    Contact contact=new Contact();
                    contact.name=editTextTextPersonName.getText().toString();
                    contact.email=editTextTextEmailAddress.getText().toString();
                    contact.phone=editTextPhone.getText().toString();
                    contact.type=editTextType.getText().toString();
                    view.findViewById(R.id.buttonsubmit).setEnabled(false);
                    view.findViewById(R.id.buttoncancel).setEnabled(false);
                    CallAPI.createdata(contact, new CallAPI.CallHandler() {
                        @Override
                        public void onSuccess(List<Contact> strings, String status, String message) {
                            if(getActivity()!=null) {
                                getActivity().runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    view.findViewById(R.id.buttoncancel).setEnabled(true);
                                                                    view.findViewById(R.id.buttonsubmit).setEnabled(true);
                                                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                                                    fragmentInterface.gohome();
                                                                }
                                                            }

                                );
                            }

                        }

                        @Override
                        public void onFailure(Exception e, String status, String message) {
                            if(getActivity()!=null){
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        view.findViewById(R.id.buttoncancel).setEnabled(true);
                                        view.findViewById(R.id.buttonsubmit).setEnabled(true);
                                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                        }
                    });
                    {

                    }
                }
                else if(editTextTextPersonName.getText().length()<=0 )
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder
                            .setMessage(getString(R.string.errorname))
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
                else if(editTextTextEmailAddress.getText().length()<=0)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder
                            .setMessage(getString(R.string.errormail))
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
                else if(editTextPhone.getText().length()<=0)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder
                            .setMessage(getString(R.string.errorphone))
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
                else if(editTextType.getText().length()<=0)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder
                            .setMessage(getString(R.string.errortype))
                            .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
            }
        });

        view.findViewById(R.id.buttoncancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.gohome();
            }
        });
        return view;

    }
}