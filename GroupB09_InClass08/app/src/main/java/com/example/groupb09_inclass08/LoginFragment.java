// Assignment : InClass 08
//File Name : GroupB09_InClass08
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_inclass08;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {

    private static final String TAG = "TAG";
    FragmentInterface fragmentInterface;
    View view;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    private FirebaseAuth mAuth;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface )
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_login, container, false);
        editTextTextEmailAddress=view.findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword=view.findViewById(R.id.editTextTextPassword);
        getActivity().setTitle(getString(R.string.login));

        view.findViewById(R.id.TextCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goRegister();
            }
        });
        view.findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTextEmailAddress.getText().length()>0 && editTextTextPassword.getText().length()>0)
                {
                    mAuth=FirebaseAuth.getInstance();
                    mAuth.signInWithEmailAndPassword(editTextTextEmailAddress.getText().toString(),editTextTextPassword.getText().toString())
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(getActivity(), getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                                        fragmentInterface.goList(mAuth.getUid());
                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                                        builder
                                                .setMessage(task.getException().getMessage())
                                                .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                    }
                                                });
                                        builder.create().show();
                                    }
                                }
                            });
                }
        else if(editTextTextEmailAddress.getText().length()<=0)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder
                            .setMessage(getString(R.string.enter_email_id))
                            .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
                else if(editTextTextPassword.getText().length()<=0)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                    builder
                            .setMessage(getString(R.string.enter_password))
                            .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
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