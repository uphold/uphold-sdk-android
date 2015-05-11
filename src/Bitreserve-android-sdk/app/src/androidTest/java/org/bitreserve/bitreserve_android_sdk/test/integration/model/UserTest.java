package org.bitreserve.bitreserve_android_sdk.test.integration.model;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.model.Card;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.User;
import org.bitreserve.bitreserve_android_sdk.model.balance.Currency;
import org.bitreserve.bitreserve_android_sdk.model.balance.UserBalance;
import org.bitreserve.bitreserve_android_sdk.model.card.CardRequest;
import org.bitreserve.bitreserve_android_sdk.model.user.Contact;
import org.bitreserve.bitreserve_android_sdk.model.user.Phone;
import org.bitreserve.bitreserve_android_sdk.paginator.Paginator;
import org.bitreserve.bitreserve_android_sdk.test.BuildConfig;
import org.bitreserve.bitreserve_android_sdk.test.util.Fixtures;
import org.bitreserve.bitreserve_android_sdk.test.util.MockRestAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.HashMap;
import java.util.List;

import retrofit.client.Request;

/**
 * User integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class UserTest {

    @Test
    public void createCardShouldReturnTheCard() throws Exception {
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
            "\"settings\": {" +
                "\"position\": 7," +
                "\"starred\": true" +
            "}" +
        "}";
        MockRestAdapter<Card> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Card>() {
            @Override
            public Promise<Card> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.createCard(new CardRequest("foo", "BTC"));
            }
        });

        Card cards = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards", BuildConfig.API_SERVER_URL));
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
        Assert.assertEquals(cards.getSettings().getPosition(), Integer.valueOf(7));
        Assert.assertTrue(cards.getAddress().containsKey("bitcoin"));
        Assert.assertTrue(cards.getSettings().getStarred());
    }

    @Test
    public void createContactShouldReturnTheContact() throws Exception {
        String responseString = "{\"id\": \"FOOBAR\", \"firstName\": \"Foo\", \"lastName\": \"Bar\", \"company\": \"FOO\", \"emails\": [\"foo@bar.org\"], \"addresses\": [\"foobiz\"], \"name\": \"Foo Bar\"}";
        MockRestAdapter<Contact> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Contact>() {
            @Override
            public Promise<Contact> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.createContact(Fixtures.loadContactRequest());
            }
        });

        Contact contact = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/contacts", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(contact.getAddresses().size(), 1);
        Assert.assertEquals(contact.getAddresses().get(0), "foobiz");
        Assert.assertEquals(contact.getCompany(), "FOO");
        Assert.assertEquals(contact.getEmails().size(), 1);
        Assert.assertEquals(contact.getEmails().get(0), "foo@bar.org");
        Assert.assertEquals(contact.getFirstName(), "Foo");
        Assert.assertEquals(contact.getId(), "FOOBAR");
        Assert.assertEquals(contact.getLastName(), "Bar");
        Assert.assertEquals(contact.getName(), "Foo Bar");
    }

    @Test
    public void getBalancesShouldReturnTheListOfBalances() throws Exception {
        String responseString = "{ \"balances\": { \"total\": \"1083.77\", \"currencies\": { \"CNY\": { \"amount\": \"6.98\" }, \"EUR\": { \"amount\": \"75.01\" } } } }";
        MockRestAdapter<List<Currency>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Currency>>() {
            @Override
            public Promise<List<Currency>> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getBalances();
            }
        });

        List<Currency> currencies = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(currencies.size(), 2);
        Assert.assertEquals(currencies.get(0).getAmount(), "6.98");
        Assert.assertEquals(currencies.get(1).getAmount(), "75.01");
    }

    @Test
    public void getBalanceByCurrencyShouldReturnTheCurrency() throws Exception {
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
        MockRestAdapter<Currency> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Currency>() {
            @Override
            public Promise<Currency> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getBalanceByCurrency("EUR");
            }
        });

        Currency currency = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(currency.getAmount(), "75.01");
        Assert.assertEquals(currency.getBalance(), "58.05");
        Assert.assertEquals(currency.getCurrency(), "USD");
        Assert.assertEquals(currency.getRate(), "1.29220");
    }

    @Test
    public void getCardsShouldReturnTheListOfCards() throws Exception {
        String responseString = "[ { \"id\": \"FOO\" }, { \"id\": \"BAR\" } ]";
        MockRestAdapter<List<Card>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Card>>() {
            @Override
            public Promise<List<Card>> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getCards();
            }
        });

        List<Card> cards = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(cards.size(), 2);
        Assert.assertEquals(cards.get(0).getId(), "FOO");
        Assert.assertEquals(cards.get(1).getId(), "BAR");
    }

    @Test
    public void getCardByIdShouldReturnTheCardWithId() throws Exception {
        String responseString = "{ \"id\": \"FOOBAR\" }";
        MockRestAdapter<Card> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Card>() {
            @Override
            public Promise<Card> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getCardById("FOOBAR");
            }
        });

        Card card = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/FOOBAR", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(card.getId(), "FOOBAR");
    }

    @Test
    public void getCardsByCurrencyShouldReturnTheListOfCardsWithCurrency() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\", \"currency\": \"USD\" }, { \"id\": \"FOOBIZ\", \"currency\": \"BTC\" } ]";
        MockRestAdapter<List<Card>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Card>>() {
            @Override
            public Promise<List<Card>> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getCardsByCurrency("BTC");
            }
        });

        List<Card> cards = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(cards.size(), 1);
        Assert.assertEquals(cards.get(0).getId(), "FOOBIZ");
    }

    @Test
    public void getContactsShouldReturnTheListOfContacts() throws Exception {
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
        MockRestAdapter<List<Contact>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Contact>>() {
            @Override
            public Promise<List<Contact>> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getContacts();
            }
        });

        List<Contact> contacts = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/contacts", BuildConfig.API_SERVER_URL));
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
    public void getCountryShouldReturnTheCountry() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("country", "foobar");
        }});

        Assert.assertEquals(user.getCountry(), "foobar");
    }

    @Test
    public void getCurrenciesShouldReturnTheListOfCurrencies() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("currencies", "BTC,USD,EUR");
        }});

        Assert.assertEquals(user.getCurrencies().size(), 3);
        Assert.assertEquals(user.getCurrencies().get(0), "BTC");
        Assert.assertEquals(user.getCurrencies().get(1), "USD");
        Assert.assertEquals(user.getCurrencies().get(2), "EUR");
    }

    @Test
    public void getEmailShouldReturnTheEmail() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("email", "foobar");
        }});

        Assert.assertEquals(user.getEmail(), "foobar");
    }

    @Test
    public void getFirstNameShouldReturnTheFirstName() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("firstName", "foobar");
        }});

        Assert.assertEquals(user.getFirstName(), "foobar");
    }

    @Test
    public void getLastNameShouldReturnTheLastName() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("lastName", "foobar");
        }});

        Assert.assertEquals(user.getLastName(), "foobar");
    }

    @Test
    public void getNameShouldReturnTheName() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("name", "foobar");
        }});

        Assert.assertEquals(user.getName(), "foobar");
    }

    @Test
    public void getPhonesShouldReturnTheListOfPhones() throws Exception {
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
        MockRestAdapter<List<Phone>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Phone>>() {
            @Override
            public Promise<List<Phone>> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getPhones();
            }
        });

        List<Phone> phones = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/phones", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(phones.size(), 1);
        Assert.assertEquals(phones.get(0).getE164Masked(), "+XXXXXXXXX04");
        Assert.assertEquals(phones.get(0).getId(), "foobar");
        Assert.assertEquals(phones.get(0).getInternationalMasked(), "+X XXX-XXX-XX04");
        Assert.assertEquals(phones.get(0).getNationalMasked(), "(XXX) XXX-XX04");
        Assert.assertTrue(phones.get(0).getPrimary());
        Assert.assertTrue(phones.get(0).getVerified());
    }

    @Test
    public void getSettingsShouldReturnTheSettings() {
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
    public void getStateShouldReturnTheState() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("state", "foobar");
        }});

        Assert.assertEquals(user.getState(), "foobar");
    }

    @Test
    public void getStatusShouldReturnTheStatus() {
        HashMap<String, String> data = new HashMap<String, String>() {{
            put("status", "ok");
        }};
        User user = Fixtures.loadUser(data);

        Assert.assertEquals(user.getStatus(), "ok");
    }

    @Test
    public void getTotalBalancesShouldReturnTheTotalBalance() throws Exception {
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
        MockRestAdapter<UserBalance> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, UserBalance>() {
            @Override
            public Promise<UserBalance> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.getTotalBalances();
            }
        });

        Request request = adapter.getRequest();
        UserBalance balance = adapter.getResult();

        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me", BuildConfig.API_SERVER_URL));
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
    public void getUserTransactionsShouldReturnThePaginatorCount() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<Integer> adapter = new MockRestAdapter<>("foobar", responseString, new HashMap<String, String>() {{
            put("Content-Range", "0-2/60");
        }});

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Integer>() {
            @Override
            public Promise<Integer> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                final Paginator<Transaction> paginator = user.getUserTransactions();

                return paginator.getElements().then(new RepromiseFunction<List<Transaction>, Integer>() {
                    @Override
                    public Promise<Integer> call(List<Transaction> transactions) {
                        return paginator.count();
                    }
                });
            }
        });

        Integer count = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-1");
        Assert.assertEquals(count, Integer.valueOf(60));
    }

    @Test
    public void getUserTransactionsShouldReturnThePaginatorHasNext() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<Boolean> adapter = new MockRestAdapter<>("foobar", responseString, new HashMap<String, String>() {{
            put("Content-Range", "0-49/51");
        }});

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Boolean>() {
            @Override
            public Promise<Boolean> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                final Paginator<Transaction> paginator = user.getUserTransactions();

                return paginator.getElements().then(new RepromiseFunction<List<Transaction>, Boolean>() {
                    @Override
                    public Promise<Boolean> call(List<Transaction> transactions) {
                        return paginator.hasNext();
                    }
                });
            }
        });

        Boolean hasNext = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-1");
        Assert.assertTrue(hasNext);
    }

    @Test
    public void getUserTransactionsShouldReturnTheListOfTransactions() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                final Paginator<Transaction> paginator = user.getUserTransactions();

                return paginator.getElements().then(new RepromiseFunction<List<Transaction>, List<Transaction>>() {
                    @Override
                    public Promise<List<Transaction>> call(List<Transaction> transactions) {
                        return paginator.getElements();
                    }
                });
            }
        });

        List<Transaction> transactions = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-49");
        Assert.assertEquals(transactions.size(), 2);
        Assert.assertEquals(transactions.get(0).getId(), "FOOBAR");
        Assert.assertEquals(transactions.get(1).getId(), "FOOBIZ");
    }

    @Test
    public void getUserTransactionsShouldReturnThePaginatorNextPage() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                final Paginator<Transaction> paginator = user.getUserTransactions();

                return paginator.getElements().then(new RepromiseFunction<List<Transaction>, List<Transaction>>() {
                    @Override
                    public Promise<List<Transaction>> call(List<Transaction> transactions) {
                        return paginator.getNext();
                    }
                });
            }
        });

        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=50-99");
    }

    @Test
    public void getUsernameShouldReturnTheUsername() {
        User user = Fixtures.loadUser(new HashMap<String, String>() {{
            put("username", "foobar");
        }});

        Assert.assertEquals(user.getUsername(), "foobar");
    }

    @Test
    public void updateShouldReturnTheUser() throws Exception {
        String responseString = "{ \"username\": \"FOOBAR\" }";
        MockRestAdapter<User> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, User>() {
            @Override
            public Promise<User> call(BitreserveRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setBitreserveRestAdapter(adapter);

                return user.update(new HashMap<String, Object>());
            }
        });

        Request request = adapter.getRequest();
        User userResponse = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "PATCH");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(userResponse.getUsername(), "FOOBAR");
    }

}
