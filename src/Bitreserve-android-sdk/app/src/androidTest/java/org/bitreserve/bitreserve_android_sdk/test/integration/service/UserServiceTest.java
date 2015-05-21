package org.bitreserve.bitreserve_android_sdk.test.integration.service;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.Balance;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.model.user.Contact;
import org.bitreserve.bitreserve_android_sdk.model.user.Phone;
import org.bitreserve.bitreserve_android_sdk.service.UserService;
import org.bitreserve.bitreserve_android_sdk.test.util.Fixtures;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

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

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Contact>() {
            @Override
            public Promise<Contact> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<Contact> promise = new RetrofitPromise<>();

                userService.createContact(Fixtures.loadContactRequest(), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/contacts");
    }

    @Test
    public void getUserShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<User> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, User>() {
            @Override
            public Promise<User> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<User> promise = new RetrofitPromise<>();

                userService.getUser(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me");
    }

    @Test
    public void getUserBalancesShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Balance> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Balance>() {
            @Override
            public Promise<Balance> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<Balance> promise = new RetrofitPromise<>();

                userService.getUserBalances(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me");
    }

    @Test
    public void getUserContactsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Contact>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Contact>>() {
            @Override
            public Promise<List<Contact>> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<List<Contact>> promise = new RetrofitPromise<>();

                userService.getUserContacts(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/contacts");
    }

    @Test
    public void getUserPhonesShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Phone>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Phone>>() {
            @Override
            public Promise<List<Phone>> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<List<Phone>> promise = new RetrofitPromise<>();

                userService.getUserPhones(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/phones");
    }

    @Test
    public void getUserTransactionsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();

                userService.getUserTransactions("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/transactions");
        Assert.assertTrue(request.getHeaders().contains(new Header("Range", "foobar")));
    }

    @Test
    public void updateUserShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<User> adapter = new MockRestAdapter<>(null, null, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, User>() {
            @Override
            public Promise<User> call(BitreserveRestAdapter bitreserveRestAdapter) {
                UserService userService = adapter.getRestAdapter().create(UserService.class);
                RetrofitPromise<User> promise = new RetrofitPromise<>();

                userService.updateUser(new HashMap<String, Object>(), promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "PATCH");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me");
    }

}
