// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw07;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserListFragment extends Fragment implements FunctionInterface {

    private static final String ARG_PARAM1 = "param1";
    private static final String TAG = "TAG";
    View view;

    public String muuid;
    ArrayList<User> userArrayList=new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    UserRecyclerAdapter userRecyclerAdapter;
    FunctionInterface functionInterface;
    FragmentInterface fragmentInterface;
    TextView textViewnouser;
    ImageView imageViewlistdp;
    User muser;
    TextView myprofile;

     FirebaseStorage storagedp1;


    public UserListFragment() {
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
    public static UserListFragment newInstance(String uuid) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, uuid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            muuid =  getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_user_list, container, false);
        getActivity().setTitle(view.getContext().getString(R.string.user).toUpperCase()+"'s List");
        functionInterface= this;

        recyclerView=view.findViewById(R.id.recyclerView2);
        linearLayoutManager=new LinearLayoutManager(getContext());
        imageViewlistdp=view.findViewById(R.id.imageViewlistdp);
        recyclerView.setLayoutManager(linearLayoutManager);
        textViewnouser=view.findViewById(R.id.textViewnouser);
        myprofile=view.findViewById(R.id.myprofile);
        textViewnouser.setVisibility(View.GONE);
        storagedp1=FirebaseStorage.getInstance();

        CallFirebase.getusers(getActivity(), userArrayList, new CallFirebase.CallHandler() {
            @Override
            public void onSuccess(Object o, String message) {
                userArrayList= (ArrayList<User>) o;

                for(int i=0;i<userArrayList.size();i++)
                {
                    if(userArrayList.get(i).getUuid().equals(muuid))
                    {
                        setmuser(userArrayList.get(i));
                        userArrayList.remove(i);
                        if(muser.getProfilepic()!=null && muser.getProfilepic().length()>0 ){
                            CallFirebase.setpicture(getActivity(), muser.getProfilepic(), new CallFirebase.CallHandler() {
                                @Override
                                public void onSuccess(Object o, String message) {
                                    StorageReference pathReference = (StorageReference) o;
                                    Glide.with(view.getContext())
                                            .load(pathReference)
                                            .into(imageViewlistdp);
                                }

                                @Override
                                public void onFailure(String message) {

                                }
                            });
                        }
                    }
                }
                if(userArrayList.size()<=0)
                    textViewnouser.setVisibility(View.VISIBLE);
                else
                    textViewnouser.setVisibility(View.GONE);
                setpictures();
                myprofile.setText(muser.getName());
                userArrayList.remove(muser);
                userRecyclerAdapter=new UserRecyclerAdapter(userArrayList,functionInterface,muuid);
                recyclerView.setAdapter(userRecyclerAdapter);
                userRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

            }
        });

        view.findViewById(R.id.imageViewlistdp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goDetails(muser,muser);
            }
        });
        view.findViewById(R.id.myprofile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goDetails(muser,muser);
            }
        });
        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInClient googleSignInclient2 = GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);

                if(googleSignInclient2!=null)
                {
                    googleSignInclient2.signOut().addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            if(view.getContext()!=null)
                                Toast.makeText(view.getContext(), view.getContext().getString(R.string.logged_out), Toast.LENGTH_SHORT).show();
                            fragmentInterface.goHome();

                        }
                    }).addOnFailureListener(getActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if(view.getContext()!=null)
                                Toast.makeText(view.getContext(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();

                        }
                    });
            }
                else
                    fragmentInterface.goHome();


            }});


        view.findViewById(R.id.buttonaddImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goaddphoto(muser);
            }
        });

        return view;
    }

    private void setmuser(User user) {
        muser=user;
    }

    private void setpictures() {

        for(User u:userArrayList)
        {
           if(u.getProfilepic()!=null && u.getProfilepic().length()>0) {

            }
        }
    }



    //Function methods

    @Override
    public void gotoDetails(User user) {
            fragmentInterface.goDetails(user,muser);
    }


}