package org.bitreserve.bitreserve_android_sdk.service;

import org.bitreserve.bitreserve_android_sdk.model.Reserve;
import org.bitreserve.bitreserve_android_sdk.model.reserve.Deposit;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Has the reserve webservice endpoints.
 */

public interface ReserveService {

    /**
     * Performs a request to get the reserve ledger.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/reserve/ledger")
    void getLedger(Callback<List<Deposit>> callback);

    /**
     * Performs a request to get the reserve statistics.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/reserve/statistics")
    void getStatistics(Callback<List<Reserve>> callback);

}
