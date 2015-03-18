package org.bitreserve.bitreserve_android_sdk.service;

import org.bitreserve.bitreserve_android_sdk.model.Balance;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.model.user.Contact;
import org.bitreserve.bitreserve_android_sdk.model.user.Phone;

import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PATCH;

/**
 * User service.
 */

public interface UserService {

    /**
     * Performs a request to get the user information.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/me")
    void getUser(Callback<User> callback);

    /**
     * Performs a request to get the user balances.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/me")
    void getUserBalances(Callback<Balance> callback);

    /**
     * Performs a request to get the user contacts.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/me/contacts")
    void getUserContacts(Callback<List<Contact>> callback);

    /**
     * Performs a request to get the user phones.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/me/phones")
    void getUserPhones(Callback<List<Phone>> callback);

    /**
     * Performs a request to get the user transactions.
     *
     * @param callback A callback to receive the request information.
     */

    @GET("/v0/me/transactions")
    void getUserTransactions(Callback<List<Transaction>> callback);

    /**
     * Performs a request to update the user values.
     *
     * @param field An {@link HashMap<String, Object>} with the user field to change and the value.
     * @param callback A callback to receive the request information.
     */

    @PATCH("/v0/me")
    void updateUser(@Body HashMap<String, Object> field, Callback<User> callback);

}
