// Assignment : Homework 03
//File Name : HW03
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09hw04;

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
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppListFragment extends Fragment   {


    FragmentInterface fragmentInterface;
    private static final String ARG_PARAM1 = "token";
    private static final String ARG_PARAM2 = "category";
    private String token;
    private String category;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView2;
    ApplistAdapter applistAdapter;
    View view;
    ProgressBar progressbarlist;


    public AppListFragment() {
    }



    public static AppListFragment newInstance(String token, String category) {
        AppListFragment fragment = new AppListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, token);
        args.putString(ARG_PARAM2, category);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString(ARG_PARAM1);
            category = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.fragment_app_list, container, false);



        new DoApplist().execute(token,category);

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






    //TODO AyncTask

    class DoApplist extends AsyncTask<String,Integer,Integer> implements AdapterInterface
    {

        List<DataServices.App> applistset;
        int success=100;
        int failed=400;
        String errormessage;


        @Override
        protected void onPreExecute() {
            getActivity().setTitle(category);
            recyclerView2=view.findViewById(R.id.recyclerView2);
            recyclerView2.setHasFixedSize(true);
            linearLayoutManager=new LinearLayoutManager(getContext());
            recyclerView2.setLayoutManager(linearLayoutManager);
            progressbarlist=view.findViewById(R.id.progressBarlist);
            progressbarlist.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressbarlist.setVisibility(View.GONE);

            if(integer==success)
            {
                applistAdapter=new ApplistAdapter(applistset,this);
                recyclerView2.setAdapter(applistAdapter);
            }
            else
                Toast.makeText(getActivity(), errormessage, Toast.LENGTH_SHORT).show();


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressbarlist.isIndeterminate();
        }

        @Override
        protected Integer doInBackground(String... strings) {

            try {
                publishProgress();
                applistset= DataServices.getAppsByCategory(strings[0],strings[1]);
                return success;
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                errormessage=e.getMessage();
                return failed;
            }

        }


        @Override
        public void OnclickListeneradapter(int position) {
            fragmentInterface.goAppDetails(applistset.get(position));
        }

    }

}