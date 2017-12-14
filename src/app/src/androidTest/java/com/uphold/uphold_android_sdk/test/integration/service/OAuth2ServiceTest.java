package com.uphold.uphold_android_sdk.test.integration.service;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;
import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;
import com.uphold.uphold_android_sdk.client.retrofitpromise.RetrofitPromise;
import com.uphold.uphold_android_sdk.model.AuthenticationResponse;
import com.uphold.uphold_android_sdk.service.OAuth2Service;
import com.uphold.uphold_android_sdk.test.BuildConfig;
import com.uphold.uphold_android_sdk.test.util.MockRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;

import retrofit.client.Header;
import retrofit.client.Request;

/**
 * OAuth2Service integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class OAuth2ServiceTest {

    @Test
    public void requestToken() throws Exception {
        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        final MockRestAdapter<AuthenticationResponse> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, AuthenticationResponse>() {
            @Override
            public Promise<AuthenticationResponse> call(UpholdRestAdapter upholdRestAdapter) {
                OAuth2Service oauth2Service = adapter.getRestAdapter().create(OAuth2Service.class);
                RetrofitPromise<AuthenticationResponse> promise = new RetrofitPromise<>();

                oauth2Service.requestToken("foo", "bar", "foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals("code=bar&grant_type=foobar", bodyOutput.toString());
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/oauth2/token", BuildConfig.API_SERVER_URL));
        Assert.assertTrue(request.getHeaders().contains(new Header("Authorization", "foo")));
    }

}
