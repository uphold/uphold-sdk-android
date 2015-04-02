package org.bitreserve.bitreserve_android_sdk.test.integration.service;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.Balance;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.model.user.Contact;
import org.bitreserve.bitreserve_android_sdk.model.user.Phone;
import org.bitreserve.bitreserve_android_sdk.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;

/**
 * Integration tests to the class {@link UserService}
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class UserServiceTest {

    private static final String MOCK_URL = "http://www.foobar.com";

    @Test
    public void getUserShouldReturnTheRequest() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final UserService userService = adapter.create(UserService.class);

        userService.getUser(new RetrofitPromise<User>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me", MOCK_URL));
    }

    @Test
    public void getUserBalancesShouldReturnTheRequest() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final UserService userService = adapter.create(UserService.class);

        userService.getUserBalances(new RetrofitPromise<Balance>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me", MOCK_URL));
    }

    @Test
    public void getUserContactsShouldReturnTheRequest() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final UserService userService = adapter.create(UserService.class);

        userService.getUserContacts(new RetrofitPromise<List<Contact>>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/contacts", MOCK_URL));
    }

    @Test
    public void getUserPhonesShouldReturnTheRequest() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final UserService userService = adapter.create(UserService.class);

        userService.getUserPhones(new RetrofitPromise<List<Phone>>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/phones", MOCK_URL));
    }

    @Test
    public void getUserTransactionsShouldReturnTheRequest() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final UserService userService = adapter.create(UserService.class);

        userService.getUserTransactions("foobar", new RetrofitPromise<List<Transaction>>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/transactions", MOCK_URL));
        Assert.assertTrue(bodyRef.get().getHeaders().contains(new Header("Range", "foobar")));
    }

    @Test
    public void updateUserShouldReturnTheRequest() throws Exception {
        final AtomicReference<Request> bodyRef = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(MOCK_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                bodyRef.set(request);
                latch.countDown();

                return null;
            }
        }).build();
        final UserService userService = adapter.create(UserService.class);

        userService.updateUser(new HashMap<String, Object>(), new RetrofitPromise<User>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "PATCH");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me", MOCK_URL));
    }

}
