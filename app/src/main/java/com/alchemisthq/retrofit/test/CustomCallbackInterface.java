package com.alchemisthq.retrofit.test;

import retrofit.Callback;

/**
 * Created by laaptu on 3/25/15.
 */
public interface CustomCallbackInterface<T> extends Callback<T> {
    public String getTag();

    public String setTag(String tag);

    public void cancel();

    public boolean isCancelled();
}
