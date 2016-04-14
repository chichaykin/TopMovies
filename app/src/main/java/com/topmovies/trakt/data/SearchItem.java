package com.topmovies.trakt.data;

public class SearchItem {
    String score;
    Movie movie;

    @Override
    public String toString() {
        return "SearchItem{" +
                "score='" + score + '\'' +
                ", movie=" + movie +
                '}';
    }
}
