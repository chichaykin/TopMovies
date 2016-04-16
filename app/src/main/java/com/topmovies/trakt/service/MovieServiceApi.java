package com.topmovies.trakt.service;


import com.topmovies.trakt.data.Movie;
import com.topmovies.trakt.data.SearchItem;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface MovieServiceApi {
    @GET("movies/popular")
    Observable<List<Movie>> getMovies(@Query("page") int page);

    @GET("search?type=movie")
    Observable<List<SearchItem>> search(@Query("query") String query, @Query("page") int page);

}
