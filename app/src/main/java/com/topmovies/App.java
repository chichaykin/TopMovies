package com.topmovies;

import android.app.Application;

import com.topmovies.trakt.service.MovieServiceApi;
import com.topmovies.trakt.service.MovieServiceBuilder;

public class App extends Application {

    private static App sInstance;
    private MovieServiceApi mMovieApi;

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        mMovieApi = MovieServiceBuilder.create(getApplicationContext());
    }


    public MovieServiceApi getMovieApi() {
        return mMovieApi;
    }
}