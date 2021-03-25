package com.example.harikrishnan_midterm;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddForumFragment extends Fragment {


    View view;
    private static final String ARG_PARAM1 = "param1";

    private static final String ARG_PARAM2 = "param2";
    EditText edittitle;
    EditText editTextdesc;
    DataServices.Forum forum;
    private DataServices.Account maccount;
    Doaddforum doaddforum;
    FragmentInterface fragmentInterface;

    private String mtoken;


    public AddForumFragment() {
        // Required empty public constructor
    }


    public static AddForumFragment newInstance(String token, DataServices.Account account) {
        AddForumFragment fragment = new AddForumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, token);
    args.putSerializable(ARG_PARAM2,account);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mtoken = getArguments().getString(ARG_PARAM1);
            maccount = (DataServices.Account) getArguments().getSerializable(ARG_PARAM2);

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
        view= inflater.inflate(R.layout.fragment_add_forum, container, false);
        editTextdesc=view.findViewById(R.id. editTextdesc);
        edittitle=view.findViewById(R.id. edittitle);
        getActivity().setTitle(getString(R.string.addforum));

        view.findViewById(R.id.buttonSubmit1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittitle.getText().toString().length()>0 && editTextdesc.getText().toString().length()>0)
                {
                    doaddforum=new Doaddforum();
                    doaddforum.execute(edittitle.getText().toString(),editTextdesc.getText().toString());

                }
                else if(edittitle.getText().toString().length()<=0)
                {
                    Toast.makeText(getContext(), getString(R.string.errortitle), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), getString(R.string.errordesc), Toast.LENGTH_SHORT).show();
                }


            }
        });


        view.findViewById(R.id.buttonCancel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.gotoforums(maccount,mtoken);
            }
        });
        return view;
    }





    class Doaddforum extends AsyncTask<String,Integer, DataServices.AuthResponse> {

        DataServices.AuthResponse authResponse;
        int status;
        int success=0;
        int failed=1;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            view.findViewById(R.id.buttonSubmit1).setEnabled(false);
            view.findViewById(R.id.buttonCancel1).setEnabled(false);



        }

        @Override
        protected void onPostExecute(DataServices.AuthResponse authResponse) {
            view.findViewById(R.id.buttonSubmit1).setEnabled(true);
            view.findViewById(R.id.buttonCancel1).setEnabled(true);

            if(status==success)
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                fragmentInterface.gotoforums(maccount,mtoken);
            }
            else
            {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected DataServices.AuthResponse doInBackground(String... strings) {
            try {
                publishProgress();
                forum=DataServices.createForum(mtoken,strings[0],strings[1]);
                status=success;
                message=getActivity().getString(R.string.successforum);
            } catch (DataServices.RequestException e) {
                e.printStackTrace();
                status=failed;
                message=e.getMessage();
            }

            return authResponse;
        }
    }
}