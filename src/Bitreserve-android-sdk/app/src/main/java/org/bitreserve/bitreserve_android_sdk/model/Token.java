package org.bitreserve.bitreserve_android_sdk.model;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.PromiseFunction;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.exception.AuthenticationRequiredException;
import org.bitreserve.bitreserve_android_sdk.service.UserService;

import android.text.TextUtils;

/**
 * Token model.
 */

public class Token {

    private String token;

    /**
     * Constructor.
     *
     * @param token The token value.
     */

    public Token(String token) {
        this.token = token;
    }

    /**
     * Gets the token.
     *
     * @return the token.
     */

    public String getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param token The token.
     */

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the current user.
     *
     * @return a {@link Promise<User>} with the user.
     */

    public Promise<User> getUser() {
        RetrofitPromise<User> promise = new RetrofitPromise<>();
        UserService userService = BitreserveRestAdapter.getRestAdapter(this).create(UserService.class);

        if (TextUtils.isEmpty(this.getToken())) {
            promise.reject(new AuthenticationRequiredException("Missing bearer authorization"));

            return promise;
        } else {
            userService.getUser(promise);

            return promise.then(new PromiseFunction<User, User>() {
                public User call(User user) {
                    user.setToken(Token.this);

                    return user;
                }
            });
        }
    }

}
