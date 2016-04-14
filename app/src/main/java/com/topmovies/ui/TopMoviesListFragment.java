package com.topmovies.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topmovies.App;
import com.topmovies.R;
import com.topmovies.trakt.data.Movie;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class TopMoviesListFragment extends Fragment {
    private static final String TAG = TopMoviesListFragment.class.getSimpleName();
    private MovieAdapter mAdapter;
    private int mPage = 1;
    private CompositeSubscription mCompositeSubscription;

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public static TopMoviesListFragment newInstance() {
        return new TopMoviesListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.top_movies_list_fragment, container, false);
        ButterKnife.bind(this, rootView);

        Context context = getContext();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mAdapter = new MovieAdapter(context, mRecyclerView, new MovieAdapter.EndlessScrollListener() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "Current page =" + mPage);
                //mAdapter.add(null);
                mPage++;
                updateAdapter();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mCompositeSubscription = new CompositeSubscription();
        updateAdapter();
        return rootView;
    }

    private void updateAdapter() {
        mCompositeSubscription.add(App.getInstance().getMovieApi().getMovies(mPage)
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SubscriptionImpl()));
    }

    private class SubscriptionImpl extends Subscriber<List<Movie>> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onNext(List<Movie> movies) {
            Log.d("Service", "OnNext");
            for (Movie e : movies) {
                Log.d("Service", e.toString());
            }
            mAdapter.addMovies(movies);
        }

    }

}
