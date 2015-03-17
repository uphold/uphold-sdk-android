package org.bitreserve.bitreserve_android_sdk.model.reserve;

/**
 * Deposit movement model.
 */

public class DepositMovement {

    public final String amount;
    public final String currency;

    /**
     * Constructor.
     *
     * @param amount The amount of the deposit movement.
     * @param currency The currency of the deposit movement.
     */

    public DepositMovement(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Gets the amount of the deposit movement.
     *
     * @return the amount of the deposit movement
     */

    public String getAmount() {
        return amount;
    }

    /**
     * Gets the currency of the deposit movement.
     *
     * @return the currency of the deposit movement
     */

    public String getCurrency() {
        return currency;
    }

}
