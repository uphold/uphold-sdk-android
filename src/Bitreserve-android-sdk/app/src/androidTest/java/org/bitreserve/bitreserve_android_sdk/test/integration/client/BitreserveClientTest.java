package org.bitreserve.bitreserve_android_sdk.test.integration.client;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.BitreserveClient;
import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationRequest;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationResponse;
import org.bitreserve.bitreserve_android_sdk.model.Rate;
import org.bitreserve.bitreserve_android_sdk.model.Token;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.List;

import retrofit.client.Request;

/**
 * Integration tests to the {@link BitreserveClient}.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class BitreserveClientTest {

    @Test
    public void bitreserveClientWithTokenShouldSetTheTokenAndRestAdapter() {
        BitreserveClient bitreserveClient = new BitreserveClient(null);

        Assert.assertNull(bitreserveClient.getToken().getBearerToken());
        Assert.assertNotNull(bitreserveClient.getToken().getBitreserveRestAdapter());
    }

    @Test
    public void bitreserveClientWithTokenShouldReturnTheBearerTokenAndRestAdapter() {
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");

        Assert.assertEquals(bitreserveClient.getToken().getBearerToken(), "foobar");
        Assert.assertNotNull(bitreserveClient.getToken().getBitreserveRestAdapter());
    }

    @Test
    public void authenticateUserShouldReturnTheAuthenticationResponse() throws Exception {
        String responseString = "{ \"access_token\": \"foo\", \"description\": \"bar\", \"expires\": null }";
        MockRestAdapter<AuthenticationResponse> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, AuthenticationResponse>() {
            @Override
            public Promise<AuthenticationResponse> call(BitreserveRestAdapter adapter) {
                BitreserveClient client = new BitreserveClient("foobar");

                client.getToken().setBitreserveRestAdapter(adapter);

                return client.authenticateUser("otp", "user", "password", new AuthenticationRequest("description"));
            }
        });

        AuthenticationResponse authenticationResponse = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getHeaders().get(0).getName(), "X-Bitreserve-OTP");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "otp");
        Assert.assertEquals(request.getHeaders().get(1).getName(), "Authorization");
        Assert.assertEquals(request.getHeaders().get(1).getValue(), "Basic dXNlcjpwYXNzd29yZA==");
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/tokens");
        Assert.assertEquals(authenticationResponse.getAccessToken(), "foo");
        Assert.assertEquals(authenticationResponse.getDescription(), "bar");
        Assert.assertNull(authenticationResponse.getExpiresIn());
    }

    @Test
    public void getReserveShouldReturnTheReserveWithRestAdapter() {
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");

        Assert.assertEquals(bitreserveClient.getReserve().getBitreserveRestAdapter(), bitreserveClient.getToken().getBitreserveRestAdapter());
    }

    @Test
    public void getTickersShouldReturnTheListOfRates() throws Exception {
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
                BitreserveClient client = new BitreserveClient("foobar");

                client.getToken().setBitreserveRestAdapter(adapter);

                return client.getTicker();
            }
        });

        List<Rate> rates = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/ticker");
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
                BitreserveClient client = new BitreserveClient("foobar");

                client.getToken().setBitreserveRestAdapter(adapter);

                return client.getTickersByCurrency("USD");
            }
        });

        List<Rate> rates = adapter.getResult();

        Assert.assertEquals(adapter.getRequest().getMethod(), "GET");
        Assert.assertEquals(adapter.getRequest().getUrl(), "https://api.bitreserve.org/v0/ticker/USD");
        Assert.assertEquals(rates.size(), 3);
        Assert.assertEquals(rates.get(0).getAsk(), "foo");
        Assert.assertEquals(rates.get(1).getAsk(), "fuz");
        Assert.assertEquals(rates.get(2).getAsk(), "foobar");
    }

    @Test
    public void getTokenShouldReturnTheToken() {
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");

        Assert.assertEquals(bitreserveClient.getToken().getBearerToken(), "foobar");
    }

    @Test
    public void setTokenShouldSetTheToken() {
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");
        bitreserveClient.setToken(new Token("new foobar"));

        Assert.assertEquals(bitreserveClient.getToken().getBearerToken(), "new foobar");
    }

    @Test
    public void getUserShouldReturnTheUser() throws Exception {
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
            "\"status\": {" +
            "\"email\": \"ok\"," +
            "\"phone\": \"pending\"," +
            "\"review\": \"pending\"," +
            "\"volume\": \"ok\"," +
            "\"identity\": \"pending\"," +
            "\"overview\": \"pending\"," +
            "\"screening\": \"pending\"," +
            "\"registration\": \"running\"" +
            "}," +
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
                BitreserveClient client = new BitreserveClient("foobar");

                client.getToken().setBitreserveRestAdapter(adapter);

                return client.getUser();
            }
        });

        Request request = adapter.getRequest();
        User user = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me");
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
        Assert.assertEquals(user.getStatus().getEmail(), "ok");
        Assert.assertEquals(user.getStatus().getIdentity(), "pending");
        Assert.assertEquals(user.getStatus().getOverview(), "pending");
        Assert.assertEquals(user.getStatus().getPhone(), "pending");
        Assert.assertEquals(user.getStatus().getRegistration(), "running");
        Assert.assertEquals(user.getStatus().getReview(), "pending");
        Assert.assertEquals(user.getStatus().getScreening(), "pending");
        Assert.assertEquals(user.getStatus().getVolume(), "ok");
        Assert.assertEquals(user.getUsername(), "foobar");
        Assert.assertFalse(user.getSettings().getHasOtpEnabled());
        Assert.assertTrue(user.getSettings().getHasNewsSubscription());
    }

}
