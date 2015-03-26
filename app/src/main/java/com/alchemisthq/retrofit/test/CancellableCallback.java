package com.alchemisthq.retrofit.test;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by laaptu on 3/26/15.
 */
public class CancellableCallback<T> implements Callback<T> {
    private Callback<T> callback;
    private boolean canceled;

    public CancellableCallback(Callback<T> callback) {
        this.callback = callback;
        canceled = false;
    }


    public void cancel() {
        canceled = true;
        this.callback = null;
    }


    @Override
    public void success(T t, Response response) {
        if (!canceled) {
            callback.success(t, response);
        }

    }

    @Override
    public void failure(RetrofitError error) {
        if (!canceled) {
            callback.failure(error);
        }

    }
}
