package com.topmovies.ui.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.topmovies.R;
import com.topmovies.presenters.search.SearchPresenter;
import com.topmovies.presenters.search.SearchPresenterImpl;
import com.topmovies.presenters.search.SearchView;
import com.topmovies.trakt.data.SearchItem;
import com.topmovies.ui.recyclerview.EndlessOnScrollListenerImpl;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.ribot.easyadapter.EasyRecyclerAdapter;

@SuppressWarnings("unused")
public class SearchResultFragment extends Fragment implements SearchView {

    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.search_request)
    EditText mRequestView;

    @SuppressWarnings("WeakerAccess")
    @Bind(R.id.search_results)
    RecyclerView mRecyclerView;

    private SearchPresenter mPresenter;
    private EasyRecyclerAdapter<SearchItem> mAdapter;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_result_fragment, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new EasyRecyclerAdapter<>(getContext(), SearchItemViewHolder.class);
        mRecyclerView.setAdapter(mAdapter);

        mPresenter = new SearchPresenterImpl(this);

        mRequestView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                mPresenter.search(text);
            }
        });
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroy();
    }

    @Override
    public void add(List<SearchItem> searchItems) {
        mAdapter.addItems(searchItems);
    }

    @Override
    public void emptyItems() {
        mAdapter.setItems(new ArrayList<SearchItem>());
    }

    @Override
    public void init(EndlessOnScrollListenerImpl.DataLoader loader) {
        mRecyclerView.addOnScrollListener(new EndlessOnScrollListenerImpl(mRecyclerView, loader));
    }
}
