package com.topmovies.presenters.search;


import android.text.TextUtils;
import android.util.Log;

import com.topmovies.App;
import com.topmovies.presenters.EndlessPresenterImpl;
import com.topmovies.trakt.data.SearchItem;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPresenterImpl extends EndlessPresenterImpl implements SearchPresenter {

    private static final String TAG = SearchPresenterImpl.class.getSimpleName();
    private String mQuery;

    public SearchPresenterImpl(SearchView view) {
        super(view);
    }

    @Override
    protected void updateAdapter() {
        if (TextUtils.isEmpty(mQuery)) {
            return;
        }

        getSubscription().add(App.getInstance().getMovieApi().search(mQuery, getPage())
                .timeout(5, TimeUnit.SECONDS)
                .retry(2)
                .delay(500, TimeUnit.MILLISECONDS)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SearchItem>>() {

                    @Override
                    public void onCompleted() {}

                    @Override
                    public void onError(Throwable e) {e.printStackTrace();}

                    @Override
                    public void onNext(List<SearchItem> searchItems) {
                        Log.d("Service", "OnNext");
                        for (SearchItem e : searchItems) {
                            Log.d("Search item", e.toString());
                        }
                        ((SearchView)getView()).add(searchItems);
                    }
                }));
    }


    @Override
    public void search(String query) {
        Log.d(TAG, "Start query: " + query);
        getSubscription().clear();
        mQuery = query;
        setFirstPage();
        ((SearchView)getView()).emptyItems();
        updateAdapter();
    }

}
