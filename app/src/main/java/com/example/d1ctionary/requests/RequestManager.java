package com.example.d1ctionary.requests;

import com.example.d1ctionary.util.LiveDataCallAdaptorFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestManager {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/")
            .addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(new LiveDataCallAdaptorFactory()).build();



    private static DictionaryApi dictionaryApi = retrofit.create(DictionaryApi.class);

    public static DictionaryApi getDictionaryApi(){

        return dictionaryApi;
    }
}
