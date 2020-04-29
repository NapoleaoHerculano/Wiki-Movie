package com.napoleao.wikimovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.napoleao.wikimovie.R;
import com.napoleao.wikimovie.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private Context context;
    private List<Movie> movieList;

    public SearchAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    /**
     * Cria a visualização do RecyclerView.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemList = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_search, parent, false);
        return new SearchAdapter.MyViewHolder(itemList);
    }

    /**
     * Exibe os itens da lista no RecyclerView.
     * @param holder
     * @param position posição do item na lista.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Movie movie = movieList.get(position);

        String url = movie.getPoster();
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(holder.movieSearchImage);

        holder.movieSearchTitle.setText(movie.getTitle());
        holder.movieSearchYear.setText(movie.getYear());
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

        ImageView movieSearchImage;
        TextView movieSearchTitle, movieSearchYear;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            movieSearchImage = itemView.findViewById(R.id.movieSearchImage);
            movieSearchTitle = itemView.findViewById(R.id.movieTitleSearch);
            movieSearchYear = itemView.findViewById(R.id.movieYearSearch);
        }
    }
}
