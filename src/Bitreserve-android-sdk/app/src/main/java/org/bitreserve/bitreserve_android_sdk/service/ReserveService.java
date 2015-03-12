package org.bitreserve.bitreserve_android_sdk.service;

import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.reserve.ReserveStatistics;
import org.bitreserve.bitreserve_android_sdk.model.reserve.Deposit;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Reserve service.
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
     * Performs a request to get a reserve transaction.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/reserve/transactions/{transactionId}")
    void getReserveTransactionById(@Path("transactionId") String transactionId, Callback<Transaction> callback);

    /**
     * Performs a request to get the reserve transactions.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/reserve/transactions")
    void getReserveTransactions(Callback<List<Transaction>> callback);

    /**
     * Performs a request to get the reserve statistics.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/reserve/statistics")
    void getStatistics(Callback<List<ReserveStatistics>> callback);

}
