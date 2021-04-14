// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI





package com.example.groupb09_hw07;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static com.example.groupb09_hw07.CallFirebase.mAuth1;


public class RegisterFragment extends Fragment {

    View view;
    FragmentInterface fragmentInterface;
    EditText editTextTextPersonName;
    EditText editTextTextEmailAddress2;
    EditText editTextTextPassword2;


    private static final String ARG_PARAM1 = "param1";
    User muser;
    private GoogleSignInClient googleSignInclient1;
    public Uri imageuri;
    ImageView imageViewdpreg;

    String imageid;

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleSignInclient1= GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);

        if(googleSignInclient1!=null)
        {
            googleSignInclient1.signOut().addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                }
            }).addOnFailureListener(getActivity(), new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if(view.getContext()!=null)
                        Toast.makeText(view.getContext(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            muser = (User) getArguments().getSerializable(ARG_PARAM1);
        }
    }
    public static RegisterFragment newInstance(User user) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_register, container, false);
        editTextTextPersonName=view.findViewById(R.id.editTextTextPersonName);
        editTextTextEmailAddress2=view.findViewById(R.id.editTextTextEmailAddress2);
        editTextTextPassword2=view.findViewById(R.id.editTextTextPassword2);
        imageViewdpreg=view.findViewById(R.id.imageViewdpreg);
        getActivity().setTitle(view.getContext().getString(R.string.create_a_new_account));
        if(muser!=null)
        {
            editTextTextPersonName.setText(muser.getName());
            editTextTextEmailAddress2.setText(muser.getEmailid());
            editTextTextEmailAddress2.setEnabled(false);

        }
        String uuid;
        view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTextPersonName.getText().length()>0 && editTextTextEmailAddress2.getText().length()>0 && editTextTextPassword2.getText().length()>0)
                {

                    CallFirebase.setauthuser(getActivity(),editTextTextEmailAddress2.getText().toString(), editTextTextPassword2.getText().toString(), new CallFirebase.CallHandler() {
                        @Override
                        public void onSuccess(Object o, String message) {
                            User user=new User();
                            user.setName(editTextTextPersonName.getText().toString());
                            user.setEmailid(editTextTextEmailAddress2.getText().toString());
                          user.setPhotosList(new ArrayList<>());
                            user.setUuid(o.toString());
                            user.setProfilepic(imageid);
                            CallFirebase.setuser(getActivity(), user, new CallFirebase.CallHandler() {
                                @Override
                                public void onSuccess(Object o, String message) {
                                    fragmentInterface.goList(o.toString());
                                    Toast.makeText(getContext(), view.getContext().getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(String message) {
                                    Toast.makeText(getContext(), getString(R.string.errormessage), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                        @Override
                        public void onFailure(String message) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                            builder
                                    .setMessage(message)
                                    .setPositiveButton(view.getContext().getString(R.string.OK), new DialogInterface.OnClickListener() {
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
                            .setMessage(view.getContext().getString(R.string.enter_email_id))
                            .setPositiveButton(view.getContext().getString(R.string.OK), new DialogInterface.OnClickListener() {
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
                            .setPositiveButton(view.getContext().getString(R.string.OK), new DialogInterface.OnClickListener() {
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
                            .setPositiveButton(view.getContext().getString(R.string.OK), new DialogInterface.OnClickListener() {
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


        view .findViewById(R.id.buttonupload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosepicture();
            }
        });


        return view;

    }

    private void uploadpicture() {

        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.uploadprogress));
        progressDialog.show();;
        imageid=UUID.randomUUID().toString();
        CallFirebase.uploadimage(imageuri,imageid, new CallFirebase.CallHandler() {
            @Override
            public void onSuccess(Object o, String message) {
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), getString(R.string.imagesuccess), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(view.getContext(), getString(R.string.imagefailed), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void choosepicture() {

        Intent intent=  new Intent();
        intent.setType(getString(R.string.image)+"*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && resultCode==RESULT_OK && data!=null)
        {
            imageuri=data.getData();
            imageViewdpreg.setImageURI(imageuri);
            uploadpicture();

        }
    }
}