package com.topmovies.presenters;


import com.topmovies.ui.recyclerview.EndlessOnScrollListenerImpl;

public interface EndlessView {
    void init(EndlessOnScrollListenerImpl.DataLoader loader);
}
