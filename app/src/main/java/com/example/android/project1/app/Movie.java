package com.example.android.project1.app;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie implements Parcelable {
    private String posterPath;
    private String adult;
    private String overview;
    private String releaseDate;
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private Double popularity;
    private Integer voteCount;
    private Boolean video;
    private Double voteAverage;

    protected Movie(Parcel in) {
        posterPath = in.readString();
        adult = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        id = in.readInt();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        popularity = in.readDouble();
        voteCount = in.readInt();
        voteAverage = in.readDouble();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }



    public Movie(JSONObject movieDetail) {
        try {
            // Set the instance variables to the movie detail values
            setPosterPath(movieDetail.getString("poster_path"));
            setAdult(movieDetail.getString("adult"));
            setOverview(movieDetail.getString("overview"));
            setReleaseDate(movieDetail.getString("release_date"));
            setId(movieDetail.getInt("id"));
            setOriginalTitle(movieDetail.getString("original_title"));
            setOriginalLanguage(movieDetail.getString("original_language"));
            setTitle(movieDetail.getString("title"));
            setBackdropPath(movieDetail.getString("backdrop_path"));
            setPopularity(movieDetail.getDouble("popularity"));
            setVoteCount(movieDetail.getInt("vote_count"));
            setVideo(movieDetail.getBoolean("video"));
            setVoteAverage(movieDetail.getDouble("vote_average"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Uri getMoviePosterUri(){
        String MOVIE_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
        String SIZE_PARAM = "w185";

        try {
            Uri builtUri = Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                    .appendEncodedPath(SIZE_PARAM)
                    .appendEncodedPath(this.posterPath)
                    .build();

            return builtUri;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(adult);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeInt(id);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeString(backdropPath);
        dest.writeDouble(popularity);
        dest.writeInt(voteCount);
        dest.writeDouble(voteAverage);
    }
}
