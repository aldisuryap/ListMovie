package com.example.listmovie.Network;

import com.example.listmovie.BuildConfig;
import com.example.listmovie.Data.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/3/movie/now_playing?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US&page=1")
    Call<MovieResponse> getNowPlaying();

    @GET("/3/movie/upcoming?api_key=" + BuildConfig.TMDB_API_KEY + "&language=en-US&page=1")
    Call<MovieResponse> getUpcoming();
}
