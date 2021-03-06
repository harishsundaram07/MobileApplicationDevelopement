// Assignment : Homework 03
//File Name : HW03
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.hw03;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoriesFragment extends Fragment implements DataServices.DataResponse,AdapterInterface,DataServices.AccountResponse {



    private static final String ARG_PARAM1 = "param1";
    FragmentInterface fragmentInterface;
     DataServices.Account account1;
    List<String> appsKeyset;
    String token;
    RecylcerViewCategoriesAdapter adapter;
    TextView textViewWelcome;
    Button buttonlogout;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView1;

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
        View view= inflater.inflate(R.layout.fragment_categories, container, false);
        DataServices.getAppCategories(token, CategoriesFragment.this);
        DataServices.getAccount(token,CategoriesFragment.this);
        textViewWelcome=view.findViewById(R.id.textViewWelcome);
        textViewWelcome.setText(getString(R.string.welcome)+" "+account1.getName());
        buttonlogout=view.findViewById(R.id.buttonlogout);
        recyclerView1=view.findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView1.setLayoutManager(linearLayoutManager);
        adapter=new RecylcerViewCategoriesAdapter(appsKeyset,account1,this);
        recyclerView1.setAdapter(adapter);
        getActivity().setTitle(getString(R.string.profiletitle));
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.home();
            }
        });
        return view;
    }


    @Override
    public void onSuccess(ArrayList data)
    {
         appsKeyset=data;
    }

    @Override
    public void onSuccess(DataServices.Account account) {
        this.account1=account;

    }

    @Override
    public void onFailure(DataServices.RequestException exception) {
        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        fragmentInterface.home();

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
    public void OnclickListeneradapter(int position) {
        fragmentInterface.goAppList(appsKeyset.get(position), token);

    }
}