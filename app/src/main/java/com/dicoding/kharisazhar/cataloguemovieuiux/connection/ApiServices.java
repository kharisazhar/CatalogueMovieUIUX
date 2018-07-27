package com.dicoding.kharisazhar.cataloguemovieuiux.connection;


import com.dicoding.kharisazhar.cataloguemovieuiux.model.ModelListMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServices{
//    @GET("movie/upcoming?api_key=da2c66905b58cbb6b972e167cd56310f&language=en-US&page=1")
//    Call<ModelListMovie> getUpcoming();
//    @GET("movie/now_playing?api_key=da2c66905b58cbb6b972e167cd56310f&language=en-US&page=1")
//    Call<ModelListMovie>getNowPlaying();
//    @GET("movie/popular?api_key=da2c66905b58cbb6b972e167cd56310f&language=en-US&page=1")
//    Call<ModelListMovie>getPopular();
//    @GET("")
//    Call<ModelListMovie>getQuery(@Query("Query") String query);
    @GET("movie/upcoming")
    Call<ModelListMovie>getUpComing(@Query("api_key") String apiKey,
                                    @Query("language") String language);
    @GET("movie/now_playing")
    Call<ModelListMovie>getNowPlaying(@Query("api_key") String apiKey,
                                      @Query("language") String language);
    @GET("movie/popular")
    Call<ModelListMovie>getPopular(@Query("api_key") String apiKey,
                                      @Query("language") String language);
    @GET("search/movie")
    Call<ModelListMovie>getQuery(@Query("api_key") String apiKey,
                                 @Query("query") String query,
                                 @Query("language") String language);
}
