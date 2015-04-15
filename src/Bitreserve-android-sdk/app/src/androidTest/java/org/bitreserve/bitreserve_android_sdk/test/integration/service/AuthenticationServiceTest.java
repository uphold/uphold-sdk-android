package org.bitreserve.bitreserve_android_sdk.test.integration.service;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationRequest;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationResponse;
import org.bitreserve.bitreserve_android_sdk.service.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Integration tests to the class {@link AuthenticationService}
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AuthenticationServiceTest {

    @Test
    public void authenticateUserShouldReturnTheRequest() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final String mockUrl = "http://www.foobar.com";
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(mockUrl).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final AuthenticationService authenticationService = adapter.create(AuthenticationService.class);

        authenticationService.authenticateUser("foobar", "authorizationfoobar", new AuthenticationRequest("foobar"), new RetrofitPromise<AuthenticationResponse>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "POST");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/tokens", mockUrl));
        Assert.assertTrue(bodyRef.get().getHeaders().contains(new Header("Authorization", "authorizationfoobar")));
        Assert.assertTrue(bodyRef.get().getHeaders().contains(new Header("X-Bitreserve-OTP", "foobar")));
    }

}
