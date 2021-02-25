// Assignment : In Class 05
//File Name : InClass05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AppListFragment extends Fragment implements DataServices.DataResponse {


    FragmentInterface fragmentInterface;
    List<DataServices.App> appsKeyset;
    private static final String ARG_PARAM1 = "token";
    private static final String ARG_PARAM2 = "category";
    private String token;
    private String category;
    ListView listView;
    AppListAdapter adapter;

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
        listView=view.findViewById(R.id.listviewapplist);
        adapter=new AppListAdapter(getActivity(),R.layout.app_list_layout,appsKeyset);
        listView.setAdapter(adapter);
        getActivity().setTitle(category);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentInterface.goAppDetails(appsKeyset.get(position));
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
    public void onSuccess(ArrayList data) {
        appsKeyset=data;
    }

    @Override
    public void onFailure(DataServices.RequestException exception) {
        Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_SHORT).show();
    }
}