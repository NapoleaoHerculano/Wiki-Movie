package com.napoleao.wikimovie.helper.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.napoleao.wikimovie.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por implementar os métodos para utilização do Banco de Dados.
 */
public class SearchDAO implements ISearchDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;
    private Context context;

    public SearchDAO(Context context){
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        write = dbHelper.getWritableDatabase();
        read = dbHelper.getReadableDatabase();
    }

    /**
     * Método responsável por salvar um filme como favorito.
     * @param movie filme escolhido pelo usuário.
     * @param v view onde o método será chamado. A utilizo para poder mostrar a Snackbar.
     * @return true se for possível persistir.
     *         false se o filme já existir no BD.
     */
    @Override
    public boolean save(Movie movie, View v) {
        ContentValues cv = new ContentValues();
        cv.put("title", movie.getTitle());
        cv.put("year", movie.getYear());
        cv.put("poster", movie.getPoster());
        cv.put("released", movie.getReleased());
        cv.put("rated", movie.getRated());
        cv.put("runtime", movie.getRuntime());
        cv.put("genre", movie.getGenre());
        cv.put("plot", movie.getPlot());
        try{
            write.insertOrThrow(DBHelper.TABLE_FAVORITES, null, cv);
            Snackbar snackbar = Snackbar.make(v, "Added to Favorites!", Snackbar.LENGTH_LONG);
            snackbar.show();
            Log.i("INFO ", "Filme salvo com sucesso!");
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "This movie is already added to favorites!", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * Método utilizado para excluir um filme dos favoritos.
     * @param movie filme escolhido pelo usuário.
     * @return true se a exclusão for bem sucedida.
     *         false se ocorrer algum erro durante o processo.
     */
    @Override
    public boolean delete(Movie movie) {
        try{
            String[] args = {movie.getId().toString()};
            write.delete(DBHelper.TABLE_FAVORITES, "id=?", args);
            Log.i("INFO ", "Filme removido com sucesso!");
        } catch (Exception e) {
            Log.e("INFO", "Erro ao remover filme " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Método responsável por retornar todos os filmes favoritos do usuário.
     * @return List com todos os filmes favoritos.
     */
    @Override
    public List<Movie> listAll() {
        List<Movie> favorites = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABLE_FAVORITES + " ;";
        Cursor cursor = read.rawQuery(sql, null);

        cursor.move(0);//Por alguma razão que desconheço, o cursor não inicializava no primeiro índice, por isso setei o índice 0 como inicial.
        while (cursor.moveToNext()){
            Movie movie = new Movie();
            Integer id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String year = cursor.getString(cursor.getColumnIndex("year"));
            String poster = cursor.getString(cursor.getColumnIndex("poster"));
            String released = cursor.getString(cursor.getColumnIndex("released"));
            String rated = cursor.getString(cursor.getColumnIndex("rated"));
            String runtime = cursor.getString(cursor.getColumnIndex("runtime"));
            String genre = cursor.getString(cursor.getColumnIndex("genre"));
            String plot = cursor.getString(cursor.getColumnIndex("plot"));

            movie.setId(id);
            movie.setTitle(title);
            movie.setYear(year);
            movie.setPoster(poster);
            movie.setReleased(released);
            movie.setRated(rated);
            movie.setRuntime(runtime);
            movie.setGenre(genre);
            movie.setPlot(plot);

            favorites.add(movie);
            Log.i("Size: ", "List Size " + favorites.size());
        }
        cursor.close();

        return favorites;

    }
}
