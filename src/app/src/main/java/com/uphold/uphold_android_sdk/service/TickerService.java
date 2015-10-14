package com.uphold.uphold_android_sdk.service;

import com.uphold.uphold_android_sdk.model.Rate;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Ticker service.
 */

public interface TickerService {

    /**
     * Performs a request to get the tickers on the system.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/ticker")
    void getAllTickers(Callback<List<Rate>> callback);

    /**
     * Performs a request to get the tickers on the system by currency.
     *
     * @param currency The currency to get the results.
     * @param callback The callback to receive the request information.
     */

    @GET("/v0/ticker/{currency}")
    void getAllTickersByCurrency(@Path("currency") String currency, Callback<List<Rate>> callback);

}
