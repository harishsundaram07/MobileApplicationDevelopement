// Assignment : In Class 05
//File Name : InClass05
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
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppListFragment extends Fragment implements DataServices.DataResponse,AdapterInterface {


    FragmentInterface fragmentInterface;
    List<DataServices.App> appsKeyset;
    private static final String ARG_PARAM1 = "token";
    private static final String ARG_PARAM2 = "category";
    private String token;
    private String category;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView2;
    ApplistAdapter applistAdapter;

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
        View view= inflater.inflate(R.layout.fragment_app_list, container, false);
        DataServices.getAppsByCategory(token,category,AppListFragment.this);

        recyclerView2=view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(linearLayoutManager);
        applistAdapter=new ApplistAdapter(appsKeyset,this);
        recyclerView2.setAdapter(applistAdapter);
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
    public void onSuccess(ArrayList data) {
        appsKeyset=data;
    }

    @Override
    public void onFailure(DataServices.RequestException exception) {
        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnclickListeneradapter(int position) {
        fragmentInterface.goAppDetails(appsKeyset.get(position));
    }
}