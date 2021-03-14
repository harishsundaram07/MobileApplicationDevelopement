// Assignment : In Class 07
//File Name : GroupB09_InClass07
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI
package com.example.groupb09inclass07;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CallAPI {




    public interface CallHandler {
        public void onSuccess(List<Contact> strings, String status, String message);
        public void onFailure(Exception e, String status, String message);
    }
    static OkHttpClient okHttpClient=new OkHttpClient();
    static final String contacts="contacts";
    static final String contact="contact";
    static final String delete="delete";
    static final String create="create";
    static final String update="update";

    static String success="SUCCESS";
    static String failed="FAILED";
    static String status;
    static  String message;
    static String Urlhost="https://www.theappsdr.com";
    static final String nofunctionalerror="Something went wrong. Please try again later.";
    static final String nocontacts="No Contacts Available";



    public static void getdata(CallHandler callHandler)
    {
        HttpUrl httpUrl=HttpUrl.parse(Urlhost).newBuilder().addPathSegments(contacts).build();
        Request request=new Request.Builder().url(httpUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            String body = null;

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                e.printStackTrace();
                callHandler.onFailure(e, status, nofunctionalerror);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody=response.body();
                if(response.isSuccessful()) {
                    status=success;
                    body = responseBody.string();
                    message="";
                    String[] strings=body.split("\n");
                    if(strings.length>=1 && !strings[0].equalsIgnoreCase(""))
                        callHandler.onSuccess(addtoList(strings),status,message);
                    else
                    {
                        callHandler.onFailure(null,failed,nocontacts);

                    }
                }
                else
                {
                    status=failed;
                    body = responseBody.string();
                    callHandler.onFailure(null,status,body);
                }


            }
        });
    }

    public static void getdatabyid(String id,CallHandler callHandler)
    {
        HttpUrl httpUrl=HttpUrl.parse(Urlhost).newBuilder().addPathSegments(contact).addPathSegment(id).build();
        Request request=new Request.Builder().url(httpUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            String body = null;

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                e.printStackTrace();
                callHandler.onFailure(e, status, nofunctionalerror);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody=response.body();
                body = responseBody.string();
                if(response.isSuccessful()) {
                    status=success;
                    message="";
                    String[] strings=body.split("\n");
                    if(strings.length>=1)
                        callHandler.onSuccess(addtoList(strings),status,message);
                    else
                        callHandler.onFailure(null,failed,"No Contacts Available");
                }
                else
                {
                    status=failed;
                    callHandler.onFailure(null,status,body);
                }


            }
        });
    }

    public static void deletedata( String id,CallHandler callHandler)
    {
        HttpUrl httpUrl=HttpUrl.parse(Urlhost).newBuilder().addPathSegments(contact).addPathSegment(delete).build();
        FormBody formBody=new FormBody.Builder().add("id",id).build();
        Request request=new Request.Builder().url(httpUrl).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                e.printStackTrace();
                callHandler.onFailure(e, status, nofunctionalerror);

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody=response.body();
                String body;
                if(response.isSuccessful()) {
                    status=success;
                    body = responseBody.string();
                    message=body;
                    Log.d("TAG", "onResponse: "+body);
                    callHandler.onSuccess(null,status,message);
                }
                else
                {
                    status=failed;
                    body = responseBody.string();
                    callHandler.onFailure(null,status,body);
                }
            }
        });

    }

    public static void createdata(Contact contact1,CallHandler callHandler)
    {
        HttpUrl httpUrl=HttpUrl.parse(Urlhost).newBuilder().addPathSegments(contact).addPathSegment(create).build();
        FormBody formBody=new FormBody.Builder()
                .add("name",contact1.name)
                .add("email",contact1.email)
                .add("phone",contact1.phone)
                .add("type",contact1.type)
                .build();
        Request request=new Request.Builder().url(httpUrl).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                e.printStackTrace();
                callHandler.onFailure(e, status, nofunctionalerror);

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody=response.body();
                String body;
                if(response.isSuccessful()) {
                    status=success;
                    body = responseBody.string();
                    message=body;
                    Log.d("TAG", "onResponse: "+body);
                    callHandler.onSuccess(null,status,message);
                }
                else
                {
                    status=failed;
                    body = responseBody.string();
                    callHandler.onFailure(null,status,body);
                }
            }
        });

    }

    public static void updatedata(Contact contact1,CallHandler callHandler)
    {
        HttpUrl httpUrl=HttpUrl.parse(Urlhost).newBuilder().addPathSegments(contact).addPathSegment(update).build();
        FormBody formBody=new FormBody.Builder()
                .add("name",contact1.name)
                .add("email",contact1.email)
                .add("phone",contact1.phone)
                .add("type",contact1.type)
                .add("id",contact1.id)
                .build();
        Request request=new Request.Builder().url(httpUrl).post(formBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                call.cancel();
                e.printStackTrace();
                callHandler.onFailure(e, status, nofunctionalerror);

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody=response.body();
                String body;
                if(response.isSuccessful()) {
                    status=success;
                    body = responseBody.string();
                    message=body;
                    callHandler.onSuccess(null,status,message);
                }
                else
                {
                    status=failed;
                    body = responseBody.string();
                    callHandler.onFailure(null,status,body);
                }
            }
        });

    }






    public static List<Contact> addtoList(String[] strings)
    {
        List<Contact> list=new ArrayList<>();
        for(String s:strings)
        {
            Contact contact=new Contact();
            String[] i =s.split(",");
            contact.id=i[0];
            contact.name=i[1];
            contact.email=i[2];
            contact.phone=i[3];
            contact.type=i[4];
            list.add(contact);
        }
        return list;
    }
    }

