package com.uphold.uphold_android_sdk.test.integration.service;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;
import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;
import com.uphold.uphold_android_sdk.client.retrofitpromise.RetrofitPromise;
import com.uphold.uphold_android_sdk.model.Account;
import com.uphold.uphold_android_sdk.service.AccountsService;
import com.uphold.uphold_android_sdk.test.BuildConfig;
import com.uphold.uphold_android_sdk.test.util.MockRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import retrofit.client.Request;

/**
 * AccountService integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AccountServiceTest {

    @Test
    public void getUserAccountByIdShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<Account> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Account>() {
            @Override
            public Promise<Account> call(UpholdRestAdapter upholdRestAdapter) {
                AccountsService accountsService = adapter.getRestAdapter().create(AccountsService.class);
                RetrofitPromise<Account> promise = new RetrofitPromise<>();

                accountsService.getUserAccountById("foobar", promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/accounts/foobar", BuildConfig.API_SERVER_URL));
    }

    @Test
    public void getUserAccountsShouldReturnTheRequest() throws Exception {
        final MockRestAdapter<List<Account>> adapter = new MockRestAdapter<>(null, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Account>>() {
            @Override
            public Promise<List<Account>> call(UpholdRestAdapter upholdRestAdapter) {
                AccountsService accountsService = adapter.getRestAdapter().create(AccountsService.class);
                RetrofitPromise<List<Account>> promise = new RetrofitPromise<>();

                accountsService.getUserAccounts(promise);

                return promise;
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/accounts", BuildConfig.API_SERVER_URL));
    }

}
