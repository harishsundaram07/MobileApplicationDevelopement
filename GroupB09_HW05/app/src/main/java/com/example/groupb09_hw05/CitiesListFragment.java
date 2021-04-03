// Assignment : Homework 05
//File Name : GroupB09_HW05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class CitiesListFragment extends Fragment implements FunctionInterface {

    View view;
    ArrayList<Data.City> cities=new ArrayList<>();
    CitylistRecylerAdapter citylistRecylerAdapter;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerViewcity;
    FragmentInterface fragmentInterface;


    public CitiesListFragment() {

    }

    public static CitiesListFragment newInstance(String param1, String param2) {
        CitiesListFragment fragment = new CitiesListFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_cities_list, container, false);
        cities=Data.cities;
        recyclerViewcity=view.findViewById(R.id.recylcerviewcity);
        getActivity().setTitle(R.string.cities);
        recyclerViewcity.setHasFixedSize(true);
        citylistRecylerAdapter=new CitylistRecylerAdapter(cities,  this);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerViewcity.setLayoutManager(linearLayoutManager);
        recyclerViewcity.setAdapter(citylistRecylerAdapter);

        return view;
    }

    @Override
    public void oncityclick(int adapterPosition) {

        fragmentInterface.gotocurrentdetails(cities.get(adapterPosition).getCity(),cities.get(adapterPosition).getCountry());

    }
}