// Assignment : InClass 08
//File Name : GroupB09_InClass08
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI


package com.example.groupb09_hw06;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewForumFragment extends Fragment {

    View view;
    private static final String ARG_PARAM1 = "param1";
    User muser;
    EditText edittitle;
    EditText editTextdesc;
    FragmentInterface fragmentInterface;
    private FirebaseFirestore mdb4;

    public NewForumFragment() {
        // Required empty public constructor
    }

    public static NewForumFragment newInstance(User user) {
        NewForumFragment fragment = new NewForumFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            muser = (User) getArguments().getSerializable(ARG_PARAM1);

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

        view= inflater.inflate(R.layout.fragment_new_forum, container, false);
        editTextdesc=view.findViewById(R.id. editTextdesc);
        edittitle=view.findViewById(R.id. edittitle);
        getActivity().setTitle(view.getContext().getString(R.string.addforum));

        view.findViewById(R.id.buttonSubmit1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edittitle.getText().toString().length()>0 && editTextdesc.getText().toString().length()>0)
                {
                    mdb4=FirebaseFirestore.getInstance();
                    UUID uuid=UUID.randomUUID();
                    Forum forum=new Forum();
                    forum.setDate(new Date().toString());
                    forum.setDescription(editTextdesc.getText().toString());
                    forum.setForumid(uuid.toString());
                    forum.setTitle(edittitle.getText().toString());
                    forum.setUsername(muser.getName());
                    forum.setUserid(muser.uuid);
                    forum.setLikedby(new ArrayList<>());
                    mdb4.collection(view.getContext().getString(R.string.forum)).document(uuid.toString()).set(forum)
                            .addOnFailureListener(getActivity(), new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), view.getContext().getString(R.string.errormessage), Toast.LENGTH_SHORT).show();

                                }
                            }).addOnSuccessListener(getActivity(), new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getActivity(), view.getContext().getString(R.string.successforum), Toast.LENGTH_SHORT).show();
                            fragmentInterface.goList(muser.getUuid());
                        }
                    });

                }
                else if(edittitle.getText().toString().length()<=0)
                {
                    Toast.makeText(getContext(), view.getContext().getString(R.string.errortitle), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), view.getContext().getString(R.string.errordesc), Toast.LENGTH_SHORT).show();
                }


            }
        });


        view.findViewById(R.id.buttonCancel1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentInterface.goList(muser.getUuid());
            }
        });
        return view;
    }
}