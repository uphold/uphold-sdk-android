package com.uphold.uphold_android_sdk.test.integration.model;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;
import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;
import com.uphold.uphold_android_sdk.model.Account;
import com.uphold.uphold_android_sdk.model.Card;
import com.uphold.uphold_android_sdk.model.Transaction;
import com.uphold.uphold_android_sdk.model.User;
import com.uphold.uphold_android_sdk.model.balance.Currency;
import com.uphold.uphold_android_sdk.model.balance.UserBalance;
import com.uphold.uphold_android_sdk.model.card.CardRequest;
import com.uphold.uphold_android_sdk.model.card.Settings;
import com.uphold.uphold_android_sdk.model.user.Contact;
import com.uphold.uphold_android_sdk.model.user.Document;
import com.uphold.uphold_android_sdk.model.user.Phone;
import com.uphold.uphold_android_sdk.paginator.Paginator;
import com.uphold.uphold_android_sdk.test.BuildConfig;
import com.uphold.uphold_android_sdk.test.util.Fixtures;
import com.uphold.uphold_android_sdk.test.util.MockRestAdapter;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

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
            "\"normalized\": [" +
                "{" +
                    "\"available\": \"0.00\"," +
                    "\"balance\": \"0.00\"," +
                    "\"currency\": \"EUR\"" +
                "}" +
            "]," +
            "\"settings\": {" +
                "\"position\": 7," +
                "\"protected\": false," +
                "\"starred\": true" +
            "}" +
        "}";
        MockRestAdapter<Card> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Card>() {
            @Override
            public Promise<Card> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

                return user.createCard(new CardRequest("foo", "BTC", new Settings(7, true)));
            }
        });

        Card cards = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(cards.getAddress().get("bitcoin"), "foobiz");
        Assert.assertEquals(cards.getAvailable(), "0.00");
        Assert.assertEquals(cards.getBalance(), "0.00");
        Assert.assertEquals(cards.getCurrency(), "BAR");
        Assert.assertEquals(cards.getId(), "foobar");
        Assert.assertEquals(cards.getLabel(), "foo");
        Assert.assertEquals(cards.getLastTransactionAt(), "2014-07-07T05:40:46.624Z");
        Assert.assertEquals(cards.getNormalized().size(), 1);
        Assert.assertEquals(cards.getNormalized().get(0).getAvailable(), "0.00");
        Assert.assertEquals(cards.getNormalized().get(0).getBalance(), "0.00");
        Assert.assertEquals(cards.getNormalized().get(0).getCurrency(), "EUR");
        Assert.assertEquals(cards.getSettings().getPosition(), Integer.valueOf(7));
        Assert.assertFalse(cards.getSettings().getProtected());
        Assert.assertTrue(cards.getAddress().containsKey("bitcoin"));
        Assert.assertTrue(cards.getSettings().getStarred());
    }

    @Test
    public void createContactShouldReturnTheContact() throws Exception {
        String responseString = "{\"id\": \"FOOBAR\", \"firstName\": \"Foo\", \"lastName\": \"Bar\", \"company\": \"FOO\", \"emails\": [\"foo@bar.org\"], \"addresses\": [\"foobiz\"], \"name\": \"Foo Bar\"}";
        MockRestAdapter<Contact> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Contact>() {
            @Override
            public Promise<Contact> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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
    public void createDocumentShouldReturnTheDocument() throws Exception {
        String responseString = "{\"type\": \"foo\", \"value\": \"bar\"}";
        MockRestAdapter<Document> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Document>() {
            @Override
            public Promise<Document> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

                return user.createDocument(Fixtures.loadDocumentRequest());
            }
        });

        Document document = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/documents", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(document.getType(), "foo");
        Assert.assertEquals(document.getValue(), "bar");
    }

    @Test
    public void getAccountByIdShouldReturnTheAccountWithId() throws Exception {
        String responseString = "{ \"currency\": \"FOO\", \"id\": \"FOOBAR\", \"label\": \"BAR\", \"status\": \"FOO BAR\", \"type\": \"Bar\" }";
        MockRestAdapter<Account> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Account>() {
            @Override
            public Promise<Account> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

                return user.getAccountById("FOOBAR");
            }
        });

        Account account = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/accounts/FOOBAR", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(account.getCurrency(), "FOO");
        Assert.assertEquals(account.getId(), "FOOBAR");
        Assert.assertEquals(account.getLabel(), "BAR");
        Assert.assertEquals(account.getStatus(), "FOO BAR");
        Assert.assertEquals(account.getType(), "Bar");
    }

    @Test
    public void getAccountsShouldReturnTheListOfAccounts() throws Exception {
        String responseString = "[ { \"id\": \"FOO\" }, { \"id\": \"BAR\" } ]";
        MockRestAdapter<List<Account>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Account>>() {
            @Override
            public Promise<List<Account>> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

                return user.getAccounts();
            }
        });

        List<Account> accounts = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/accounts", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(accounts.size(), 2);
        Assert.assertEquals(accounts.get(0).getId(), "FOO");
        Assert.assertEquals(accounts.get(1).getId(), "BAR");
    }

    @Test
    public void getBalancesShouldReturnTheListOfBalances() throws Exception {
        String responseString = "{ \"balances\": { \"total\": \"1083.77\", \"currencies\": { \"CNY\": { \"amount\": \"6.98\" }, \"EUR\": { \"amount\": \"75.01\" } } } }";
        MockRestAdapter<List<Currency>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Currency>>() {
            @Override
            public Promise<List<Currency>> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Currency>() {
            @Override
            public Promise<Currency> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Card>>() {
            @Override
            public Promise<List<Card>> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Card>() {
            @Override
            public Promise<Card> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Card>>() {
            @Override
            public Promise<List<Card>> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Contact>>() {
            @Override
            public Promise<List<Contact>> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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
    public void getDocumentsShouldReturnTheListOfDocuments() throws Exception {
        String responseString = "[{\"type\": \"foo\", \"value\": \"bar\"}]";
        MockRestAdapter<List<Document>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Document>>() {
            @Override
            public Promise<List<Document>> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

                return user.getDocuments();
            }
        });

        List<Document> documents = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/documents", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(documents.size(), 1);
        Assert.assertEquals(documents.get(0).getType(), "foo");
        Assert.assertEquals(documents.get(0).getValue(), "bar");
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
                "\"e164Masked\": \"+XXXXXXXXX04\"" +
            "}" +
        "]";
        MockRestAdapter<List<Phone>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Phone>>() {
            @Override
            public Promise<List<Phone>> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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
        Assert.assertTrue(phones.get(0).getPrimary());
        Assert.assertTrue(phones.get(0).getVerified());
    }

    @Test
    public void getSettingsShouldReturnTheSettings() {
        HashMap<String, String> data = new HashMap<String, String>() {{
            put("currency", "USD");
            put("hasNewsSubscription", "true");
            put("theme", "minimalistic");
            put("internationalizationUserSettingLanguage", "en-US");
            put("internationalizationUserSettingDateTimeFormat", "en-GB");
            put("internationalizationUserSettingNumberFormat", "en-AU");
            put("settingsOTPLogin", "true");
            put("settingsOTPTransactionsSend", "true");
            put("settingsOTPTransactionsTransfer", "true");
            put("settingsOTPTransactionsWithdrawCrypto", "true");
        }};
        User user = Fixtures.loadUser(data);

        Assert.assertEquals(user.getSettings().getCurrency(), "USD");
        Assert.assertTrue(user.getSettings().getHasNewsSubscription());
        Assert.assertEquals(user.getSettings().getTheme(), "minimalistic");
        Assert.assertEquals(user.getSettings().getIntl().getLanguage().getLocale(), "en-US");
        Assert.assertEquals(user.getSettings().getIntl().getDateTimeFormat().getLocale(), "en-GB");
        Assert.assertEquals(user.getSettings().getIntl().getNumberFormat().getLocale(), "en-AU");
        Assert.assertTrue(user.getSettings().getOtp().getLogin().getEnabled());
        Assert.assertTrue(user.getSettings().getOtp().getTransactions().getSend().getEnabled());
        Assert.assertTrue(user.getSettings().getOtp().getTransactions().getTransfer().getEnabled());
        Assert.assertTrue(user.getSettings().getOtp().getTransactions().getWithdraw().getCrypto().getEnabled());
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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, UserBalance>() {
            @Override
            public Promise<UserBalance> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Integer>() {
            @Override
            public Promise<Integer> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Boolean>() {
            @Override
            public Promise<Boolean> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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
    public void getVerificationsShouldReturnTheVerifications() {
        HashMap<String, String> data = new HashMap<String, String>() {{
            put("verificationsReasonAddress", "reasonAddress");
            put("verificationsReasonBirthdate", "reasonBirthdate");
            put("verificationsReasonDocuments", "reasonDocuments");
            put("verificationsReasonEmail", "reasonEmail");
            put("verificationsReasonIdentity", "reasonIdentity");
            put("verificationsReasonLocation", "reasonLocation");
            put("verificationsReasonPhone", "reasonPhone");
            put("verificationsReasonTerms", "reasonTerms");
            put("verificationsStatusAddress", "statusAddress");
            put("verificationsStatusBirthdate", "statusBirthdate");
            put("verificationsStatusDocuments", "statusDocuments");
            put("verificationsStatusEmail", "statusEmail");
            put("verificationsStatusIdentity", "statusIdentity");
            put("verificationsStatusLocation", "statusLocation");
            put("verificationsStatusPhone", "statusPhone");
            put("verificationsStatusTerms", "statusTerms");
        }};
        User user = Fixtures.loadUser(data);

        Assert.assertEquals(user.getVerifications().getAddress().getReason(), "reasonAddress");
        Assert.assertEquals(user.getVerifications().getAddress().getStatus(), "statusAddress");
        Assert.assertEquals(user.getVerifications().getBirthdate().getReason(), "reasonBirthdate");
        Assert.assertEquals(user.getVerifications().getBirthdate().getStatus(), "statusBirthdate");
        Assert.assertEquals(user.getVerifications().getDocuments().getReason(), "reasonDocuments");
        Assert.assertEquals(user.getVerifications().getDocuments().getStatus(), "statusDocuments");
        Assert.assertEquals(user.getVerifications().getEmail().getReason(), "reasonEmail");
        Assert.assertEquals(user.getVerifications().getEmail().getStatus(), "statusEmail");
        Assert.assertEquals(user.getVerifications().getIdentity().getReason(), "reasonIdentity");
        Assert.assertEquals(user.getVerifications().getIdentity().getStatus(), "statusIdentity");
        Assert.assertEquals(user.getVerifications().getLocation().getReason(), "reasonLocation");
        Assert.assertEquals(user.getVerifications().getLocation().getStatus(), "statusLocation");
        Assert.assertEquals(user.getVerifications().getPhone().getReason(), "reasonPhone");
        Assert.assertEquals(user.getVerifications().getPhone().getStatus(), "statusPhone");
        Assert.assertEquals(user.getVerifications().getTerms().getReason(), "reasonTerms");
        Assert.assertEquals(user.getVerifications().getTerms().getStatus(), "statusTerms");
    }

    @Test
    public void updateShouldReturnTheUser() throws Exception {
        String responseString = "{ \"username\": \"FOOBAR\" }";
        MockRestAdapter<User> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, User>() {
            @Override
            public Promise<User> call(UpholdRestAdapter adapter) {
                User user = Fixtures.loadUser();

                user.setUpholdRestAdapter(adapter);

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
