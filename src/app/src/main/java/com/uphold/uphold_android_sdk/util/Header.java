package com.uphold.uphold_android_sdk.util;

import com.uphold.uphold_android_sdk.config.GlobalConfigurations;

import android.text.TextUtils;
import android.util.Base64;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Header util.
 */

public class Header {

    /**
     * Build a string with the range header.
     *
     * @param start The position of the first element.
     * @param end The position of the last element.
     *
     * @return the string with the header value.
     */

    public static String buildRangeHeader(int start, int end) {
        return String.format("items=%d-%d", start, end);
    }

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
        return new HashMap<String, String>() {{
            put("User-Agent", String.format("uphold-android-sdk/%s (%s)", GlobalConfigurations.UPHOLD_SDK_VERSION, GlobalConfigurations.SDK_GITHUB_URL));
        }};
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
     * Gets the total number of the results available for the request.
     *
     * @param headers The list of the headers.
     *
     * @return the total number of elements.
     */

    public static Integer getTotalNumberOfResults(List<retrofit.client.Header> headers) {
        for (retrofit.client.Header header : headers) {
            if (header.getName().compareTo("Content-Range") == 0) {
                Pattern pattern = Pattern.compile("(\\d+)-(\\d+)/(\\d+)");
                Matcher matcher = pattern.matcher(header.getValue());

                if (matcher.find() && !TextUtils.isEmpty(matcher.group(3))) {
                    return Integer.parseInt(matcher.group(3));
                }

                return null;
            }
        }

        return null;
    }

}
