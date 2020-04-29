package com.napoleao.wikimovie.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.napoleao.wikimovie.R;
import com.napoleao.wikimovie.api.DataService;
import com.napoleao.wikimovie.helper.DAO.SearchDAO;
import com.napoleao.wikimovie.helper.OMDBConfig;
import com.napoleao.wikimovie.helper.RetrofitConfig;
import com.napoleao.wikimovie.model.Movie;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieDetailsActivity extends AppCompatActivity {
    private NestedScrollView detailsTexts;
    private ImageView imagePoster;
    private TextView textTitle, textYear, textRated, textReleased, textRuntime, textGenre, textPlot;
    private Movie movie;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_colapsed);

        detailsTexts = findViewById(R.id.detailsTexts);
        detailsTexts.setVisibility(View.INVISIBLE);

        progressBar = findViewById(R.id.progressMovieDetails);
        progressBar.setVisibility(View.VISIBLE);

        imagePoster = findViewById(R.id.moviePosterCollapsing);
        textTitle = findViewById(R.id.titleMovie);
        textYear = findViewById(R.id.yearMovie);
        textRated = findViewById(R.id.ratedMovie);
        textReleased = findViewById(R.id.releasedMovie);
        textRuntime = findViewById(R.id.runtimeMovie);
        textGenre = findViewById(R.id.genreMovie);
        textPlot = findViewById(R.id.plotMovie);
        FloatingActionButton buttonFavorite = findViewById(R.id.btnFavoriteMovie);

        Bundle intent = getIntent().getExtras();
        String movieName = intent.getString("movieName");
        String filmYear = intent.getString("movieYear");
        Log.d("Search", "MovieName " + movieName);
        Log.d("Search", "movieYear " + filmYear);
        movie = (Movie) getIntent().getSerializableExtra("searchObject");

        if (movie != null){
            mountPreview();
            progressBar.setVisibility(View.GONE);
            detailsTexts.setVisibility(View.VISIBLE);
        }else {
            getMovie(movieName, filmYear);
        }

        buttonFavorite.setOnClickListener(v -> {
            SearchDAO searchDAO = new SearchDAO(getApplicationContext());
            View view = findViewById(R.id.movieDetailsActivity);
            searchDAO.save(movie, view);
        });

    }

    private void urlToImageView(String url){
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(imagePoster);
    }

    private void getMovie(String movieName, String movieYear){
            String movieTitle = movieName.replaceAll(" ", "+");
            Retrofit retrofit = RetrofitConfig.getRetrofit();

            DataService dataService = retrofit.create(DataService.class);
            dataService.getMovieByNameAndYear(movieTitle, movieYear, OMDBConfig.PLOT, OMDBConfig.OMDB_API_KEY).enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful()) {
                        movie = response.body();
                        mountPreview();
                        progressBar.setVisibility(View.GONE);
                        detailsTexts.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Details unavailable!", Toast.LENGTH_LONG).show();
                    Log.d("Response", "Response: " + t.getMessage());
                    finish();
                }
            });

    }

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

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
