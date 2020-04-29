package com.napoleao.wikimovie.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.napoleao.wikimovie.R;
import com.napoleao.wikimovie.adapter.SearchAdapter;
import com.napoleao.wikimovie.helper.DAO.SearchDAO;
import com.napoleao.wikimovie.helper.RecyclerItemClickListener;
import com.napoleao.wikimovie.model.Movie;

import java.util.List;
import java.util.Objects;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerFavorites;
    private List<Movie> favorites;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerFavorites = view.findViewById(R.id.recyclerFavorites);
        recyclerFavorites.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerFavorites, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent it = new Intent(getActivity(), FavoriteMovieActivity.class);
                        it.putExtra("searchObject", favorites.get(position));
                        startActivity(it);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Movie selectedMovie = favorites.get(position);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                        dialog.setTitle("Delete");
                        dialog.setMessage("Do you want to delete the movie - " + selectedMovie.getTitle() + "?");
                        dialog.setPositiveButton("Yes", (dialog1, which) -> {
                            SearchDAO searchDAO = new SearchDAO(getContext());
                            if (searchDAO.delete(selectedMovie)){
                                configurarRecyclerView();
                                Toast.makeText(getContext(), "Success in deleting favorite!", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), "Error deleting bookmark!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        dialog.setNegativeButton("No", null);

                        dialog.create();
                        dialog.show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));

        configurarRecyclerView();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        configurarRecyclerView();
        super.onStart();
    }

    private void configurarRecyclerView() {
        SearchDAO searchDAO = new SearchDAO(getContext());
        favorites = searchDAO.listAll();
        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        SearchAdapter searchAdapter = new SearchAdapter(getActivity(), favorites);
        recyclerFavorites.setLayoutManager(layoutManager);
        recyclerFavorites.setHasFixedSize(true);
        recyclerFavorites.setAdapter(searchAdapter);
    }
}
