// Assignment : InClass 08
//File Name : GroupB09_InClass08
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_hw06;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterFragment extends Fragment {

    View view;
    FragmentInterface fragmentInterface;
    EditText editTextTextPersonName;
    EditText editTextTextEmailAddress2;
    EditText editTextTextPassword2;
    private FirebaseAuth mAuth1;
    private  FirebaseFirestore mdb1;


    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentInterface.goHome();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface)
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_register, container, false);
        editTextTextPersonName=view.findViewById(R.id.editTextTextPersonName);
        editTextTextEmailAddress2=view.findViewById(R.id.editTextTextEmailAddress2);
        editTextTextPassword2=view.findViewById(R.id.editTextTextPassword2);
        getActivity().setTitle(getString(R.string.create_a_new_account));
        String uuid;
        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTextPersonName.getText().length()>0 && editTextTextEmailAddress2.getText().length()>0 && editTextTextPassword2.getText().length()>0)
                {
                    mAuth1=FirebaseAuth.getInstance();
                    User user=new User();
                    user.setName(editTextTextPersonName.getText().toString());
                    user.setEmailid(editTextTextEmailAddress2.getText().toString());

                    mAuth1.createUserWithEmailAndPassword(editTextTextEmailAddress2.getText().toString(),editTextTextPassword2.getText().toString())
                            .addOnSuccessListener(getActivity(), new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    user.setUuid(mAuth1.getUid());
                                    mdb1=FirebaseFirestore.getInstance();
                                    mdb1.collection(getString(R.string.user)).document(mAuth1.getUid()).set(user).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(getContext(), getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(getActivity(), new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    fragmentInterface.goList(mAuth1.getUid());
                                }
                            }).addOnFailureListener(getActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                            builder
                                    .setMessage(e.getMessage())
                                    .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                            builder.create().show();
                        }
                    });


                }
                else if(editTextTextEmailAddress2.getText().length()<=0){
                    AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                    builder
                            .setMessage(getString(R.string.enter_email_id))
                            .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();

                }else if(editTextTextPassword2.getText().length()<=0)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                    builder
                            .setMessage(R.string.enter_password)
                            .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
                else if(editTextTextPersonName.getText().length()<=0)
                {
                    AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                    builder
                            .setMessage(R.string.enter_name)
                            .setPositiveButton(getString(R.string.OK), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
            }
        });
        view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goHome();
            }
        });
        return view;

    }
}