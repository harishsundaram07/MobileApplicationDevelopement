// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_hw07;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class AddPhotoFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private User muser;
    private String mParam2;
    Uri imageuri;
    FragmentInterface fragmentInterface;


    View view;
    ImageView addnewimage;
    EditText edittextcaption;
    String imageid;

    public AddPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface)
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");
    }
    public static AddPhotoFragment newInstance(User user, String param2) {
        AddPhotoFragment fragment = new AddPhotoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            muser = (User) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_add_photo, container, false);
        addnewimage=view.findViewById(R.id.addnewimage);
        view.findViewById(R.id.textViewy).setVisibility(View.VISIBLE);
        edittextcaption=view.findViewById(R.id.editTextTextMultiLinecaption);
        getActivity().setTitle(getString(R.string.add_photos));
        addnewimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosepicture();
            }
        });
        view.findViewById(R.id.buttonuploadimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (imageuri!=null){
                    uploadpicture();
                }
               else
                   Toast.makeText(getActivity(), getString(R.string.uploadimage), Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.buttoncancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goList(muser.getUuid());
            }
        });


        return view;
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
            addnewimage.setImageURI(imageuri);

        }
    }

    private void uploadpicture() {

        final ProgressDialog progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle(getString(R.string.uploadprogress));
        progressDialog.show();;
        imageid= UUID.randomUUID().toString();
        ArrayList<Photos> temp=muser.getPhotosList();
        Photos photos=new Photos();
        photos.setCaption(edittextcaption.getText().length()>0?edittextcaption.getText().toString():"");
        photos.setCommentList(new ArrayList<>());
        photos.setDate(new Date().toString());
        photos.setLikedby(new ArrayList<>());
        photos.setPhotoid(imageid);
        photos.setName(muser.getName());
        photos.setUuid(muser.getUuid());
        muser.setPhotosList(temp);
        CallFirebase.uploadimage(imageuri,imageid, new CallFirebase.CallHandler() {
            @Override
            public void onSuccess(Object o, String message) {
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), getString(R.string.imagesuccess), Toast.LENGTH_SHORT).show();

                CallFirebase.addimage(getActivity(),muser,photos, new CallFirebase.CallHandler() {
                    @Override
                    public void onSuccess(Object o, String message) {
                        progressDialog.dismiss();
                        Toast.makeText(view.getContext(), getString(R.string.imagesuccess), Toast.LENGTH_SHORT).show();
                        fragmentInterface.goDetails(muser, muser);

                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(view.getContext(), getString(R.string.imagefailed), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
                fragmentInterface.goDetails(muser, muser);
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(view.getContext(), getString(R.string.imagefailed), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }
}