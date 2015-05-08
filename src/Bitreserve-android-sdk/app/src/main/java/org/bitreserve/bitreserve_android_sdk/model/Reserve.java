package org.bitreserve.bitreserve_android_sdk.model;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.PromiseFunction;

import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPaginatorPromise;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.reserve.Deposit;
import org.bitreserve.bitreserve_android_sdk.model.reserve.ReserveStatistics;
import org.bitreserve.bitreserve_android_sdk.paginator.Paginator;
import org.bitreserve.bitreserve_android_sdk.paginator.PaginatorInterface;
import org.bitreserve.bitreserve_android_sdk.service.ReserveService;
import org.bitreserve.bitreserve_android_sdk.util.Header;

import java.util.List;

/**
 * Reserve model.
 */

public class Reserve extends BaseModel {

    /**
     * Gets the ledger.
     *
     * @return a {@link Paginator<Deposit>} with the list of deposits.
     */

    public Paginator<Deposit> getLedger() {
        RetrofitPromise<List<Deposit>> promise = new RetrofitPromise<>();
        ReserveService reserveService = this.getBitreserveRestAdapter().create(ReserveService.class);

        reserveService.getLedger(Header.buildRangeHeader(Paginator.DEFAULT_START, Paginator.DEFAULT_OFFSET - 1), promise);

        final PaginatorInterface<Deposit> paginatorInterface = new PaginatorInterface<Deposit>() {
            @Override
            public Promise<List<Deposit>> getNext(String range) {
                RetrofitPromise<List<Deposit>> promise = new RetrofitPromise<>();
                ReserveService reserveService = Reserve.this.getBitreserveRestAdapter().create(ReserveService.class);

                reserveService.getLedger(range, promise);

                return promise;
            }

            @Override
            public Promise<Integer> count() {
                RetrofitPaginatorPromise<Deposit> promise = new RetrofitPaginatorPromise<>();
                ReserveService reserveService = Reserve.this.getBitreserveRestAdapter().create(ReserveService.class);

                reserveService.getLedger(Header.buildRangeHeader(0, 1), promise);

                return promise.then(new PromiseFunction<ResponseModel, Integer>() {
                    public Integer call(ResponseModel depositPaginatorModel) {
                        return Header.getTotalNumberOfResults(depositPaginatorModel.getResponse().getHeaders());
                    }
                });
            }

            @Override
            public Promise<Boolean> hasNext(final Integer currentPage) {
                RetrofitPaginatorPromise<Deposit> promise = new RetrofitPaginatorPromise<>();
                ReserveService reserveService = Reserve.this.getBitreserveRestAdapter().create(ReserveService.class);

                reserveService.getLedger(Header.buildRangeHeader(0, 1), promise);

                return promise.then(new PromiseFunction<ResponseModel, Boolean>() {
                    public Boolean call(ResponseModel depositPaginatorModel) {
                        Integer totalNumberOfResults = Header.getTotalNumberOfResults(depositPaginatorModel.getResponse().getHeaders());

                        return (currentPage * Paginator.DEFAULT_OFFSET) < totalNumberOfResults;
                    }
                });
            }
        };

        return new Paginator<>(promise, paginatorInterface);
    }

    /**
     * Gets the reserve summary of all the obligations and assets within it.
     *
     * @return a {@link Promise<List<ReserveStatistics>>} with the reserve summary of all the obligations and assets within it.
     */

    public Promise<List<ReserveStatistics>> getStatistics() {
        RetrofitPromise<List<ReserveStatistics>> promise = new RetrofitPromise<>();
        ReserveService reserveService = this.getBitreserveRestAdapter().create(ReserveService.class);

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
        ReserveService reserveService = this.getBitreserveRestAdapter().create(ReserveService.class);

        reserveService.getReserveTransactionById(transactionId, promise);

        return promise;
    }

    /**
     * Gets the public view of all transactions from the beginning of time.
     *
     * @return a {@link Paginator<Transaction>} with the list of transactions.
     */

    public Paginator<Transaction> getTransactions() {
        RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();
        ReserveService reserveService = this.getBitreserveRestAdapter().create(ReserveService.class);

        reserveService.getReserveTransactions(Header.buildRangeHeader(Paginator.DEFAULT_START, Paginator.DEFAULT_OFFSET - 1), promise);

        PaginatorInterface<Transaction> paginatorInterface = new PaginatorInterface<Transaction>() {
            @Override
            public Promise<List<Transaction>> getNext(String range) {
                RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();
                ReserveService reserveService = Reserve.this.getBitreserveRestAdapter().create(ReserveService.class);

                reserveService.getReserveTransactions(range, promise);

                return promise;
            }

            @Override
            public Promise<Integer> count() {
                RetrofitPaginatorPromise<Transaction> promise = new RetrofitPaginatorPromise<>();
                ReserveService reserveService = Reserve.this.getBitreserveRestAdapter().create(ReserveService.class);

                reserveService.getReserveTransactions(Header.buildRangeHeader(0, 1), promise);

                return promise.then(new PromiseFunction<ResponseModel, Integer>() {
                    public Integer call(ResponseModel transactionPaginatorModel) {
                        return Header.getTotalNumberOfResults(transactionPaginatorModel.getResponse().getHeaders());
                    }
                });
            }

            @Override
            public Promise<Boolean> hasNext(final Integer currentPage) {
                RetrofitPaginatorPromise<Transaction> promise = new RetrofitPaginatorPromise<>();
                ReserveService reserveService = Reserve.this.getBitreserveRestAdapter().create(ReserveService.class);

                reserveService.getReserveTransactions(Header.buildRangeHeader(0, 1), promise);

                return promise.then(new PromiseFunction<ResponseModel, Boolean>() {
                    public Boolean call(ResponseModel listPaginatorModel) {
                        Integer totalNumberOfResults = Header.getTotalNumberOfResults(listPaginatorModel.getResponse().getHeaders());

                        return (currentPage * Paginator.DEFAULT_OFFSET) < totalNumberOfResults;
                    }
                });
            }
        };

        return new Paginator<>(promise, paginatorInterface);
    }

}
