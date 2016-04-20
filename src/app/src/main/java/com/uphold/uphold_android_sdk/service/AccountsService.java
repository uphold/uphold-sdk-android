package com.uphold.uphold_android_sdk.service;

import com.uphold.uphold_android_sdk.model.Account;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Accounts service.
 */

public interface AccountsService {

    /**
     * Gets a user's account.
     *
     * @param accountId The account id.
     * @param callback A callback to handle the response.
     */

    @GET("/v0/me/accounts/{accountId}")
    void getUserAccountById(@Path("accountId") String accountId, Callback<Account> callback);

    /**
     * Gets the user's list of accounts.
     *
     * @param callback A list with the user's accounts.
     */

    @GET("/v0/me/accounts")
    void getUserAccounts(Callback<List<Account>> callback);

}
