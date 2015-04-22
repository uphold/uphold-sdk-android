package org.bitreserve.bitreserve_android_sdk.test.integration.service;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.reserve.Deposit;
import org.bitreserve.bitreserve_android_sdk.model.reserve.ReserveStatistics;
import org.bitreserve.bitreserve_android_sdk.service.ReserveService;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.List;

import retrofit.client.Header;
import retrofit.client.Request;

/**
 * Integration tests to the class {@link ReserveService}
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ReserveServiceTest {

    @Test
    public void getLedgerShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Deposit>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Deposit>>() {
            @Override
            public Promise<List<Deposit>> call(BitreserveRestAdapter bitreserveRestAdapter) {
                ReserveService reserveService = adapter.getRestAdapter().create(ReserveService.class);
                RetrofitPromise<List<Deposit>> promise = new RetrofitPromise<>();

                reserveService.getLedger("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/reserve/ledger");
        Assert.assertTrue(request.getHeaders().contains(new Header("Range", "foobar")));
    }

    @Test
    public void getReserveTransactionByIdShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter bitreserveRestAdapter) {
                ReserveService reserveService = adapter.getRestAdapter().create(ReserveService.class);
                RetrofitPromise<Transaction> promise = new RetrofitPromise<>();

                reserveService.getReserveTransactionById("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/reserve/transactions/foobar");
    }

    @Test
    public void getReserveTransactions() throws Exception {
        final MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(BitreserveRestAdapter bitreserveRestAdapter) {
                ReserveService reserveService = adapter.getRestAdapter().create(ReserveService.class);
                RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();

                reserveService.getReserveTransactions("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/reserve/transactions");
        Assert.assertTrue(request.getHeaders().contains(new Header("Range", "foobar")));
    }

    @Test
    public void getReserveStatistics() throws Exception {
        final MockRestAdapter<List<ReserveStatistics>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<ReserveStatistics>>() {
            @Override
            public Promise<List<ReserveStatistics>> call(BitreserveRestAdapter bitreserveRestAdapter) {
                ReserveService reserveService = adapter.getRestAdapter().create(ReserveService.class);
                RetrofitPromise<List<ReserveStatistics>> promise = new RetrofitPromise<>();

                reserveService.getStatistics(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/reserve/statistics");
    }

}