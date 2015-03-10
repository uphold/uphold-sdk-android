package org.bitreserve.bitreserve_android_sdk.model;

import org.bitreserve.bitreserve_android_sdk.model.transaction.Denomination;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Destination;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Origin;
import org.bitreserve.bitreserve_android_sdk.model.transaction.Parameters;

/**
 * This class represents the transaction model.
 */

public class Transaction {

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
