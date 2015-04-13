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

public class Token extends BaseModel {

    private String bearerToken;

    /**
     * Constructor.
     *
     * @param bearerToken The token value.
     */

    public Token(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    /**
     * Gets the token.
     *
     * @return the token.
     */

    public String getBearerToken() {
        return bearerToken;
    }

    /**
     * Sets the token.
     *
     * @param bearerToken The token.
     */

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    /**
     * Gets the current user.
     *
     * @return a {@link Promise<User>} with the user.
     */

    public Promise<User> getUser() {
        RetrofitPromise<User> promise = new RetrofitPromise<>();
        UserService userService = this.getBitreserveRestAdapter().create(UserService.class);

        if (TextUtils.isEmpty(this.getBearerToken())) {
            promise.reject(new AuthenticationRequiredException("Missing bearer authorization"));

            return promise;
        }

        userService.getUser(promise);

        return promise.then(new PromiseFunction<User, User>() {
            public User call(User user) {
                user.setBitreserveRestAdapter(new BitreserveRestAdapter(Token.this.getBearerToken()));

                return user;
            }
        });
    }

}
