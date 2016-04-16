package com.topmovies.presenters.search;

import com.topmovies.presenters.EndlessView;
import com.topmovies.trakt.data.SearchItem;

import java.util.List;

public interface SearchView extends EndlessView{
    void add(List<SearchItem> searchItems);

    void emptyItems();
}
