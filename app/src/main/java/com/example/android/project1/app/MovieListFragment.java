
package com.example.android.project1.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MovieListFragment extends Fragment {

    private MovieAdapter movieListAdapter;
    private ArrayList<Movie> movieArrayList;
    public static final String MOVIES_KEY = "movies";

    public MovieListFragment() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIES_KEY, movieArrayList);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);

        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIES_KEY)) {
            updateMovieList();
        } else {
            movieArrayList = savedInstanceState.<Movie>getParcelableArrayList(MOVIES_KEY);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here.
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        movieListAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        gridView.setAdapter(movieListAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movieDetail = movieListAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class)
                        .putExtra(getResources().getString(R.string.movie_intent_extra), movieDetail);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void updateMovieList() {
        FetchMovieListTask movieListTask = new FetchMovieListTask();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortKey = sharedPrefs.getString(
                getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_popularity));
        movieListTask.execute(sortKey);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovieList();
    }

    public class FetchMovieListTask extends AsyncTask<String, Void, Movie[]> {

        private final String LOG_TAG = FetchMovieListTask.class.getSimpleName();



        /**
         * Take the String representing the complete movie list in JSON Format and
         * pull out the data we need
         */
        private Movie[] getMovieDataFromJson(String movieListJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_LIST = "results";

            JSONObject movieListJson = new JSONObject(movieListJsonStr);
            JSONArray movieArray = movieListJson.getJSONArray(TMDB_LIST);

            Movie[] movieObjArray = new Movie[movieArray.length()];


            for(int i = 0; i < movieArray.length(); i++) {

                // Get the JSON object representing the movie
                JSONObject movieDetail = movieArray.getJSONObject(i);
                Movie movieObject = new Movie(movieDetail);
                movieObjArray[i] = movieObject;

            }
            return movieObjArray;

        }
        @Override
        protected Movie[] doInBackground(String... params) {

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            if (params.length == 0) {
                return null;
            }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String movieListJsonStr = null;

            try {
                // Construct the URL for the Movie Database Query
                final String THE_MOVIE_DB_BASE_URL =
                        "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_PARAM = "sort_by";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(THE_MOVIE_DB_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, params[0])
                        .appendQueryParameter(APPID_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                // Create the request to TheMovieDatabase, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieListJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(movieListJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the data.
            return null;
        }

        @Override
        protected void onPostExecute(Movie[] result) {
            if (result != null) {
                movieListAdapter.clear();
                for(Movie movieDetailObj : result) {
                    movieListAdapter.add(movieDetailObj);
                }
                // New data is back from the server.
            }
        }
    }
}
