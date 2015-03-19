package org.bitreserve.bitreserve_android_sdk.client;

import com.darylteo.rx.promises.java.Promise;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationRequest;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationResponse;
import org.bitreserve.bitreserve_android_sdk.model.Ticker;
import org.bitreserve.bitreserve_android_sdk.model.Token;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.service.AuthenticationService;
import org.bitreserve.bitreserve_android_sdk.service.TickerService;
import org.bitreserve.bitreserve_android_sdk.util.Header;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Bitreserve client.
 */

public class BitreserveClient {

    private String token;

    /**
     * Constructor.
     */

    public BitreserveClient() {
    }

    /**
     * Constructor.
     *
     * @param token The user token.
     */

    public BitreserveClient(String token) {
        this.token = token;
    }

    /**
     * Gets the {@link AuthenticationResponse} with the user personal access token.
     *
     * @param otp The OTP sent to the user.
     * @param user The user username.
     * @param password The user password.
     * @param authorizationRequest The {@link AuthenticationRequest}.
     *
     * @return a {@link Promise<AuthenticationResponse>} with the user personal access token.
     */

    public Promise<AuthenticationResponse> authenticateUser(String otp, String user, String password, AuthenticationRequest authorizationRequest) {
        RetrofitPromise<AuthenticationResponse> promise = new RetrofitPromise<>();
        AuthenticationService authenticationService = this.getRestAdapter().create(AuthenticationService.class);

        authenticationService.authenticateUser(otp, Header.encodeCredentialsForBasicAuthorization(user, password), authorizationRequest, promise);

        return promise;
    }

    /**
     * Gets all exchanges rates for all currency pairs.
     *
     * @return a {@link Promise<List<Ticker>>} with all exchanges rates for all currency pairs.
     */

    public Promise<List<Ticker>> getTickers() {
        RetrofitPromise<List<Ticker>> promise = new RetrofitPromise<>();
        TickerService tickerService = this.getRestAdapter().create(TickerService.class);

        tickerService.getAllTickers(promise);

        return promise;
    }

    /**
     * Gets all exchanges rates relative to a given currency.
     *
     * @param currency The filter currency.
     *
     * @return a {@link Promise<List<Ticker>>} with all exchanges rates relative to a given currency.
     */

    public Promise<List<Ticker>> getTickersByCurrency(String currency) {
        RetrofitPromise<List<Ticker>> promise = new RetrofitPromise<>();
        TickerService tickerService = this.getRestAdapter().create(TickerService.class);

        tickerService.getAllTickersByCurrency(currency, promise);

        return promise;
    }

    /**
     * Gets the current user.
     *
     * @return a {@link Promise<User>} with the user.
     */

    public Promise<User> getUser() {
        return new Token(this.token).getUser();
    }

    /**
     * Gets the rest adapter with the current token.
     *
     * @return a {@link RestAdapter} for the current token.
     */

    private RestAdapter getRestAdapter() {
        return BitreserveRestAdapter.getRestAdapter(new Token(this.token));
    }

}
