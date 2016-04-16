package com.topmovies.presenters;


import android.util.Log;

import com.topmovies.ui.recyclerview.EndlessOnScrollListenerImpl;

import rx.subscriptions.CompositeSubscription;

public abstract class EndlessPresenterImpl implements Presenter {
    private final EndlessView mView;
    private final String TAG;
    private int mPage = 1;
    private final CompositeSubscription mCompositeSubscription;

    protected EndlessPresenterImpl(EndlessView view) {
        TAG = this.getClass().getSimpleName();
        mView = view;
        mCompositeSubscription = new CompositeSubscription();

        view.init(new EndlessOnScrollListenerImpl.DataLoader() {

            @Override
            public void onLoadMore() {
                mPage++;
                Log.d(TAG, "Start loading page =" + mPage);
                updateAdapter();
            }
        });

        updateAdapter();
    }

    protected EndlessView getView() {
        return mView;
    }

    protected int getPage() {
        return mPage;
    }

    protected void setFirstPage() {
        mPage = 1;
    }

    protected CompositeSubscription getSubscription() {
        return mCompositeSubscription;
    }

    protected abstract void updateAdapter();

    @Override
    public void destroy() {
        mCompositeSubscription.unsubscribe();
    }
}
