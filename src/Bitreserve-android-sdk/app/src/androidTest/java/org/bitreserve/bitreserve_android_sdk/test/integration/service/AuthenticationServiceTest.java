package org.bitreserve.bitreserve_android_sdk.test.integration.service;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationRequest;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationResponse;
import org.bitreserve.bitreserve_android_sdk.service.AuthenticationService;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import retrofit.client.Header;
import retrofit.client.Request;

/**
 * Integration tests to the class {@link AuthenticationService}
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AuthenticationServiceTest {

    @Test
    public void authenticateUserShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<AuthenticationResponse> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, AuthenticationResponse>() {
            @Override
            public Promise<AuthenticationResponse> call(BitreserveRestAdapter bitreserveRestAdapter) {
                AuthenticationService authenticationService = adapter.getRestAdapter().create(AuthenticationService.class);
                RetrofitPromise<AuthenticationResponse> promise = new RetrofitPromise<>();

                authenticationService.authenticateUser("foobar", "authorizationfoobar", new AuthenticationRequest("foobar"), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/tokens");
        Assert.assertTrue(request.getHeaders().contains(new Header("Authorization", "authorizationfoobar")));
        Assert.assertTrue(request.getHeaders().contains(new Header("X-Bitreserve-OTP", "foobar")));
    }

}
