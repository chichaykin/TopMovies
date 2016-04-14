package com.topmovies.ui;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.topmovies.R;
import com.topmovies.trakt.data.Movie;

import java.util.ArrayList;
import java.util.List;


public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // Two view types which will be used to determine whether a row should be displaying
    // data or a Progressbar
    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ITEM = 1;
    static final int VISIBLE_THRESHOLD = 5;
    private static final String TAG = "MovieAdapter";
    private final Context mContext;
    private final List<Movie> mList;
    private final RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private EndlessScrollListener mEndlessScrollListener;
    private boolean loading = true;
    private int visibleThreshold = 0;

    public MovieAdapter(Context context, RecyclerView recyclerView, EndlessScrollListener endlessScrollListener) {
        mContext = context;
        mList = new ArrayList<>();
        mEndlessScrollListener = endlessScrollListener;
        mRecyclerView = recyclerView;

        //Support endless list
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            mLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new OnScrollListenerImpl());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        View view;
        if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
            viewHolder = new MovieHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress_layout, null);
            viewHolder = new ProgressViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieHolder) {
            MovieHolder movieHolder = (MovieHolder) holder;
            Movie movie = mList.get(position);
            movieHolder.title.setText(movie.getTitle());
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * return the type of the row,
     * the last row indicates the user that the ListView is loading more data
     */
    @Override
    public int getItemViewType(int position) {
        return (position >= mList.size()) ? VIEW_TYPE_LOADING
                : VIEW_TYPE_ITEM;
    }

    public void addMovies(List<Movie> list) {
        int insertPos = mList.size();
        mList.addAll(list);
        notifyItemRangeInserted(insertPos, list.size());
        loading = true;
    }

    public interface EndlessScrollListener {
        /**
         * Loads more data.
         */
        void onLoadMore();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        }
    }

    private class OnScrollListenerImpl extends RecyclerView.OnScrollListener {
        private int visibleThreshold = 5;
        private int previousTotal = 0;

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = mRecyclerView.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

            if (!loading) {
                if (totalItemCount > previousTotal) {
                    loading = true;
                    previousTotal = totalItemCount;
                }
            }
            if (loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {

                loading = false;
                mEndlessScrollListener.onLoadMore();
            }
        }
    }


}
