package com.topmovies.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.topmovies.R;


public class SectionsPagerAdapter
        extends FragmentPagerAdapter {

    private CharSequence mTopMoviesSectionName;
    private CharSequence mSearchSectionName;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mTopMoviesSectionName = context.getString(R.string.TopMovies);
        mSearchSectionName = context.getString(R.string.Search);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return TopMoviesListFragment.newInstance();
            case 1:
                return SearchResultFragment.newInstance();
            default:
                throw new IllegalArgumentException("Wrong position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mTopMoviesSectionName;
            case 1:
                return mSearchSectionName;
        }
        return null;
    }
}

