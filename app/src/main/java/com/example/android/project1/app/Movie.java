package com.example.android.project1.app;


import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class Movie {
    public String posterPath;
    public Boolean adult;
    public String overview;
    public String releaseDate;
    public int id;
    public String originalTitle;
    public String originalLanguage;
    public String title;
    public String backdropPath;
    public Double popularity;
    public Integer voteCount;
    public Boolean video;
    public Double voteAverage;

    public Movie(JSONObject movieDetail) {
        try {
            this.posterPath = movieDetail.getString("poster_path");
            this.adult = movieDetail.getBoolean("adult");
            this.overview = movieDetail.getString("overview");
            this.releaseDate = movieDetail.getString("release_date");
            this.id = movieDetail.getInt("id");
            this.originalTitle = movieDetail.getString("original_title");
            this.originalLanguage = movieDetail.getString("original_language");
            this.title = movieDetail.getString("title");
            this.backdropPath = movieDetail.getString("backdrop_path");
            this.popularity = movieDetail.getDouble("popularity");
            this.voteCount = movieDetail.getInt("vote_count");
            this.video = movieDetail.getBoolean("video");
            this.voteAverage = movieDetail.getDouble("vote_average");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public URL getMoviePosterUrl(){
        String MOVIE_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
        String SIZE_PARAM = "w185";

        try {
            Uri builtUri = Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                    .appendEncodedPath(SIZE_PARAM)
                    .appendEncodedPath(this.posterPath)
                    .build();

            URL url = new URL(builtUri.toString());
            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
