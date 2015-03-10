package org.bitreserve.bitreserve_android_sdk.model.transaction;

/**
 * This class represents the transaction request model.
 */

public class TransactionRequest {

    private final String amount;
    private final String currency;
    private final String destination;

    /**
     * Constructor.
     *
     * @param amount The amount of the transaction request.
     * @param currency The currency of the transaction request.
     * @param destination The destination of the transaction request.
     */

    public TransactionRequest(String amount, String currency, String destination) {
        this.amount = amount;
        this.currency = currency;
        this.destination = destination;
    }

    /**
     * Gets the amount of the transaction request.
     *
     * @return the amount of the transaction request
     */

    public String getAmount() {
        return amount;
    }


    /**
     * Gets the currency of the transaction request.
     *
     * @return the currency of the transaction request
     */

    public String getCurrency() {
        return currency;
    }


    /**
     * Gets the destination of the transaction request.
     *
     * @return the destination of the transaction request
     */

    public String getDestination() {
        return destination;
    }
}
