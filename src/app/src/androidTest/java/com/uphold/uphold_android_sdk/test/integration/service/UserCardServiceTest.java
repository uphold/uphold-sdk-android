package com.uphold.uphold_android_sdk.test.integration.service;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;
import com.uphold.uphold_android_sdk.client.body.EmptyOutput;
import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;
import com.uphold.uphold_android_sdk.client.retrofitpromise.RetrofitPromise;
import com.uphold.uphold_android_sdk.model.Card;
import com.uphold.uphold_android_sdk.model.Transaction;
import com.uphold.uphold_android_sdk.model.card.CardRequest;
import com.uphold.uphold_android_sdk.model.card.Settings;
import com.uphold.uphold_android_sdk.model.transaction.TransactionCardDepositRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionCommitRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionDenominationRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionDepositRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionTransferRequest;
import com.uphold.uphold_android_sdk.service.UserCardService;
import com.uphold.uphold_android_sdk.test.BuildConfig;
import com.uphold.uphold_android_sdk.test.util.MockRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter upholdRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Transaction> promise = new RetrofitPromise<>();

                userCardService.cancelTransaction("foo", "bar", EmptyOutput.INSTANCE, promise);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter upholdRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Transaction> promise = new RetrofitPromise<>();

                userCardService.confirmTransaction("foo", "bar", new TransactionCommitRequest("message"), "otp", promise);

                return promise;
            }
        });

        Header otpHeader = null;
        Request request = adapter.getRequest();

        for (Header header : request.getHeaders()) {
            if (header.getName().compareToIgnoreCase("X-Bitreserve-OTP") == 0) {
                otpHeader = header;

                break;
            }
        }

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foo/transactions/bar/commit", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(otpHeader.getValue(), "otp");
    }

    @Test
    public void createTransactionCardDepositShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter upholdRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Transaction> promise = new RetrofitPromise<>();

                userCardService.createTransaction(false, "foobar", new TransactionCardDepositRequest(new TransactionDenominationRequest("foo", "bar"), "bar", "1234"), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions?commit=false", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void createTransactionDepositShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter upholdRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Transaction> promise = new RetrofitPromise<>();

                userCardService.createTransaction(false, "foobar", new TransactionDepositRequest(new TransactionDenominationRequest("foo", "bar"), "bar"), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions?commit=false", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void createTransactionTransferShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Transaction> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter upholdRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Transaction> promise = new RetrofitPromise<>();

                userCardService.createTransaction(false, "foobar", new TransactionTransferRequest(new TransactionDenominationRequest("foo", "bar"), "bar"), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions?commit=false", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void createUserCardShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Card> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Card>() {
            @Override
            public Promise<Card> call(UpholdRestAdapter upholdRestAdapter) {
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
    public void createUserCardWithSettingsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Card> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Card>() {
            @Override
            public Promise<Card> call(UpholdRestAdapter upholdRestAdapter) {
                UserCardService userCardService = adapter.getRestAdapter().create(UserCardService.class);
                RetrofitPromise<Card> promise = new RetrofitPromise<>();

                userCardService.createUserCard(new CardRequest("foo", "bar", new Settings(0, true)), promise);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Card>() {
            @Override
            public Promise<Card> call(UpholdRestAdapter upholdRestAdapter) {
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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Card>>() {
            @Override
            public Promise<List<Card>> call(UpholdRestAdapter upholdRestAdapter) {
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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(UpholdRestAdapter upholdRestAdapter) {
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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Card>() {
            @Override
            public Promise<Card> call(UpholdRestAdapter upholdRestAdapter) {
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
