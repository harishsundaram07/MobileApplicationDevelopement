// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;


public class CommentsFragment extends Fragment implements  Commentfragmentinterface{


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";


    private Photos mphoto;
    private User muser;
    private User mloggedinuser;
    FragmentInterface fragmentInterface;
    View view;
    RecyclerView recylercomments;
    LinearLayoutManager linearLayoutManager;
    ImageView imageViewmainpiccomments;
    TextView textViewprofilenamescomments;
    TextView captioncomments;
    TextView commentscount;
    CommentRecylcerAdapter commentRecylcerAdapter;
    Commentfragmentinterface commentfragmentinterface;
    EditText editTextTextMultiLine;
    Button buttonpost;

    public CommentsFragment() {
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


    public static CommentsFragment newInstance(Photos photo, User user, User loggedinuser) {
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, photo);
        args.putSerializable(ARG_PARAM2, user);
        args.putSerializable(ARG_PARAM3, loggedinuser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mphoto = (Photos) getArguments().getSerializable(ARG_PARAM1);
            muser = (User) getArguments().getSerializable(ARG_PARAM2);
            mloggedinuser = (User) getArguments().getSerializable(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_comments, container, false);
        recylercomments=view.findViewById(R.id.recylercomments);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recylercomments.setLayoutManager(linearLayoutManager);
        view.findViewById(R.id.textViewnocomments).setVisibility(View.GONE);
        commentscount=view.findViewById(R.id.commentscount);
        getActivity().setTitle(getString(R.string.comments).toUpperCase());
        commentfragmentinterface=this;
        captioncomments=view.findViewById(R.id.captioncomments);
        textViewprofilenamescomments=view.findViewById(R.id.textViewprofilenamescomments);
        editTextTextMultiLine=view.findViewById(R.id.editTextTextMultiLine);
        buttonpost=view.findViewById(R.id.buttonpost);

        captioncomments.setText(mphoto.getCaption());
        textViewprofilenamescomments.setText(mphoto.getName());

        getcomments();

        buttonpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTextMultiLine.getText()!=null && editTextTextMultiLine.getText().length()>0)
                    postcomments();
                else
                    Toast.makeText(getContext(), view.getContext().getString(R.string.commedntadd), Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

    private void postcomments() {
        Comment comment=new Comment();
        comment.setComment(editTextTextMultiLine.getText().toString());
        comment.setCommentid(UUID.randomUUID().toString());
        comment.setDate(new Date().toString());
        comment.setName(mloggedinuser.getName());
        comment.setphotoid(mphoto.getPhotoid());
        comment.setUuid(mloggedinuser.getUuid());
        CallFirebase.addcomment(getActivity(), muser, mphoto, comment, new CallFirebase.CallHandler() {
            @Override
            public void onSuccess(Object o, String message) {
                Toast.makeText(getActivity(), view.getContext().getString(R.string.commedntadded), Toast.LENGTH_SHORT).show();
                getcomments();
                editTextTextMultiLine.setText("");
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getActivity(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();
                editTextTextMultiLine.setText("");

            }
        });



    }

    private void getcomments() {

        CallFirebase.getcommentspic(getActivity(),muser, mphoto, new CallFirebase.CallHandler() {
            @Override
            public void onSuccess(Object o, String message) {
                ArrayList<Comment> commentArrayList= new ArrayList<>();
                commentArrayList.clear();
                commentArrayList= (ArrayList<Comment>) o;
                if(commentArrayList.size()>0)
                    view.findViewById(R.id.textViewnocomments).setVisibility(View.GONE);
                else
                    view.findViewById(R.id.textViewnocomments).setVisibility(View.VISIBLE);
                setphotos(commentArrayList);
                Collections.sort(commentArrayList, new Comparator<Comment>() {
                    @Override
                    public int compare(Comment o1, Comment o2) {
                        return (-1)*(o1.getDate().compareTo(o2.getDate()));
                    }
                });
                commentscount.setText(commentArrayList.size()+" "+view.getContext().getString(R.string.comments));
                commentRecylcerAdapter=new CommentRecylcerAdapter(commentArrayList,muser,mloggedinuser,mphoto,commentfragmentinterface);
                recylercomments.setAdapter(commentRecylcerAdapter);
                commentRecylcerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void setphotos(ArrayList<Comment> commentArrayList) {
        mphoto.setCommentList(commentArrayList);
    }

    @Override
    public void deletecomment(Comment comment, User mloggedinuser, User muser, Photos photos) {
        CallFirebase.deletecomment(getActivity(), mphoto, muser, comment, new CallFirebase.CallHandler() {
            @Override
            public void onSuccess(Object o, String message) {
                Toast.makeText(getActivity(), view.getContext().getString(R.string.commedntdeleted), Toast.LENGTH_SHORT).show();
                getcomments();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getActivity(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();
            }
        });

    }
}