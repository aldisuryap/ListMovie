package com.example.listmovie.Database;

import android.provider.BaseColumns;

public class FavoriteMovieContract {

    private FavoriteMovieContract(){

    }

    public static class FavoriteMovie implements BaseColumns {
        public static final String TABLE_NAME = "FAVORITE";
        public static final String COLUMN_MOVIE_TITLE = "MOVIE_TITLE";
        public static final String COLUMN_MOVIE_DESCRIPTION = "MOVIE_DESCRIPTIOM";
        public static final String COLUMN_MOVIE_IMAGE = "MOVIE_IMAGE";
        public static final String COLUMN_MOVIE_BACKDROP = "MOVIE_BACKDROP";
    }
}
