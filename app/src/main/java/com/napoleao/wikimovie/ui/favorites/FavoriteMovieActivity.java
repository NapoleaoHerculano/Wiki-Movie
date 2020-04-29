package com.napoleao.wikimovie.ui.favorites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.napoleao.wikimovie.R;
import com.napoleao.wikimovie.model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Classe responsável por exibir os detalhes dos filmes favoritos do usuário.
 */
public class FavoriteMovieActivity extends AppCompatActivity {
    private ImageView imagePoster;
    private TextView textTitle, textYear, textRated, textReleased, textRuntime, textGenre, textPlot;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_favorite);

        imagePoster = findViewById(R.id.moviePosterCollapsing);
        textTitle = findViewById(R.id.titleMovie);
        textYear = findViewById(R.id.yearMovie);
        textRated = findViewById(R.id.ratedMovie);
        textReleased = findViewById(R.id.releasedMovie);
        textRuntime = findViewById(R.id.runtimeMovie);
        textGenre = findViewById(R.id.genreMovie);
        textPlot = findViewById(R.id.plotMovie);

        //Capturando um objeto vindo de outra tela e depois montando a visualização
        //com seus dados.
        movie = (Movie) getIntent().getSerializableExtra("searchObject");
        mountPreview();
    }

    /**
     * Obtendo a imagem a partir de uma URL e depois exibindo.
     * @param url link da imagem.
     */
    private void urlToImageView(String url){
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(imagePoster);
    }

    /**
     * Setando as informações do filme nos componentes da interface.
     */
    private void mountPreview(){
        urlToImageView(movie.getPoster());
        textTitle.setText(movie.getTitle());
        textYear.setText(movie.getYear());
        textRated.setText(movie.getRated());
        textReleased.setText(movie.getReleased());
        textRuntime.setText(movie.getRuntime());
        textGenre.setText(movie.getGenre());
        textPlot.setText(movie.getPlot());

    }

    /**
     * Fechando a Activity após clicar no botão de voltar.
     */
    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
