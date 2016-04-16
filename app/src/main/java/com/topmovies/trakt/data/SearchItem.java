package com.topmovies.trakt.data;

@SuppressWarnings("unused")
public class SearchItem {
    private String score;
    private Movie movie;

    @Override
    public String toString() {
        return "SearchItem{" +
                "score='" + score + '\'' +
                ", movie=" + movie +
                '}';
    }

    public Movie getMovie() {
        return movie;
    }
}
