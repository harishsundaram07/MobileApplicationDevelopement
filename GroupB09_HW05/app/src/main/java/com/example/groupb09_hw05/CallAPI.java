// Assignment : Homework 05
//File Name : GroupB09_HW05
//Full name of the student : HARIKRISHNAN SUNDARAM  & MALHAR JOSHI




package com.example.groupb09_hw05;

import android.util.Log;

import com.example.groupb09_hw05.datamodel.Forecast;
import com.example.groupb09_hw05.datamodel.WeatherCurrent;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CallAPI {

    public interface CallHandler {
        public void onSuccess(Object o,String message);

        public void onFailure(String message);
    }

    static OkHttpClient okHttpClient=new OkHttpClient();
    static int success=200;
    static String failed="FAILED";
    static int status;
    static  String message;
    static String currentApiKey="61678e8c15c40ab57edb04f81f477ee0";
    static String Urlhostcurrent="https://api.openweathermap.org/data/2.5";
    static String weather="weather";
    static String forecast="forecast";
    static String errormessage="Something went wrong. Please try again later.";


    public static void getcurrent(String city,CallHandler callHandler){


        HttpUrl httpUrl=HttpUrl.parse(Urlhostcurrent).newBuilder()
                .addPathSegments(weather)
                .addQueryParameter("q",city.toUpperCase())
                .addQueryParameter("appid",currentApiKey)
                .addQueryParameter("units","imperial")
                .build();
        Request request=new Request.Builder().url(httpUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callHandler.onFailure(e.getMessage());

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    Gson gson=new Gson();
                    WeatherCurrent weatherCurrent=gson.fromJson(response.body().charStream(),WeatherCurrent.class);
                    if(weatherCurrent!=null && weatherCurrent.cod == success)
                    {
                        callHandler.onSuccess(weatherCurrent,"");
                    }
                    else
                    {
                        callHandler.onSuccess(null,errormessage);
                    }
                    
                }
                else
                {
                    callHandler.onSuccess(null,errormessage);
                }

            }
        });
    }

    public static void getforecast(String city,CallHandler callHandler){


        HttpUrl httpUrl=HttpUrl.parse(Urlhostcurrent).newBuilder().addPathSegments(forecast)
                .addQueryParameter("q",city.toUpperCase())
                .addQueryParameter("appid",currentApiKey)
                .addQueryParameter("units","imperial")
                .build();
        Request request=new Request.Builder().url(httpUrl).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                callHandler.onFailure(e.getMessage());

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful())
                {
                    Gson gson=new Gson();
                    Forecast forecast=gson.fromJson(response.body().charStream(),Forecast.class);
                    if(forecast!=null && Integer.parseInt(forecast.cod)==success)
                    {
                        Log.d("TAG", "onResponse: "+forecast);
                        callHandler.onSuccess(forecast,"");
                    }
                    else
                    {
                        callHandler.onSuccess(null,errormessage);
                    }

                }
                else
                {
                    callHandler.onSuccess(null,errormessage);
                }

            }
        });
    }
}
