// Assignment : In Class 05
//File Name : InClass05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppDetailsFragment extends Fragment {

    FragmentInterface fragmentInterface;
    ListView listviewdetails;
    AppDeatailsAdapter adapter;
    TextView textViewappname2;
    TextView textViewnameartist;
    TextView textViewdate;
    private DataServices.App app;


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
        listviewdetails=view.findViewById(R.id.listviewdetails);
       textViewappname2.setText(app.name);
       textViewdate.setText(app.releaseDate);
       textViewnameartist.setText(app.artistName);
        adapter=new AppDeatailsAdapter(getActivity(),R.layout.profile_hiew_holder,app.genres);
        listviewdetails.setAdapter(adapter);
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