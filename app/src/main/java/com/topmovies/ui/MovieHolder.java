package com.topmovies.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.topmovies.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.title)
    public TextView title;

    public MovieHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
