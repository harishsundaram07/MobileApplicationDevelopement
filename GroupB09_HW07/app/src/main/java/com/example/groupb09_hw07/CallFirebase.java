// Assignment : HW 07
//File Name : GroupB09_HW07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI

package com.example.groupb09_hw07;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CallFirebase {
    static FirebaseStorage storagedp;
    static User muser;
    static  FirebaseFirestore mdbsetuser;
    static  FirebaseFirestore mdbsetuser1;
    static FirebaseAuth mAuth1;
    static FirebaseFirestore mdbget;
    static FirebaseFirestore mdbget1;
    static FirebaseFirestore mdbset;
    static FirebaseFirestore mdbset1;



    public static void uploadimage(Uri imageuri,String imageid,CallHandler callHandler) {
        storagedp=FirebaseStorage.getInstance();
        StorageReference riversRef = storagedp.getReference().child("image/"+imageid);
        riversRef.putFile(imageuri).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {

                callHandler.onFailure(null);

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                callHandler.onSuccess(null,null);

            }
        });

    }

    public static void setuser(Activity activity, User user, CallHandler callHandler) {
        mdbsetuser= FirebaseFirestore.getInstance();
        mdbsetuser.collection(activity.getString(R.string.user)).document(user.getUuid()).set(user).addOnSuccessListener(activity, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                callHandler.onSuccess(user.getUuid(),null);

            }
        }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callHandler.onFailure(e.getMessage());
            }
        });
    }

    public static void setauthuser(Activity activity,String email, String password,CallHandler callHandler) {

        mAuth1=FirebaseAuth.getInstance();
        mAuth1.createUserWithEmailAndPassword(email.toString(),password.toString())
                .addOnSuccessListener(activity, new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        callHandler.onSuccess(mAuth1.getUid(),null);
                    }
                }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                    callHandler.onFailure(e.getMessage());
            }
        });

    }

    public static void getauth(Activity activity, String email, String password,CallHandler callHandler) {
        mAuth1=FirebaseAuth.getInstance();
        mAuth1.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            callHandler.onSuccess(mAuth1.getUid(),null);
                        }
                        else
                        {
                            callHandler.onFailure(task.getException().getMessage());

                        }
                    }
                });
        
    }

    public static void getusers(Activity activity, ArrayList<User> userArrayList,CallHandler callHandler) {

        mdbget=FirebaseFirestore.getInstance();
        mdbget.collection(activity.getString(R.string.user)).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                userArrayList.clear();
                    for(QueryDocumentSnapshot documentSnapshot:value)
                    {
                        User user=new User();
                        user=documentSnapshot.toObject(User.class);
                        userArrayList.add(user);
                    }
                callHandler.onSuccess(userArrayList,null);



            }
        });


    }




    public static void getcurrentuser(Activity activity, String muuid, CallHandler callHandler) {
        mdbget=FirebaseFirestore.getInstance();
        mdbget.collection(activity.getString(R.string.user)).document(muuid).get()
                .addOnSuccessListener(activity, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user=documentSnapshot.toObject(User.class);
                        callHandler.onSuccess(user,null);
                    }
                }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callHandler.onFailure(e.getMessage());
            }
        });


    }

    public static void getpictures(FragmentActivity activity,User user, CallHandler callHandler) {
        ArrayList<Photos> photosList=user.getPhotosList();
        photosList.clear();
        mdbget=FirebaseFirestore.getInstance();
        mdbget.collection(activity.getString(R.string.user)).document(user.getUuid()).collection(activity.getString(R.string.photosdb)).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               if(value.size()>0) {
                   photosList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : value) {

                        Photos photo = new Photos();
                        photo = documentSnapshot.toObject(Photos.class);
                        photosList.add(photo);
                    }
                   callHandler.onSuccess(photosList,null);
               }
               else
                   callHandler.onSuccess(photosList,null);

            }
        });




    }


    public static void setpicture(FragmentActivity activity, String profilepic, CallHandler callHandler) {
        storagedp = FirebaseStorage.getInstance();;
        StorageReference storageRef = storagedp.getReference();
        StorageReference pathReference = storageRef.child("image/"+profilepic);
        callHandler.onSuccess(pathReference,null);
    }

    public static void addimage(FragmentActivity activity, User muser, Photos photos, CallHandler callHandler) {
        mdbset=FirebaseFirestore.getInstance();
        mdbset.collection(activity.getString(R.string.user)).document(muser.getUuid()).collection(activity.getString(R.string.photosdb)).document(photos.getPhotoid()).set(photos).addOnSuccessListener(activity, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                callHandler.onSuccess(null,null);
            }
        }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callHandler.onFailure(e.getMessage());

            }
        });
    }

    public static void deletepicture(FragmentActivity activity,Photos photos,User user, CallHandler callHandler) {

        mdbset=FirebaseFirestore.getInstance();
        mdbset.collection(activity.getString(R.string.user)).document(user.getUuid()).collection(activity.getString(R.string.photosdb)).document(photos.getPhotoid()).delete().addOnSuccessListener(activity, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                ArrayList<Photos> temp=user.getPhotosList();
                temp.remove(photos);
                user.setPhotosList(temp);
                String photosList="photosList";
                mdbset1=FirebaseFirestore.getInstance();
                mdbset1.collection(activity.getString(R.string.user)).document(user.getUuid()).update(photosList,temp).addOnSuccessListener(activity, new OnSuccessListener<Void>() {

                    @Override
                    public void onSuccess(Void aVoid) {
                        storagedp = FirebaseStorage.getInstance();;
                        StorageReference storageRef = storagedp.getReference();
                        StorageReference pathReference = storageRef.child("image/"+photos.getPhotoid());
                        pathReference.delete().addOnSuccessListener(activity, new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                callHandler.onSuccess(user,null);
                            }
                        }).addOnFailureListener(activity, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callHandler.onFailure(e.getMessage());
                            }
                        });
                    }
                }).addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callHandler.onFailure(e.getMessage());
                    }
                });
            }
        }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callHandler.onFailure(e.getMessage());

            }
        });

    }

    private static void setuserdata(User user) {
        muser=user;
    }

    public static void likepicture(FragmentActivity activity, Photos photos, User user, List<String> likedby, CallHandler callHandler) {
        String likedby1="likedby";
        mdbset=FirebaseFirestore.getInstance();
        mdbset.collection(activity.getString(R.string.user)).document(user.getUuid()).collection(activity.getString(R.string.photosdb)).document(photos.getPhotoid()).update(likedby1,likedby).addOnSuccessListener(activity, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {

            }
        }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callHandler.onFailure(e.getMessage());

            }
        });



        }
    public static void getcommentspic(FragmentActivity activity, User user, Photos mphoto, CallHandler callHandler) {
        List<Comment> commentArrayList = mphoto.getCommentList();
        commentArrayList.clear();
        mdbget = FirebaseFirestore.getInstance();
        mdbget.collection(activity.getString(R.string.user)).document(user.getUuid()).collection(activity.getString(R.string.photosdb)).document(mphoto.getPhotoid()).collection(activity.getString(R.string.comment)).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.size() > 0) {
                    commentArrayList.clear();
                    for (QueryDocumentSnapshot documentSnapshot : value) {

                        Comment comment = new Comment();
                        comment = documentSnapshot.toObject(Comment.class);
                        commentArrayList.add(comment);
                    }
                    callHandler.onSuccess(commentArrayList, null);
                } else
                    callHandler.onSuccess(commentArrayList, null);

            }
        });

    }

    public static void addcomment(FragmentActivity activity, User muser, Photos photos,Comment comment, CallHandler callHandler) {
        mdbset=FirebaseFirestore.getInstance();
        mdbset.collection(activity.getString(R.string.user)).document(muser.getUuid()).collection(activity.getString(R.string.photosdb)).document(photos.getPhotoid()).collection(activity.getString(R.string.comment)).document(comment.getCommentid()).set(comment).addOnSuccessListener(activity, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                callHandler.onSuccess(null,null);
            }
        }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callHandler.onFailure(e.getMessage());

            }
        });
    }

    public static void deletecomment(FragmentActivity activity,Photos photos,User user,Comment comment, CallHandler callHandler) {

        mdbset=FirebaseFirestore.getInstance();
        mdbset.collection(activity.getString(R.string.user)).document(user.getUuid()).collection(activity.getString(R.string.photosdb)).document(photos.getPhotoid()).collection(activity.getString(R.string.comment)).document(comment.getCommentid()).delete().addOnSuccessListener(activity, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                callHandler.onSuccess(null,null);
            }
        }).addOnFailureListener(activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callHandler.onFailure(e.getMessage());

            }
        });

    }


    public interface CallHandler {
        public void onSuccess(Object o,String message) ;

        public void onFailure(String message);
    }

}
