package com.example.demoapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String Data1 = "Data1";
    private static final String Data2 = "Data2";

    // TODO: Rename and change types of parameters
    private String firstData;
    private String secondData;

    public CustomFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CustomFragment newInstance(String data1, String data2) {
        CustomFragment fragment = new CustomFragment();
        Bundle args = new Bundle();
        args.putString(Data1, data1);
        args.putString(Data2, data2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.firstData = getArguments().getString(Data1);
            this.secondData = getArguments().getString(Data2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_custom, container, false);
        TextView textView2=view.findViewById(R.id.textView2);
        textView2.setText(firstData+secondData);
        return view;
    }
}