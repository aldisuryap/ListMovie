package com.example.listmovie.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.listmovie.Fragment.FavoriteFragments;
import com.example.listmovie.Fragment.NowPlayingFragments;
import com.example.listmovie.Fragment.UpComingFragments;
import com.example.listmovie.Preferences.Preferences;
import com.example.listmovie.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FragmentManager manager = getSupportFragmentManager();
    private Fragment nowPlaying = new NowPlayingFragments();
    private Fragment favoriteFragment = new FavoriteFragments();
    private Fragment upComingFragment = new UpComingFragments();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_now_playing:
                    nowPlaying = new NowPlayingFragments();
                    manager.beginTransaction().replace(R.id.fragment_container, nowPlaying).commit();
                    return true;
                case R.id.navigation_favorite:
                    favoriteFragment = new FavoriteFragments();
                    manager.beginTransaction().replace(R.id.fragment_container, favoriteFragment).commit();
                    return true;
                case R.id.navigation_up_coming:
                    upComingFragment = new UpComingFragments();
                    manager.beginTransaction().replace(R.id.fragment_container, upComingFragment).commit();
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null){
            manager
                    .beginTransaction()
                    .replace(R.id.fragment_container, nowPlaying)
                    .commit();
        }

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.clearLoggedInUser(getBaseContext());
                startActivity(new Intent(getBaseContext(),LoginActivity.class));
                finish();
            }
        });
    }
}
