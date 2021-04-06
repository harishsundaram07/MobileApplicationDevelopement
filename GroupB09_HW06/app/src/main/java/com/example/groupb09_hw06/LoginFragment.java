// Assignment : HW 06
//File Name : GroupB09_HW06
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_hw06;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginFragment extends Fragment {

    private static final String TAG = "TAG";
    FragmentInterface fragmentInterface;
    View view;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    private FirebaseAuth mAuth;
    SignInButton signInGoogle;
     private GoogleSignInClient googleSignInclient;

    private FirebaseFirestore tempdb;


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
        view= inflater.inflate(R.layout.fragment_login, container, false);
        editTextTextEmailAddress=view.findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword=view.findViewById(R.id.editTextTextPassword);
        getActivity().setTitle(view.getContext().getString(R.string.login));







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
                                        Toast.makeText(getActivity(), view.getContext().getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                                        fragmentInterface.goList(mAuth.getUid());
                                    }
                                    else
                                    {
                                        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                                        builder
                                                .setMessage(task.getException().getMessage())
                                                .setPositiveButton(view.getContext().getString(R.string.OK), new DialogInterface.OnClickListener() {
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
                            .setMessage(view.getContext().getString(R.string.enter_email_id))
                            .setPositiveButton(view.getContext().getString(R.string.OK), new DialogInterface.OnClickListener() {
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
                            .setMessage(view.getContext().getString(R.string.enter_password))
                            .setPositiveButton(view.getContext().getString(R.string.OK), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
            }
        });

        signInGoogle=view.findViewById(R.id.signInGoogle);

        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getActivity().getString(R.string.clientid)).requestEmail().build();
        googleSignInclient= GoogleSignIn.getClient(getActivity(),googleSignInOptions);

        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=googleSignInclient.getSignInIntent();
                startActivityForResult(intent,100);
               }
        });

        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100)
        {
            Task<GoogleSignInAccount> googleSignInAccountTask=GoogleSignIn.getSignedInAccountFromIntent(data);
            if(googleSignInAccountTask.isSuccessful())
            {
                try {
                    GoogleSignInAccount googleSignInAccount=googleSignInAccountTask.getResult(ApiException.class);


                    if(googleSignInAccount!=null)
                    {
                        AuthCredential authCredential= GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(),null);
                        mAuth=FirebaseAuth.getInstance();
                        mAuth.signInWithCredential(authCredential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    User user=new User();
                                    user.setName(mAuth.getCurrentUser().getDisplayName().toString());
                                    user.setEmailid(mAuth.getCurrentUser().getEmail().toString());
                                    user.setUuid(mAuth.getUid());
                                    tempdb= FirebaseFirestore.getInstance();
                                    tempdb.collection(view.getContext().getString(R.string.user)).document(mAuth.getUid()).set(user).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(view.getContext(), view.getContext().getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(getActivity(), new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    Toast.makeText(getActivity(), view.getContext().getString(R.string.welcome), Toast.LENGTH_SHORT).show();

                                    fragmentInterface.goList(mAuth.getUid());

                                }
                                else
                                {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                                    builder
                                            .setMessage(task.getException().getMessage())
                                            .setPositiveButton(view.getContext().getString(R.string.OK), new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                }
                                            });
                                    builder.create().show();
                                }
                            }
                        });
                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
            else
            {
             //   Log.d(TAG, "onActivityResult: "+googleSignInAccountTask.getResult().toString());
                Toast.makeText(getActivity(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();

            }
        }
    }


}