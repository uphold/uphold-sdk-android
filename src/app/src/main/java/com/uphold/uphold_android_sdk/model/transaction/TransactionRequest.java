package com.uphold.uphold_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Transaction request model.
 */

public abstract class TransactionRequest implements Serializable {

    private final TransactionDenominationRequest denomination;

    /**
     * Constructor.
     *
     * @param denomination The {@link TransactionDenominationRequest} of the transaction request.
     */

    public TransactionRequest(TransactionDenominationRequest denomination) {
        this.denomination = denomination;
    }

    /**
     * Gets the denomination of the transaction request.
     *
     * @return the {@link TransactionDenominationRequest} of the transaction request
     */

    public TransactionDenominationRequest getDenomination() {
        return denomination;
    }

}
