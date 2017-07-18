package com.uphold.uphold_android_sdk.test.integration.model;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;
import com.uphold.uphold_android_sdk.client.restadapter.UpholdRestAdapter;
import com.uphold.uphold_android_sdk.model.Card;
import com.uphold.uphold_android_sdk.model.Transaction;
import com.uphold.uphold_android_sdk.model.card.Address;
import com.uphold.uphold_android_sdk.model.card.AddressRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionCardDepositRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionDepositRequest;
import com.uphold.uphold_android_sdk.model.transaction.TransactionTransferRequest;
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
 * Card integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CardTest {

    @Test
    public void createAddressShouldReturnTheAddress() throws Exception {
        String responseString = "{ \"id\": \"foobar\", \"network\": \"foobiz\" }";
        MockRestAdapter<Address> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Address>() {
            @Override
            public Promise<Address> call(UpholdRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setUpholdRestAdapter(adapter);

                return card.createAddress(new AddressRequest("foobiz"));
            }
        });

        Request request = adapter.getRequest();
        Address address = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/addresses", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(address.getId(), "foobar");
        Assert.assertEquals(address.getNetwork(), "foobiz");
    }

    @Test
    public void createTransactionShouldReturnTheTransaction() throws Exception {
        String responseString = "{" +
            "\"id\": \"foobar\"," +
            "\"type\": \"transfer\"," +
            "\"message\": \"foobar\"," +
            "\"network\": \"qux\"," +
            "\"status\": \"pending\"," +
            "\"RefundedById\": \"foobiz\"," +
            "\"createdAt\": \"2014-08-27T00:01:11.616Z\"," +
            "\"denomination\": {" +
              "\"amount\": \"0.1\"," +
              "\"currency\": \"BTC\"," +
              "\"pair\": \"BTCBTC\"," +
              "\"rate\": \"1.00\"" +
            "}," +
            "\"origin\": {" +
              "\"AccountId\": \"fiz\"," +
              "\"CardId\": \"bar\"," +
              "\"accountType\": \"biz\"," +
              "\"amount\": \"0.1\"," +
              "\"base\": \"0.1\"," +
              "\"commission\": \"0.00\"," +
              "\"currency\": \"BTC\"," +
              "\"description\": \"Foo Bar\"," +
              "\"fee\": \"0.00\"," +
              "\"merchant\": {" +
                "\"city\": \"foo\"," +
                "\"country\": \"biz\"," +
                "\"name\": \"foobiz\"," +
                "\"state\": \"fuzbiz\"," +
                "\"zipCode\": \"fizbaz\"" +
              "}," +
              "\"rate\": \"1.00\"," +
              "\"type\": \"card\"," +
              "\"username\": \"foobar\"" +
            "}," +
            "\"destination\": {" +
              "\"AccountId\": \"fuz\"," +
              "\"accountType\": \"buz\"," +
              "\"amount\": \"0.1\"," +
              "\"base\": \"0.1\"," +
              "\"commission\": \"0.00\"," +
              "\"currency\": \"BTC\"," +
              "\"description\": \"foo@bar.com\"," +
              "\"fee\": \"0.00\"," +
              "\"merchant\": {" +
                "\"city\": \"foo\"," +
                "\"country\": \"bar\"," +
                "\"name\": \"foobar\"," +
                "\"state\": \"fizbiz\"," +
                "\"zipCode\": \"fizbuz\"" +
              "}," +
              "\"rate\": \"1.00\"," +
              "\"type\": \"email\"" +
            "}," +
            "\"params\": {" +
              "\"currency\": \"BTC\"," +
              "\"margin\": \"0.00\"," +
              "\"pair\": \"BTCBTC\"," +
              "\"rate\": \"1.00\"," +
              "\"refunds\": \"fizbiz\"," +
              "\"ttl\": 30000," +
              "\"type\": \"invite\"" +
            "}," +
            "\"normalized\": [{" +
              "\"amount\": \"123\"," +
              "\"commission\": \"0.00\"," +
              "\"currency\": \"BTC\"," +
              "\"fee\": \"1.00\"," +
              "\"rate\": \"2.00\"" +
            "}]," +
            "\"fees\": [{" +
                "\"type\": \"deposit\"," +
                "\"amount\": \"0.30\"," +
                "\"target\": \"origin\"," +
                "\"currency\": \"USD\"," +
                "\"percentage\": \"2.75\"" +
            "}]" +
        "}";
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setUpholdRestAdapter(adapter);

                return card.createTransaction(new TransactionTransferRequest(null, "foobar"));
            }
        });

        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions?commit=false", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(transaction.getId(), "foobar");
        Assert.assertEquals(transaction.getType(), "transfer");
        Assert.assertEquals(transaction.getMessage(), "foobar");
        Assert.assertEquals(transaction.getNetwork(), "qux");
        Assert.assertEquals(transaction.getStatus(), "pending");
        Assert.assertEquals(transaction.getRefundedById(), "foobiz");
        Assert.assertEquals(transaction.getCreatedAt(), "2014-08-27T00:01:11.616Z");
        Assert.assertEquals(transaction.getDenomination().getAmount(), "0.1");
        Assert.assertEquals(transaction.getDenomination().getCurrency(), "BTC");
        Assert.assertEquals(transaction.getDenomination().getPair(), "BTCBTC");
        Assert.assertEquals(transaction.getDenomination().getRate(), "1.00");
        Assert.assertEquals(transaction.getFees().get(0).getAmount(), "0.30");
        Assert.assertEquals(transaction.getFees().get(0).getCurrency(), "USD");
        Assert.assertEquals(transaction.getFees().get(0).getPercentage(), "2.75");
        Assert.assertEquals(transaction.getFees().get(0).getTarget(), "origin");
        Assert.assertEquals(transaction.getFees().get(0).getType(), "deposit");
        Assert.assertEquals(transaction.getOrigin().getAccountId(), "fiz");
        Assert.assertEquals(transaction.getOrigin().getCardId(), "bar");
        Assert.assertEquals(transaction.getOrigin().getAccountType(), "biz");
        Assert.assertEquals(transaction.getOrigin().getAmount(), "0.1");
        Assert.assertEquals(transaction.getOrigin().getBase(), "0.1");
        Assert.assertEquals(transaction.getOrigin().getCommission(), "0.00");
        Assert.assertEquals(transaction.getOrigin().getCurrency(), "BTC");
        Assert.assertEquals(transaction.getOrigin().getDescription(), "Foo Bar");
        Assert.assertEquals(transaction.getOrigin().getFee(), "0.00");
        Assert.assertEquals(transaction.getOrigin().getMerchant().getCity(), "foo");
        Assert.assertEquals(transaction.getOrigin().getMerchant().getCountry(), "biz");
        Assert.assertEquals(transaction.getOrigin().getMerchant().getName(), "foobiz");
        Assert.assertEquals(transaction.getOrigin().getMerchant().getState(), "fuzbiz");
        Assert.assertEquals(transaction.getOrigin().getMerchant().getZipCode(), "fizbaz");
        Assert.assertEquals(transaction.getOrigin().getRate(), "1.00");
        Assert.assertEquals(transaction.getOrigin().getType(), "card");
        Assert.assertEquals(transaction.getOrigin().getUsername(), "foobar");
        Assert.assertEquals(transaction.getDestination().getAccountId(), "fuz");
        Assert.assertEquals(transaction.getDestination().getAccountType(), "buz");
        Assert.assertEquals(transaction.getDestination().getAmount(), "0.1");
        Assert.assertEquals(transaction.getDestination().getBase(), "0.1");
        Assert.assertEquals(transaction.getDestination().getCommission(), "0.00");
        Assert.assertEquals(transaction.getDestination().getCurrency(), "BTC");
        Assert.assertEquals(transaction.getDestination().getDescription(), "foo@bar.com");
        Assert.assertEquals(transaction.getDestination().getFee(), "0.00");
        Assert.assertEquals(transaction.getDestination().getMerchant().getCity(), "foo");
        Assert.assertEquals(transaction.getDestination().getMerchant().getCountry(), "bar");
        Assert.assertEquals(transaction.getDestination().getMerchant().getName(), "foobar");
        Assert.assertEquals(transaction.getDestination().getMerchant().getState(), "fizbiz");
        Assert.assertEquals(transaction.getDestination().getMerchant().getZipCode(), "fizbuz");
        Assert.assertEquals(transaction.getDestination().getRate(), "1.00");
        Assert.assertEquals(transaction.getDestination().getType(), "email");
        Assert.assertEquals(transaction.getParams().getCurrency(), "BTC");
        Assert.assertEquals(transaction.getParams().getMargin(), "0.00");
        Assert.assertEquals(transaction.getParams().getPair(), "BTCBTC");
        Assert.assertEquals(transaction.getParams().getRate(), "1.00");
        Assert.assertEquals(transaction.getParams().getRefunds(), "fizbiz");
        Assert.assertEquals(transaction.getParams().getTtl(), Integer.valueOf(30000));
        Assert.assertEquals(transaction.getParams().getType(), "invite");
        Assert.assertEquals(transaction.getNormalized().size(), 1);
        Assert.assertEquals(transaction.getNormalized().get(0).getAmount(), "123");
        Assert.assertEquals(transaction.getNormalized().get(0).getCommission(), "0.00");
        Assert.assertEquals(transaction.getNormalized().get(0).getCurrency(), "BTC");
        Assert.assertEquals(transaction.getNormalized().get(0).getFee(), "1.00");
        Assert.assertEquals(transaction.getNormalized().get(0).getRate(), "2.00");
    }

    @Test
    public void createTransactionCardDepositShouldReturnTheTransactionCommitted() throws Exception {
        String responseString = "{ \"id\": \"foobar\" }";
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setUpholdRestAdapter(adapter);

                return card.createTransaction(new TransactionCardDepositRequest(null, "foobar", "12345"), true);
            }
        });

        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions?commit=true", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(transaction.getId(), "foobar");
    }

    @Test
    public void createTransactionDepositShouldReturnTheTransactionCommitted() throws Exception {
        String responseString = "{ \"id\": \"foobar\" }";
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setUpholdRestAdapter(adapter);

                return card.createTransaction(new TransactionDepositRequest(null, "foobar"), true);
            }
        });

        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions?commit=true", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(transaction.getId(), "foobar");
    }

    @Test
    public void createTransactionTransferShouldReturnTheTransactionCommitted() throws Exception {
        String responseString = "{ \"id\": \"foobar\" }";
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(UpholdRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setUpholdRestAdapter(adapter);

                return card.createTransaction(new TransactionTransferRequest(null, "foobar"), true);
            }
        });

        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions?commit=true", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(transaction.getId(), "foobar");
    }

    @Test
    public void getIdShouldReturnId() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("id", "foobar");
        }});

        Assert.assertEquals(card.getId(), "foobar");
    }

    @Test
    public void getAddressShouldReturnAddress() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("addressKeys", "foobar,foobiz");
            put("addressValues", "foo,bar");
        }});

        Assert.assertEquals(card.getAddress().size(), 2);
        Assert.assertTrue(card.getAddress().containsKey("foobar"));
        Assert.assertEquals(card.getAddress().get("foobar"), "foo");
        Assert.assertTrue(card.getAddress().containsKey("foobiz"));
        Assert.assertEquals(card.getAddress().get("foobiz"), "bar");
    }

    @Test
    public void getAvailableShouldReturnAvailable() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("available", "bar");
        }});

        Assert.assertEquals(card.getAvailable(), "bar");
    }

    @Test
    public void getBalanceShouldReturnBalance() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("balance", "foobar");
        }});

        Assert.assertEquals(card.getBalance(), "foobar");
    }

    @Test
    public void getCurrencyShouldReturnCurrency() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("currency", "foo");
        }});

        Assert.assertEquals(card.getCurrency(), "foo");
    }

    @Test
    public void getLableShouldReturnLabel() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("label", "foobiz");
        }});

        Assert.assertEquals(card.getLabel(), "foobiz");
    }

    @Test
    public void getLastTransactionAtShouldReturnLastTransactionAt() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("lastTransactionAt", "foobiz");
        }});

        Assert.assertEquals(card.getLastTransactionAt(), "foobiz");
    }

    @Test
    public void getNormalizedShouldReturnTheNormalizedList() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("normalizedAvailable", "1.00");
            put("normalizedBalance", "2.00");
            put("normalizedCurrencies", "EUR");
        }});

        Assert.assertEquals(card.getNormalized().size(), 1);
        Assert.assertEquals(card.getNormalized().get(0).getAvailable(), "1.00");
        Assert.assertEquals(card.getNormalized().get(0).getBalance(), "2.00");
        Assert.assertEquals(card.getNormalized().get(0).getCurrency(), "EUR");
    }

    @Test
    public void getSettingsShouldReturnSettings() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("settingsPosition", "1");
            put("settingsStarred", "true");
        }});

        Assert.assertEquals(card.getSettings().getPosition(), Integer.valueOf(1));
        Assert.assertTrue(card.getSettings().getStarred());
    }

    @Test
    public void getTransactionsShouldReturnThePaginatorCount() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<Integer> adapter = new MockRestAdapter<>("foobar", responseString, new HashMap<String, String>() {{
            put("Content-Range", "0-2/60");
        }});

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Integer>() {
            @Override
            public Promise<Integer> call(UpholdRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setUpholdRestAdapter(adapter);

                final Paginator<Transaction> paginator = card.getTransactions();

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
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-1");
        Assert.assertEquals(count, Integer.valueOf(60));
    }

    @Test
    public void getTransactionsShouldReturnThePaginatorHasNext() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<Boolean> adapter = new MockRestAdapter<>("foobar", responseString, new HashMap<String, String>() {{
            put("Content-Range", "0-49/51");
        }});

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Boolean>() {
            @Override
            public Promise<Boolean> call(UpholdRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setUpholdRestAdapter(adapter);

                final Paginator<Transaction> paginator = card.getTransactions();

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
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-1");
        Assert.assertTrue(hasNext);
    }

    @Test
    public void getTransactionsShouldReturnTheListOfTransactions() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(UpholdRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setUpholdRestAdapter(adapter);

                final Paginator<Transaction> paginator = card.getTransactions();

                return paginator.getElements().then(new RepromiseFunction<List<Transaction>, List<Transaction>>() {
                    @Override
                    public Promise<List<Transaction>> call(List<Transaction> transactions) {
                        return paginator.getElements();
                    }
                });
            }
        });

        Request request = adapter.getRequest();
        List<Transaction> transactions = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-49");
        Assert.assertEquals(transactions.size(), 2);
        Assert.assertEquals(transactions.get(0).getId(), "FOOBAR");
        Assert.assertEquals(transactions.get(1).getId(), "FOOBIZ");
    }

    @Test
    public void getTransactionsShouldReturnThePaginatorNextPage() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(UpholdRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setUpholdRestAdapter(adapter);

                final Paginator<Transaction> paginator = card.getTransactions();

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
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar/transactions", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=50-99");
    }

    @Test
    public void updateShouldReturnPromiseWithCard() throws Exception {
        String responseString = "{ \"id\": \"FOOBAR\" }";
        MockRestAdapter<Card> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<UpholdRestAdapter, Card>() {
            @Override
            public Promise<Card> call(UpholdRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setUpholdRestAdapter(adapter);

                return card.update(new HashMap<String, Object>());
            }
        });

        Card card = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "PATCH");
        Assert.assertEquals(request.getUrl(), String.format("%s/v0/me/cards/foobar", BuildConfig.API_SERVER_URL));
        Assert.assertEquals(card.getId(), "FOOBAR");
    }

}
