// Assignment : In Class 05
//File Name : InClass05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.inclass05;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements DataServices.DataResponse  {



    private static final String ARG_PARAM1 = "param1";
    FragmentInterface fragmentInterface;
    private DataServices.Account account1;
    List<String> appsKeyset;
    String token;
    AppProfileAdapter adapter;
    ListView listView;
    TextView textViewWelcome;
    Button buttonlogout;





    public ProfileFragment() {
        // Required empty public constructor
    }


    public static ProfileFragment newInstance(DataServices.Account account ,String token) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.key,account);
        args.putString(ARG_PARAM1,token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            account1 = (DataServices.Account) getArguments().getSerializable(MainActivity.key);
            token=getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        listView=view.findViewById(R.id.listView);
        DataServices.getAppCategories(token,ProfileFragment.this);
        adapter=new AppProfileAdapter(getActivity(),R.layout.profile_hiew_holder,appsKeyset);
        textViewWelcome=view.findViewById(R.id.textViewWelcome);
        buttonlogout=view.findViewById(R.id.buttonlogout);
        getActivity().setTitle(getString(R.string.profiletitle));
        textViewWelcome.setText(getActivity().getString(R.string.welcome)+" "+account1.getName());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragmentInterface.goAppList(appsKeyset.get(position),token);
            }
        });
        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.home();
            }
        });
        return view;
    }


    @Override
    public void onSuccess(ArrayList data) {
         appsKeyset=data;
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
}