package com.napoleao.wikimovie.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.napoleao.wikimovie.R;
import com.napoleao.wikimovie.adapter.MovieAdapter;
import com.napoleao.wikimovie.api.DataService;
import com.napoleao.wikimovie.helper.OMDBConfig;
import com.napoleao.wikimovie.helper.RetrofitConfig;
import com.napoleao.wikimovie.model.Movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static androidx.recyclerview.widget.RecyclerView.HORIZONTAL;

/**
 * Fragment da tela Home (recomendações).
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerMovie;
    private List<Movie> movieList = new ArrayList<>();
    private Retrofit retrofit;
    private ProgressBar progressBar;

    //A API não me fornece recomendações de filmes, para contornar essa limitação, defini alguns
    //filmes que gosto para esta tela.
    private static final String [][] recomendados = {{"The Amazing Spider-Man", "2012"}, {"Birds of Prey", "2020"},
            {"Joker","2019"}, {"Logan", "2017"}, {"Avengers", "2012"}};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        progressBar = view.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        recyclerMovie = view.findViewById(R.id.recyclerMovie);
        recyclerMovie.setVisibility(View.INVISIBLE);

        retrofit = RetrofitConfig.getRetrofit();
        if (movieList.size() == 0){
            getMovies();
        }else{
            recyclerMovie.setVisibility(View.VISIBLE);
            configureRecyclerView();
            progressBar.setVisibility(View.GONE);
        }

    }

    /**
     * Neste método faço a busca dos filmes definidos no meu array de recomendações.
     * Os adiciono na lista que será carregada pelo meu RecyclerView.
     * Carrego o RecyclerView e retiro a ProgressBar após exibir os itens.
     */
    private void getMovies(){
        for (String[] recomendado : recomendados) {
            String movieTitle = recomendado[0].replaceAll(" ", "+");
            String movieYear = recomendado[1];

            DataService dataService = retrofit.create(DataService.class);
            dataService.getMovieByNameAndYear(movieTitle, movieYear, OMDBConfig.PLOT, OMDBConfig.OMDB_API_KEY).enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful()) {
                        Movie movie = response.body();
                        movieList.add(movie);
                        recyclerMovie.setVisibility(View.VISIBLE);
                        configureRecyclerView();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                }
            });
            Log.d("Response", "Size " + movieList.size());
        }
    }

    /**
     * Configura o RecyclerView.
     */
    private void configureRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), HORIZONTAL, false);
        MovieAdapter movieAdapter = new MovieAdapter(getActivity(), movieList);
        recyclerMovie.setLayoutManager(layoutManager);
        recyclerMovie.setHasFixedSize(true);
        recyclerMovie.setAdapter(movieAdapter);
    }

}
