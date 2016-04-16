package com.topmovies.ui.allmovies;

import android.view.View;
import android.widget.TextView;

import com.topmovies.R;
import com.topmovies.trakt.data.Movie;

import uk.co.ribot.easyadapter.ItemViewHolder;
import uk.co.ribot.easyadapter.PositionInfo;
import uk.co.ribot.easyadapter.annotations.LayoutId;
import uk.co.ribot.easyadapter.annotations.ViewId;

@SuppressWarnings("unused")
@LayoutId(R.layout.movie_view_holder)
public class MovieViewHolder extends ItemViewHolder<Movie> {

    @ViewId(R.id.title)
    TextView mTitle;

    public MovieViewHolder(View view) {
        super(view);
    }

    @Override
    public void onSetValues(Movie item, PositionInfo positionInfo) {
        mTitle.setText(item.getTitle());
    }
}
