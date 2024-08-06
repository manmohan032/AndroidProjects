package com.example.chatwiseappdemo.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit=null;
    private static String baseUrl="https://dummyjson.com/";
    private  static void createClient(){

        retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(httpClient)
                .build();
    }

    public static  Retrofit getClient(){
        if(retrofit==null)
        {
            createClient();
        }
        return retrofit;
    }
}
