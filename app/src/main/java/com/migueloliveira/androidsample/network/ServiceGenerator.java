package com.migueloliveira.androidsample.network;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.JsonObject;
import com.migueloliveira.androidsample.utils.Constants;
import com.migueloliveira.androidsample.utils.HashUtils;

import java.io.IOException;
import java.security.MessageDigest;

import okhttp3.Authenticator;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by migueloliveira on 8/10/16.
 */
public class ServiceGenerator {
    private static final String API_BASE_URL = "http://gateway.marvel.com";
    private static final String QUERY_TS = "ts";
    private static final String QUERY_APIKEY = "apikey";
    private static final String QUERY_HASH = "hash";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(final Chain chain) throws IOException {
                okhttp3.Request original = chain.request();
                okhttp3.Request request;

                long timeStamp = System.currentTimeMillis();
                String toHash = timeStamp + Constants.API_PRIVATE + Constants.API_PUBLIC;

                HttpUrl url = original.url().newBuilder()
                        .addQueryParameter(QUERY_TS, String.valueOf(timeStamp))
                        .addQueryParameter(QUERY_APIKEY, Constants.API_PUBLIC)
                        .addQueryParameter(QUERY_HASH, HashUtils.md5(toHash)).build();

                try {
                    request = original.newBuilder()
                            .url(url)
                            .method(original.method(), original.body())
                            .build();
                    Log.e("_DEBUG_","request: " + request.toString());
                    return chain.proceed(request);
                } catch (NullPointerException e){
                    return chain.proceed(original);
                }
            }
        });

        // Must uncomment gradle file && application.java lines to enable this
        httpClient.networkInterceptors().add(new StethoInterceptor());


        OkHttpClient client = httpClient.build();
        client.dispatcher().setMaxRequests(32);
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
