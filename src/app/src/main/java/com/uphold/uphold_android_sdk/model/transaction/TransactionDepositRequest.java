package com.uphold.uphold_android_sdk.model.transaction;

/**
 * Transaction deposit request model.
 */

public class TransactionDepositRequest extends TransactionRequest {

    private final String origin;

    /**
     * Constructor.
     *
     * @param denomination The {@link TransactionDenominationRequest} of the transaction request.
     * @param origin The origin of the transaction request in the case of a deposit.
     */

    public TransactionDepositRequest(TransactionDenominationRequest denomination, String origin) {
        super(denomination);

        this.origin = origin;
    }

    /**
     * Gets the origin of the transaction request.
     *
     * @return the origin of the transaction request
     */

    public String getOrigin() {
        return origin;
    }

}
