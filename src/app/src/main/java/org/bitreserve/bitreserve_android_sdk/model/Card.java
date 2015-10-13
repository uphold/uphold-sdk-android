package org.bitreserve.bitreserve_android_sdk.model;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.PromiseFunction;

import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPaginatorPromise;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.model.card.Normalized;
import org.bitreserve.bitreserve_android_sdk.model.card.Settings;
import org.bitreserve.bitreserve_android_sdk.model.transaction.TransactionRequest;
import org.bitreserve.bitreserve_android_sdk.paginator.Paginator;
import org.bitreserve.bitreserve_android_sdk.paginator.PaginatorInterface;
import org.bitreserve.bitreserve_android_sdk.service.UserCardService;
import org.bitreserve.bitreserve_android_sdk.util.Header;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Card model.
 */

public class Card extends BaseModel implements Serializable {

    private final String id;
    private final Map<String, String> address;
    private final String available;
    private final String balance;
    private final String currency;
    private final String label;
    private final String lastTransactionAt;
    private final List<Normalized> normalized;
    private final Settings settings;

    /**
     * Constructor.
     *
     * @param id The id of the card.
     * @param address The address of the card.
     * @param available The balance available for withdrawal/usage.
     * @param balance The total balance of the card, including all pending transactions.
     * @param currency The currency of the card.
     * @param label The display name of the card as chosen by the user.
     * @param lastTransactionAt A timestamp of the last time a transaction on this card was conducted.
     * @param normalized The list with the normalized fields.
     * @param settings The {@link Settings} of the card.
     */

    public Card(String id, Map<String, String> address, String available, String balance, String currency, String label, String lastTransactionAt, List<Normalized> normalized, Settings settings) {
        this.id = id;
        this.address = address;
        this.available = available;
        this.balance = balance;
        this.currency = currency;
        this.label = label;
        this.lastTransactionAt = lastTransactionAt;
        this.normalized = normalized;
        this.settings = settings;
    }

    /**
     * Creates a transaction.
     *
     * @param transactionRequest The {@link Transaction} with the transaction request information.
     *
     * @return a {@link Promise<Transaction>} with the transaction.
     */

    public Promise<Transaction> createTransaction(TransactionRequest transactionRequest) {
        return createTransaction(transactionRequest, false);
    }

    /**
     * Creates a transaction.
     *
     * @param commit A boolean to indicate if it is to commit the transaction on the creation process.
     * @param transactionRequest The {@link Transaction} with the transaction request information.
     *
     * @return a {@link Promise<Transaction>} with the transaction.
     */

    public Promise<Transaction> createTransaction(TransactionRequest transactionRequest, Boolean commit) {
        RetrofitPromise<Transaction> promise = new RetrofitPromise<>();
        UserCardService userCardService = this.getBitreserveRestAdapter().create(UserCardService.class);

        userCardService.createTransaction(commit, this.getId(), transactionRequest, promise);

        return promise;
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
     * Gets the normalized fields.
     *
     * @return the normalized fields
     */

    public List<Normalized> getNormalized() {
        return normalized;
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
     * Gets a {@link Paginator} with the transactions for the current card.
     *
     * @return a {@link Paginator<Transaction>} with the list of transactions for the current card.
     */

    public Paginator<Transaction> getTransactions() {
        RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();
        UserCardService userCardService = this.getBitreserveRestAdapter().create(UserCardService.class);

        userCardService.getUserCardTransactions(Header.buildRangeHeader(Paginator.DEFAULT_START, Paginator.DEFAULT_OFFSET - 1), this.getId(), promise);

        PaginatorInterface<Transaction> paginatorInterface = new PaginatorInterface<Transaction>() {
            @Override
            public Promise<Integer> count() {
                RetrofitPaginatorPromise<Transaction> promise = new RetrofitPaginatorPromise<>();
                UserCardService userCardService = Card.this.getBitreserveRestAdapter().create(UserCardService.class);

                userCardService.getUserCardTransactions(Header.buildRangeHeader(0, 1), Card.this.getId(), promise);

                return promise.then(new PromiseFunction<ResponseModel, Integer>() {
                    public Integer call(ResponseModel listPaginatorModel) {
                        return Header.getTotalNumberOfResults(listPaginatorModel.getResponse().getHeaders());
                    }
                });
            }

            @Override
            public Promise<List<Transaction>> getNext(String range) {
                RetrofitPromise<List<Transaction>> promise = new RetrofitPromise<>();
                UserCardService userCardService = Card.this.getBitreserveRestAdapter().create(UserCardService.class);

                userCardService.getUserCardTransactions(range, Card.this.getId(), promise);

                return promise;
            }

            @Override
            public Promise<Boolean> hasNext(final Integer currentPage) {
                RetrofitPaginatorPromise<Transaction> promise = new RetrofitPaginatorPromise<>();
                UserCardService userCardService = Card.this.getBitreserveRestAdapter().create(UserCardService.class);

                userCardService.getUserCardTransactions(Header.buildRangeHeader(0, 1), Card.this.getId(), promise);

                return promise.then(new PromiseFunction<ResponseModel, Boolean>() {
                    public Boolean call(ResponseModel listPaginatorModel) {
                        Integer totalNumberOfResults = Header.getTotalNumberOfResults(listPaginatorModel.getResponse().getHeaders());

                        return (currentPage * Paginator.DEFAULT_OFFSET) < totalNumberOfResults;
                    }
                });
            }
        };

        return new Paginator<>(promise, paginatorInterface);
    }

    /**
     * Update the card information.
     *
     * @param updateRequest The card fields to update.
     *
     * @return a {@link Promise<Card>} with the card updated.
     */

    public Promise<Card> update(HashMap<String, Object> updateRequest) {
        RetrofitPromise<Card> promise = new RetrofitPromise<>();
        UserCardService userCardService = this.getBitreserveRestAdapter().create(UserCardService.class);

        userCardService.update(this.getId(), updateRequest, promise);

        return promise;
    }

}
