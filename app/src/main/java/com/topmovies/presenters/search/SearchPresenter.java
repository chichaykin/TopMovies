package com.topmovies.presenters.search;


import com.topmovies.presenters.Presenter;

public interface SearchPresenter extends Presenter {
    void search(String query);
}
