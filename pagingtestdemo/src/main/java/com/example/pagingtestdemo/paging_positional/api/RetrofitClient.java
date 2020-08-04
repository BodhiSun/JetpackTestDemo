package com.example.pagingtestdemo.paging_positional.api;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.douban.com/v2/";

    private static final String API_KEY = "0b2bdeda43b5688921839c8ecb20399b";

    private static RetrofitClient retrofitClient;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();
    }


    public static synchronized RetrofitClient getInstance(){
        if(retrofitClient ==null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }


    public Api getApi(){
        return retrofit.create(Api.class);
    }


    private OkHttpClient getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl originalUrl = originalRequest.url();

                HttpUrl newUrl = originalUrl.newBuilder()
                        .addQueryParameter("apikey", API_KEY)
                        .build();

                Request.Builder newRequestBuilder = originalRequest
                        .newBuilder()
                        .url(newUrl);

                Request newRequest = newRequestBuilder.build();
                return chain.proceed(newRequest);
            }
        });
        return httpClient.build();
    }




}
