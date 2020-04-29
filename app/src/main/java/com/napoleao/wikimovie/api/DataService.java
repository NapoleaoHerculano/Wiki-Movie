package com.napoleao.wikimovie.api;

import com.napoleao.wikimovie.model.Movie;
import com.napoleao.wikimovie.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DataService {

    /**
     * Realiza uma requisição de busca à OMDB API.
     * @param s nome do filme que o usuário deseja buscar.
     * @param type a API também contém séries, usamos esse atributo para o retorno conter apenas
     *             filmes.
     * @param apikey chave de acesso a API.
     * @return lista de filmes com o nome buscado.
     */
    @GET("?")
    Call<MovieResponse> getMovies(
            @Query("s") String s,
            @Query("type") String type,
            @Query("apikey") String apikey
    );

    /**
     * Realiza uma requisição individual de busca pelo nome do filme.
     * @param t nome do filme que o usuário deseja informações.
     * @param plot define o tamanho da sinopse que desejamos para retorno (full ou short).
     * @param apikey chave de acesso a API.
     * @return objeto com as informações do filme buscado.
     */
    @GET("?")
    Call<Movie> getMovieByName(
            @Query("t") String t,
            @Query("plot") String plot,
            @Query("apikey") String apikey
    );

    /**
     * Realiza uma requisição individual, porém mais específica, já que podem existir filmes com o
     * mesmo nome.
     * @param t nome do filme que o usuário deseja informações.
     * @param y ano de lançamento do filme.
     * @param plot define o tamanho da sinopse que desejamos para retorno (full ou short).
     * @param apikey chave de acesso a API.
     * @return objeto com as informações do filme buscado.
     */
    @GET("?")
    Call<Movie> getMovieByNameAndYear(
            @Query("t") String t,
            @Query("y") String y,
            @Query("plot") String plot,
            @Query("apikey") String apikey
    );
}
