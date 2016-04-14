package com.topmovies;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.topmovies.trakt.service.MovieServiceApi;
import com.topmovies.trakt.service.MovieServiceBuilder;

public class App extends Application {

    private static App sInstance;
    private SharedPreferences mPreferences;
    private MovieServiceApi mMovieApi;

    public static App getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPreferences = getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);

        sInstance = this;
        mMovieApi = MovieServiceBuilder.create(getApplicationContext());
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }


    public MovieServiceApi getMovieApi() {
        return mMovieApi;
    }
}