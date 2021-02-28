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
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppDetailsFragment extends Fragment {

    FragmentInterface fragmentInterface;
    ListView listviewdetails;
   // AppDeatailsAdapter adapter;
    TextView textViewappname2;
    TextView textViewnameartist;
    TextView textViewdate;
    private DataServices.App app;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView3;
    RecylcerDetailsAdapter adapter;



    public AppDetailsFragment() {
        // Required empty public constructor
    }

    public static AppDetailsFragment newInstance(DataServices.App app) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.key, app);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            app = (DataServices.App) getArguments().getSerializable(MainActivity.key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_app_details, container, false);
        getActivity().setTitle(getString(R.string.detailstitle));
        textViewnameartist=view.findViewById(R.id.textViewnameartist);
        textViewappname2=view.findViewById(R.id.textViewappname2);
        textViewdate=view.findViewById(R.id.textViewdate);
       textViewappname2.setText(app.name);
       textViewdate.setText(app.releaseDate);
       textViewnameartist.setText(app.artistName);

        recyclerView3=view.findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView3.setLayoutManager(linearLayoutManager);
        adapter=new RecylcerDetailsAdapter(app.genres);
        recyclerView3.setAdapter(adapter);



       /* adapter=new AppDeatailsAdapter(getActivity(),R.layout.categories_row_holder,app.genres);
        listviewdetails.setAdapter(adapter);*/
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
}