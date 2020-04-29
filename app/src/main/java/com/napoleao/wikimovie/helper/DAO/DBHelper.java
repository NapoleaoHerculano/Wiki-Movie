package com.napoleao.wikimovie.helper.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Classe responsável por criar o Banco de Dados para salvamento dos filmes favoritos do usuário.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "WIKI_MOVIE_DB";
    public static String TABLE_FAVORITES = "favorites";
    public static int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    /**
     * Criando a tabela resposável por guardar os filmes favoritos.
     * @param db banco de dados nativo do Android. Utilizo-o para facilitar os comandos SQL.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_FAVORITES +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR(60)," +
                " year VARCHAR(10), poster TEXT, released VARCHAR(10)," +
                " rated VARCHAR(10), runtime VARCHAR(10), genre VARCHAR(50)," +
                " plot TEXT, UNIQUE(title, year))";

        try {
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar tabela");

        } catch (Exception e) {
            Log.i("INFO DB", "Erro ao criar tabela " + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
