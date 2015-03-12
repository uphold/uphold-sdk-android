package org.bitreserve.bitreserve_android_sdk.service;

import org.bitreserve.bitreserve_android_sdk.model.Ticker;

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
    void getAllTickers(Callback<List<Ticker>> callback);

    /**
     * Performs a request to get the tickers on the system by currency.
     *
     * @param currency The currency to get the results.
     * @param callback The callback to receive the request information.
     */

    @GET("/v0/ticker/{currency}")
    void getAllTickersByCurrency(@Path("currency") String currency, Callback<List<Ticker>> callback);

}
