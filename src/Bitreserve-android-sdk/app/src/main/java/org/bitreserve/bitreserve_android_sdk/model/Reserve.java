package org.bitreserve.bitreserve_android_sdk.model;

import com.darylteo.rx.promises.java.Promise;

import org.bitreserve.bitreserve_android_sdk.client.promisewrapper.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
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
        RetrofitPromise<List<Deposit>> retrofitPromise = new RetrofitPromise<> ();
        ReserveService reserveService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(ReserveService.class);

        reserveService.getLedger(retrofitPromise);

        return retrofitPromise;
    }

    /**
     * Gets the reserve summary of all the obligations and assets within it.
     *
     * @return a {@link Promise<List<ReserveStatistics>>} with the reserve summary of all the obligations and assets within it.
     */

    public Promise<List<ReserveStatistics>> getStatistics() {
        RetrofitPromise<List<ReserveStatistics>> retrofitPromise = new RetrofitPromise<> ();
        ReserveService reserveService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(ReserveService.class);

        reserveService.getStatistics(retrofitPromise);

        return retrofitPromise;
    }

    /**
     * Gets the public view of any transaction.
     *
     * @param transactionId The id of the transaction.
     * @return a {@link Promise<Transaction>} with the transaction.
     */

    public Promise<Transaction> getTransactionById(String transactionId) {
        RetrofitPromise<Transaction> retrofitPromise = new RetrofitPromise<> ();
        ReserveService reserveService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(ReserveService.class);

        reserveService.getReserveTransactionById(transactionId, retrofitPromise);

        return retrofitPromise;
    }

    /**
     * Gets the public view of all transactions from the beginning of time.
     *
     * @return a {@link Promise<List<Transaction>>} with the list of transactions.
     */

    public Promise<List<Transaction>> getTransactions() {
        RetrofitPromise<List<Transaction>> retrofitPromise = new RetrofitPromise<> ();
        ReserveService reserveService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(ReserveService.class);

        reserveService.getReserveTransactions(retrofitPromise);

        return retrofitPromise;
    }

}
