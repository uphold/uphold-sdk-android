package org.bitreserve.bitreserve_android_sdk.test.integration.service;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.Card;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.card.CardRequest;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionDenominationRequest;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionCommitRequest;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionRequest;
import org.bitreserve.bitreserve_android_sdk.service.UserCardService;
import org.bitreserve.bitreserve_android_sdk.test.BuildConfig;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.lang.String;
import java.util.HashMap;
import java.util.List;

import retrofit.client.Header;
import retrofit.client.Request;

/**
 * UserCardService integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class UserCardServiceTest {

    @Test
    public void cancelTransactionShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Transaction> promise = new RetrofitPromise<>();

                userCardService.cancelTransaction("foo", "bar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/cancel", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void confirmTransactionShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Transaction> promise = new RetrofitPromise<>();

                userCardService.confirmTransaction("foo", "bar", new TransactionCommitRequest("message"), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/commit", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void createTransactionShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Transaction> promise = new RetrofitPromise<>();

                userCardService.createTransaction("foobar", new TransactionRequest(new TransactionDenominationRequest("foo", "bar"), "bar"), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void createUserCardShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Card> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Card>() {
            @Override
            public Promise<Card> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Card> promise = new RetrofitPromise<>();

                userCardService.createUserCard(new CardRequest("foo", "bar"), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getUserCardByIdShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Card> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Card>() {
            @Override
            public Promise<Card> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Card> promise = new RetrofitPromise<>();

                userCardService.getUserCardById("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getUserCardsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Card>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Card>>() {
            @Override
            public Promise<List<Card>> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<List<Card>> promise = new RetrofitPromise<>();

                userCardService.getUserCards(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getUserCardTransactionsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();

                userCardService.getUserCardTransactions("foo", "bar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/bar/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertTrue(request.getHeaders().contains(new Header("Range", "foo")));
    }

    @Test
    public void updateShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Card> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Card>() {
            @Override
            public Promise<Card> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Card> promise = new RetrofitPromise<>();

                userCardService.update("foobar", new HashMap<String, Object>(), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "PATCH");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar", BuildConfig.API_SERVER_URL));
    }

}
