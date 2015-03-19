package org.bitreserve.bitreserve_android_sdk.util;

import org.bitreserve.bitreserve_android_sdk.config.GlobalConfigurations;

import android.util.Base64;

import java.util.HashMap;
import java.util.List;

/**
 * Header util.
 */

public class Header {

    /**
     * Generates an encoded string to be added to the http authentication header.
     *
     * @param user The username value for the http authentication.
     * @param password The password value for the http authentication.
     *
     * @return The encoded string to be added to the authentication header.
     */

    public static String encodeCredentialsForBasicAuthorization(String user, String password) {
        final String userAndPassword = user + ":" + password;

        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }

    /**
     * Returns the headers to be added to a http request.
     *
     * @return the header to be added to a http request.
     */

    public static HashMap<String, String> getHeaders() {
        HashMap<String, String> headers = new HashMap<>();

        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("User-Agent", "bitreserve-android-sdk " + GlobalConfigurations.BITRESERVE_SDK_VERSION + " (" + GlobalConfigurations.SDK_GITHUB_URL + ")");

        return headers;
    }

    /**
     * Returns a boolean indicating if OTP is required.
     *
     * @param headers The response headers.
     *
     * @return a boolean indicating if the OTP is required.
     */

    public static Boolean isOTPRequired(List<retrofit.client.Header> headers) {
        for (retrofit.client.Header header : headers) {
            if (header.getName().compareTo("X-Bitreserve-OTP") == 0) {
                if (header.getValue().compareTo("required") == 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Gets the rate limit reset time.
     *
     * @param headers The response headers.
     *
     * @return the rate limit reset time.
     */

    public static String getSecondsUntilRateLimitReset(List<retrofit.client.Header> headers) {
        for (retrofit.client.Header header : headers) {
            if (header.getName().compareTo("Retry-After") == 0) {
                return header.getValue();
            }
        }

        return null;
    }

    /**
     * Gets the rate limit value.
     *
     * @param headers The response headers.
     *
     * @return the rate limit value.
     */

    public static String getRateLimitValue(List<retrofit.client.Header> headers) {
        for (retrofit.client.Header header : headers) {
            if (header.getName().compareTo("X-RateLimit-Limit") == 0) {
                return header.getValue();
            }
        }

        return null;
    }

}
