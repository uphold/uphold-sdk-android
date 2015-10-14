package com.uphold.uphold_android_sdk.test.integration.service;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;
import com.uphold.uphold_android_sdk.client.retrofitpromise.RetrofitPromise;
import com.uphold.uphold_android_sdk.model.Transaction;
import com.uphold.uphold_android_sdk.model.reserve.Deposit;
import com.uphold.uphold_android_sdk.model.reserve.ReserveStatistics;
import com.uphold.uphold_android_sdk.service.ReserveService;
import com.uphold.uphold_android_sdk.test.BuildConfig;
import com.uphold.uphold_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.lang.String;
import java.util.List;

import retrofit.client.Header;
import retrofit.client.Request;

/**
 * ReserveService integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ReserveServiceTest {

    @Test
    public void getLedgerShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Deposit>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Deposit>>() {
            @Override
            public Promise<List<Deposit>> call(UpholdRestAdapter upholdRestAdapter) {
                ReserveService reserveService = adapter.getRestAdapter().create(ReserveService.class);
                RetrofitPromise<List<Deposit>> promise = new RetrofitPromise<>();

                reserveService.getLedger("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/ledger", BuildConfig.API_SERVER_URL));
        Assert.assertTrue(request.getHeaders().contains(new Header("Range", "foobar")));
    }

    @Test
    public void getReserveTransactionByIdShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter upholdRestAdapter) {
                ReserveService reserveService = adapter.getRestAdapter().create(ReserveService.class);
                RetrofitPromise<Transaction> promise = new RetrofitPromise<>();

                reserveService.getReserveTransactionById("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/transactions/foobar", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getReserveTransactionsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(UpholdRestAdapter upholdRestAdapter) {
                ReserveService reserveService = adapter.getRestAdapter().create(ReserveService.class);
                RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();

                reserveService.getReserveTransactions("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertTrue(request.getHeaders().contains(new Header("Range", "foobar")));
    }

    @Test
    public void getReserveStatisticsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<ReserveStatistics>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<ReserveStatistics>>() {
            @Override
            public Promise<List<ReserveStatistics>> call(UpholdRestAdapter upholdRestAdapter) {
                ReserveService reserveService = adapter.getRestAdapter().create(ReserveService.class);
                RetrofitPromise<List<ReserveStatistics>> promise = new RetrofitPromise<>();

                reserveService.getStatistics(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/reserve/statistics", BuildConfig.API_SERVER_URL));
    }

}
