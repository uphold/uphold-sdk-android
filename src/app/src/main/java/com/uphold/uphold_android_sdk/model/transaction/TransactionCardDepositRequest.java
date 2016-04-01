package com.uphold.uphold_android_sdk.model.transaction;

/**
 * Transaction card deposit request model.
 */

public class TransactionCardDepositRequest extends TransactionDepositRequest {

    private final String securityCode;

    /**
     * Constructor.
     *
     * @param denomination The {@link TransactionDenominationRequest} of the transaction request.
     * @param origin The origin of the transaction request in the case of a deposit.
     * @param securityCode The card's security code.
     */
    public TransactionCardDepositRequest(TransactionDenominationRequest denomination, String origin, String securityCode) {
        super(denomination, origin);

        this.securityCode = securityCode;
    }

    /**
     * Gets the card's security code.
     *
     * @return the card security code.
     */

    public String getSecurityCode() {
        return securityCode;
    }

}
