package org.bitreserve.bitreserve_android_sdk.test.integration.model;

import com.darylteo.rx.promises.java.functions.PromiseAction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.config.GlobalConfigurations;
import org.bitreserve.bitreserve_android_sdk.model.Card;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.model.balance.Currency;
import org.bitreserve.bitreserve_android_sdk.model.balance.UserBalance;
import org.bitreserve.bitreserve_android_sdk.model.card.CardRequest;
import org.bitreserve.bitreserve_android_sdk.model.user.Contact;
import org.bitreserve.bitreserve_android_sdk.model.user.Phone;
import org.bitreserve.bitreserve_android_sdk.paginator.Paginator;
import org.bitreserve.bitreserve_android_sdk.test.util.Fixtures;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

/**
 * Integration tests to the class {@link User}.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class UserTest {

    @Test
    public void createCardShouldReturnTheCard() throws Exception {
        final AtomicReference<Card> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "{" +
                    "\"id\": \"foobar\"," +
                    "\"address\": {" +
                        "\"bitcoin\": \"foobiz\"" +
                    "}," +
                    "\"label\": \"foo\"," +
                    "\"currency\": \"BAR\"," +
                    "\"balance\": \"0.00\"," +
                    "\"available\": \"0.00\"," +
                    "\"lastTransactionAt\": \"2014-07-07T05:40:46.624Z\"," +
                    "\"addresses\": [" +
                        "{" +
                            "\"id\": \"foobuz\"," +
                            "\"network\": \"bitcoin\"" +
                        "}, {" +
                            "\"id\": \"foobaz\"," +
                            "\"network\": \"bitcoin\"" +
                        "}" +
                    "]," +
                    "\"settings\": [" +
                        "{" +
                            "\"position\": \"7\"," +
                            "\"starred\": true" +
                        "}" +
                    "]" +
                "}";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>(), new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        user.createCard(new CardRequest("foo", "BTC")).then(new PromiseAction<Card>() {
            @Override
            public void call(Card card) {
                bodyRefResponse.set(card);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        Card cards = bodyRefResponse.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards");
        Assert.assertEquals(cards.getAddress().get("bitcoin"), "foobiz");
        Assert.assertEquals(cards.getAddresses().size(), 2);
        Assert.assertEquals(cards.getAddresses().get(0).getId(), "foobuz");
        Assert.assertEquals(cards.getAddresses().get(0).getNetwork(), "bitcoin");
        Assert.assertEquals(cards.getAddresses().get(1).getId(), "foobaz");
        Assert.assertEquals(cards.getAddresses().get(1).getNetwork(), "bitcoin");
        Assert.assertEquals(cards.getAvailable(), "0.00");
        Assert.assertEquals(cards.getBalance(), "0.00");
        Assert.assertEquals(cards.getCurrency(), "BAR");
        Assert.assertEquals(cards.getId(), "foobar");
        Assert.assertEquals(cards.getLabel(), "foo");
        Assert.assertEquals(cards.getLastTransactionAt(), "2014-07-07T05:40:46.624Z");
        Assert.assertEquals(cards.getSettings().size(), 1);
        Assert.assertEquals(cards.getSettings().get(0).getPosition(), "7");
        Assert.assertTrue(cards.getAddress().containsKey("bitcoin"));
        Assert.assertTrue(cards.getSettings().get(0).getStarred());
    }

    @Test
    public void getBalancesShouldReturnTheListOfBalances() throws Exception {
        final AtomicReference<List<Currency>> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "{ \"balances\": { \"total\": \"1083.77\", \"currencies\": { \"CNY\": { \"amount\": \"6.98\" }, \"EUR\": { \"amount\": \"75.01\" } } } }";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>(), new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        user.getBalances().then(new PromiseAction<List<Currency>>() {
            @Override
            public void call(List<Currency> currencies) {
                bodyRefResponse.set(currencies);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        ArrayList<Currency> currencies = new ArrayList<>(bodyRefResponse.get());
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me");
        Assert.assertEquals(currencies.size(), 2);
        Assert.assertEquals(currencies.get(0).getAmount(), "6.98");
        Assert.assertEquals(currencies.get(1).getAmount(), "75.01");
    }

    @Test
    public void getBalanceByCurrencyShouldReturnTheCurrency() throws Exception {
        final AtomicReference<Currency> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "{" +
                    "\"balances\": {" +
                        "\"total\": \"1083.77\"," +
                        "\"currencies\": {" +
                            "\"CNY\": {" +
                                "\"amount\": \"6.98\"," +
                                "\"balance\": \"42.82\"," +
                                "\"currency\": \"USD\"," +
                                "\"rate\": \"6.13880\"" +
                            "}," +
                            "\"EUR\": {" +
                                "\"amount\": \"75.01\"," +
                                "\"balance\": \"58.05\"," +
                                "\"currency\": \"USD\"," +
                                "\"rate\": \"1.29220\"" +
                            "}" +
                        "}" +
                    "}" +
                "}";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>(), new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        user.getBalanceByCurrency("EUR").then(new PromiseAction<Currency>() {
            @Override
            public void call(Currency currency) {
                bodyRefResponse.set(currency);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        Currency currency = bodyRefResponse.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me");
        Assert.assertEquals(currency.getAmount(), "75.01");
        Assert.assertEquals(currency.getBalance(), "58.05");
        Assert.assertEquals(currency.getCurrency(), "USD");
        Assert.assertEquals(currency.getRate(), "1.29220");
    }

    @Test
    public void getCardsShouldReturnTheListOfCards() throws Exception {
        final AtomicReference<List<Card>> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "[ { \"id\": \"FOO\" }, { \"id\": \"BAR\" } ]";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>(), new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        user.getCards().then(new PromiseAction<List<Card>>() {
            @Override
            public void call(List<Card> cards) {
                bodyRefResponse.set(cards);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        List<Card> cards = bodyRefResponse.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards");
        Assert.assertEquals(cards.size(), 2);
        Assert.assertEquals(cards.get(0).getId(), "FOO");
        Assert.assertEquals(cards.get(1).getId(), "BAR");
    }

    @Test
    public void getCardByIdShouldReturnTheCardWithId() throws Exception {
        final AtomicReference<Card> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "{ \"id\": \"FOOBAR\" }";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>(), new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        user.getCardById("FOOBAR").then(new PromiseAction<Card>() {
            @Override
            public void call(Card card) {
                bodyRefResponse.set(card);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        Card card = bodyRefResponse.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards/FOOBAR");
        Assert.assertEquals(card.getId(), "FOOBAR");
    }

    @Test
    public void getCardsByCurrencyShouldReturnTheListOfCardsWithCurrency() throws Exception {
        final AtomicReference<List<Card>> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User mockedUserResponse = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "[ { \"id\": \"FOOBAR\", \"currency\": \"USD\" }, { \"id\": \"FOOBIZ\", \"currency\": \"BTC\" } ]";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>(), new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        mockedUserResponse.setBitreserveRestAdapter(mockRestAdapter);
        mockedUserResponse.getCardsByCurrency("BTC").then(new PromiseAction<List<Card>>() {
            @Override
            public void call(List<Card> cards) {
                bodyRefResponse.set(cards);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        List<Card> cards = bodyRefResponse.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards");
        Assert.assertEquals(cards.size(), 1);
        Assert.assertEquals(cards.get(0).getId(), "FOOBIZ");
    }

    @Test
    public void getContactsShouldReturnTheListOfContacts() throws Exception {
        final AtomicReference<List<Contact>> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "[" +
                    "{" +
                        "\"id\": \"FOOBAR\"," +
                        "\"firstName\": \"Foo\"," +
                        "\"lastName\": \"Bar\"," +
                        "\"company\": \"FOO\"," +
                        "\"emails\": [" +
                            "\"foo@bar.org\"" +
                        "]," +
                        "\"addresses\": []," +
                        "\"name\": \"Foo Bar\"" +
                    "}, {" +
                        "\"id\": \"FUZBAR\"," +
                        "\"firstName\": \"Fuz\"," +
                        "\"lastName\": \"Buz\"," +
                        "\"company\": \"BAR\"," +
                        "\"emails\": [" +
                            "\"fuzbuz@buz.org\"" +
                        "]," +
                        "\"addresses\": []," +
                        "\"name\": \"Fuz Buz\"" +
                    "}" +
                "]";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>(), new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        user.getContacts().then(new PromiseAction<List<Contact>>() {
            @Override
            public void call(List<Contact> contacts) {
                bodyRefResponse.set(contacts);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        List<Contact> contacts = bodyRefResponse.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/contacts");
        Assert.assertEquals(contacts.size(), 2);
        Assert.assertEquals(contacts.get(0).getAddresses().size(), 0);
        Assert.assertEquals(contacts.get(0).getCompany(), "FOO");
        Assert.assertEquals(contacts.get(0).getEmails().get(0), "foo@bar.org");
        Assert.assertEquals(contacts.get(0).getFirstName(), "Foo");
        Assert.assertEquals(contacts.get(0).getId(), "FOOBAR");
        Assert.assertEquals(contacts.get(0).getLastName(), "Bar");
        Assert.assertEquals(contacts.get(0).getName(), "Foo Bar");
        Assert.assertEquals(contacts.get(1).getAddresses().size(), 0);
        Assert.assertEquals(contacts.get(1).getCompany(), "BAR");
        Assert.assertEquals(contacts.get(1).getEmails().get(0), "fuzbuz@buz.org");
        Assert.assertEquals(contacts.get(1).getFirstName(), "Fuz");
        Assert.assertEquals(contacts.get(1).getId(), "FUZBAR");
        Assert.assertEquals(contacts.get(1).getLastName(), "Buz");
        Assert.assertEquals(contacts.get(1).getName(), "Fuz Buz");
    }

    @Test
    public void getCountryShouldReturnCountry() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("country", "foobar");
        }});

        Assert.assertEquals(user.getCountry(), "foobar");
    }

    @Test
    public void getEmailShouldReturnEmail() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("email", "foobar");
        }});

        Assert.assertEquals(user.getEmail(), "foobar");
    }

    @Test
    public void getFirstNameShouldReturnFirstName() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("firstName", "foobar");
        }});

        Assert.assertEquals(user.getFirstName(), "foobar");
    }

    @Test
    public void getLastNameShouldReturnLastName() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("lastName", "foobar");
        }});

        Assert.assertEquals(user.getLastName(), "foobar");
    }

    @Test
    public void getNameShouldReturnName() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("name", "foobar");
        }});

        Assert.assertEquals(user.getName(), "foobar");
    }

    @Test
    public void getPhonesShouldReturnTheListOfPhones() throws Exception {
        final AtomicReference<List<Phone>> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "[" +
                    "{" +
                        "\"id\": \"foobar\"," +
                        "\"verified\": \"true\"," +
                        "\"primary\": \"true\"," +
                        "\"e164Masked\": \"+XXXXXXXXX04\"," +
                        "\"nationalMasked\": \"(XXX) XXX-XX04\"," +
                        "\"internationalMasked\": \"+X XXX-XXX-XX04\"" +
                    "}" +
                "]";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>(), new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        user.getPhones().then(new PromiseAction<List<Phone>>() {
            @Override
            public void call(List<Phone> phones) {
                bodyRefResponse.set(phones);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        List<Phone> phones = bodyRefResponse.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/phones");
        Assert.assertEquals(phones.size(), 1);
        Assert.assertEquals(phones.get(0).getE164Masked(), "+XXXXXXXXX04");
        Assert.assertEquals(phones.get(0).getId(), "foobar");
        Assert.assertEquals(phones.get(0).getInternationalMasked(), "+X XXX-XXX-XX04");
        Assert.assertEquals(phones.get(0).getNationalMasked(), "(XXX) XXX-XX04");
        Assert.assertTrue(phones.get(0).getPrimary());
        Assert.assertTrue(phones.get(0).getVerified());
    }

    @Test
    public void getSettingsShouldReturnSettings() {
        HashMap<String, String> data = new HashMap<String, String>() {{
            put("currency", "USD");
            put("hasNewsSubscription", "true");
            put("hasOtpEnabled", "false");
            put("theme", "minimalistic");
            put("internationalizationUserSettingLanguage", "en-US");
            put("internationalizationUserSettingDateTimeFormat", "en-GB");
            put("internationalizationUserSettingNumberFormat", "en-AU");
        }};
        User user = Fixtures.loadUser(data);

        Assert.assertEquals(user.getSettings().getCurrency(), "USD");
        Assert.assertTrue(user.getSettings().getHasNewsSubscription());
        Assert.assertFalse(user.getSettings().getHasOtpEnabled());
        Assert.assertEquals(user.getSettings().getTheme(), "minimalistic");
        Assert.assertEquals(user.getSettings().getIntl().getLanguage().getLocale(), "en-US");
        Assert.assertEquals(user.getSettings().getIntl().getDateTimeFormat().getLocale(), "en-GB");
        Assert.assertEquals(user.getSettings().getIntl().getNumberFormat().getLocale(), "en-AU");
    }

    @Test
    public void getStateShouldReturnState() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("state", "foobar");
        }});

        Assert.assertEquals(user.getState(), "foobar");
    }

    @Test
    public void getStatusShouldReturnStatus() {
        HashMap<String, String> data = new HashMap<String, String>() {{
            put("emailStatus", "ok");
            put("identityStatus", "pending");
            put("overviewStatus", "ok");
            put("phoneStatus", "pending");
            put("registrationStatus", "running");
            put("reviewStatus", "ok");
            put("screeningStatus", "pending");
            put("volumeStatus", "running");
        }};
        User user = Fixtures.loadUser(data);

        Assert.assertEquals(user.getStatus().getEmail(), "ok");
        Assert.assertEquals(user.getStatus().getIdentity(), "pending");
        Assert.assertEquals(user.getStatus().getOverview(), "ok");
        Assert.assertEquals(user.getStatus().getPhone(), "pending");
        Assert.assertEquals(user.getStatus().getRegistration(), "running");
        Assert.assertEquals(user.getStatus().getReview(), "ok");
        Assert.assertEquals(user.getStatus().getScreening(), "pending");
        Assert.assertEquals(user.getStatus().getVolume(), "running");
    }

    @Test
    public void getTotalBalancesShouldReturnTheTotalBalance() throws Exception {
        final AtomicReference<UserBalance> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "{" +
                    "\"balances\": {" +
                        "\"total\": \"1083.77\"," +
                        "\"currencies\": {" +
                            "\"CNY\": {" +
                                "\"amount\": \"6.98\"," +
                                "\"balance\": \"42.82\"," +
                                "\"currency\": \"USD\"," +
                                "\"rate\": \"6.13880\"" +
                            "}," +
                            "\"EUR\": {" +
                                "\"amount\": \"75.01\"," +
                                "\"balance\": \"58.05\"," +
                                "\"currency\": \"USD\"," +
                                "\"rate\": \"1.29220\"" +
                            "}" +
                        "}" +
                    "}" +
                "}";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>(), new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        user.getTotalBalances().then(new PromiseAction<UserBalance>() {
            @Override
            public void call(UserBalance userBalance) {
                bodyRefResponse.set(userBalance);
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
        UserBalance balance = bodyRefResponse.get();

        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me");
        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(balance.getCurrencies().get("CNY").getAmount(), "6.98");
        Assert.assertEquals(balance.getCurrencies().get("CNY").getBalance(), "42.82");
        Assert.assertEquals(balance.getCurrencies().get("CNY").getCurrency(), "USD");
        Assert.assertEquals(balance.getCurrencies().get("CNY").getRate(), "6.13880");
        Assert.assertEquals(balance.getCurrencies().get("EUR").getAmount(), "75.01");
        Assert.assertEquals(balance.getCurrencies().get("EUR").getBalance(), "58.05");
        Assert.assertEquals(balance.getCurrencies().get("EUR").getCurrency(), "USD");
        Assert.assertEquals(balance.getCurrencies().get("EUR").getRate(), "1.29220");
        Assert.assertEquals(balance.getTotal(), "1083.77");
    }

    @Test
    public void getUserTransactionsShouldReturnTheListOfTransactions() throws Exception {
        final AtomicReference<List<Transaction>> bodyRefElements = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>() {{
                    add(new retrofit.client.Header("Range", "items=0-4"));
                    add(new retrofit.client.Header("Content-Range", "0-4/100"));
                }}, new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        Paginator<Transaction> paginator = user.getUserTransactions();
        paginator.getElements().then(new PromiseAction<List<Transaction>>() {
            @Override
            public void call(List<Transaction> transactions) {
                bodyRefElements.set(transactions);
                latch.countDown();
            }
        }).fail(new PromiseAction<Exception>() {
            @Override
            public void call(Exception e) {
                latch.countDown();
            }
        });
        latch.await();

        List<Transaction> transactions = bodyRefElements.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/transactions");
        Assert.assertEquals(transactions.size(), 2);
        Assert.assertEquals(transactions.get(0).getId(), "FOOBAR");
        Assert.assertEquals(transactions.get(1).getId(), "FOOBIZ");
    }

    @Test
    public void getUserTransactionsShouldReturnTheHasNext() throws Exception {
        final AtomicReference<Boolean> bodyRefHasNext = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>() {{
                    add(new retrofit.client.Header("Range", "items=0-4"));
                    add(new retrofit.client.Header("Content-Range", "0-4/100"));
                }}, new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        final Paginator<Transaction> paginator = user.getUserTransactions();
        paginator.getElements().then(new PromiseAction<List<Transaction>>() {
            @Override
            public void call(List<Transaction> transactions) {
                paginator.hasNext().then(new PromiseAction<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        bodyRefHasNext.set(aBoolean);
                        latch.countDown();
                    }
                }).fail(new PromiseAction<Exception>() {
                    @Override
                    public void call(Exception e) {
                        latch.countDown();
                    }
                });
            }
        });
        latch.await();

        Boolean hasNext = bodyRefHasNext.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/transactions");
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-1");
        Assert.assertTrue(hasNext);
    }

    @Test
    public void getUserTransactionsShouldReturnTheCount() throws Exception {
        final AtomicReference<Integer> bodyRefCount = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>() {{
                    add(new retrofit.client.Header("Range", "items=0-4"));
                    add(new retrofit.client.Header("Content-Range", "0-4/100"));
                }}, new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        final Paginator<Transaction> paginator = user.getUserTransactions();
        paginator.getElements().then(new PromiseAction<List<Transaction>>() {
            @Override
            public void call(List<Transaction> transactions) {
                paginator.count().then(new PromiseAction<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        bodyRefCount.set(integer);
                        latch.countDown();
                    }
                }).fail(new PromiseAction<Exception>() {
                    @Override
                    public void call(Exception e) {
                        latch.countDown();
                    }
                });
            }
        });
        latch.await();

        Integer count = bodyRefCount.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/transactions");
        Assert.assertEquals(count, Integer.valueOf(100));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-1");
    }

    @Test
    public void getUserTransactionsShouldReturnTheNextPage() throws Exception {
        final AtomicReference<List<Transaction>> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
                String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>() {{
                    add(new retrofit.client.Header("Range", "items=0-4"));
                    add(new retrofit.client.Header("Content-Range", "0-4/100"));
                }}, new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        final Paginator<Transaction> paginator = user.getUserTransactions();
        paginator.getElements().then(new PromiseAction<List<Transaction>>() {
            @Override
            public void call(List<Transaction> transactions) {
                paginator.getNext().then(new PromiseAction<List<Transaction>>() {
                    @Override
                    public void call(List<Transaction> transactions) {
                        bodyRefResponse.set(transactions);
                        latch.countDown();
                    }
                }).fail(new PromiseAction<Exception>() {
                    @Override
                    public void call(Exception e) {
                        latch.countDown();
                    }
                });
            }
        });
        latch.await();

        List<Transaction> transactions = bodyRefResponse.get();
        Request request = bodyRefRequest.get();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/transactions");
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=50-99");
        Assert.assertEquals(transactions.size(), 2);
        Assert.assertEquals(transactions.get(0).getId(), "FOOBAR");
        Assert.assertEquals(transactions.get(1).getId(), "FOOBIZ");
    }

    @Test
    public void getUserNameShouldReturnUsername() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("username", "foobar");
        }});

        Assert.assertEquals(user.getUsername(), "foobar");
    }

    @Test
    public void updateShouldReturnTheUser() throws Exception {
        final AtomicReference<User> bodyRefResponse = new AtomicReference<>();
        final AtomicReference<Request> bodyRefRequest = new AtomicReference<>();
        final CountDownLatch latch = new CountDownLatch(1);
        User user = Fixtures.loadUser();
        BitreserveRestAdapter mockRestAdapter = new BitreserveRestAdapter("foobar");

        mockRestAdapter.setAdapter(new RestAdapter.Builder().setEndpoint(GlobalConfigurations.SERVER_URL).setClient(new Client() {
            @Override
            public Response execute(Request request) throws IOException {
              String responseString = "{ \"username\": \"FOOBAR\" }";

                bodyRefRequest.set(request);

                return new Response("some/url", 200, "reason", new ArrayList<retrofit.client.Header>(), new TypedByteArray("application/json", responseString.getBytes()));
            }
        }).build());
        user.setBitreserveRestAdapter(mockRestAdapter);
        user.update(new HashMap<String, Object>()).then(new PromiseAction<User>() {
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

        Assert.assertEquals(request.getMethod(), "PATCH");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me");
        Assert.assertEquals(userResponse.getUsername(), "FOOBAR");
    }

}