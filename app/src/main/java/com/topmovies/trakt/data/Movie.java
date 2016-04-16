package com.topmovies.trakt.data;

@SuppressWarnings("unused")
public class Movie {
    private String title;
    private int year;
    private String overview;
    private Ids ids;
    private Images images;

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", overview='" + overview + '\'' +
                ", imdb=" + ids.imdb +
                ", thumb=" + getThumb() +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getOverview() {
        return overview;
    }

    public String getThumb() {
        if (images != null && images.poster != null) {
            return images.poster.thumb;
        } else {
            return null;
        }
    }

    class Ids {
        String imdb;
    }

    class Images {
        Poster poster;

        class Poster {
            String thumb;
        }
    }

}
