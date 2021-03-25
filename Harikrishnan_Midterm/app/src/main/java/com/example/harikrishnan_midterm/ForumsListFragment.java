package com.example.harikrishnan_midterm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForumsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForumsListFragment<DogetallForum> extends Fragment  implements FunctionInterface{

    View view;
    FragmentInterface fragmentInterface;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<DataServices.Forum> forumArrayList;
    DoLike doLike;
    Dodelete dodelete;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ForumRecyclerAdapter forumRecyclerAdapter;
    FunctionInterface functionInterface;





    private DataServices.Account maccount;
    private String mtoken;
    DoGetallForums doGetallForums;

    public ForumsListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        if(doGetallForums != null){
            doGetallForums.cancel(true);
        }
    }

    public static ForumsListFragment newInstance(DataServices.Account account, String token) {
        ForumsListFragment fragment = new ForumsListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, account);
        args.putString(ARG_PARAM2, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           maccount = (DataServices.Account) getArguments().getSerializable(ARG_PARAM1);
            mtoken = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_forums_list, container, false);
        view.findViewById(R.id.buttonAddForum).setVisibility(View.GONE);
        view.findViewById(R.id.buttonLogout).setVisibility(View.GONE);
        doGetallForums=new DoGetallForums();
        doGetallForums.execute(mtoken);
        getActivity().setTitle(getString(R.string.formtitl));
        functionInterface=this;
        view.findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goHome();
            }
        });
        view.findViewById(R.id.buttonAddForum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.gonewforum(mtoken,maccount);
            }
        });
        return view;
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
    public void likeby(String token, String forumId,String flag) {
        doLike=new DoLike();
         doLike.execute(token,forumId,flag);

    }

    @Override
    public void deleteforum(String token, String forumId) {
        dodelete=new Dodelete();
        dodelete.execute(token,forumId);

    }

    @Override
    public void goforum(int adapterPosition) {
        fragmentInterface.gotodetails(maccount,mtoken,forumArrayList.get(adapterPosition).getForumId(),forumArrayList.get(adapterPosition));
    }




    class DoGetallForums extends AsyncTask<String,Integer, Integer>
    {
        DataServices.AuthResponse authResponse;
        int status;
        int success=0;
        int failed=1;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView=view.findViewById(R.id.recyclerView2);
            linearLayoutManager=new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            view.findViewById(R.id.buttonAddForum).setVisibility(View.VISIBLE);
            view.findViewById(R.id.buttonLogout).setVisibility(View.VISIBLE);


            if (doGetallForums.isCancelled()!=true) {
                if (integer==success)
                {
                    forumRecyclerAdapter=new ForumRecyclerAdapter(forumArrayList,maccount,mtoken,functionInterface);
                    recyclerView.setAdapter(forumRecyclerAdapter);
                    forumRecyclerAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

                }
            }
        }

        @Override
        protected Integer doInBackground(String... strings) {
            if (doGetallForums.isCancelled()!=true){
                publishProgress();
                try {
                    forumArrayList = DataServices.getAllForums(mtoken);
                    status = success;

                } catch (DataServices.RequestException e) {
                    status = failed;
                    e.printStackTrace();
                    message = e.getMessage();
                }
            }
            return status;
        }
    }

    class DoLike extends AsyncTask<String,Integer,Integer>{

        int status;
        int success=0;
        int failed=1;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.findViewById(R.id.buttonAddForum).setEnabled(false);
            view.findViewById(R.id.buttonLogout).setEnabled(false);
        }

        @Override
        protected Integer doInBackground(String... strings) {

            try {
                if(strings[2].equalsIgnoreCase("1"))
                DataServices.likeForum(strings[0],Long.valueOf(strings[1]));
                if(strings[2].equalsIgnoreCase("0"))
                    DataServices.unLikeForum(strings[0],Long.valueOf(strings[1]));
                status = success;
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                status = failed;
                message=e.getMessage();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            view.findViewById(R.id.buttonAddForum).setEnabled(true);
            view.findViewById(R.id.buttonLogout).setEnabled(true);

                if (integer==success)
                {
                    doGetallForums=new DoGetallForums();
                    doGetallForums.execute(mtoken);
                }
                else
                {
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

                }

        }


    }

    class Dodelete extends AsyncTask<String,Integer,Integer>{

        int status;
        int success=0;
        int failed=1;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.findViewById(R.id.buttonAddForum).setEnabled(false);
            view.findViewById(R.id.buttonLogout).setEnabled(false);
        }

        @Override
        protected Integer doInBackground(String... strings) {

            try {

                DataServices.deleteForum(strings[0],Long.valueOf(strings[1]));
                status = success;
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                status = failed;
                message=e.getMessage();
            }
            return status;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            view.findViewById(R.id.buttonAddForum).setEnabled(true);
            view.findViewById(R.id.buttonLogout).setEnabled(true);
            view.findViewById(R.id.buttonAddForum).setVisibility(View.VISIBLE);
            view.findViewById(R.id.buttonLogout).setVisibility(View.VISIBLE);

            if (integer==success)
            {
                doGetallForums=new DoGetallForums();
                doGetallForums.execute(mtoken);
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

            }

        }
}}