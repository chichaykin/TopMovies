package com.topmovies.presenters.allmovies;

import android.util.Log;

import com.topmovies.App;
import com.topmovies.presenters.EndlessPresenterImpl;
import com.topmovies.trakt.data.Movie;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TopMoviesPresenterImpl extends EndlessPresenterImpl {

    public TopMoviesPresenterImpl(AllMoviesView view) {
        super(view);
    }

    @Override
    protected void updateAdapter() {
        getSubscription().add(App.getInstance().getMovieApi().getMovies(getPage())
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Movie>>() {

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onNext(List<Movie> movies) {
                        Log.d("Service", "OnNext");
                        for (Movie e : movies) {
                            Log.d("Service", e.toString());
                        }
                        ((AllMoviesView)getView()).add(movies);
                    }
                }));
    }

}
