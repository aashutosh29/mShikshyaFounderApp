package com.bihanitech.shikshyaprasasak.remote;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dilip on 8/28/17.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;

    private static final String TAG = RetrofitClient.class.getSimpleName();

    public static Retrofit getClient(String baseUrl){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        if(retrofit != null ){
            if(baseUrl.equalsIgnoreCase(""+retrofit.baseUrl())){
                return retrofit;
            }
        }


            Log.v(TAG,"baseUrl "+baseUrl);
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        Log.v(TAG,"URL retorift :"+retrofit.baseUrl());
        return retrofit;


        /*
        if(retrofit == null){

            Log.v(TAG,"baseUrl "+baseUrl);
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        Log.v(TAG,"URL retorift :"+retrofit.baseUrl());
        return retrofit;
*/
    }
}
