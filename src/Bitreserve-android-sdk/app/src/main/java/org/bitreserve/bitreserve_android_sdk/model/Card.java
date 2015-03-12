package org.bitreserve.bitreserve_android_sdk.model;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.PromiseFunction;

import org.bitreserve.bitreserve_android_sdk.client.promisewrapper.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.model.card.Address;
import org.bitreserve.bitreserve_android_sdk.model.card.Settings;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionRequest;
import org.bitreserve.bitreserve_android_sdk.service.UserCardService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Card model.
 */

public class Card extends BaseModel {

    private final String id;
    private final Map<String, String> address;
    private final List<Address> addresses;
    private final String available;
    private final String balance;
    private final String currency;
    private final String label;
    private final String lastTransactionAt;
    private final Settings settings;

    /**
     * Constructor.
     *
     * @param id The id of the card.
     * @param address The address of the card.
     * @param addresses The list of address for the card
     * @param available The balance available for withdrawal/usage.
     * @param balance The total balance of the card, including all pending transactions.
     * @param currency The currency of the card.
     * @param label The display name of the card as chosen by the user.
     * @param lastTransactionAt A timestamp of the last time a transaction on this card was conducted.
     * @param settings The {@link Settings} of the card.
     */

    public Card(String id, Map<String, String> address, List<Address> addresses, String available, String balance, String currency, String label, String lastTransactionAt, Settings settings) {
        this.id = id;
        this.address = address;
        this.addresses = addresses;
        this.available = available;
        this.balance = balance;
        this.currency = currency;
        this.label = label;
        this.lastTransactionAt = lastTransactionAt;
        this.settings = settings;
    }

    /**
     * Creates a transaction.
     *
     * @param transactionRequest The {@link Transaction} with the transaction request information.
     * @return a {@link Promise<Transaction>} with the transaction.
     */

    public Promise<Transaction> createTransaction(TransactionRequest transactionRequest) {
        RetrofitPromise<Transaction> retrofitPromise = new RetrofitPromise<>();
        UserCardService userCardService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(UserCardService.class);

        userCardService.createTransaction(this.getId(), transactionRequest, retrofitPromise);

        return retrofitPromise.then(new PromiseFunction<Transaction, Transaction>() {
            public Transaction call(Transaction transaction) {
                transaction.setToken(Card.this.getToken());

                return transaction;
            }
        });
    }

    /**
     * Gets the card id.
     *
     * @return the card id
     */

    public String getId() {
        return id;
    }

    /**
     * Gets the address of the card
     *
     * @return the address of the card
     */

    public Map<String, String> getAddress() {
        return address;
    }

    /**
     * Gets the list of addresses for the card.
     *
     * @return the list of {@link Address} for the card
     */

    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * Gets the balance available in the card for withdrawal/usage.
     *
     * @return the balance available in the card for withdrawal/usage
     */

    public String getAvailable() {
        return available;
    }

    /**
     * Gets the total balance of the card, including all pending transactions.
     *
     * @return the total balance of the card, including all pending transactions
     */

    public String getBalance() {
        return balance;
    }

    /**
     * Gets the currency of the card.
     *
     * @return the currency of the card
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the label of the card.
     *
     * @return the label of the card
     */

    public String getLabel() {
        return label;
    }

    /**
     * Gets the timestamp of the last time a transaction on this card was conducted.
     *
     * @return timestamp of the last time a transaction on this card was conducted
     */

    public String getLastTransactionAt() {
        return lastTransactionAt;
    }

    /**
     * Gets the card settings.
     *
     * @return the {@link Settings} of the card
     */

    public Settings getSettings() {
        return settings;
    }

    /**
     * Gets the list of transactions for the current card.
     *
     * @return a {@link Promise<List<Transaction>>} with the list of transactions for the current card.
     */

    public Promise<List<Transaction>> getTransactions() {
        RetrofitPromise<List<Transaction>> retrofitPromise = new RetrofitPromise<> ();
        UserCardService userCardService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(UserCardService.class);

        userCardService.getUserCardTransactions(this.getId(), retrofitPromise);

        return retrofitPromise.then(new PromiseFunction<List<Transaction>, List<Transaction>> () {
            public List<Transaction> call(List<Transaction> transactions) {
                for (Transaction transaction : transactions) {
                    transaction.setToken(Card.this.getToken());
                }

                return transactions;
            }
        });
    }

    /**
     * Update the card information.
     *
     * @param updateRequest The card fields to update.
     *
     * @return a {@link Promise<Card>} with the card updated.
     */

    public Promise<Card> update(HashMap<String, Object> updateRequest) {
        RetrofitPromise<Card> retrofitPromise = new RetrofitPromise<> ();
        UserCardService userCardService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(UserCardService.class);

        userCardService.update(this.getId(), updateRequest, retrofitPromise);

        return retrofitPromise.then(new PromiseFunction<Card, Card> () {
            public Card call(Card card) {
                card.setToken(Card.this.getToken());

                return card;
            }
        });
    }

}
