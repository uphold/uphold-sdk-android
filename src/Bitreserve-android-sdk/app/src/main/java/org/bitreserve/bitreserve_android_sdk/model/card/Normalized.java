package org.bitreserve.bitreserve_android_sdk.model.card;

import java.io.Serializable;

/**
 * Card normalized model.
 */

public class Normalized implements Serializable {

    private final String available;
    private final String balance;
    private final String currency;

    /**
     * Constructor.
     *
     * @param available The amount available normalized.
     * @param balance The balance available normalized.
     * @param currency The currency used in the normalization.
     */

    public Normalized(String available, String balance, String currency) {
        this.available = available;
        this.balance = balance;
        this.currency = currency;
    }

    /**
     * Gets the amount available.
     *
     * @return The amount available
     */

    public String getAvailable() {
        return available;
    }

    /**
     * Gets the balance.
     *
     * @return The balance available
     */

    public String getBalance() {
        return balance;
    }

    /**
     * Gets the currency.
     *
     * @return The currency used in the normalization
     */

    public String getCurrency() {
        return currency;
    }

}
