package org.bitreserve.bitreserve_android_sdk.test.integration.model;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.RepromiseFunction;

import junit.framework.Assert;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.model.Card;
import org.bitreserve.bitreserve_android_sdk.model.Transaction;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionRequest;
import org.bitreserve.bitreserve_android_sdk.paginator.Paginator;
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
 * Card integration tests.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CardTest {

    @Test
    public void createTransactionShouldReturnTheTransaction() throws Exception {
        String responseString = "{" +
            "\"id\": \"foobar\"," +
            "\"type\": \"transfer\"," +
            "\"message\": \"foobar\"," +
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
              "\"CardId\": \"bar\"," +
              "\"amount\": \"0.1\"," +
              "\"base\": \"0.1\"," +
              "\"commission\": \"0.00\"," +
              "\"currency\": \"BTC\"," +
              "\"description\": \"Foo Bar\"," +
              "\"fee\": \"0.00\"," +
              "\"rate\": \"1.00\"," +
              "\"type\": \"card\"," +
              "\"username\": \"foobar\"" +
            "}," +
            "\"destination\": {" +
              "\"amount\": \"0.1\"," +
              "\"base\": \"0.1\"," +
              "\"commission\": \"0.00\"," +
              "\"currency\": \"BTC\"," +
              "\"description\": \"foo@bar.com\"," +
              "\"fee\": \"0.00\"," +
              "\"rate\": \"1.00\"," +
              "\"type\": \"email\"" +
            "}," +
            "\"params\": {" +
              "\"currency\": \"BTC\"," +
              "\"margin\": \"0.00\"," +
              "\"pair\": \"BTCBTC\"," +
              "\"rate\": \"1.00\"," +
              "\"ttl\": 30000," +
              "\"type\": \"invite\"" +
            "}" +
        "}";
        MockRestAdapter<Transaction> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Transaction>() {
            @Override
            public Promise<Transaction> call(BitreserveRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setBitreserveRestAdapter(adapter);

                return card.createTransaction(new TransactionRequest(null, "foobar"));
            }
        });

        Request request = adapter.getRequest();
        Transaction transaction = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "POST");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards/foobar/transactions");
        Assert.assertEquals(transaction.getId(), "foobar");
        Assert.assertEquals(transaction.getType(), "transfer");
        Assert.assertEquals(transaction.getMessage(), "foobar");
        Assert.assertEquals(transaction.getStatus(), "pending");
        Assert.assertEquals(transaction.getRefundedById(), "foobiz");
        Assert.assertEquals(transaction.getCreatedAt(), "2014-08-27T00:01:11.616Z");
        Assert.assertEquals(transaction.getDenomination().getAmount(), "0.1");
        Assert.assertEquals(transaction.getDenomination().getCurrency(), "BTC");
        Assert.assertEquals(transaction.getDenomination().getPair(), "BTCBTC");
        Assert.assertEquals(transaction.getDenomination().getRate(), "1.00");
        Assert.assertEquals(transaction.getOrigin().getCardId(), "bar");
        Assert.assertEquals(transaction.getOrigin().getAmount(), "0.1");
        Assert.assertEquals(transaction.getOrigin().getBase(), "0.1");
        Assert.assertEquals(transaction.getOrigin().getCommission(), "0.00");
        Assert.assertEquals(transaction.getOrigin().getCurrency(), "BTC");
        Assert.assertEquals(transaction.getOrigin().getDescription(), "Foo Bar");
        Assert.assertEquals(transaction.getOrigin().getFee(), "0.00");
        Assert.assertEquals(transaction.getOrigin().getRate(), "1.00");
        Assert.assertEquals(transaction.getOrigin().getType(), "card");
        Assert.assertEquals(transaction.getOrigin().getUsername(), "foobar");
        Assert.assertEquals(transaction.getDestination().getAmount(), "0.1");
        Assert.assertEquals(transaction.getDestination().getBase(), "0.1");
        Assert.assertEquals(transaction.getDestination().getCommission(), "0.00");
        Assert.assertEquals(transaction.getDestination().getCurrency(), "BTC");
        Assert.assertEquals(transaction.getDestination().getDescription(), "foo@bar.com");
        Assert.assertEquals(transaction.getDestination().getFee(), "0.00");
        Assert.assertEquals(transaction.getDestination().getRate(), "1.00");
        Assert.assertEquals(transaction.getDestination().getType(), "email");
        Assert.assertEquals(transaction.getParams().getCurrency(), "BTC");
        Assert.assertEquals(transaction.getParams().getMargin(), "0.00");
        Assert.assertEquals(transaction.getParams().getPair(), "BTCBTC");
        Assert.assertEquals(transaction.getParams().getRate(), "1.00");
        Assert.assertEquals(transaction.getParams().getTtl(), Integer.valueOf(30000));
        Assert.assertEquals(transaction.getParams().getType(), "invite");
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
    public void getAddressesShouldReturnAddresses() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("addressesKeys", "foobar,foobiz");
            put("addressesValues", "foo,bar");
        }});

        Assert.assertEquals(card.getAddresses().size(), 2);
        Assert.assertEquals(card.getAddresses().get(0).getId(), "foobar");
        Assert.assertEquals(card.getAddresses().get(0).getNetwork(), "foo");
        Assert.assertEquals(card.getAddresses().get(1).getId(), "foobiz");
        Assert.assertEquals(card.getAddresses().get(1).getNetwork(), "bar");
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
    public void getSettingsShouldReturnSettings() {
        Card card = Fixtures.loadCard(new HashMap<String, String>() {{
            put("settingsPosition", "1");
            put("settingsStarred", "true");
        }});

        Assert.assertEquals(card.getSettings().getPosition(), Integer.valueOf(1));
        Assert.assertTrue(card.getSettings().getStarred());
    }

    @Test
    public void getTransactionsShouldReturnTheListOfTransactions() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(BitreserveRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setBitreserveRestAdapter(adapter);

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
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards/foobar/transactions");
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=0-49");
        Assert.assertEquals(transactions.size(), 2);
        Assert.assertEquals(transactions.get(0).getId(), "FOOBAR");
        Assert.assertEquals(transactions.get(1).getId(), "FOOBIZ");
    }

    @Test
    public void getTransactionsShouldReturnTheNextPage() throws Exception {
        String responseString = "[ { \"id\": \"FOOBAR\" }, { \"id\": \"FOOBIZ\" } ]";
        MockRestAdapter<List<Transaction>> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, List<Transaction>>() {
            @Override
            public Promise<List<Transaction>> call(BitreserveRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setBitreserveRestAdapter(adapter);

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
        List<Transaction> transactions = adapter.getResult();

        Assert.assertEquals(request.getMethod(), "GET");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards/foobar/transactions");
        Assert.assertEquals(request.getHeaders().get(0).getName(), "Range");
        Assert.assertEquals(request.getHeaders().get(0).getValue(), "items=50-99");
        Assert.assertEquals(transactions.size(), 2);
        Assert.assertEquals(transactions.get(0).getId(), "FOOBAR");
        Assert.assertEquals(transactions.get(1).getId(), "FOOBIZ");
    }

    @Test
    public void updateShouldReturnPromiseWithCard() throws Exception {
        String responseString = "{ \"id\": \"FOOBAR\" }";

        MockRestAdapter<Card> adapter = new MockRestAdapter<>("foobar", responseString, null);

        adapter.request(new RepromiseFunction<BitreserveRestAdapter, Card>() {
            @Override
            public Promise<Card> call(BitreserveRestAdapter adapter) {
                Card card = Fixtures.loadCard(new HashMap<String, String>() {{
                    put("id", "foobar");
                }});

                card.setBitreserveRestAdapter(adapter);

                return card.update(new HashMap<String, Object>());
            }
        });

        Card card = adapter.getResult();
        Request request = adapter.getRequest();

        Assert.assertEquals(request.getMethod(), "PATCH");
        Assert.assertEquals(request.getUrl(), "https://api.bitreserve.org/v0/me/cards/foobar");
        Assert.assertEquals(card.getId(), "FOOBAR");
    }

}
