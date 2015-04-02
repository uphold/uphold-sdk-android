package org.bitreserve.bitreserve_android_sdk.test.integration.service;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.Ticker;
import org.bitreserve.bitreserve_android_sdk.service.TickerService;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Integration tests to the class {@link TickerService}
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class TickerServiceTest {

    private static final String MOCK_URL = "http://www.foobar.com";

    @Test
    public void getAllTickersShouldReturnTheRequest() throws Exception {
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
        final TickerService tickerService = adapter.create(TickerService.class);

        tickerService.getAllTickers(new RetrofitPromise<List<Ticker>>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/ticker", MOCK_URL));
    }

    @Test
    public void getAllTickersByCurrencyShouldReturnTheRequest() throws Exception {
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
        final TickerService tickerService = adapter.create(TickerService.class);

        tickerService.getAllTickersByCurrency("foobar", new RetrofitPromise<List<Ticker>>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/ticker/%s", MOCK_URL, "foobar"));
    }

}
