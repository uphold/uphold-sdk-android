package org.bitreserve.bitreserve_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Transaction request model.
 */

public class TransactionRequest implements Serializable {

    private final TransactionDenominationRequest denomination;
    private final String destination;

    /**
     * Constructor.
     *
     * @param denomination The {@link TransactionDenominationRequest} of the transaction request.
     * @param destination The destination of the transaction request.
     */

    public TransactionRequest(TransactionDenominationRequest denomination, String destination) {
        this.denomination = denomination;
        this.destination = destination;
    }

    /**
     * Gets the denomination of the transaction request.
     *
     * @return the {@link TransactionDenominationRequest} of the transaction request
     */

    public TransactionDenominationRequest getDenomination() {
        return denomination;
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
