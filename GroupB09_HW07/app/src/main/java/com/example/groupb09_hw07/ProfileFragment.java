// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ProfileFragment extends Fragment  implements  ProfileFunctionInterface{

    ProfileFunctionInterface profileFunctionInterface;
    FragmentInterface fragmentInterface;
    View view;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private User muser;
    private User mloggedinuser;
    //HashMap<String,Photos> mphotosmap = new HashMap<>();
    RecyclerView profilerecylcer;
    ImageView profileidp;
    TextView textViewprofilename;
    TextView textViewprofileemail1;
    TextView profilephotocount;
    LinearLayoutManager linearLayoutManager;
    ProfilerecylcerAdapter profilerecylcerAdapter;
    TextView textViewaddphotos;
    ImageView buttonaddImage1;



    public ProfileFragment() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentInterface.goList(mloggedinuser.getUuid());
    }

    public static ProfileFragment newInstance(User user, User loggedinuser) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);
        args.putSerializable(ARG_PARAM2, loggedinuser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof FragmentInterface)
            fragmentInterface= (FragmentInterface) context;
        else
            throw new RuntimeException(context.toString()+" must be implemented");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            muser = (User) getArguments().getSerializable(ARG_PARAM1);
            mloggedinuser = (User) getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_profile, container, false);
        profilerecylcer=view.findViewById(R.id.profilerecylcer);
        profileidp=view.findViewById(R.id.profileidp);
        textViewprofilename=view.findViewById(R.id.textViewprofilename);
        textViewprofileemail1=view.findViewById(R.id.textViewprofileemail1);
        profilephotocount=view.findViewById(R.id.profilephotocount);
        textViewprofilename.setText(muser.getName());
        textViewprofileemail1.setText(muser.getEmailid());
        getActivity().setTitle(muser.getUuid().equalsIgnoreCase(mloggedinuser.getUuid())?view.getContext().getString(R.string.yourprofile):muser.getName()+"'s profile");
        if(muser.getProfilepic()!=null &&muser.getProfilepic().length()>0) {
            CallFirebase.setpicture((FragmentActivity) view.getContext(), muser.getProfilepic(), new CallFirebase.CallHandler() {
                @Override
                public void onSuccess(Object o, String message) {
                    Glide.with(view.getContext())
                            .load((StorageReference) o)
                            .into(profileidp);
                }

                @Override
                public void onFailure(String message) {
                }
            });
        }
        else
        {
            profileidp.setImageResource(R.drawable.profilepic);
        }

        buttonaddImage1=view.findViewById(R.id.buttonaddImage1);
        textViewaddphotos=view.findViewById(R.id.textViewaddphotos);
        if(muser.getUuid().equalsIgnoreCase(mloggedinuser.getUuid()))
        {
            buttonaddImage1.setVisibility(View.VISIBLE);
            textViewaddphotos.setVisibility(View.VISIBLE);
        }
        else
        {
            buttonaddImage1.setVisibility(View.GONE);
            textViewaddphotos.setVisibility(View.GONE);
        }
        buttonaddImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goaddphoto(muser);;
            }
        });
        textViewaddphotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goaddphoto(muser);;
            }
        });
        linearLayoutManager=new LinearLayoutManager(getContext());
        profilerecylcer.setLayoutManager(linearLayoutManager);
        profileFunctionInterface=this;
        getphotos();




        return view;
    }


    @Override
    public void gotoDelete(User user, Photos photos) {

        CallFirebase.deletepicture(getActivity(),photos,user, new CallFirebase.CallHandler() {
            @Override
            public void onSuccess(Object o, String message) {
              User user= (User) o;
              mloggedinuser=user;
              muser=user;
              getphotos();
            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getActivity(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void gotolikedby(boolean flag, User user, User loggedinuser, Photos photos, int adapterPosition) {
        List<String> likedby=photos.getLikedby();
        if(flag==true)
            likedby.add(loggedinuser.uuid);
        else
            likedby.remove(loggedinuser.uuid);

        CallFirebase.likepicture(getActivity(),photos,muser,likedby, new CallFirebase.CallHandler() {
            @Override
            public void onSuccess(Object o, String message) {

            }

            @Override
            public void onFailure(String message) {
                Toast.makeText(getActivity(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void gotoComments(User muser, User mloggedinuser, Photos photos) {
        fragmentInterface.gotocomment(muser,mloggedinuser,photos);
    }

    private void getphotos() {
        CallFirebase.getpictures(getActivity(), muser, new CallFirebase.CallHandler() {
            @Override
            public void onSuccess(Object o, String message) {
                ArrayList<Photos> photosList= new ArrayList<>();
                photosList.clear();
                photosList=(ArrayList<Photos>) o;
                setphotolist(photosList);
                if(photosList.size()>0)
                    view.findViewById(R.id.textViewnopost).setVisibility(View.GONE);
                else
                    view.findViewById(R.id.textViewnopost).setVisibility(View.VISIBLE);

                if(photosList.size()>0)
                    Collections.sort(photosList, new Comparator<Photos>() {
                        @Override
                        public int compare(Photos o1, Photos o2) {
                            return (-1)*(o1.getDate().compareTo(o2.getDate()));
                        }
                    });
                profilephotocount.setText(photosList.size()+" "+view.getContext().getString(R.string.photos));
                profilerecylcerAdapter=new ProfilerecylcerAdapter(photosList,muser,mloggedinuser, profileFunctionInterface);
                profilerecylcer.setAdapter(profilerecylcerAdapter);
                profilerecylcerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void setphotolist(ArrayList<Photos> photos) {
        muser.setPhotosList(photos);
    }


}