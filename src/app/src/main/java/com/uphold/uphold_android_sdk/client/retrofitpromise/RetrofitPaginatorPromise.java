package com.uphold.uphold_android_sdk.client.retrofitpromise;

import com.darylteo.rx.promises.java.Promise;

import com.uphold.uphold_android_sdk.model.ResponseModel;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Converts a {@link retrofit.Callback <T>} into a {@link com.darylteo.rx.promises.java.Promise <T>}.
 *
 * @param <T> The type of the {@link com.darylteo.rx.promises.java.Promise}.
 */

public class RetrofitPaginatorPromise<T> extends Promise<ResponseModel> implements Callback<List<T>> {

    /**
     * Constructor.
     */

    public RetrofitPaginatorPromise() {
    }

    /**
     * Executed when the request is performed with the success.
     *
     * @param responseObject The {@link T} object.
     * @param response The {@link retrofit.client.Response}.
     */

    @Override
    public void success(List<T> responseObject, Response response) {
        this.fulfill(new ResponseModel(response));
    }

    /**
     * Executed when the request gives an error.
     *
     * @param error The {@link retrofit.RetrofitError}.
     */

    @Override
    public void failure(RetrofitError error) {
        this.reject(error);
    }

}