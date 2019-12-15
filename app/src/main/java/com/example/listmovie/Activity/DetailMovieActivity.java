package com.example.listmovie.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.listmovie.Data.Movie;
import com.example.listmovie.Database.FavoriteMovieContract;
import com.example.listmovie.Database.FavoriteMovieDBHelper;
import com.example.listmovie.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class DetailMovieActivity extends AppCompatActivity {

    private boolean isFavorite = false;
    private final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500";

    TextView texrTitle;
    TextView textDesciption;
    ImageView imageBackdrop;
    ImageView imageMovieImage;
    ImageView favorite;

    String title = "";
    String description = "";
    String movieImage = "";
    String backdrop = "";

    ConstraintLayout constraintLayout;

    FavoriteMovieDBHelper dbHelper = new FavoriteMovieDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Intent intent = getIntent();

        title = intent.getStringExtra("TITLE");
        description = intent.getStringExtra("DESCRIPTION");
        movieImage = intent.getStringExtra("MOVIE_IMAGE");
        backdrop = intent.getStringExtra("BACKDROP");

        texrTitle = findViewById(R.id.text_title);
        textDesciption = findViewById(R.id.text_description);
        imageBackdrop = findViewById(R.id.image_movie_detail);
        imageMovieImage = findViewById(R.id.image_movie);
        constraintLayout = findViewById(R.id.layout_detail);

        texrTitle.setText(title);
        textDesciption.setText(description);
        Glide.with(this)
                .load(BASE_POSTER_URL + backdrop)
                .into(imageBackdrop);
        Glide.with(this)
                .load(BASE_POSTER_URL + movieImage)
                .into(imageMovieImage);

        favorite = findViewById(R.id.button_favorite);
        favoriteState();
        setFavorite();
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    deleteDataFromDatabase();
                } else {
                    inserToDatabase();
                }
                isFavorite = !isFavorite;
                setFavorite();
            }
        });
    }

    public void inserToDatabase() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_TITLE, title);
        values.put(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_DESCRIPTION, description);
        values.put(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_IMAGE, movieImage);
        values.put(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_BACKDROP, backdrop);

        db.insert(FavoriteMovieContract.FavoriteMovie.TABLE_NAME, null, values);

        Snackbar.make(constraintLayout, "is favorited:)", Snackbar.LENGTH_SHORT).show();
    }

    public void deleteDataFromDatabase() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_TITLE + " LIKE ?";
        String[] selectionArgs = {title};

        db.delete(FavoriteMovieContract.FavoriteMovie.TABLE_NAME, selection, selectionArgs);

        Snackbar.make(constraintLayout, "is un - favorited :(", Snackbar.LENGTH_SHORT).show();
    }

    public void favoriteState() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Movie> favorites = new ArrayList<Movie>();
        Cursor cursor = db.query(FavoriteMovieContract.FavoriteMovie.TABLE_NAME,
                new String[]{FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_TITLE},
                FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_TITLE + " =?",
                new String[]{title}, null, null,
                FavoriteMovieContract.FavoriteMovie._ID + " ASC", null);

        cursor.moveToFirst();
        Movie movie;

        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setMovieTitle(cursor.getString(cursor.getColumnIndexOrThrow(FavoriteMovieContract.FavoriteMovie.COLUMN_MOVIE_TITLE)));

                favorites.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        if (!favorites.isEmpty()) {
            isFavorite = true;
        }
    }

    public void setFavorite() {
        if (isFavorite) {
            Glide.with(this).load(R.drawable.ic_favorite_button_after).into(favorite);
        } else {
            Glide.with(this).load(R.drawable.ic_favorite_button_before).into(favorite);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
