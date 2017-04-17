package com.uphold.uphold_android_sdk.test.integration.service;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;
import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;
import com.uphold.uphold_android_sdk.client.retrofitpromise.RetrofitPromise;
import com.uphold.uphold_android_sdk.model.Balance;
import com.uphold.uphold_android_sdk.model.Transaction;
import com.uphold.uphold_android_sdk.model.User;
import com.uphold.uphold_android_sdk.model.user.Contact;
import com.uphold.uphold_android_sdk.model.user.Document;
import com.uphold.uphold_android_sdk.model.user.Phone;
import com.uphold.uphold_android_sdk.service.UserService;
import com.uphold.uphold_android_sdk.test.BuildConfig;
import com.uphold.uphold_android_sdk.test.util.Fixtures;
import com.uphold.uphold_android_sdk.test.util.MockRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import retrofit.client.Header;
import retrofit.client.Request;

/**
 * UserService integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class UserServiceTest {

    @Test
    public void createContactShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Contact> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Contact>() {
            @Override
            public Promise<Contact> call(UpholdRestAdapter upholdRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<Contact> promise = new RetrofitPromise<>();

                userService.createContact(Fixtures.loadContactRequest(), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/contacts", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void createDocumentShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Document> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Document>() {
            @Override
            public Promise<Document> call(UpholdRestAdapter upholdRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<Document> promise = new RetrofitPromise<>();

                userService.createDocument(Fixtures.loadDocumentRequest(), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/documents", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getDocumentsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Document>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Document>>() {
            @Override
            public Promise<List<Document>> call(UpholdRestAdapter upholdRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<List<Document>> promise = new RetrofitPromise<>();

                userService.getDocuments(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/documents", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getUserShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<User> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, User>() {
            @Override
            public Promise<User> call(UpholdRestAdapter upholdRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<User> promise = new RetrofitPromise<>();

                userService.getUser(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getUserBalancesShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Balance> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Balance>() {
            @Override
            public Promise<Balance> call(UpholdRestAdapter upholdRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<Balance> promise = new RetrofitPromise<>();

                userService.getUserBalances(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getUserContactsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Contact>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Contact>>() {
            @Override
            public Promise<List<Contact>> call(UpholdRestAdapter upholdRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<List<Contact>> promise = new RetrofitPromise<>();

                userService.getUserContacts(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/contacts", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getUserPhonesShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Phone>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Phone>>() {
            @Override
            public Promise<List<Phone>> call(UpholdRestAdapter upholdRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<List<Phone>> promise = new RetrofitPromise<>();

                userService.getUserPhones(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/phones", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getUserTransactionsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(UpholdRestAdapter upholdRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();

                userService.getUserTransactions("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertTrue(request.getHeaders().contains(new Header("Range", "foobar")));
    }

    @Test
    public void updateUserShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<User> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, User>() {
            @Override
            public Promise<User> call(UpholdRestAdapter upholdRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<User> promise = new RetrofitPromise<>();

                userService.updateUser(new HashMap<String, Object>(), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "PATCH");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me", BuildConfig.API_SERVER_URL));
    }

}
