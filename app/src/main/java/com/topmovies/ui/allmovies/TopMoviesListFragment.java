package com.topmovies.ui.allmovies;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topmovies.R;
import com.topmovies.presenters.Presenter;
import com.topmovies.presenters.allmovies.AllMoviesView;
import com.topmovies.presenters.allmovies.TopMoviesPresenterImpl;
import com.topmovies.trakt.data.Movie;
import com.topmovies.ui.recyclerview.EndlessOnScrollListenerImpl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

@SuppressWarnings("unused")
public class TopMoviesListFragment extends Fragment implements AllMoviesView {

    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private EasyRecyclerAdapter<Movie> mAdapter;
    private Presenter mPresenter;

    public static TopMoviesListFragment newInstance() {
        return new TopMoviesListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.top_movies_list_fragment, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new EasyRecyclerAdapter<>(getContext(), MovieViewHolder.class);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter = new TopMoviesPresenterImpl(this);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    @Override
    public void init(EndlessOnScrollListenerImpl.DataLoader loader) {
        mRecyclerView.addOnScrollListener(new EndlessOnScrollListenerImpl(mRecyclerView, loader));
    }

    @Override
    public void add(List<Movie> movies) {
        mAdapter.addItems(movies);
    }


}
