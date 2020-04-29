package com.napoleao.wikimovie.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Classe responsável por configurar o Retrofit para o consumo da API.
 */
public class RetrofitConfig {

    /**
     * Método estático para acesso em diferentes partes do código.
     * @return acesso ao retrofit.
     */
    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(OMDBConfig.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
