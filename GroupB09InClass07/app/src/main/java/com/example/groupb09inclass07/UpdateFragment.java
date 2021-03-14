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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFragment extends Fragment {


    View view;
    FragmentInterface fragmentInterface;
    Contact mcontact;
    EditText updatePersonName;
    EditText updateEmailAddress;
    EditText updatePhone;
    EditText updateType;
    TextView textViewIdDetails;

    public UpdateFragment() {
        // Required empty public constructor
    }

    public static UpdateFragment newInstance(Contact contact) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.key,contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface )
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mcontact = (Contact) getArguments().getSerializable(MainActivity.key);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_update, container, false);
        updateEmailAddress=view.findViewById(R.id.updateEmailAddress);
        updatePersonName=view.findViewById(R.id.updatePersonName);
        updatePhone=view.findViewById(R.id.updatePhone);
        updateType=view.findViewById(R.id.updateType);
        textViewIdDetails=view.findViewById(R.id.textViewIdDetails);
        textViewIdDetails.setText(mcontact.id);
        updateEmailAddress.setText(mcontact.email);
        updatePersonName.setText(mcontact.name);
        updatePhone.setText(mcontact.phone);
        updateType.setText(mcontact.type);
        getActivity().setTitle(R.string.Editprofile);


        view.findViewById(R.id.buttoncancelupdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goDetails(mcontact);
            }
        });
        view.findViewById(R.id.buttonupdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(updateEmailAddress.getText().length()>0 && updatePersonName.getText().length()>0 && updatePhone.getText().length()>0 && updateType.getText().length()>0)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder
                            .setMessage(getString(R.string.confirm))
                            .setPositiveButton(getString(R.string.confirmation), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mcontact.name=updatePersonName.getText().toString();
                                    mcontact.phone=updatePhone.getText().toString();
                                    mcontact.email=updateEmailAddress.getText().toString();
                                    mcontact.type=updateType.getText().toString();


                                    CallAPI.updatedata(mcontact, new CallAPI.CallHandler() {
                                        @Override
                                        public void onSuccess(List<Contact> strings, String status, String message) {
                                            if(getActivity()!=null){
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                                        fragmentInterface.goDetails(mcontact);

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
                                                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
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
            else if(updatePersonName.getText().length()<=0 )
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
                else if(updateEmailAddress.getText().length()<=0)
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
                else if(updatePhone.getText().length()<=0)
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
                else if(updateType.getText().length()<=0)
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


        return view;
    }
}