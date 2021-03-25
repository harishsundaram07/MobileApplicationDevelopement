package com.example.harikrishnan_midterm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumDetailsFragment extends Fragment implements Commentfragmentinterface {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private  long mforumid;
    View view;
    FragmentInterface fragmentInterface;
    private String mtoken;
    private DataServices.Account maccount;
    ArrayList<DataServices.Comment> commentArrayList;
    DoForumdetails doForumdetails;
    RecyclerView recylercomment;
    LinearLayoutManager linearLayoutManager;
    Commentfragmentinterface commentfragmentinterface;
    CommentRecylcerAdapter commentRecylcerAdapter;
    TextView textViewtitle1;
    TextView textViewcearte1;
    TextView textViewdesc1;
    TextView textViewcommentsno;
    DataServices.Forum forum;
    EditText editTextTextMultiLinecomment;
    DoPostcomment doPostcomment;
    Dodeletecomment dodeletecomment;





    public ForumDetailsFragment() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(doForumdetails != null){
            doForumdetails.cancel(true);
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface )
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");
    }

    public static ForumDetailsFragment newInstance(DataServices.Account account, String token,long forumid, DataServices.Forum forum) {
        ForumDetailsFragment fragment = new ForumDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1,account);
        args.putString(ARG_PARAM2, token);
        args.putLong(ARG_PARAM3,forumid);
        args.putSerializable(ARG_PARAM4,forum);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            maccount = (DataServices.Account) getArguments().getSerializable(ARG_PARAM1);
            mtoken = getArguments().getString(ARG_PARAM2);
            mforumid=getArguments().getLong(ARG_PARAM3);
            this.forum= (DataServices.Forum) getArguments().getSerializable(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_forum_details, container, false);
        commentfragmentinterface=this;
        getActivity().getString(R.string.formtitlw);
        doForumdetails=new DoForumdetails();
        doForumdetails.execute(mtoken,String.valueOf(forum.getForumId()));
        getActivity().setTitle(forum.getTitle());
        view.findViewById(R.id.buttonpost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTextMultiLinecomment.getText().length()>0)
                {
                    doPostcomment=new DoPostcomment();
                    doPostcomment.execute(mtoken,String.valueOf(forum.getForumId()),editTextTextMultiLinecomment.getText().toString());
                }
            }
        });

        return view;
    }

    @Override
    public void deletecomment(String token, String valueOf, String valueOf1) {
        Log.d("TAG", "deletecomment: "+forum.getForumId());
        dodeletecomment=new Dodeletecomment();
        dodeletecomment.execute(token,valueOf,valueOf1);



    }


    class Dodeletecomment extends AsyncTask<String,Integer, Integer>
    {



        int status;
        int success=0;
        int failed=1;
        String message;

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recylercomment=view.findViewById(R.id.recylercomment);
            linearLayoutManager=new LinearLayoutManager(getContext());
            recylercomment.setLayoutManager(linearLayoutManager);

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);


            if (integer == success) {

                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                commentRecylcerAdapter = new CommentRecylcerAdapter(commentArrayList, commentfragmentinterface, maccount, mtoken,forum.getForumId());
                recylercomment.setAdapter(commentRecylcerAdapter);

                doForumdetails=new DoForumdetails();
                doForumdetails.execute(mtoken,String.valueOf(forum.getForumId()));


            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

        }



        @Override
        protected  Integer doInBackground(String... strings) {

                publishProgress();
                try {
                    DataServices.deleteComment(strings[0],Long.valueOf(strings[1]),Long.valueOf(strings[2]));
                    Log.d("TAG", "doInBackground: "+strings[1]);
                    status = success;
                    message = getString(R.string.commedntdeleted);
                } catch (DataServices.RequestException e) {
                    e.printStackTrace();
                    status = failed;
                    message = e.getMessage();
                }



            return status;
        }
    }

    class DoPostcomment extends AsyncTask<String,Integer, Integer>
    {



        int status;
        int success=0;
        int failed=1;
        String message;

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.findViewById(R.id.buttonpost).setEnabled(false);
            recylercomment=view.findViewById(R.id.recylercomment);
            linearLayoutManager=new LinearLayoutManager(getContext());
            recylercomment.setLayoutManager(linearLayoutManager);



        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            view.findViewById(R.id.buttonpost).setEnabled(true);

            if (integer == success) {

                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    commentRecylcerAdapter = new CommentRecylcerAdapter(commentArrayList, commentfragmentinterface, maccount, mtoken,forum.getForumId());
                    recylercomment.setAdapter(commentRecylcerAdapter);
                    editTextTextMultiLinecomment.setText("");
                    doForumdetails=new DoForumdetails();
                    doForumdetails.execute(mtoken,String.valueOf(forum.getForumId()));

                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }

        }



        @Override
        protected  Integer doInBackground(String... strings) {
            if(isCancelled()!=true){

                publishProgress();
                try {
                    commentArrayList.add(DataServices.createComment(strings[0],Long.valueOf(strings[1]),strings[2]));
                    Log.d("TAG", "doInBackground: "+forum.getForumId());
                    status = success;
                    message = getString(R.string.commedntadded);
                } catch (DataServices.RequestException e) {
                    e.printStackTrace();
                    status = failed;
                    message = e.getMessage();
                }
            }


            return status;
        }
    }

    class DoForumdetails extends AsyncTask<String,Integer, Integer>
    {



        int status;
        int success=0;
        int failed=1;
        String message;

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recylercomment=view.findViewById(R.id.recylercomment);
            linearLayoutManager=new LinearLayoutManager(getContext());
            recylercomment.setLayoutManager(linearLayoutManager);
             textViewtitle1=view.findViewById(R.id.textViewtitle1);
             textViewcearte1=view.findViewById(R.id.textViewcearte1);
             textViewdesc1=view.findViewById(R.id.textViewdesc1);
             textViewcommentsno=view.findViewById(R.id.textViewcommentsno);
            editTextTextMultiLinecomment=view.findViewById(R.id.editTextTextMultiLinecomment);
           // editTextTextMultiLinecomment.setVisibility(View.GONE);
           // view.findViewById(R.id.buttonpost).setVisibility(View.GONE);
            textViewcearte1.setText(forum.getCreatedBy().getName());
            textViewtitle1.setText(forum.getTitle());
            textViewdesc1.setText(forum.getDescription());







        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if(isCancelled()!=true){
                editTextTextMultiLinecomment.setVisibility(View.VISIBLE);
                view.findViewById(R.id.buttonpost).setVisibility(View.VISIBLE);
                if (integer == success) {

                    commentRecylcerAdapter = new CommentRecylcerAdapter(commentArrayList, commentfragmentinterface, maccount, mtoken,forum.getForumId());
                    recylercomment.setAdapter(commentRecylcerAdapter);

                    textViewcommentsno.setText(String.valueOf(commentArrayList.size()) +" "+ getActivity().getString(R.string.comments));

                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            }
        }



        @Override
        protected  Integer doInBackground(String... strings) {
            if(isCancelled()!=true){
                publishProgress();
                try {
                    commentArrayList = DataServices.getForumComments(strings[0], Long.valueOf(strings[1]));
                    status = success;
                } catch (DataServices.RequestException e) {
                    e.printStackTrace();
                    status = failed;
                    message = e.getMessage();
                }
            }


            return status;
        }
    }
}