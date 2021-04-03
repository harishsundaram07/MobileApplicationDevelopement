package com.example.groupb09_hw06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;


public class DetailsForumFragment extends Fragment implements Commentfragmentinterface{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    View view;
    FragmentInterface fragmentInterface;
    RecyclerView recylercomment;
    LinearLayoutManager linearLayoutManager;
    TextView textViewtitle1;
    TextView textViewcearte1;
    TextView textViewdesc1;
    TextView textViewcommentsno;
    EditText editTextTextMultiLinecomment;
    Commentfragmentinterface commentfragmentinterface;
    CommentRecylcerAdapter commentRecylcerAdapter;
    ArrayList<Comment> commentArrayList=new ArrayList<>();
    private FirebaseFirestore mdb5;
    private FirebaseFirestore mdb6;

    TextView textViewnocomments;



    private String muuid;
    private Forum mforum;
    private User muser;

    public DetailsForumFragment() {

    }


    public static DetailsForumFragment newInstance(String muuid, Forum forum,User user) {
        DetailsForumFragment fragment = new DetailsForumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, muuid);
        args.putSerializable(ARG_PARAM2, forum);
        args.putSerializable(ARG_PARAM3, user);
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
            muuid = getArguments().getString(ARG_PARAM1);
            mforum = (Forum) getArguments().getSerializable(ARG_PARAM2);
            muser=(User) getArguments().getSerializable(ARG_PARAM3);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_details_forum, container, false);
        getActivity().setTitle(mforum.getTitle());
        recylercomment=view.findViewById(R.id.recylercomment);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recylercomment.setLayoutManager(linearLayoutManager);
        textViewtitle1=view.findViewById(R.id.textViewtitle1);
        textViewcearte1=view.findViewById(R.id.textViewcearte1);
        textViewdesc1=view.findViewById(R.id.textViewdesc1);
        textViewcommentsno=view.findViewById(R.id.textViewcommentsno);
        textViewtitle1.setText(mforum.getTitle());
        textViewcearte1.setText(mforum.getUsername());
        textViewdesc1.setText(mforum.getDescription());
        commentfragmentinterface=this;
        editTextTextMultiLinecomment=view.findViewById(R.id.editTextTextMultiLinecomment);
        textViewnocomments=view.findViewById(R.id.textViewnocomments);



        getcomment();

        view.findViewById(R.id.buttonpost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    postcomment();

            }
        });



        return view;
    }

    private void getcomment() {
        Comment comments;
        mdb6=FirebaseFirestore.getInstance();
        mdb6.collection(getString(R.string.forum)).document(mforum.getForumid()).collection(getString(R.string.comment)).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                commentArrayList.clear();
                if(value.size()>0) {
                    textViewnocomments.setVisibility(View.GONE);
                    for (QueryDocumentSnapshot document : value) {
                        {
                            Comment comment = document.toObject(Comment.class);
                            commentArrayList.add(comment);
                        }
                        textViewcommentsno.setText(commentArrayList.size()+" "+getString(R.string.comments));
                        commentRecylcerAdapter = new CommentRecylcerAdapter(commentArrayList,muuid,mforum.getForumid(), commentfragmentinterface);
                        recylercomment.setAdapter(commentRecylcerAdapter);
                    }
                }
                else
                {
                    textViewnocomments.setVisibility(View.VISIBLE);
                    commentRecylcerAdapter = new CommentRecylcerAdapter(commentArrayList,muuid,mforum.getForumid(), commentfragmentinterface);
                    recylercomment.setAdapter(commentRecylcerAdapter);

                }
            }
        });

    }



    private void postcomment() {
        UUID uuid=UUID.randomUUID();
        Comment comment=new Comment();
        comment.setComment(editTextTextMultiLinecomment.getText().toString());
        comment.setCommentid(uuid.toString());
        comment.setDate(new Date().toString());
        comment.setForummid(mforum.getForumid());
        comment.setName(muser.getName());
        comment.setUuid(muser.getUuid());
        mdb5=FirebaseFirestore.getInstance();
        mdb5.collection(getString(R.string.forum)).document(mforum.getForumid()).collection(getString(R.string.comment)).document(uuid.toString()).set(comment).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), getString(R.string.commedntadded), Toast.LENGTH_SHORT).show();
                editTextTextMultiLinecomment.setText("");
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), getString(R.string.errormessage), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void deletecomment(String mcommentId) {
        mdb5=FirebaseFirestore.getInstance();
        mdb5.collection(getString(R.string.forum)).document(mforum.getForumid()).collection(getString(R.string.comment)).document(mcommentId).delete().addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(), getString(R.string.commedntdeleted), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), getString(R.string.errormessage), Toast.LENGTH_SHORT).show();
            }
        });



}

}