package com.uphold.uphold_android_sdk.model;

import com.darylteo.rx.promises.java.Promise;

import com.uphold.uphold_android_sdk.client.retrofitpromise.RetrofitPromise;
import com.uphold.uphold_android_sdk.client.session.SessionManager;
import com.uphold.uphold_android_sdk.exception.AuthenticationRequiredException;
import com.uphold.uphold_android_sdk.service.UserService;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Token model.
 */

public class Token extends BaseModel implements Serializable {

    private String bearerToken;

    /**
     * Constructor.
     */

    public Token() {
        this.bearerToken = null;

        SessionManager.INSTANCE.invalidateSession();
    }

    /**
     * Constructor.
     *
     * @param bearerToken The bearer token.
     */

    public Token(String bearerToken) {
        this.bearerToken = bearerToken;

        SessionManager.INSTANCE.setBearerToken(this);
    }

    /**
     * Gets the user bearer token.
     *
     * @return the user bearer token.
     */

    public String getBearerToken() {
        return bearerToken;
    }

    /**
     * Gets the current user.
     *
     * @return a {@link Promise<User>} with the user.
     */

    public Promise<User> getUser() {
        RetrofitPromise<User> promise = new RetrofitPromise<>();
        UserService userService = this.getUpholdRestAdapter().create(UserService.class);

        if (TextUtils.isEmpty(SessionManager.INSTANCE.getBearerToken())) {
            promise.reject(new AuthenticationRequiredException("Missing bearer authorization"));

            return promise;
        }

        userService.getUser(promise);

        return promise;
    }

}
