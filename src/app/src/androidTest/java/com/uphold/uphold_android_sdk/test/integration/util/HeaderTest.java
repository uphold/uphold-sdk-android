package com.uphold.uphold_android_sdk.test.integration.util;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.uphold.uphold_android_sdk.config.GlobalConfigurations;
import com.uphold.uphold_android_sdk.util.Header;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Header integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class HeaderTest {

    @Test
    public void buildRangeHeaderShouldReturnTheRangeHeader() {
        Assert.assertEquals("items=0-10", Header.buildRangeHeader(0, 10));
    }

    @Test
    public void encodeCredentialsForBasicAuthorizationShouldReturnTheBasicAuthorizationHeader() {
        Assert.assertEquals("Basic Zm9vQGJhci5vcmc6Zm9vYmFy", Header.encodeCredentialsForBasicAuthorization("foo@bar.org", "foobar"));
    }

    @Test
    public void getHeadersShouldReturnTheHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>() {{
            put("User-Agent", String.format("uphold-android-sdk/%s (%s)", GlobalConfigurations.UPHOLD_SDK_VERSION, GlobalConfigurations.SDK_GITHUB_URL));
        }};

        Assert.assertEquals(headers, Header.getHeaders());
    }

    @Test
    public void getRateLimitValueShouldReturnTheRateLimitHeader() {
        final List<retrofit.client.Header> listHeaders = new ArrayList<retrofit.client.Header>() {{
            add(new retrofit.client.Header("Retry-After", "10"));
            add(new retrofit.client.Header("X-Bitreserve-OTP", "required"));
            add(new retrofit.client.Header("X-RateLimit-Limit", "300"));
        }};

        Assert.assertEquals("300", Header.getRateLimitValue(listHeaders));
    }

    @Test
    public void getSecondsUntilRateLimitResetShouldReturnTheRetryAfterHeader() {
        final List<retrofit.client.Header> listHeaders = new ArrayList<retrofit.client.Header>() {{
            add(new retrofit.client.Header("Retry-After", "10"));
            add(new retrofit.client.Header("X-Bitreserve-OTP", "required"));
            add(new retrofit.client.Header("X-RateLimit-Limit", "300"));
        }};

        Assert.assertEquals("10", Header.getSecondsUntilRateLimitReset(listHeaders));
    }

    @Test
    public void getTotalNumberOfResultsShouldReturnTheTotalNumberOfResults() {
        final List<retrofit.client.Header> listHeaders = new ArrayList<retrofit.client.Header>() {{
            add(new retrofit.client.Header("Content-Range", "0-4/20"));
            add(new retrofit.client.Header("Retry-After", "10"));
            add(new retrofit.client.Header("X-Bitreserve-OTP", "required"));
            add(new retrofit.client.Header("X-RateLimit-Limit", "300"));
        }};

        Assert.assertTrue(20 == Header.getTotalNumberOfResults(listHeaders));
    }

}
