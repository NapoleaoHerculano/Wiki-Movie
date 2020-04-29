package com.napoleao.wikimovie.helper.DAO;

import android.view.View;

import com.napoleao.wikimovie.model.Movie;

import java.util.List;

/**
 * Interface utilizada para definir os métodos que utilizo no Banco de Dados.
 */
public interface ISearchDAO {

    boolean save(Movie movie, View v);
    boolean delete(Movie movie);
    List<Movie> listAll();
}
