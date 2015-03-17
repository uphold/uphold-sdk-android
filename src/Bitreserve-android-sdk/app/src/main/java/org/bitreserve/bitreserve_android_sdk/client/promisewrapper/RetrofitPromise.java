package org.bitreserve.bitreserve_android_sdk.client.promisewrapper;

import com.darylteo.rx.promises.java.Promise;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Converts a {@link Callback<T>} into a {@link Promise<T>}.
 *
 * @param <T> The type of the {@link Promise}.
 */

public class RetrofitPromise<T> extends Promise<T> implements Callback<T> {

    /**
     * Constructor.
     */

    public RetrofitPromise() {
    }

    /**
     * Executed when the request is performed with the success.
     *
     * @param responseObject The {@link T} object.
     * @param response The {@link Response}.
     */

    @Override
    public void success(T responseObject, Response response) {
        this.fulfill (responseObject);
    }

    /**
     * Executed when the request gives an error.
     *
     * @param error The {@link retrofit.RetrofitError}.
     */

    @Override
    public void failure(RetrofitError error) {
        this.reject (error);
    }

}