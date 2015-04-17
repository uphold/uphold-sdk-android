package org.bitreserve.bitreserve_android_sdk.test.integration.client;

import com.darylteo.rx.promises.java.functions.PromiseAction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.BitreserveClient;
import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.config.GlobalConfigurations;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationRequest;
import org.bitreserve.bitreserve_android_sdk.model.AuthenticationResponse;
import org.bitreserve.bitreserve_android_sdk.model.Ticker;
import org.bitreserve.bitreserve_android_sdk.model.Token;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Header;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Integration tests to the {@link BitreserveClient}.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class BitreserveClientTest {

    @Test
    public void bitreserveClientWithTokenShouldSetTokenAndRestAdapter() {
        BitreserveClient bitreserveClient = new BitreserveClient(null);

        Assert.assertNull(bitreserveClient.getToken().getBearerToken());
        Assert.assertNotNull(bitreserveClient.getToken().getBitreserveRestAdapter());
    }

    @Test
    public void bitreserveClientWithTokenShouldReturnTokenAndRestAdapter() {
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");

        Assert.assertEquals(bitreserveClient.getToken().getBearerToken(), "foobar");
        Assert.assertNotNull(bitreserveClient.getToken().getBitreserveRestAdapter());
    }

    @Test
    public void authenticateUserShouldReturnPromiseWithAuthenticationResponse() throws Exception {
        final AtomicReference<AuthenticationResponse> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");
        BitreserveRestAdapter mockRestDapter = new BitreserveRestAdapter("foobar");

        mockRestDapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "{" +
                    "\"access_token\": \"foo\"," +
                    "\"description\": \"bar\"," +
                    "\"expires_in\": null" +
                "}";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<Header>() {{
                    add(new retrofit.client.Header("foo", "bar"));
                }}, new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        bitreserveClient.getToken().setBitreserveRestAdapter(mockRestDapter);
        bitreserveClient.authenticateUser("otp", "user", "password", new AuthenticationRequest("description")).then(new PromiseAction<AuthenticationResponse>() {
            @Override
            public void call(AuthenticationResponse authenticationResponse) {
                bodyRefResponse.set(authenticationResponse);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        AuthenticationResponse authenticationResponse = bodyRefResponse.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(authenticationResponse.getAccessToken(), "foo");
        Assert.assertEquals(authenticationResponse.getDescription(), "bar");
        Assert.assertNull(authenticationResponse.getExpiresIn());
        Assert.assertEquals(request.getHeaders().get(0).getName(), "X-Bitreserve-OTP");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "otp");
        Assert.assertEquals(request.getHeaders().get(1).getName(), "Authorization");
        Assert.assertEquals(request.getHeaders().get(1).getValue(), "Basic dXNlcjpwYXNzd29yZA==");
        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/tokens");
    }

    @Test
    public void getReserveShouldReturnReserveWithRestAdapter() {
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");

        Assert.assertEquals(bitreserveClient.getReserve().getBitreserveRestAdapter(), bitreserveClient.getToken().getBitreserveRestAdapter());
    }

    @Test
    public void getTickersShouldReturnPromiseWithListTickers() throws Exception {
        final AtomicReference<List<Ticker>> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");
        BitreserveRestAdapter mockRestDapter = new BitreserveRestAdapter("foobar");

        mockRestDapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
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

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<Header>() {{
                    add(new retrofit.client.Header("foo", "bar"));
                }}, new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        bitreserveClient.getToken().setBitreserveRestAdapter(mockRestDapter);
        bitreserveClient.getTickers().then(new PromiseAction<List<Ticker>>() {
            @Override
            public void call(List<Ticker> tickers) {
                bodyRefResponse.set(tickers);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        Request request = bodyRefRequest.get();
        List<Ticker> tickersResponse = bodyRefResponse.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/ticker");
        Assert.assertEquals(tickersResponse.size(), 3);
        Assert.assertEquals(tickersResponse.get(0).getAsk(), "foo");
        Assert.assertEquals(tickersResponse.get(0).getBid(), "bar");
        Assert.assertEquals(tickersResponse.get(0).getCurrency(), "foobar");
        Assert.assertEquals(tickersResponse.get(0).getPair(), "foobiz");
        Assert.assertEquals(tickersResponse.get(1).getAsk(), "fiz");
        Assert.assertEquals(tickersResponse.get(1).getBid(), "biz");
        Assert.assertEquals(tickersResponse.get(1).getCurrency(), "foobiz");
        Assert.assertEquals(tickersResponse.get(1).getPair(), "bar");
        Assert.assertEquals(tickersResponse.get(2).getAsk(), "foobar");
        Assert.assertEquals(tickersResponse.get(2).getBid(), "foobaz");
        Assert.assertEquals(tickersResponse.get(2).getCurrency(), "bar");
        Assert.assertEquals(tickersResponse.get(2).getPair(), "foo");
    }

    @Test
    public void getTickersByCurrencyShouldReturnPromiseWithListTickers() throws Exception {
        final AtomicReference<List<Ticker>> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");
        BitreserveRestAdapter mockRestDapter = new BitreserveRestAdapter("foobar");

        mockRestDapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
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

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<Header>() {{
                    add(new retrofit.client.Header("foo", "bar"));
                }}, new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        bitreserveClient.getToken().setBitreserveRestAdapter(mockRestDapter);
        bitreserveClient.getTickersByCurrency("USD").then(new PromiseAction<List<Ticker>>() {
            @Override
            public void call(List<Ticker> tickers) {
                bodyRefResponse.set(tickers);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        Request request = bodyRefRequest.get();
        List<Ticker> tickersResponse = bodyRefResponse.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/ticker/USD");
        Assert.assertEquals(tickersResponse.size(), 3);
        Assert.assertEquals(tickersResponse.get(0).getAsk(), "foo");
        Assert.assertEquals(tickersResponse.get(0).getBid(), "bar");
        Assert.assertEquals(tickersResponse.get(0).getCurrency(), "foobar");
        Assert.assertEquals(tickersResponse.get(0).getPair(), "foobiz");
        Assert.assertEquals(tickersResponse.get(1).getAsk(), "fuz");
        Assert.assertEquals(tickersResponse.get(1).getBid(), "buz");
        Assert.assertEquals(tickersResponse.get(1).getCurrency(), "foobuz");
        Assert.assertEquals(tickersResponse.get(1).getPair(), "bar");
        Assert.assertEquals(tickersResponse.get(2).getAsk(), "foobar");
        Assert.assertEquals(tickersResponse.get(2).getBid(), "foobaz");
        Assert.assertEquals(tickersResponse.get(2).getCurrency(), "bar");
        Assert.assertEquals(tickersResponse.get(2).getPair(), "foo");
    }

    @Test
    public void getTokenShouldReturnToken() {
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");

        Assert.assertEquals(bitreserveClient.getToken().getBearerToken(), "foobar");
    }

    @Test
    public void setTokenShouldSetToken() {
        BitreserveClient bitreserveClient = new BitreserveClient("foobar");
        bitreserveClient.setToken(new Token("new foobar"));

        Assert.assertEquals(bitreserveClient.getToken().getBearerToken(), "new foobar");
    }

    @Test
    public void getUserShouldReturnUserWithPromise() throws Exception {
        final AtomicReference<User> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        BitreserveClient mockedBitreserveClient = new BitreserveClient("foobar");
        BitreserveRestAdapter mockRestDapter = new BitreserveRestAdapter("foobar");

        mockRestDapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
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

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<Header>() {{
                    add(new retrofit.client.Header("foo", "bar"));
                }}, new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        mockedBitreserveClient.getToken().setBitreserveRestAdapter(mockRestDapter);
        mockedBitreserveClient.getUser().then(new PromiseAction<User>() {
            @Override
            public void call(User user) {
                bodyRefResponse.set(user);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        Request request = bodyRefRequest.get();
        User userResponse = bodyRefResponse.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me");
        Assert.assertEquals(userResponse.getCountry(), "BAR");
        Assert.assertEquals(userResponse.getEmail(), "foobar@bfoobar.org");
        Assert.assertEquals(userResponse.getFirstName(), "foo");
        Assert.assertEquals(userResponse.getLastName(), "bar");
        Assert.assertEquals(userResponse.getName(), "Foo Bar");
        Assert.assertEquals(userResponse.getSettings().getCurrency(), "USD");
        Assert.assertEquals(userResponse.getSettings().getIntl().getDateTimeFormat().getLocale(), "en-US");
        Assert.assertEquals(userResponse.getSettings().getIntl().getLanguage().getLocale(), "en-US");
        Assert.assertEquals(userResponse.getSettings().getIntl().getNumberFormat().getLocale(), "en-US");
        Assert.assertEquals(userResponse.getSettings().getTheme(), "minimalistic");
        Assert.assertEquals(userResponse.getState(), "FOO");
        Assert.assertEquals(userResponse.getStatus().getEmail(), "ok");
        Assert.assertEquals(userResponse.getStatus().getIdentity(), "pending");
        Assert.assertEquals(userResponse.getStatus().getOverview(), "pending");
        Assert.assertEquals(userResponse.getStatus().getPhone(), "pending");
        Assert.assertEquals(userResponse.getStatus().getRegistration(), "running");
        Assert.assertEquals(userResponse.getStatus().getReview(), "pending");
        Assert.assertEquals(userResponse.getStatus().getScreening(), "pending");
        Assert.assertEquals(userResponse.getStatus().getVolume(), "ok");
        Assert.assertEquals(userResponse.getUsername(), "foobar");
        Assert.assertFalse(userResponse.getSettings().getHasOtpEnabled());
        Assert.assertTrue(userResponse.getSettings().getHasNewsSubscription());
    }

}