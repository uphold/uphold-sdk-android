package com.uphold.uphold_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Transaction normalized model.
 */

public class Normalized implements Serializable {

    private final String amount;
    private final String commission;
    private final String currency;
    private final String fee;
    private final String rate;

    /**
     * Constructor.
     *
     * @param amount The amount of the transaction normalized.
     * @param commission The commission of the transaction normalized.
     * @param currency The currency of the transaction normalized.
     * @param fee The fee of the transaction normalized.
     * @param rate The rate of the transaction normalized.
     */

    public Normalized(String amount, String commission, String currency, String fee, String rate) {
        this.amount = amount;
        this.commission = commission;
        this.currency = currency;
        this.fee = fee;
        this.rate = rate;
    }

    /**
     * Gets the amount of the transaction normalized.
     *
     * @return the amount of the transaction normalized.
     */

    public String getAmount() {
        return amount;
    }

    /**
     * Gets the commission of the transaction normalized.
     *
     * @return the commission of the transaction normalized.
     */

    public String getCommission() {
        return commission;
    }

    /**
     * Gets the currency of the transaction normalized.
     *
     * @return the commission of the transaction normalized.
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the fee of the transaction normalized.
     *
     * @return the fee of the transaction normalized.
     */

    public String getFee() {
        return fee;
    }

    /**
     * Gets the rate of the transaction normalized.
     *
     * @return the rate of the transaction normalized.
     */

    public String getRate() {
        return rate;
    }

}
