package com.uphold.uphold_android_sdk.model.transaction;

import java.io.Serializable;

/**
 * Fee model.
 */

public class Fee implements Serializable {

    private final String amount;
    private final String currency;
    private final String percentage;
    private final String target;
    private final String type;

    /**
     * Constructor.
     *
     * @param amount The amount.
     * @param currency The currency.
     * @param percentage The percentage.
     * @param target The target.
     * @param type The type.
     */

    public Fee(String amount, String currency, String percentage, String target, String type) {
        this.amount = amount;
        this.currency = currency;
        this.percentage = percentage;
        this.target = target;
        this.type = type;
    }

    /**
     * Gets the amount.
     *
     * @return the amount.
     */

    public String getAmount() {
        return amount;
    }

    /**
     * Gets the currency.
     *
     * @return the currency.
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Gets the percentage.
     *
     * @return the percentage.
     */

    public String getPercentage() {
        return percentage;
    }

    /**
     * Gets the target.
     *
     * @return the target,
     */

    public String getTarget() {
        return target;
    }

    /**
     * Gets the type.
     *
     * @return the type.
     */

    public String getType() {
        return type;
    }

}
