package com.uphold.uphold_android_sdk.test.integration.client.errorhandling;

import com.darylteo.rx.promises.java.functions.PromiseAction;

import junit.framework.Assert;

import com.uphold.uphold_android_sdk.client.errorhandling.UpholdRetrofitErrorHandling;
import com.uphold.uphold_android_sdk.client.retrofitpromise.RetrofitPromise;
import com.uphold.uphold_android_sdk.exception.ApiLimitExceedException;
import com.uphold.uphold_android_sdk.exception.AuthenticationRequiredException;
import com.uphold.uphold_android_sdk.exception.BadRequestException;
import com.uphold.uphold_android_sdk.exception.UpholdClientException;
import com.uphold.uphold_android_sdk.exception.NotFoundException;
import com.uphold.uphold_android_sdk.exception.RuntimeException;
import com.uphold.uphold_android_sdk.model.User;
import com.uphold.uphold_android_sdk.service.UserService;
import com.uphold.uphold_android_sdk.test.util.MockClientUpholdRetrofitErrorHandlingTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import retrofit.RestAdapter;
import retrofit.client.Header;

/**
 * UpholdRetrofitErrorHandling integration tests.
 */

@RunWith(Parameterized.class)
public class UpholdRetrofitErrorHandlingTest {

    private static final String MOCK_URL = "http://foobar.com";

    private Object expectedClass;
    private String expectedReason;
    private List<Header> httpHeadersInput;
    private Integer httpStatusCodeInput;
    private Integer httpStatusCodeResponseExpected;
    private String reason;

    public UpholdRetrofitErrorHandlingTest(Integer httpStatusCodeInput, List<Header> httpHeadersInput, String reason, Integer httpStatusCodeResponseExpected, Object expectedClass, String expectedReason) {
        this.httpStatusCodeInput = httpStatusCodeInput;
        this.httpHeadersInput = httpHeadersInput;
        this.reason = reason;
        this.httpStatusCodeResponseExpected = httpStatusCodeResponseExpected;
        this.expectedClass = expectedClass;
        this.expectedReason = expectedReason;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { 0, null, null, null, RuntimeException.class.getName(), "Invalid status code: 0" },
            { 400, new ArrayList<Header>(), "reason", 400, BadRequestException.class.getName(), "400 reason" },
            { 401, new ArrayList<Header>(), "reason", 401, AuthenticationRequiredException.class.getName(), "401 reason" },
            { 404, new ArrayList<Header>(), "reason", 404, NotFoundException.class.getName(), String.format("Object or route not found: %s/v0/me", MOCK_URL) },
            { 412, new ArrayList<Header>(), "reason", 412, BadRequestException.class.getName(), "Precondition failed" },
            { 419, new ArrayList<Header>(), "reason", 419, BadRequestException.class.getName(), "Requested range not satisfiable" },
            { 429, new ArrayList<Header>() {{
                add(new Header("Retry-After", "10"));
                add(new Header("X-RateLimit-Limit", "300"));
            }}, "reason", 429, ApiLimitExceedException.class.getName(), "You have exceeded Uphold's API rate limit of 300 requests. Current time window ends in 10 seconds." },
            { 500, new ArrayList<Header>(), "reason", 500, RuntimeException.class.getName(), "500 reason" }});
    }

    @Test
    public void upholdRetrofitErrorHandlingShouldReturnTheUpholdClientException() throws Exception {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicReference<Exception> bodyRef = new AtomicReference<>();
        RetrofitPromise<User> promise = new RetrofitPromise<>();
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL)
            .setErrorHandler(new UpholdRetrofitErrorHandling())
            .setClient(new MockClientUpholdRetrofitErrorHandlingTest(httpStatusCodeInput, httpHeadersInput, reason))
            .build();
        UserService userService = adapter.create(UserService.class);

        userService.getUser(promise);
        promise.then(new PromiseAction<User>() {
            @Override
            public void call(User user) {
                throw new AssertionError();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                bodyRef.set(e);
                latch.countDown();
            }
        });
        latch.await();

        UpholdClientException responseException = (UpholdClientException) bodyRef.get();

        Assert.assertEquals(bodyRef.get().getClass().getName(), expectedClass);
        Assert.assertEquals(responseException.getHttpCode(), httpStatusCodeResponseExpected);
        Assert.assertEquals(expectedReason, responseException.getMessage());
    }

}
