package com.topmovies.trakt.data;


public class Movie {
    String title;
    int year;
    String overview;
    Ids ids;

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", overview='" + overview + '\'' +
                ", ids=" + ids +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    class Ids {
        String imdb;

        @Override
        public String toString() {
            return "Ids{" +
                    "imdb='" + imdb + '\'' +
                    '}';
        }
    }
}
