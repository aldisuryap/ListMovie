package com.example.listmovie.Data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    private List<Movie> response;


    public List<Movie> getMovieResponse() {
        return response;
    }
}
