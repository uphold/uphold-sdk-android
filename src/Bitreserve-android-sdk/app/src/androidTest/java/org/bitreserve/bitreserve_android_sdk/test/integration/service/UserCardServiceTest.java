package org.bitreserve.bitreserve_android_sdk.test.integration.service;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.Card;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.card.CardRequest;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionDenominationRequest;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionRequest;
import org.bitreserve.bitreserve_android_sdk.service.UserCardService;
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
 * Integration tests to the class {@link UserCardService}
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class UserCardServiceTest {

    private static final String MOCK_URL = "http://www.foobar.com";

    @Test
    public void cancelTransactionShouldReturnTheRequest() throws Exception {
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
        final UserCardService userCardService = adapter.create(UserCardService.class);

        userCardService.cancelTransaction("foo", "bar", new RetrofitPromise<Transaction>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "POST");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/cards/%s/transactions/%s/cancel", MOCK_URL, "foo", "bar"));
    }

    @Test
    public void confirmTransactionShouldReturnTheRequest() throws Exception {
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
        final UserCardService userCardService = adapter.create(UserCardService.class);

        userCardService.confirmTransaction("foo", "bar", new RetrofitPromise<Transaction>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "POST");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/cards/%s/transactions/%s/commit", MOCK_URL, "foo", "bar"));
    }

    @Test
    public void createTransactionShouldReturnTheRequest() throws Exception {
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
        final UserCardService userCardService = adapter.create(UserCardService.class);

        userCardService.createTransaction("foobar", new TransactionRequest(new TransactionDenominationRequest("foo", "bar"), "bar"), new RetrofitPromise<Transaction>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "POST");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/cards/%s/transactions", MOCK_URL, "foobar"));
    }

    @Test
    public void createUserCardShouldReturnTheRequest() throws Exception {
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
        final UserCardService userCardService = adapter.create(UserCardService.class);

        userCardService.createUserCard(new CardRequest("foo", "bar"), new RetrofitPromise<Card>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "POST");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/cards", MOCK_URL));
    }

    @Test
    public void getUserCardByIdShouldReturnTheRequest() throws Exception {
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
        final UserCardService userCardService = adapter.create(UserCardService.class);

        userCardService.getUserCardById("foobar", new RetrofitPromise<Card>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/cards/%s", MOCK_URL, "foobar"));
    }

    @Test
    public void getUserCardsShouldReturnTheRequest() throws Exception {
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
        final UserCardService userCardService = adapter.create(UserCardService.class);

        userCardService.getUserCards(new RetrofitPromise<List<Card>>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/cards", MOCK_URL));
    }

    @Test
    public void getUserCardTransactionsShouldReturnTheRequest() throws Exception {
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
        final UserCardService userCardService = adapter.create(UserCardService.class);

        userCardService.getUserCardTransactions("foo", "bar", new RetrofitPromise<List<Transaction>>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "GET");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/cards/%s/transactions", MOCK_URL, "bar"));
        Assert.assertTrue(bodyRef.get().getHeaders().contains(new Header("Range", "foo")));
    }

    @Test
    public void updateShouldReturnTheRequest() throws Exception {
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
        final UserCardService userCardService = adapter.create(UserCardService.class);

        userCardService.update("foobar", new HashMap<String, Object>(), new RetrofitPromise<Card>());
        latch.await();

        Assert.assertEquals(bodyRef.get().getMethod(), "PATCH");
        Assert.assertEquals(bodyRef.get().getUrl(), String.format("%s/v0/me/cards/%s", MOCK_URL, "foobar"));
    }

}
