package org.bitreserve.bitreserve_android_sdk.test.integration.client;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.BuildConfig;
import org.bitreserve.bitreserve_android_sdk.client.BitreserveClient;
import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.session.SessionManager;
import org.bitreserve.bitreserve_android_sdk.exception.BitreserveSdkNotInitializedException;
import org.bitreserve.bitreserve_android_sdk.exception.StateMatchException;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationResponse;
import org.bitreserve.bitreserve_android_sdk.model.Rate;
import org.bitreserve.bitreserve_android_sdk.model.Token;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.test.util.MockSharedPreferencesContext;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.net.Uri;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.ByteArrayOutputStream;
import java.lang.String;
import java.lang.reflect.Field;
import java.util.List;

import retrofit.client.Header;
import retrofit.client.Request;

/**
 * BitreserveClient integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class BitreserveClientTest {

    @Test
    public void bitreserveClientWithoutTokenShouldSetTheTokenAndRestAdapter() throws Exception {
        BitreserveClient.initialize(new MockSharedPreferencesContext());

        BitreserveClient bitreserveClient = new BitreserveClient();

        Assert.assertNull(SessionManager.INSTANCE.getBearerToken());
        Assert.assertNotNull(bitreserveClient.getToken().getBitreserveRestAdapter());
    }

    @Test(expected = BitreserveSdkNotInitializedException.class)
    public void bitreserveClientWithoutTokenShouldThrowBitreserveSdkNotInitializedException() throws Exception {
        BitreserveClient bitreserveClient = new BitreserveClient();
    }

    @Test
    public void bitreserveClientWithTokenShouldReturnTheBearerTokenAndRestAdapter() throws Exception {
        BitreserveClient.initialize(new MockSharedPreferencesContext());

        BitreserveClient bitreserveClient = new BitreserveClient("foobiz");

        Assert.assertEquals(SessionManager.INSTANCE.getBearerToken(), "foobiz");
        Assert.assertNotNull(bitreserveClient.getToken().getBitreserveRestAdapter());
    }

    @Test(expected = BitreserveSdkNotInitializedException.class)
    public void bitreserveClientWithTokenShouldThrowBitreserveSdkNotInitializedException() throws Exception {
        BitreserveClient bitreserveClient = new BitreserveClient("foobiz");
    }

    @Test
    public void completeAuthorizationShouldReturnBitreserveClientExceptionStateMatchError() throws Exception {
        BitreserveClient.initialize(new MockSharedPreferencesContext());

        String responseString = "{ \"access_token\": \"foo\", \"description\": \"bar\", \"expires\": null }";
        MockRestAdapter<AuthenticationResponse> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, AuthenticationResponse>() {
            @Override
            public Promise<AuthenticationResponse> call(BitreserveRestAdapter adapter) {
                try {
                    BitreserveClient client = new BitreserveClient("foobar");
                    Uri uri = Uri.parse("bitreserve://foobar.com?state=foobar");

                    client.getToken().setBitreserveRestAdapter(adapter);

                    return client.completeAuthorization(uri, "foo", "bar", "foobar", "foobuz");
                } catch (BitreserveSdkNotInitializedException e) {
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
        BitreserveClient.initialize(new MockSharedPreferencesContext());

        ByteArrayOutputStream bodyOutput = new ByteArrayOutputStream();
        String responseString = "{ \"access_token\": \"foo\", \"token_type\": \"bar\", \"expires_in\": 1234, \"scope\": \"foobar\"}";
        MockRestAdapter<AuthenticationResponse> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, AuthenticationResponse>() {
            @Override
            public Promise<AuthenticationResponse> call(BitreserveRestAdapter adapter) {
                try {
                    BitreserveClient client = new BitreserveClient("foobar");
                    Uri uri = Uri.parse("bitreserve://foobar.com?code=foo&state=foobar");

                    client.getToken().setBitreserveRestAdapter(adapter);

                    return client.completeAuthorization(uri, "foo", "bar", "foobiz", "foobar");
                } catch (BitreserveSdkNotInitializedException e) {
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
        BitreserveClient.initialize(new MockSharedPreferencesContext());

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
        MockRestAdapter<List<Rate>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Rate>>() {
            @Override
            public Promise<List<Rate>> call(BitreserveRestAdapter adapter) {
                try {
                    BitreserveClient client = new BitreserveClient("foobar");

                    client.getToken().setBitreserveRestAdapter(adapter);

                    return client.getTicker();
                } catch (BitreserveSdkNotInitializedException exception) {
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
        BitreserveClient.initialize(new MockSharedPreferencesContext());

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
        MockRestAdapter<List<Rate>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Rate>>() {
            @Override
            public Promise<List<Rate>> call(BitreserveRestAdapter adapter) {
                try {
                    BitreserveClient client = new BitreserveClient("foobar");

                    client.getToken().setBitreserveRestAdapter(adapter);

                    return client.getTickersByCurrency("USD");
                } catch (BitreserveSdkNotInitializedException e) {
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
        BitreserveClient.initialize(new MockSharedPreferencesContext());

        BitreserveClient bitreserveClient = new BitreserveClient("foobar");

        Assert.assertEquals(SessionManager.INSTANCE.getBearerToken(), "foobar");
    }

    @Test
    public void setTokenShouldSetTheToken() throws Exception {
        BitreserveClient.initialize(new MockSharedPreferencesContext());

        BitreserveClient bitreserveClient = new BitreserveClient("foobar");
        bitreserveClient.setToken(new Token("foobar"));

        Assert.assertEquals(SessionManager.INSTANCE.getBearerToken(), "foobar");
    }

    @Test
    public void getUserShouldReturnTheUser() throws Exception {
        BitreserveClient.initialize(new MockSharedPreferencesContext());

        String responseString = "{" +
            "\"username\": \"foobar\"," +
            "\"email\": \"foobar@bfoobar.org\"," +
            "\"firstName\": \"foo\"," +
            "\"lastName\": \"bar\"," +
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
              "\"hasOtpEnabled\": \"false\"" +
            "}" +
        "}";
        MockRestAdapter<User> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, User>() {
            @Override
            public Promise<User> call(BitreserveRestAdapter adapter) {
                try {
                    BitreserveClient client = new BitreserveClient("foobar");

                    client.getToken().setBitreserveRestAdapter(adapter);

                    return client.getUser();
                } catch (BitreserveSdkNotInitializedException e) {
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
        Assert.assertEquals(user.getName(), "Foo Bar");
        Assert.assertEquals(user.getSettings().getCurrency(), "USD");
        Assert.assertEquals(user.getSettings().getIntl().getDateTimeFormat().getLocale(), "en-US");
        Assert.assertEquals(user.getSettings().getIntl().getLanguage().getLocale(), "en-US");
        Assert.assertEquals(user.getSettings().getIntl().getNumberFormat().getLocale(), "en-US");
        Assert.assertEquals(user.getSettings().getTheme(), "minimalistic");
        Assert.assertEquals(user.getState(), "FOO");
        Assert.assertEquals(user.getStatus(), "ok");
        Assert.assertEquals(user.getUsername(), "foobar");
        Assert.assertFalse(user.getSettings().getHasOtpEnabled());
        Assert.assertTrue(user.getSettings().getHasNewsSubscription());
    }

    @After
    public void tearDown() throws Exception {
        Field sdkInitializedField = BitreserveClient.class.getDeclaredField("sdkInitialized");

        sdkInitializedField.setAccessible(true);
        sdkInitializedField.set(null, false);
    }

}
