package com.uphold.uphold_android_sdk.client;

import com.darylteo.rx.promises.java.Promise;

import com.uphold.uphold_android_sdk.BuildConfig;
import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;
import com.uphold.uphold_android_sdk.client.retrofitpromise.RetrofitPromise;
import com.uphold.uphold_android_sdk.client.session.SessionManager;
import com.uphold.uphold_android_sdk.exception.UpholdSdkNotInitializedException;
import com.uphold.uphold_android_sdk.exception.StateMatchException;
import com.uphold.uphold_android_sdk.model.AuthenticationResponse;
import com.uphold.uphold_android_sdk.model.Rate;
import com.uphold.uphold_android_sdk.model.Reserve;
import com.uphold.uphold_android_sdk.model.Token;
import com.uphold.uphold_android_sdk.model.User;
import com.uphold.uphold_android_sdk.service.OAuth2Service;
import com.uphold.uphold_android_sdk.service.TickerService;
import com.uphold.uphold_android_sdk.util.Header;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

/**
 * Uphold client.
 */

public class UpholdClient {

    private static Context applicationContext;
    private static Boolean sdkInitialized = false;

    private Token token;

    /**
     * Initialize the client.
     */

    public static synchronized void initialize(Context context) {
        if (sdkInitialized) {
            return;
        }

        if (context == null) {
            return;
        }

        applicationContext = context;
        sdkInitialized = true;
    }

    /**
     * Constructor.
     */

    public UpholdClient() throws UpholdSdkNotInitializedException {
        if (!sdkInitialized) {
            throw new UpholdSdkNotInitializedException("The SDK has not been initialized, make sure to call UpholdClient.initialize(context)");
        }

        this.token = new Token();
        this.token.setUpholdRestAdapter(new UpholdRestAdapter());
    }

    /**
     * Constructor.
     *
     * @param bearerToken The user bearer token.
     */

    public UpholdClient(String bearerToken) throws UpholdSdkNotInitializedException {
        if (!sdkInitialized) {
            throw new UpholdSdkNotInitializedException("The SDK has not been initialized, make sure to call UpholdClient.initialize(context)");
        }

        this.token = new Token(bearerToken);
        this.token.setUpholdRestAdapter(new UpholdRestAdapter());
    }

    /**
     * Starts the authorization flow.
     *
     * @param context The context where the Uphold connect flow starts.
     * @param clientId The client id.
     * @param state The state.
     */

    public void beginAuthorization(Context context, String clientId, List<String> scopes, String state) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri clientUri = Uri.parse(String.format("%s/authorize/%s?scope=%s&state=%s", BuildConfig.AUTHORIZATION_SERVER_URL, clientId, TextUtils.join(" ", scopes), state));

        intent.setData(clientUri);
        context.startActivity(intent);
    }

    /**
     * Completes the authorization flow.
     *
     * @param uri The uri returned by the intent called by the begin authorization flow.
     * @param clientId The client secret.
     * @param clientSecret The client id.
     * @param state The state.
     *
     * @return the {@link AuthenticationResponse}.
     */

    public Promise<AuthenticationResponse> completeAuthorization(Uri uri, String clientId, String clientSecret, String grantType, String state) {
        RetrofitPromise<AuthenticationResponse> promise = new RetrofitPromise<>();
        OAuth2Service oAuth2Service = this.getToken().getUpholdRestAdapter().create(OAuth2Service.class);

        if (state.compareTo(uri.getQueryParameter("state")) != 0) {
            promise.reject(new StateMatchException("State does not match."));
        }

        oAuth2Service.requestToken(Header.encodeCredentialsForBasicAuthorization(clientId, clientSecret), uri.getQueryParameter("code"), grantType, promise);

        return promise;
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }

    /**
     * Gets the reserve model.
     *
     * @return a {@link Reserve}.
     */

    public Reserve getReserve() {
        return new Reserve();
    }

    /**
     * Gets all exchanges rates for all currency pairs.
     *
     * @return a {@link Promise<List<Rate>>} with all exchanges rates for all currency pairs.
     */

    public Promise<List<Rate>> getTicker() {
        RetrofitPromise<List<Rate>> promise = new RetrofitPromise<>();
        TickerService tickerService = this.getToken().getUpholdRestAdapter().create(TickerService.class);

        tickerService.getAllTickers(promise);

        return promise;
    }

    /**
     * Gets all exchanges rates relative to a given currency.
     *
     * @param currency The filter currency.
     *
     * @return a {@link Promise<List<Rate>>} with all exchanges rates relative to a given currency.
     */

    public Promise<List<Rate>> getTickersByCurrency(String currency) {
        RetrofitPromise<List<Rate>> promise = new RetrofitPromise<>();
        TickerService tickerService = this.getToken().getUpholdRestAdapter().create(TickerService.class);

        tickerService.getAllTickersByCurrency(currency, promise);

        return promise;
    }

    /**
     * Gets the Uphold client token.
     *
     * @return the {@link Token}
     */

    public Token getToken() {
        return token;
    }

    /**
     * Sets the Uphold token.
     *
     * @param token The {@link Token}.
     */

    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * Gets the current user.
     *
     * @return a {@link Promise<User>} with the user.
     */

    public Promise<User> getUser() {
        return this.getToken().getUser();
    }

    /**
     * Invalidates user current session.
     */

    public void invalidateSession() {
        SessionManager.INSTANCE.invalidateSession();
    }

}
