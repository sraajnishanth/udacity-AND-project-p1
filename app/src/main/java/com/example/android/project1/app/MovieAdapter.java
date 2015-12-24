package com.example.android.project1.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movieDetail = getItem(position);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_forecast, parent, false);

        TextView txt = (TextView) rootView.findViewById(R.id.list_item_forecast_textview);
        txt.setText(movieDetail.getMoviePosterUrl().toString());

        return rootView;
    }
}
