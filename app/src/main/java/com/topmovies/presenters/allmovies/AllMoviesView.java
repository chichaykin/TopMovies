package com.topmovies.presenters.allmovies;


import com.topmovies.presenters.EndlessView;
import com.topmovies.trakt.data.Movie;

import java.util.List;

public interface AllMoviesView extends EndlessView {
    void add(List<Movie> movies);
}
