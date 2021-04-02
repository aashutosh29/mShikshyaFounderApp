package com.bihanitech.shikshyaprasasak.remote;

//import android.util.Log;
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//import retrofit2.converter.gson.GsonConverterFactory;
//
///**
// * Created by dilip on 8/28/17.
// */
//
//public class RetrofitClient {
//
//    private static Retrofit retrofit = null;
//
//    private static final String TAG = RetrofitClient.class.getSimpleName();
//
//    public static Retrofit getClient(String baseUrl){
//
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .connectTimeout(1, TimeUnit.MINUTES)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .build();
//
//        if(retrofit != null ){
//            if(baseUrl.equalsIgnoreCase(""+retrofit.baseUrl())){
//                return retrofit;
//            }
//        }
//
//
//            Log.v(TAG,"baseUrl "+baseUrl);
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .client(okHttpClient)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//        Log.v(TAG,"URL retorift :"+retrofit.baseUrl());
//        return retrofit;
//
//
//        /*
//        if(retrofit == null){
//
//            Log.v(TAG,"baseUrl "+baseUrl);
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//
//        Log.v(TAG,"URL retorift :"+retrofit.baseUrl());
//        return retrofit;
//*/
//    }
//}

import android.util.Log;

import com.bihanitech.shikshyaprasasak.utility.MyApp;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dilip on 8/28/17.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;


//    private static File httpCacheDirectory = new File(getContext().getCacheDir(), "offlineCache");

    //10 MB
    private static final okhttp3.Cache cache = new okhttp3.Cache(MyApp.getContext().getCacheDir(), 20 * 1024 * 1024);

    private static final String TAG = RetrofitClient.class.getSimpleName();
    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cache(cache)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(provideCacheInterceptor())
            .addInterceptor(provideOfflineCacheInterceptor())
            .build();

    public static Retrofit getClient(String baseUrl) {


        if (retrofit != null) {
            if (baseUrl.equalsIgnoreCase("" + retrofit.baseUrl())) {
                return retrofit;
            }
        }


        Log.v(TAG, "baseUrl " + baseUrl);

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();


        Log.v(TAG, "URL retorift :" + retrofit.baseUrl());
        return retrofit;


    }

    private static Interceptor provideCacheInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response originalResponse = chain.proceed(request);
                String cacheControl = originalResponse.header("Cache-Control");

                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                        cacheControl.contains("must-revalidate") || cacheControl.contains("max-stale=0") || cacheControl.contains("max-age=0")) {

                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + 0)
                            .build();
                } else {
                    return originalResponse;
                }
            }
        };

    }


    private static Interceptor provideOfflineCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                try {
                    return chain.proceed(chain.request());
                } catch (Exception e) {
                    Request request = chain.request();
                    request = request.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + (7 * 24 * 60 * 60 * 1000))
                            .build();
                    return chain.proceed(request);
                }
            }
        };
    }

}