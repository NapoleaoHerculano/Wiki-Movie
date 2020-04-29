package com.napoleao.wikimovie.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por guardar os objetos retornados pela busca da API.
 * O atributo List<Movie> deve possuir o mesmo nome do retorno da API, senão o retrofit não irá
 * conseguir recuperar os objetos.
 */
public class MovieResponse {

    private List<Movie> Search = new ArrayList<>();

    public List<Movie> getSearchList() {
        return Search;
    }
}
