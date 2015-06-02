package org.bitreserve.bitreserve_android_sdk.test.integration.client.restadapter;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.BuildConfig;
import org.bitreserve.bitreserve_android_sdk.client.errorhandling.BitreserveRetrofitErrorHandling;
import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.model.Card;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.test.util.Fixtures;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import retrofit.RestAdapter;
import retrofit.client.Header;
import retrofit.client.Request;

/**
 * BitreserveRestAdapter integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class BitreserveRestAdapterTest {

    @Test
    public void setAdapterShouldSetTheAdapter() {
        BitreserveRestAdapter bitreserveRestAdapter = new BitreserveRestAdapter();
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(BuildConfig.API_SERVER_URL)
            .setErrorHandler(new BitreserveRetrofitErrorHandling())
            .setRequestInterceptor(bitreserveRestAdapter.getBitreserveRequestInterceptor("foobar"))
            .build();

        bitreserveRestAdapter.setAdapter(adapter);

        Assert.assertEquals(adapter, bitreserveRestAdapter.getAdapter());
    }

    @Test
    public void bitreserveRestAdapterWithTokenShoulSetTheBitreserveCustomHeadersWithAuthenticationHeader() throws Exception {
        MockRestAdapter<Card> adapter = new MockRestAdapter<>("fuz", null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Card>() {
            @Override
            public Promise<Card> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getCardById("foobar");
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getHeaders().size(), 2);
        Assert.assertTrue(request.getHeaders().contains(new Header("Authorization", "Bearer fuz")));
        Assert.assertTrue(request.getHeaders().contains(new Header("User-Agent", "bitreserve-android-sdk 0.1 (https://github.com/bitreserve/bitreserve-sdk-android)")));
    }

    @Test
    public void setBitreserveRequestInterceptorWithEmptyTokenShouldSetTheBitreserveCustomHeadersWithoutAuthenticationHeader() throws Exception {
        MockRestAdapter<Card> adapter = new MockRestAdapter<>("", null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Card>() {
            @Override
            public Promise<Card> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getCardById("foobar");
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getHeaders().size(), 1);
        Assert.assertTrue(request.getHeaders().contains(new Header("User-Agent", "bitreserve-android-sdk 0.1 (https://github.com/bitreserve/bitreserve-sdk-android)")));
    }

    @Test
    public void setBitreserveRequestInterceptorWithNullTokenShouldSetTheBitreserveCustomHeadersWithoutAuthenticationHeader() throws Exception{
        MockRestAdapter<Card> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Card>() {
            @Override
            public Promise<Card> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getCardById("foobar");
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getHeaders().size(), 1);
        Assert.assertTrue(request.getHeaders().contains(new Header("User-Agent", "bitreserve-android-sdk 0.1 (https://github.com/bitreserve/bitreserve-sdk-android)")));
    }

}
