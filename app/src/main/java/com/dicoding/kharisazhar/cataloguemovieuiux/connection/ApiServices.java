package com.dicoding.kharisazhar.cataloguemovieuiux.connection;

import com.dicoding.kharisazhar.cataloguemovieuiux.model.ModelListMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServices{
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
