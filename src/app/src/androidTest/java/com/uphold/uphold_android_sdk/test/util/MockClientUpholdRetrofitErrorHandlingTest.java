package com.uphold.uphold_android_sdk.test.util;

import java.io.IOException;
import java.util.List;

import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * MockClientUpholdRetrofitErrorHandlingTest.
 */

public class MockClientUpholdRetrofitErrorHandlingTest implements Client {

    private int httpResponseCode;
    private List<Header> httpResponseHeaders;
    private String reason;

    public MockClientUpholdRetrofitErrorHandlingTest(int httpResponseCode, List<Header> httpResponseHeaders, String reason) {
        this.httpResponseCode = httpResponseCode;
        this.httpResponseHeaders = httpResponseHeaders;
        this.reason = reason;
    }

    @Override
    public Response execute(Request request) throws IOException {
        return new Response(request.getUrl(), httpResponseCode, reason, httpResponseHeaders, new TypedByteArray("application/json", "foobar".getBytes()));
    }

}
