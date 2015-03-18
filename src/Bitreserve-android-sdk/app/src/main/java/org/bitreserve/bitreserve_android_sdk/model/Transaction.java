package org.bitreserve.bitreserve_android_sdk.model;

import com.darylteo.rx.promises.java.Promise;
import com.darylteo.rx.promises.java.functions.PromiseFunction;

import org.bitreserve.bitreserve_android_sdk.client.restadapter.BitreserveRestAdapter;
import org.bitreserve.bitreserve_android_sdk.client.retrofitpromise.RetrofitPromise;
import org.bitreserve.bitreserve_android_sdk.exception.LogicException;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Denomination;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Destination;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Origin;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Parameters;
import org.bitreserve.bitreserve_android_sdk.service.UserCardService;

import android.text.TextUtils;

/**
 * Transaction model.
 */

public class Transaction extends BaseModel {

    private final String id;
    private final String createdAt;
    private final Denomination denomination;
    private final Destination destination;
    private final String message;
    private final Origin origin;
    private final Parameters params;
    private final String refundedById;
    private final String status;
    private final String type;

    /**
     * Constructor.
     *
     * @param id A unique id the transaction.
     * @param createdAt The date and time the transaction was initiated.
     * @param denomination The funds to be transfered.
     * @param destination The recipient of the funds.
     * @param message A message or note provided by the user at the time the transaction was initiated, with the intent of communicating additional information and context about the nature/purpose of the transaction.
     * @param origin The sender of the funds.
     * @param params Other parameters of this transaction.
     * @param refundedById When a transaction is cancelled, specifically a transaction in which money is sent to an email address, this contains the transaction ID of the transaction which refunds the amount back to the user.
     * @param status The current status of the transaction.
     * @param type The nature of the transaction.
     */

    public Transaction(String id, String createdAt, Denomination denomination, Destination destination, String message, Origin origin, Parameters params, String refundedById, String status, String type) {
        this.id = id;
        this.createdAt = createdAt;
        this.denomination = denomination;
        this.destination = destination;
        this.message = message;
        this.origin = origin;
        this.params = params;
        this.refundedById = refundedById;
        this.status = status;
        this.type = type;
    }

    /**
     * Cancel a transaction.
     *
     * @return a {@link Promise<Transaction>} with the transaction.
     */

    public Promise<Transaction> cancel() {
        RetrofitPromise<Transaction> promise = new RetrofitPromise<>();
        UserCardService userCardService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(UserCardService.class);

        if (TextUtils.isEmpty(this.getOrigin().getCardId())) {
            promise.reject(new LogicException("Origin CardId is missing from this transaction"));

            return promise;
        } else if (this.getStatus().compareTo("pending") == 0) {
            promise.reject(new LogicException("Unable to cancel uncommited transaction"));

            return promise;
        } else if (this.getStatus().compareTo("waiting") != 0) {
            promise.reject(new LogicException(String.format("This transaction cannot be cancelled, because the current status is %s", this.getStatus())));

            return promise;
        } else {
            userCardService.cancelTransaction(this.getOrigin().getCardId(), this.getId(), promise);

            return promise.then(new PromiseFunction<Transaction, Transaction>() {
                public Transaction call(Transaction transaction) {
                    transaction.setToken(Transaction.this.getToken());

                    return transaction;
                }
            });
        }
    }

    /**
     * Confirm a transaction.
     *
     * @return a {@link Promise<Transaction>} with the transaction.
     */

    public Promise<Transaction> commit() {
        RetrofitPromise<Transaction> promise = new RetrofitPromise<>();
        UserCardService userCardService = BitreserveRestAdapter.getRestAdapter(this.getToken()).create(UserCardService.class);

        if (TextUtils.isEmpty(this.getOrigin().getCardId())) {
            promise.reject(new LogicException("Origin CardId is missing from this transaction"));

            return promise;
        } else if (this.getStatus().compareTo("pending") != 0) {
            promise.reject(new LogicException(String.format("This transaction cannot be committed, because the current status is %s", this.getStatus())));

            return promise;
        } else {
            userCardService.confirmTransaction(this.getOrigin().getCardId(), this.getId(), promise);

            return promise.then(new PromiseFunction<Transaction, Transaction>() {
                public Transaction call(Transaction transaction) {
                    transaction.setToken(Transaction.this.getToken());

                    return transaction;
                }
            });
        }
    }

    /**
     * Gets the id of the transaction.
     *
     * @return the id of the transaction
     */

    public String getId() {
        return id;
    }

    /**
     * Gets the date and time the transaction was initiated.
     *
     * @return the date and time the transaction was initiated
     */

    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the funds to be transfered.
     *
     * @return the {@link Denomination} funds to be transfered
     */

    public Denomination getDenomination() {
        return denomination;
    }

    /**
     * Gets the recipient of the funds.
     *
     * @return the {@link Destination} of the funds
     */

    public Destination getDestination() {
        return destination;
    }

    /**
     * Gets the message or note provided by the user at the time the transaction was initiated, with the intent of communicating additional information and context about the nature/purpose of the transaction.
     *
     * @return the message or note provided by the user at the time the transaction was initiated, with the intent of communicating additional information and context about the nature/purpose of the transaction
     */

    public String getMessage() {
        return message;
    }

    /**
     * Gets the sender of the funds.
     *
     * @return the {@link Origin} of the funds
     */

    public Origin getOrigin() {
        return origin;
    }

    /**
     * Gets the other parameters of this transaction.
     *
     * @return the {@link Parameters} of this transaction
     */

    public Parameters getParams() {
        return params;
    }

    /**
     * Gets the refund by of the transaction.
     *
     * @return the refund by of the transaction
     */

    public String getRefundedById() {
        return refundedById;
    }

    /**
     * Gets the status of the transaction.
     *
     * @return the status of the transaction
     */

    public String getStatus() {
        return status;
    }

    /**
     * Gets the type of the transaction.
     *
     * @return the type of the transaction
     */

    public String getType() {
        return type;
    }

}
