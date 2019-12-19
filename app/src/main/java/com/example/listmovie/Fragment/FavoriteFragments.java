package com.example.listmovie.Fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.listmovie.Adapter.MovieAdapter;
import com.example.listmovie.Data.Movie;
import com.example.listmovie.Database.FavoriteMovieContract;
import com.example.listmovie.Database.FavoriteMovieDBHelper;
import com.example.listmovie.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragments extends Fragment {

    RecyclerView recyclerView;
    MovieAdapter adapter;
    ArrayList<Movie> favorites = new ArrayList<Movie>();

    public FavoriteFragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Fragment", "favorite");

        View view = inflater.inflate(R.layout.fragment_favorite_fragments, container, false);

        favorites = loadDataFromDatabase();

        recyclerView = view.findViewById(R.id.rv_favorite);
        adapter = new MovieAdapter(getActivity(), favorites);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        recyclerView.setAdapter(adapter);

        return view;
    }

    public ArrayList<Movie> loadDataFromDatabase(){
        FavoriteMovieDBHelper dbHelper = new FavoriteMovieDBHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Movie> favorites = new ArrayList<Movie>();
        Cursor cursor = db.query(FavoriteMovieContract.FavoriteMovie.TABLE_NAME, null,
                null, null, null, null,
                FavoriteMovieContract.FavoriteMovie._ID +" DESC", null);

        cursor.moveToFirst();
        Movie movie;

        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setMovieTitle(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_TITLE)));
                movie.setMovieOverview(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_DESCRIPTION)));
                movie.setMovieImage(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_IMAGE)));
                movie.setMovieBackdrop(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_BACKDROP)));

                favorites.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return favorites;
    }

}
