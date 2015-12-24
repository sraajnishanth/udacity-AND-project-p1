package com.example.android.project1.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movieDetail = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie, parent, false);

        // TextView txt = (TextView) rootView.findViewById(R.id.grid_item_movie_imageview);
        // txt.setText(movieDetail.getMoviePosterUri().toString());

        ImageView img = (ImageView) rootView.findViewById(R.id.grid_item_movie_imageview);
        int width = getContext().getResources().getDisplayMetrics().widthPixels;
        Picasso
                .with(getContext())
                .load(movieDetail.getMoviePosterUri())
                //.centerCrop().resize(width /2,width / 2)
                .into(img);
//        Picasso.with(getContext()).load(abc).into(img);
        //Picasso.with(getContext()).load(R.drawable.ic_launcher).into(img);
        return rootView;
    }
}
