package com.example.demoapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class TestFragment extends Fragment {

    private  String test;
    final String TAG="DEMO";
    TextView textview;
    private static final String Data1 = "Data1";
    private static final String Data2 = "Data2";
    FragmentInterface fragmentInterface;

    // TODO: Rename and change types of parameters
    private String firstData;
    private String secondData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, " TestFragment : onCreate");
        if (getArguments() != null) {
            this.firstData = getArguments().getString(Data1);
            this.secondData = getArguments().getString(Data2);
        }


    }
    public static TestFragment newInstance(String data1, String data2) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString(Data1, data1);
        args.putString(Data2, data2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, " TestFragment : onCreateView");

        View view= inflater.inflate(R.layout.fragment_test, container, false);
         textview=view.findViewById(R.id.textView4);
        view.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              textview.setText(textview.getText()+"*");
                fragmentInterface.Setdata(textview.getText().toString());

            }
        });
        fragmentInterface.Setdata(textview.getText().toString());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, " TestFragment : onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, " TestFragment : onStart");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, " TestFragment : onAttach");
        if(context instanceof FragmentInterface )
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, " TestFragment : onActivityCreated");


    }
}