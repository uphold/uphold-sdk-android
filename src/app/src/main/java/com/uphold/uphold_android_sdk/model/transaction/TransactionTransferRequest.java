package com.uphold.uphold_android_sdk.model.transaction;

/**
 * Transaction destination request model.
 */

public class TransactionTransferRequest extends TransactionRequest {

    private final String destination;
    private final String reference;

    /**
     * Constructor.
     *
     * @param denomination The {@link TransactionDenominationRequest} of the transaction request.
     * @param destination The destination of the transaction request.
     */

    public TransactionTransferRequest(TransactionDenominationRequest denomination, String destination) {
        super(denomination);

        this.destination = destination;
        this.reference = null;
    }

    /**
     * Constructor.
     *
     * @param denomination The {@link TransactionDenominationRequest} of the transaction request.
     * @param destination The destination of the transaction request.
     * @param reference The reference of the transaction request.
     */

    public TransactionTransferRequest(TransactionDenominationRequest denomination, String destination, String reference) {
        super(denomination);

        this.destination = destination;
        this.reference = reference;
    }

    /**
     * Gets the destination of the transaction request.
     *
     * @return the destination of the transaction request.
     */

    public String getDestination() {
        return destination;
    }

    /**
     * Gets the reference of the transaction request.
     *
     * @return the reference of the transaction request.
     */

    public String getReference() {
        return reference;
    }

}
