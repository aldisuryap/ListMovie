package com.example.listmovie.Fragment;


import android.content.Context;
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
import com.example.listmovie.Data.MovieResponse;
import com.example.listmovie.Network.GetDataService;
import com.example.listmovie.Network.RetrofitClient;
import com.example.listmovie.R;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragments extends Fragment {

    private Context context;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;

    private GetDataService service = RetrofitClient.getRetrofitClient().create(GetDataService.class);
    private Call<MovieResponse> getUpComing;
    private List<Movie> upComing = Collections.emptyList();

    public UpComingFragments() {
        // Required empty public constructor
    }


    public void getUpComing(){
        getUpComing.clone().enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.body() == null){
                    upComing = null;
                }else {
                    upComing = response.body().getMovieResponse();
                }
                setUpRecycleView(upComing);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    private void setUpRecycleView(List<Movie> upComing) {
        adapter = new MovieAdapter(context, upComing);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming_fragments, container, false);

        Log.d("Fragment", "up coming");

        context = getActivity();
        recyclerView = view.findViewById(R.id.rv_up_coming);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));

        getUpComing = service.getUpcoming();

        getUpComing();

        return view;
    }

}
