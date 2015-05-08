package org.bitreserve.bitreserve_android_sdk.test.integration.service;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.Rate;
import org.bitreserve.bitreserve_android_sdk.service.TickerService;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.List;

import retrofit.client.Request;

/**
 * TickerService integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class TickerServiceTest {

    @Test
    public void getAllTickersShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Rate>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Rate>>() {
            @Override
            public Promise<List<Rate>> call(BitreserveRestAdapter bitreserveRestAdapter) {
                TickerService tickerService = adapter.getRestAdapter().create(TickerService.class);
                RetrofitPromise<List<Rate>> promise = new RetrofitPromise<>();

                tickerService.getAllTickers(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/ticker");
    }

    @Test
    public void getAllTickersByCurrencyShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Rate>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Rate>>() {
            @Override
            public Promise<List<Rate>> call(BitreserveRestAdapter bitreserveRestAdapter) {
                TickerService tickerService = adapter.getRestAdapter().create(TickerService.class);
                RetrofitPromise<List<Rate>> promise = new RetrofitPromise<>();

                tickerService.getAllTickersByCurrency("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/ticker/foobar");
    }

}
