package com.uphold.uphold_android_sdk.test.integration.client;

import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;
import com.uphold.uphold_android_sdk.BuildConfig;
import com.uphold.uphold_android_sdk.client.UpholdClient;
import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;
import com.uphold.uphold_android_sdk.client.session.SessionManager;
import com.uphold.uphold_android_sdk.exception.StateMatchException;
import com.uphold.uphold_android_sdk.exception.UpholdSdkNotInitializedException;
import com.uphold.uphold_android_sdk.model.AuthenticationResponse;
import com.uphold.uphold_android_sdk.model.Rate;
import com.uphold.uphold_android_sdk.model.Token;
import com.uphold.uphold_android_sdk.model.User;
import com.uphold.uphold_android_sdk.test.util.MockRestAdapter;
import com.uphold.uphold_android_sdk.test.util.MockSharedPreferencesContext;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.List;

import retrofit.client.Header;
import retrofit.client.Request;

/**
 * UpholdClient integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class UpholdClientTest {

    @Test
    public void upholdClientWithoutTokenShouldSetTheTokenAndRestAdapter() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext());

        UpholdClient upholdClient = new UpholdClient();

        Assert.assertNull(SessionManager.INSTANCE.getBearerToken());
        Assert.assertNotNull(upholdClient.getToken().getUpholdRestAdapter());
    }

    @Test(expected = UpholdSdkNotInitializedException.class)
    public void upholdClientWithoutTokenShouldThrowUpholdSdkNotInitializedException() throws Exception {
        UpholdClient upholdClient = new UpholdClient();
    }

    @Test
    public void upholdClientWithTokenShouldReturnTheBearerTokenAndRestAdapter() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext());

        UpholdClient upholdClient = new UpholdClient("foobiz");

        Assert.assertEquals(SessionManager.INSTANCE.getBearerToken(), "foobiz");
        Assert.assertNotNull(upholdClient.getToken().getUpholdRestAdapter());
    }

    @Test(expected = UpholdSdkNotInitializedException.class)
    public void upholdClientWithTokenShouldThrowUpholdSdkNotInitializedException() throws Exception {
        UpholdClient upholdClient = new UpholdClient("foobiz");
    }

    @Test
    public void completeAuthorizationShouldReturnUpholdClientExceptionStateMatchError() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext());

        String responseString = "{ \"access_token\": \"foo\", \"description\": \"bar\", \"expires\": null }";
        MockRestAdapter<AuthenticationResponse> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, AuthenticationResponse>() {
            @Override
            public Promise<AuthenticationResponse> call(UpholdRestAdapter adapter) {
                try {
                    UpholdClient client = new UpholdClient("foobar");
                    Uri uri = Uri.parse("uphold://foobar.com?state=foobar");

                    client.getToken().setUpholdRestAdapter(adapter);

                    return client.completeAuthorization(uri, "foo", "bar", "foobar", "foobuz");
                } catch (UpholdSdkNotInitializedException e) {
                    return null;
                }
            }
        });

        Exception exception = adapter.getException();

        Assert.assertEquals(exception.getClass().getName(), StateMatchException.class.getName());
        Assert.assertEquals(exception.getMessage(), "State does not match.");
    }

    @Test
    public void completeAuthorizationShouldReturnTheAuthenticationResponse() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext());

        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        String responseString = "{ \"access_token\": \"foo\", \"token_type\": \"bar\", \"expires_in\": 1234, \"scope\": \"foobar\"}";
        MockRestAdapter<AuthenticationResponse> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, AuthenticationResponse>() {
            @Override
            public Promise<AuthenticationResponse> call(UpholdRestAdapter adapter) {
                try {
                    UpholdClient client = new UpholdClient("foobar");
                    Uri uri = Uri.parse("uphold://foobar.com?code=foo&state=foobar");

                    client.getToken().setUpholdRestAdapter(adapter);

                    return client.completeAuthorization(uri, "foo", "bar", "foobiz", "foobar");
                } catch (UpholdSdkNotInitializedException e) {
                    return null;
                }
            }
        });

        AuthenticationResponse authenticationResponse = adapter.getResult();
        Request request = adapter.getRequest();

        request.getBody().writeTo(bodyOutput);

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/oauth2/token", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(authenticationResponse.getAccessToken(), "foo");
        Assert.assertEquals(authenticationResponse.getExpiresIn(), Integer.valueOf(1234));
        Assert.assertEquals(authenticationResponse.getScope(), "foobar");
        Assert.assertEquals(authenticationResponse.getTokenType(), "bar");
        Assert.assertEquals(bodyOutput.toString(), "code=foo&grant_type=foobiz");
        Assert.assertTrue(request.getHeaders().contains(new Header("Authorization", "Basic Zm9vOmJhcg==")));
    }

    @Test
    public void getTickersShouldReturnTheListOfRates() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext());

        String responseString = "[" +
            "{" +
                "\"ask\": \"foo\"," +
                "\"bid\": \"bar\"," +
                "\"currency\": \"foobar\"," +
                "\"pair\": \"foobiz\"" +
            "}, {" +
                "\"ask\": \"fiz\"," +
                "\"bid\": \"biz\"," +
                "\"currency\": \"foobiz\"," +
                "\"pair\": \"bar\"" +
            "}, {" +
                "\"ask\": \"foobar\"," +
                "\"bid\": \"foobaz\"," +
                "\"currency\": \"bar\"," +
                "\"pair\": \"foo\"" +
            "}" +
        "]";
        MockRestAdapter<List<Rate>> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Rate>>() {
            @Override
            public Promise<List<Rate>> call(UpholdRestAdapter adapter) {
                try {
                    UpholdClient client = new UpholdClient("foobar");

                    client.getToken().setUpholdRestAdapter(adapter);

                    return client.getTicker();
                } catch (UpholdSdkNotInitializedException exception) {
                    return null;
                }
            }
        });

        List<Rate> rates = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/ticker", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(rates.size(), 3);
        Assert.assertEquals(rates.get(0).getAsk(), "foo");
        Assert.assertEquals(rates.get(0).getBid(), "bar");
        Assert.assertEquals(rates.get(0).getCurrency(), "foobar");
        Assert.assertEquals(rates.get(0).getPair(), "foobiz");
        Assert.assertEquals(rates.get(1).getAsk(), "fiz");
        Assert.assertEquals(rates.get(1).getBid(), "biz");
        Assert.assertEquals(rates.get(1).getCurrency(), "foobiz");
        Assert.assertEquals(rates.get(1).getPair(), "bar");
        Assert.assertEquals(rates.get(2).getAsk(), "foobar");
        Assert.assertEquals(rates.get(2).getBid(), "foobaz");
        Assert.assertEquals(rates.get(2).getCurrency(), "bar");
        Assert.assertEquals(rates.get(2).getPair(), "foo");
    }

    @Test
    public void getTickersByCurrencyShouldReturnTheListOfRates() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext());

        String responseString = "[" +
          "{" +
              "\"ask\": \"foo\"," +
              "\"bid\": \"bar\"," +
              "\"currency\": \"foobar\"," +
              "\"pair\": \"foobiz\"" +
          "}, {"  +
              "\"ask\": \"fuz\"," +
              "\"bid\": \"buz\"," +
              "\"currency\": \"foobuz\"," +
              "\"pair\": \"bar\"" +
          "}, {"  +
              "\"ask\": \"foobar\"," +
              "\"bid\": \"foobaz\"," +
              "\"currency\": \"bar\"," +
              "\"pair\": \"foo\"" +
          "}" +
        "]";
        MockRestAdapter<List<Rate>> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Rate>>() {
            @Override
            public Promise<List<Rate>> call(UpholdRestAdapter adapter) {
                try {
                    UpholdClient client = new UpholdClient("foobar");

                    client.getToken().setUpholdRestAdapter(adapter);

                    return client.getTickersByCurrency("USD");
                } catch (UpholdSdkNotInitializedException e) {
                    return null;
                }
            }
        });

        List<Rate> rates = adapter.getResult();

        Assert.assertEquals(adapter.getRequest().getMethod(), "GET");
        Assert.assertEquals(adapter.getRequest().getUrl(), String.format("%s/v0/ticker/USD", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(rates.size(), 3);
        Assert.assertEquals(rates.get(0).getAsk(), "foo");
        Assert.assertEquals(rates.get(1).getAsk(), "fuz");
        Assert.assertEquals(rates.get(2).getAsk(), "foobar");
    }

    @Test
    public void getTokenShouldReturnTheToken() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext());

        UpholdClient upholdClient = new UpholdClient("foobar");

        Assert.assertEquals(SessionManager.INSTANCE.getBearerToken(), "foobar");
    }

    @Test
    public void setTokenShouldSetTheToken() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext());

        UpholdClient upholdClient = new UpholdClient("foobar");
        upholdClient.setToken(new Token("foobar"));

        Assert.assertEquals(SessionManager.INSTANCE.getBearerToken(), "foobar");
    }

    @Test
    public void getUserShouldReturnTheUser() throws Exception {
        UpholdClient.initialize(new MockSharedPreferencesContext());

        String responseString = "{" +
            "\"username\": \"foobar\"," +
            "\"email\": \"foobar@bfoobar.org\"," +
            "\"firstName\": \"foo\"," +
            "\"lastName\": \"bar\"," +
            "\"memberAt\": \"Foobar\"," +
            "\"name\": \"Foo Bar\"," +
            "\"country\": \"BAR\"," +
            "\"state\": \"FOO\"," +
            "\"currencies\": [" +
                "\"BTC\"," +
            "]," +
            "\"status\": \"ok\"," +
            "\"settings\": {" +
                "\"theme\": \"minimalistic\"," +
                "\"currency\": \"USD\"," +
                "\"hasNewsSubscription\": \"true\"," +
                "\"intl\": {" +
                    "\"language\": {" +
                        "\"locale\": \"en-US\"" +
                    "}," +
                    "\"dateTimeFormat\": {" +
                        "\"locale\": \"en-US\"" +
                    "}," +
                    "\"numberFormat\": {" +
                        "\"locale\": \"en-US\"" +
                    "}" +
                "}," +
                "\"otp\": {" +
                    "\"login\": {" +
                        "\"enabled\": false" +
                    "}," +
                    "\"transactions\": {" +
                        "\"send\": {" +
                            "\"enabled\": false" +
                        "}," +
                        "\"transfer\": {" +
                            "\"enabled\": true" +
                        "}," +
                        "\"withdraw\": {" +
                            "\"crypto\": {" +
                                "\"enabled\": true" +
                            "}" +
                        "}" +
                    "}" +
                "}" +
            "}," +
            "\"verifications\": {" +
                "\"email\": {" +
                    "\"status\": \"unconfirmed\"" +
                "}," +
                "\"phone\": {" +
                    "\"status\": \"required\"" +
                "}," +
                "\"address\": {" +
                    "\"status\": \"required\"" +
                "}," +
                "\"identity\": {" +
                    "\"status\": \"required\"" +
                "}," +
                "\"location\": {" +
                    "\"reason\": \"state\"," +
                    "\"status\": \"required\"" +
                "}," +
                "\"birthdate\": {" +
                    "\"status\": \"required\"" +
                "}," +
                "\"marketing\": {" +
                    "\"status\": \"required\"" +
                "}," +
                "\"terms\": {" +
                    "\"status\": \"required\"" +
                "}" +
            "}" +
            "}";
        MockRestAdapter<User> adapter = new MockRestAdapter<>(responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, User>() {
            @Override
            public Promise<User> call(UpholdRestAdapter adapter) {
                try {
                    UpholdClient client = new UpholdClient("foobar");

                    client.getToken().setUpholdRestAdapter(adapter);

                    return client.getUser();
                } catch (UpholdSdkNotInitializedException e) {
                    return null;
                }
            }
        });

        Request request = adapter.getRequest();
        User user = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(user.getCountry(), "BAR");
        Assert.assertEquals(user.getEmail(), "foobar@bfoobar.org");
        Assert.assertEquals(user.getFirstName(), "foo");
        Assert.assertEquals(user.getLastName(), "bar");
        Assert.assertEquals(user.getMemberAt(), "Foobar");
        Assert.assertEquals(user.getName(), "Foo Bar");
        Assert.assertEquals(user.getSettings().getCurrency(), "USD");
        Assert.assertEquals(user.getSettings().getIntl().getDateTimeFormat().getLocale(), "en-US");
        Assert.assertEquals(user.getSettings().getIntl().getLanguage().getLocale(), "en-US");
        Assert.assertEquals(user.getSettings().getIntl().getNumberFormat().getLocale(), "en-US");
        Assert.assertEquals(user.getSettings().getTheme(), "minimalistic");
        Assert.assertEquals(user.getState(), "FOO");
        Assert.assertEquals(user.getStatus(), "ok");
        Assert.assertEquals(user.getUsername(), "foobar");
        Assert.assertFalse(user.getSettings().getOtp().getLogin().getEnabled());
        Assert.assertFalse(user.getSettings().getOtp().getTransactions().getSend().getEnabled());
        Assert.assertTrue(user.getSettings().getOtp().getTransactions().getTransfer().getEnabled());
        Assert.assertTrue(user.getSettings().getOtp().getTransactions().getWithdraw().getCrypto().getEnabled());
        Assert.assertTrue(user.getSettings().getHasNewsSubscription());
        Assert.assertEquals(user.getVerifications().getAddress().getStatus(), "required");
        Assert.assertEquals(user.getVerifications().getBirthdate().getStatus(), "required");
        Assert.assertEquals(user.getVerifications().getEmail().getStatus(), "unconfirmed");
        Assert.assertEquals(user.getVerifications().getIdentity().getStatus(), "required");
        Assert.assertEquals(user.getVerifications().getLocation().getReason(), "state");
        Assert.assertEquals(user.getVerifications().getLocation().getStatus(), "required");
        Assert.assertEquals(user.getVerifications().getMarketing().getStatus(), "required");
        Assert.assertEquals(user.getVerifications().getPhone().getStatus(), "required");
        Assert.assertEquals(user.getVerifications().getTerms().getStatus(), "required");
    }

    @After
    public void tearDown() throws Exception {
        Field sdkInitializedField = UpholdClient.class.getDeclaredField("sdkInitialized");

        sdkInitializedField.setAccessible(true);
        sdkInitializedField.set(null, false);
    }

}
