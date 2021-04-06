// Assignment : InClass 08
//File Name : GroupB09_InClass08
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_hw06;

import android.content.Context;
import android.media.MediaDrm;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ForumListFragment extends Fragment implements FunctionInterface{


    private static final String ARG_PARAM1 = "param1";
    View view;

    public String muuid;
    public User muser;
    Forum mforum;
    private FirebaseFirestore mdb2;
    private FirebaseFirestore mdb3;
    ArrayList<Forum> forumList=new ArrayList<>();
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ForumRecyclerAdapter forumRecyclerAdapter;
    FunctionInterface functionInterface;
    FragmentInterface fragmentInterface;
    TextView textViewnoforum;
    private GoogleSignInClient googleSignInclient1;




    public ForumListFragment() {
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface)
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");
    }


    public static ForumListFragment newInstance(String uuid) {
        ForumListFragment fragment = new ForumListFragment();
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
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_forum_list, container, false);
        getActivity().setTitle(view.getContext().getString(R.string.formtitl));
        functionInterface=  this;
        recyclerView=view.findViewById(R.id.recyclerView2);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        textViewnoforum=view.findViewById(R.id.textViewnoforum);
        textViewnoforum.setVisibility(View.GONE);




        mdb3=FirebaseFirestore.getInstance();
        mdb3.collection(view.getContext().getString(R.string.forum)).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                forumList.clear();
                if(value.size()>0){

                    for(QueryDocumentSnapshot document:value)
                    {
                         mforum=new Forum();
                         mforum=document.toObject(Forum.class);
                        forumList.add(mforum);
                    }
                    forumRecyclerAdapter=new ForumRecyclerAdapter(forumList,functionInterface,muuid);
                    recyclerView.setAdapter(forumRecyclerAdapter);
                    forumRecyclerAdapter.notifyDataSetChanged();
                }
                else
                {
                    textViewnoforum.setVisibility(View.VISIBLE);
                    forumRecyclerAdapter=new ForumRecyclerAdapter(forumList,functionInterface,muuid);
                    recyclerView.setAdapter(forumRecyclerAdapter);
                    forumRecyclerAdapter.notifyDataSetChanged();
                }

            }

        });
        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                googleSignInclient1= GoogleSignIn.getClient(getActivity(), GoogleSignInOptions.DEFAULT_SIGN_IN);

                if(googleSignInclient1!=null)
                {
                    googleSignInclient1.signOut().addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
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
            }
        });
        view.findViewById(R.id.buttonAddForum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdb2=FirebaseFirestore.getInstance();
                mdb2.collection(view.getContext().getString(R.string.user)).document(muuid).get().addOnSuccessListener(getActivity(), new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        muser=documentSnapshot.toObject(User.class);
                        fragmentInterface.gonewforum(muser);

                    }
                }).addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        return view;
    }

    private void storedata(ArrayList<Comment> comments) {
        mforum.setComments(comments);
    }

    @Override
    public void deleteforum(String mforumid) {
        mdb3=FirebaseFirestore.getInstance();
        mdb3.collection(view.getContext().getString(R.string.forum)).document(mforumid).delete().addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(view.getContext(), view.getContext().getString(R.string.forumdeleted), Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(view.getContext(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void likedby(Forum forum, String muuid, boolean like) {
        if(like)
            forum.getLikedby().add(muuid);
        else
            forum.getLikedby().remove(muuid);
        mdb3=FirebaseFirestore.getInstance();
        String likedby="likedby";
        mdb3.collection(view.getContext().getString(R.string.forum)).document(forum.forumid).update(likedby,forum.getLikedby()).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(view.getContext(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void gotoDetails(String muuid, Forum forum) {
        mdb2=FirebaseFirestore.getInstance();
        mdb2.collection(view.getContext().getString(R.string.user)).document(muuid).get().addOnSuccessListener(getActivity(), new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                muser=documentSnapshot.toObject(User.class);
                fragmentInterface.goDetails(muuid,muser,forum);

            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(view.getContext(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();
            }
        });

    }




}