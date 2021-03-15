// Assignment : In Class 07
//File Name : GroupB09_InClass07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09inclass07;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ContactListFragment extends Fragment implements FunctionInterface{


    View view;
    FragmentInterface fragmentInterface;
    RecyclerView recyclerviewcontact;
   static List<Contact> contactlist;
    FunctionInterface functionInterface;
    TextView textviewnodata;
    LinearLayoutManager linearLayoutManager;
    Contact mcontact;
    RecyclerviewAdapter recyclerviewAdapter;






    public ContactListFragment() {
    }

    public static ContactListFragment newInstance(Contact contact) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putSerializable(MainActivity.key,contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mcontact= (Contact) getArguments().getSerializable(MainActivity.key);
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
        view= inflater.inflate(R.layout.fragment_contact_list, container, false);
        textviewnodata=view.findViewById(R.id.textViewNodata);
        textviewnodata.setVisibility(View.GONE);
        recyclerviewcontact = view.findViewById(R.id.recyclerviewcontact);
        recyclerviewcontact.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerviewcontact.setLayoutManager(linearLayoutManager);

        functionInterface=this;
        contactlist=new ArrayList<>();
        getActivity().setTitle(R.string.Contacts);
        CallAPI.getdata(new CallAPI.CallHandler() {

            @Override
            public void onSuccess(List<Contact> strings, String status, String message) {
                contactlist=strings;
                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            recyclerviewAdapter = new RecyclerviewAdapter(contactlist, functionInterface);
                            //contactAdapter=new ContactAdapter(getActivity(),R.layout.layout_holder,contactlist,functionInterface);
                            recyclerviewcontact.setAdapter(recyclerviewAdapter);

                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e, String status, String message) {
                if(getActivity()!=null)  {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (status == CallAPI.failed) {
                                textviewnodata.setVisibility(View.VISIBLE);
                                Log.d("TAG", "run: ");
                                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                                builder
                                        .setMessage(message)
                                        .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                builder.create().show();                            }
                        }
                    });
                }
            }

        });

        view.findViewById(R.id.buttonrefresh1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentInterface.gohome();
            }
        });


        view.findViewById(R.id.buttonadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goCreate();
            }
        });
        functionInterface=this;



        return view;
    }


    @Override
    public void delete(int adapterPosition) {
        CallAPI.deletedata(contactlist.get(adapterPosition).id, new CallAPI.CallHandler() {
            @Override
            public void onSuccess(List<Contact> strings, String status, String message) {
                if(getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                            builder
                                    .setMessage(message)
                                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    });
                            builder.create().show();
                            contactlist.remove(contactlist.get(adapterPosition));
                            //contactAdapter.notifyDataSetChanged();
                            recyclerviewAdapter.notifyDataSetChanged();
                        }
                    });
                }

            }

            @Override
            public void onFailure(Exception e, String status, String message) {

            }
        });

    }

    @Override
    public void gotodetails(int adapterPosition) {
        fragmentInterface.goDetails(contactlist.get(adapterPosition));

    }
}