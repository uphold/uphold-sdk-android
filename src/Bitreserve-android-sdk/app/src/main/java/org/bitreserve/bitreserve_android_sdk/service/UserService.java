package org.bitreserve.bitreserve_android_sdk.service;

import org.bitreserve.bitreserve_android_sdk.model.Card;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.model.user.Contact;
import org.bitreserve.bitreserve_android_sdk.model.user.Phone;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Has the user webservice endpoints.
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

}
