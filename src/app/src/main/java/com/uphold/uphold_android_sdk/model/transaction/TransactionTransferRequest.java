package com.uphold.uphold_android_sdk.model.transaction;

/**
 * Transaction destination request model.
 */

public class TransactionTransferRequest extends TransactionRequest {

    private final String destination;

    /**
     * Constructor.
     *
     * @param denomination The {@link TransactionDenominationRequest} of the transaction request.
     * @param destination The destination of the transaction request.
     */

    public TransactionTransferRequest(TransactionDenominationRequest denomination, String destination) {
        super(denomination);

        this.destination = destination;
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
