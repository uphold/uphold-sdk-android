package org.bitreserve.bitreserve_android_sdk.test.integration.util;

import org.bitreserve.bitreserve_android_sdk.config.GlobalConfigurations;
import org.bitreserve.bitreserve_android_sdk.util.Header;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Integration tests to the class {@link Header}.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class HeaderTest extends AndroidTestCase {

    @Test
    public void buildRangeHeaderShouldReturnStringRangeHeader() {
        assertEquals("items=0-10", Header.buildRangeHeader(0, 10));
    }

    @Test
    public void encodeCredentialsForBasicAuthorizationShouldReturnBasicHeader() {
        assertEquals("Basic Zm9vQGJhci5vcmc6Zm9vYmFy", Header.encodeCredentialsForBasicAuthorization("foo@bar.org", "foobar"));
    }

    @Test
    public void getHeadersShouldReturnHeaders() {
        HashMap<String, String> headers = new HashMap<String, String>() {{
            put("Accept", "application/json");
            put("Content-Type", "application/json");
            put("User-Agent", String.format("bitreserve-android-sdk %s (%s)", GlobalConfigurations.BITRESERVE_SDK_VERSION, GlobalConfigurations.SDK_GITHUB_URL));
        }};

        assertEquals(headers, Header.getHeaders());
    }

    @Test
    public void getRateLimitValueShouldReturn300() {
        final List<retrofit.client.Header> listHeaders = new ArrayList<retrofit.client.Header>() {{
            add(new retrofit.client.Header("Retry-After", "10"));
            add(new retrofit.client.Header("X-Bitreserve-OTP", "required"));
            add(new retrofit.client.Header("X-RateLimit-Limit", "300"));
        }};

        assertEquals("300", Header.getRateLimitValue(listHeaders));
    }

    @Test
    public void getSecondsUntilRateLimitResetShouldReturn10() {
        final List<retrofit.client.Header> listHeaders = new ArrayList<retrofit.client.Header>() {{
            add(new retrofit.client.Header("Retry-After", "10"));
            add(new retrofit.client.Header("X-Bitreserve-OTP", "required"));
            add(new retrofit.client.Header("X-RateLimit-Limit", "300"));
        }};

        assertEquals("10", Header.getSecondsUntilRateLimitReset(listHeaders));
    }

    @Test
    public void getTotalNumberOfResultsShouldReturn20() {
        final List<retrofit.client.Header> listHeaders = new ArrayList<retrofit.client.Header>() {{
            add(new retrofit.client.Header("Content-Range", "0-4/20"));
            add(new retrofit.client.Header("Retry-After", "10"));
            add(new retrofit.client.Header("X-Bitreserve-OTP", "required"));
            add(new retrofit.client.Header("X-RateLimit-Limit", "300"));
        }};

        assertTrue(20 == Header.getTotalNumberOfResults(listHeaders));
    }

    @Test
    public void isOTPRequiredShouldReturnTrue() {
        List<retrofit.client.Header> listHeaders = new ArrayList<retrofit.client.Header>() {{
            add(new retrofit.client.Header("Retry-After", "10"));
            add(new retrofit.client.Header("X-Bitreserve-OTP", "required"));
            add(new retrofit.client.Header("X-RateLimit-Limit", "10"));
        }};

        assertTrue(Header.isOTPRequired(listHeaders));
    }

}
