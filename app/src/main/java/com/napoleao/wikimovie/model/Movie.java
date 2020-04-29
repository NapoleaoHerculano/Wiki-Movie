package com.napoleao.wikimovie.model;

import java.io.Serializable;

/**
 * Classe de modelo para o objeto que contém as informações dos filmes.
 * Estou implementando Serializable para habilitar a capacidade de enviar um objeto entre telas.
 * O id é utilizado apenas para salvamento nos favoritos.
 * O restante dos atributos devem seguir a ordem e nomenclatura definidos pelo retorno da API, senão
 * o Retrofit não irá conseguir montar os objetos.
 */
public class Movie implements Serializable {
    private Integer id;
    private String Title;
    private String Year;
    private String Poster;
    private String Released;
    private String Rated;
    private String Runtime;
    private String Genre;
    private String Plot;
    private String Metascore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getPoster() {
        return Poster;
    }

    public String getReleased() {
        return Released;
    }

    public String getRated() {
        return Rated;
    }

    public String getRuntime() {
        return Runtime;
    }

    public String getGenre() {
        return Genre;
    }

    public String getPlot() {
        return Plot;
    }

    public String getMetascore() {
        return Metascore;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setYear(String year) {
        Year = year;
    }

    public void setPoster(String poster) {
        Poster = poster;
    }

    public void setReleased(String released) {
        Released = released;
    }

    public void setRated(String rated) {
        Rated = rated;
    }

    public void setRuntime(String runtime) {
        Runtime = runtime;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

}
