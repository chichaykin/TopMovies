package com.topmovies.ui.search;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.topmovies.R;
import com.topmovies.trakt.data.Movie;
import com.topmovies.trakt.data.SearchItem;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@SuppressWarnings("unused")
@LayoutId(R.layout.search_item_view_holder)
public class SearchItemViewHolder extends ItemViewHolder<SearchItem> {

    @ViewId(R.id.title)
    private
    TextView mTitleView;

    @ViewId(R.id.year)
    private
    TextView mYearView;

    @ViewId(R.id.overview)
    private
    TextView mOverviewView;

    @ViewId(R.id.poster)
    private
    ImageView mPosterView;

    public SearchItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(SearchItem item, PositionInfo positionInfo) {
        Movie movie = item.getMovie();
        mTitleView.setText(movie.getTitle());
        mYearView.setText(Integer.toString(movie.getYear()));
        mOverviewView.setText(movie.getOverview());

        if (TextUtils.isEmpty(movie.getThumb())) {
            mPosterView.setVisibility(View.GONE);
        } else {
            Picasso.with(getContext())
                    .load(movie.getThumb())
                    .into(mPosterView);
        }
    }
}
