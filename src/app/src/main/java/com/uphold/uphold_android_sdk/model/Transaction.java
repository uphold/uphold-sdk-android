package com.uphold.uphold_android_sdk.model;

import android.text.TextUtils;

import com.darylteo.rx.promises.java.Promise;
import com.uphold.uphold_android_sdk.client.body.EmptyOutput;
import com.uphold.uphold_android_sdk.client.retrofitpromise.RetrofitPromise;
import com.uphold.uphold_android_sdk.exception.LogicException;
import com.uphold.uphold_android_sdk.model.transaction.Denomination;
import com.uphold.uphold_android_sdk.model.transaction.Destination;
import com.uphold.uphold_android_sdk.model.transaction.Fee;
import com.uphold.uphold_android_sdk.model.transaction.Normalized;
import com.uphold.uphold_android_sdk.model.transaction.Origin;
import com.uphold.uphold_android_sdk.model.transaction.Parameters;
import com.uphold.uphold_android_sdk.model.transaction.TransactionCommitRequest;
import com.uphold.uphold_android_sdk.service.UserCardService;

import java.io.Serializable;
import java.util.List;

/**
 * Transaction model.
 */

public class Transaction extends BaseModel implements Serializable {

    private final String id;
    private final String createdAt;
    private final Denomination denomination;
    private final Destination destination;
    private final List<Fee> fees;
    private final String message;
    private final List<Normalized> normalized;
    private final Origin origin;
    private final Parameters params;
    private final String RefundedById;
    private final String status;
    private final String type;

    /**
     * Constructor.
     *
     * @param id A unique id the transaction.
     * @param createdAt The date and time the transaction was initiated.
     * @param denomination The funds to be transfered.
     * @param destination The recipient of the funds.
     * @param fees The transaction fees.
     * @param message A message or note provided by the user at the time the transaction was initiated, with the intent of communicating additional information and context about the nature/purpose of the transaction.
     * @param normalized The transaction details normalized.
     * @param origin The sender of the funds.
     * @param params Other parameters of this transaction.
     * @param refundedById When a transaction is cancelled, specifically a transaction in which money is sent to an email address, this contains the transaction ID of the transaction which refunds the amount back to the user.
     * @param status The current status of the transaction.
     * @param type The nature of the transaction.
     */

    public Transaction(String id, String createdAt, Denomination denomination, Destination destination, List<Fee> fees, String message, List<Normalized> normalized, Origin origin, Parameters params, String refundedById, String status, String type) {
        this.id = id;
        this.createdAt = createdAt;
        this.denomination = denomination;
        this.destination = destination;
        this.fees = fees;
        this.message = message;
        this.normalized = normalized;
        this.origin = origin;
        this.params = params;
        this.RefundedById = refundedById;
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
        UserCardService userCardService = this.getUpholdRestAdapter().create(UserCardService.class);

        if (TextUtils.isEmpty(this.getOrigin().getCardId())) {
            promise.reject(new LogicException("Origin CardId is missing from this transaction"));

            return promise;
        }

        if (this.getStatus().compareTo("pending") == 0) {
            promise.reject(new LogicException("Unable to cancel uncommited transaction"));

            return promise;
        }

        if (this.getStatus().compareTo("waiting") != 0) {
            promise.reject(new LogicException(String.format("This transaction cannot be cancelled, because the current status is %s", this.getStatus())));

            return promise;
        }

        userCardService.cancelTransaction(this.getOrigin().getCardId(), this.getId(), EmptyOutput.INSTANCE, promise);

        return promise;
    }

    /**
     * Confirm a transaction.
     *
     * @return a {@link Promise<Transaction>} with the transaction.
     */

    public Promise<Transaction> commit() {
        return commit(null);
    }

    /**
     * Commit a transaction.
     *
     * @param transactionCommitRequest an optional transaction message.
     *
     * @return a {@link Promise<Transaction>} with the transaction.
     */

    public Promise<Transaction> commit(TransactionCommitRequest transactionCommitRequest) {
        RetrofitPromise<Transaction> promise = new RetrofitPromise<>();
        UserCardService userCardService = this.getUpholdRestAdapter().create(UserCardService.class);

        if (TextUtils.isEmpty(this.getOrigin().getCardId())) {
            promise.reject(new LogicException("Origin CardId is missing from this transaction"));

            return promise;
        }

        if (this.getStatus().compareTo("pending") != 0) {
            promise.reject(new LogicException(String.format("This transaction cannot be committed, because the current status is %s", this.getStatus())));

            return promise;
        }

        if (transactionCommitRequest == null) {
            transactionCommitRequest = new TransactionCommitRequest(null);
        }

        userCardService.confirmTransaction(this.getOrigin().getCardId(), this.getId(), transactionCommitRequest, promise);

        return promise;
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
     * Gets the transaction fees.
     *
     * @return the transaction fees.
     */

    public List<Fee> getFees() {
        return fees;
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
     * Gets the normalized transaction information.
     *
     * @return the normalized information of the transaction.
     */

    public List<Normalized> getNormalized() {
        return normalized;
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
        return RefundedById;
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
