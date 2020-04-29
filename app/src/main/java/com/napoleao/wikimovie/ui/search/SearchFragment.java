package com.napoleao.wikimovie.ui.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.napoleao.wikimovie.R;
import com.napoleao.wikimovie.adapter.SearchAdapter;
import com.napoleao.wikimovie.api.DataService;
import com.napoleao.wikimovie.helper.OMDBConfig;
import com.napoleao.wikimovie.helper.RecyclerItemClickListener;
import com.napoleao.wikimovie.helper.RetrofitConfig;
import com.napoleao.wikimovie.model.Movie;
import com.napoleao.wikimovie.model.MovieResponse;
import com.napoleao.wikimovie.ui.home.MovieDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerSearch;
    private List<Movie> movies = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private Retrofit retrofit;
    private ProgressBar progressBar;
    private MovieResponse movieResponse;
    private MaterialSearchView materialSearchView;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerSearch = view.findViewById(R.id.recyclerSearch);
        progressBar = view.findViewById(R.id.progressSearch);
        progressBar.setVisibility(View.INVISIBLE);
        retrofit = RetrofitConfig.getRetrofit();

    }

    /**
     * Método para realizar a busca do filme solicitado pelo usuário.
     * @param movie nome do filme.
     */
    private void searchMovie(String movie){
        String movieTitle = movie.replaceAll(" ", "+");

        DataService dataService = retrofit.create(DataService.class);
        dataService.getMovies(movieTitle, OMDBConfig.TYPE, OMDBConfig.OMDB_API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if( response.isSuccessful()){
                    movieResponse = response.body();
                    movies = movieResponse.getSearchList();
                    if (movies.size() == 0){
                        Toast.makeText(getActivity(), "Filme não encontrado! Indisponível na API.", Toast.LENGTH_SHORT).show();
                    }
                    configureRecyclerView();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Houve algum problema ao requisitar a API!", Toast.LENGTH_SHORT).show();
                Log.d("Reponse", "Response: " + t.toString());
            }
        });
    }

    /**
     * Método utilizado para adicionar e configurar o ícone de busca na Toolbar do Fragment.
     * @param menu menu que estou utilizando neste fragment.
     * @param inflater utilizado para carregar a visualização.
     */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.menu_search);

        materialSearchView = getActivity().findViewById(R.id.searchView);
        materialSearchView.setMenuItem(item);
        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                searchMovie(query);
                materialSearchView.closeSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * Configura o RecyclerView.
     * A visualização e os eventos de clique.
     */
    private void configureRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        searchAdapter = new SearchAdapter(getActivity(), movies);
        recyclerSearch.setLayoutManager(layoutManager);
        recyclerSearch.setHasFixedSize(true);
        recyclerSearch.setAdapter(searchAdapter);
        recyclerSearch.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerSearch, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent it = new Intent(getActivity(), MovieDetailsActivity.class);
                it.putExtra("movieName", movies.get(position).getTitle());
                it.putExtra("movieYear", movies.get(position).getYear());
                startActivity(it);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));
    }

}
