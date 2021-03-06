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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment{



    private static final String ARG_PARAM1 = "param1";
    FragmentInterface fragmentInterface;
    String token;
    RecylcerViewCategoriesAdapter adapter;
    TextView textViewWelcome;
    Button buttonlogout;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView1;
    ProgressBar progressBarCAtegories;


    View view;
    public CategoriesFragment() {
        // Required empty public constructor
    }


    public static CategoriesFragment newInstance(String token) {
        CategoriesFragment fragment = new CategoriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1,token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token=getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_categories, container, false);

        new DoCategories().execute(token);

        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.home();
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





    //TODO AsyncTask

    class DoCategories extends AsyncTask<String,Integer,Integer>  implements AdapterInterface
    {

        int success=100;
        int failed=400;
        String errormessage;
        DataServices.Account account1;
        ArrayList<String> appcategories;


        @Override
        protected void onPreExecute() {
            getActivity().setTitle(getString(R.string.profiletitle));
            textViewWelcome=view.findViewById(R.id.textViewWelcome);
            buttonlogout=view.findViewById(R.id.buttonlogout);
            progressBarCAtegories=view.findViewById(R.id.progressBarCAtegories);
            buttonlogout.setVisibility(View.GONE);
            recyclerView1=view.findViewById(R.id.recyclerView1);
            recyclerView1.setHasFixedSize(true);
            linearLayoutManager=new LinearLayoutManager(getContext());
            recyclerView1.setLayoutManager(linearLayoutManager);
            progressBarCAtegories.setVisibility(View.VISIBLE);


        }


        @Override
        protected Integer doInBackground(String... strings) {
            try {
                publishProgress();
                appcategories=DataServices.getAppCategories(strings[0]);
                account1= DataServices.getAccount(strings[0]);
                return success;

            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                errormessage=e.getMessage();
                return failed;
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBarCAtegories.isIndeterminate();

        }

        @Override
        protected void onPostExecute(Integer integer) {
            progressBarCAtegories.setVisibility(View.GONE);
            if(integer==success)
            {
                buttonlogout.setVisibility(View.VISIBLE);
                textViewWelcome.setText(getString(R.string.welcome)+" "+account1.getName());
                adapter=new RecylcerViewCategoriesAdapter(appcategories,account1,this);
                recyclerView1.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(getActivity(), errormessage, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void OnclickListeneradapter(int position) {
            fragmentInterface.goAppList(appcategories.get(position), token);

        }
    }


}