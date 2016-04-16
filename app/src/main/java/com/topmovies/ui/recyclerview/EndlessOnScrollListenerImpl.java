package com.topmovies.ui.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class EndlessOnScrollListenerImpl extends RecyclerView.OnScrollListener {
    private static final int VISIBLE_THRESHOLD = 5;
    private final RecyclerView mRecyclerView;
    private final DataLoader mDataLoader;
    private LinearLayoutManager mLayoutManager;
    private int mPreviousTotal = 0;
    private boolean mIsLoading;

    public EndlessOnScrollListenerImpl(@NonNull RecyclerView recyclerView, @NonNull DataLoader loader) {
        mRecyclerView = recyclerView;
        mDataLoader = loader;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(this);
        } else {
            throw new RuntimeException("Don't support another layout managers for a while");
        }
    }

    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = mRecyclerView.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

        if (!mIsLoading) {
            if (totalItemCount > mPreviousTotal) {
                mIsLoading = true;
                mPreviousTotal = totalItemCount;
            }
        }
        if (mIsLoading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + VISIBLE_THRESHOLD)) {

            mIsLoading = false;
            mDataLoader.onLoadMore();
        }
    }

    /***
     * Implement to load more data.
     */
    public interface DataLoader {
        void onLoadMore();
    }

}
