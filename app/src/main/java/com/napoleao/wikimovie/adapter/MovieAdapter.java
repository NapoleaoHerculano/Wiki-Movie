package com.napoleao.wikimovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.napoleao.wikimovie.R;
import com.napoleao.wikimovie.model.Movie;
import com.napoleao.wikimovie.ui.home.MovieDetailsActivity;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(Context context, List<Movie> movieList){
        this.movieList = movieList;
        this.context = context;
    }

    /**
     * Cria a visualização do RecyclerView.
     * @param parent
     * @param i
     * @return
     */
    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_movie, parent, false);
        return new MovieAdapter.MyViewHolder(itemList);
    }

    /**
     * Exibe os itens da lista no RecyclerView.
     * @param myViewHolder
     * @param i posição do item na lista.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Movie movie = movieList.get(i);
        myViewHolder.movieTitle.setText(movie.getTitle());

        String url = movie.getPoster();
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(myViewHolder.moviePoster);

        myViewHolder.detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, MovieDetailsActivity.class);
                it.putExtra("searchObject", movie);
                context.startActivity(it);
            }
        });
        double rating = (Double.parseDouble(movie.getMetascore())/100)*5;
        myViewHolder.ratingBar.setRating((float) rating);

        String movieTimeGenre = movie.getRuntime() + " | " + movie.getGenre();
        myViewHolder.movieTimeGenre.setText(movieTimeGenre);
    }

    /**
     *
     * @return quantidade total de itens na lista.
     */
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        View movieFragment;
        ImageView moviePoster;
        TextView movieTitle, movieTimeGenre;
        FloatingActionButton detailsButton;
        ScaleRatingBar ratingBar;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            movieFragment = itemView.findViewById(R.id.movieFragment);
            movieTitle = itemView.findViewById(R.id.movieName);
            movieTimeGenre = itemView.findViewById(R.id.movieTimeGenre);
            moviePoster = itemView.findViewById(R.id.moviePoster);
            detailsButton = itemView.findViewById(R.id.detailsButton);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
