package org.bitreserve.bitreserve_android_sdk.model;

import com.darylteo.rx.promises.java.Promise;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.reserve.Deposit;
import org.bitreserve.bitreserve_android_sdk.model.reserve.ReserveStatistics;
import org.bitreserve.bitreserve_android_sdk.service.ReserveService;

import java.util.List;

/**
 * Reserve model.
 */

public class Reserve extends BaseModel {

    /**
     * Gets the ledger.
     *
     * @return a {@link Promise<List<Deposit>>} with the list of deposits.
     */

    public Promise<List<Deposit>> getLedger() {
        RetrofitPromise<List<Deposit>> promise = new RetrofitPromise<>();
        ReserveService reserveService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(ReserveService.class);

        reserveService.getLedger(promise);

        return promise;
    }

    /**
     * Gets the reserve summary of all the obligations and assets within it.
     *
     * @return a {@link Promise<List<ReserveStatistics>>} with the reserve summary of all the obligations and assets within it.
     */

    public Promise<List<ReserveStatistics>> getStatistics() {
        RetrofitPromise<List<ReserveStatistics>> promise = new RetrofitPromise<>();
        ReserveService reserveService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(ReserveService.class);

        reserveService.getStatistics(promise);

        return promise;
    }

    /**
     * Gets the public view of any transaction.
     *
     * @param transactionId The id of the transaction.
     *
     * @return a {@link Promise<Transaction>} with the transaction.
     */

    public Promise<Transaction> getTransactionById(String transactionId) {
        RetrofitPromise<Transaction> promise = new RetrofitPromise<>();
        ReserveService reserveService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(ReserveService.class);

        reserveService.getReserveTransactionById(transactionId, promise);

        return promise;
    }

    /**
     * Gets the public view of all transactions from the beginning of time.
     *
     * @return a {@link Promise<List<Transaction>>} with the list of transactions.
     */

    public Promise<List<Transaction>> getTransactions() {
        RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();
        ReserveService reserveService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(ReserveService.class);

        reserveService.getReserveTransactions(promise);

        return promise;
    }

}
