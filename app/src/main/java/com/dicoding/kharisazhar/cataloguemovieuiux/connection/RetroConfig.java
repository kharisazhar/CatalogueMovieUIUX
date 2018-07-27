package com.dicoding.kharisazhar.cataloguemovieuiux.connection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroConfig {


    private static Retrofit getRetrofit(){
        Retrofit r = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        return r;
    }

    public static ApiServices getApiServices(){
        return getRetrofit().create(ApiServices.class);
    }
}
