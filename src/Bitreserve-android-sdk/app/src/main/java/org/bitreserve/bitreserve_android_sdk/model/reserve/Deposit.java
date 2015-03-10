package org.bitreserve.bitreserve_android_sdk.model.reserve;

/**
 * This class represents the deposit model.
 */

public class Deposit {

    private final String type;
    private final DepositMovement out;
    private final DepositMovement in;
    private final String createdAt;
    private final String transactionId;

    /**
     * Constructor.
     *
     * @param type The type of the deposit.
     * @param out The deposit out movement.
     * @param in The deposit in movement.
     * @param createdAt The date when the transaction was created.
     * @param transactionId The id of the transaction.
     */

    public Deposit(String type, DepositMovement out, DepositMovement in, String createdAt, String transactionId) {
        this.type = type;
        this.out = out;
        this.in = in;
        this.createdAt = createdAt;
        this.transactionId = transactionId;
    }

    /**
     * Gets the type of the transaction.
     *
     * @return the type of the transaction
     */

    public String getType() {
        return type;
    }

    /**
     * Gets the out deposit movement
     *
     * @return the out {@link DepositMovement}
     */

    public DepositMovement getOut() {
        return out;
    }

    /**
     * Gets the in deposit movement
     *
     * @return the in {@link DepositMovement}
     */

    public DepositMovement getIn() {
        return in;
    }

    /**
     * Gets the date when the deposit was created
     *
     * @return the date when the deposit was created
     */

    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Gets the transaction id
     *
     * @return the transaction id
     */

    public String getTransactionId() {
        return transactionId;
    }
}
