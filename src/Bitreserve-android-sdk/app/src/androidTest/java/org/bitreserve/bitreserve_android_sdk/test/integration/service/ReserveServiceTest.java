package org.bitreserve.bitreserve_android_sdk.test.integration.service;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.reserve.Deposit;
import org.bitreserve.bitreserve_android_sdk.model.reserve.ReserveStatistics;
import org.bitreserve.bitreserve_android_sdk.service.ReserveService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Integration tests to the class {@link ReserveService}
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ReserveServiceTest {

    private static final String MOCK_URL = "http://www.foobar.com";

    @Test
    public void getLedgerShouldReturnTheRequest() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final ReserveService reserveService = adapter.create(ReserveService.class);

        reserveService.getLedger("foobar", new RetrofitPromise<List<Deposit>>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/reserve/ledger", MOCK_URL));
        Assert.assertTrue(bodyRef.get().getHeaders().contains(new Header("Range", "foobar")));
    }

    @Test
    public void getReserveTransactionByIdShouldReturnTheRequest() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final ReserveService reserveService = adapter.create(ReserveService.class);

        reserveService.getReserveTransactionById("foobar", new RetrofitPromise<Transaction>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/reserve/transactions/%s", MOCK_URL, "foobar"));
    }

    @Test
    public void getReserveTransactions() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final ReserveService reserveService = adapter.create(ReserveService.class);

        reserveService.getReserveTransactions("foobar", new RetrofitPromise<List<Transaction>>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/reserve/transactions", MOCK_URL));
        Assert.assertTrue(bodyRef.get().getHeaders().contains(new Header("Range", "foobar")));
    }

    @Test
    public void getReserveStatistics() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final ReserveService reserveService = adapter.create(ReserveService.class);

        reserveService.getStatistics(new RetrofitPromise<List<ReserveStatistics>>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/reserve/statistics", MOCK_URL));
    }

}
